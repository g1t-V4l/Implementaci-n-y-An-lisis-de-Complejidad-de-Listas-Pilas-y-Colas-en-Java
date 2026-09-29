import java.io.*;
import java.util.*;
import java.util.function.Supplier;


public final class BenchmarkMain {
    private record Row(String group, String implementation, String operation, int n, double nanos) {}
    private static final int[] SIZES = {10, 100, 1_000, 10_000, 100_000, 1_000_000};
    private static final int REPETITIONS = 3;
    private static final int BATCH = 1_000;
    private static volatile int SINK;

    private static long time(Runnable op) { long start=System.nanoTime(); op.run(); return System.nanoTime()-start; }
    private static double median(double[] a){Arrays.sort(a);return a[a.length/2];}

    private static <T extends ListADT<Integer>> double measureList(Supplier<T> factory, String op, int n){
        double[] samples=new double[REPETITIONS];
        boolean batch = op.equals("pushFront") || op.equals("pushBack") || op.equals("popFront") || op.equals("popBack") || op.equals("find");
        int repeats = batch ? BATCH : 1;
        for(int r=0;r<REPETITIONS;r++){
            T list=factory.get();
            int initial = (op.equals("popFront") || op.equals("popBack")) ? n + repeats : n;
            for(int i=0;i<initial;i++) list.pushFront(i);
            int target=new Random(2026L + n*31L + op.hashCode() + r).nextInt(n);
            samples[r]=time(()->{
                for(int k=0;k<repeats;k++){
                    switch(op){
                        case "pushFront" -> list.pushFront(-k-1);
                        case "pushBack" -> list.pushBack(-k-1);
                        case "popFront" -> list.popFront();
                        case "popBack" -> list.popBack();
                        case "find" -> list.find(target);
                        case "erase" -> list.erase(target);
                        case "addBefore" -> list.addBefore(target,-k-1);
                        case "addAfter" -> list.addAfter(target,-k-1);
                        default -> throw new IllegalArgumentException(op);
                    }
                }
            });
            SINK = list.size();
            samples[r] = samples[r] / repeats;
        }
        return median(samples);
    }

    private static double measureStack(String op,int n){
        double[] samples=new double[REPETITIONS];
        boolean batch=op.equals("push")||op.equals("pop")||op.equals("peek"); int repeats=batch?BATCH:1;
        for(int r=0;r<REPETITIONS;r++){
            DynamicCircularStack<Integer> s=new DynamicCircularStack<>(n+repeats);
            int initial=op.equals("pop")?n+repeats:n; for(int i=0;i<initial;i++)s.push(i); int target=new Random(2026L + n*31L + op.hashCode() + r).nextInt(n);
            samples[r]=time(()->{for(int k=0;k<repeats;k++){switch(op){case "push"->s.push(-k-1);case "pop"->s.pop();case "peek"->s.peek();case "delete"->s.delete(target);}}}); SINK=s.size(); samples[r]/=repeats;
        }
        return median(samples);
    }

    private static double measureQueue(String op,int n){
        double[] samples=new double[REPETITIONS];
        boolean batch=op.equals("enqueue")||op.equals("dequeue")||op.equals("front"); int repeats=batch?BATCH:1;
        for(int r=0;r<REPETITIONS;r++){
            DynamicCircularQueue<Integer> q=new DynamicCircularQueue<>(n+repeats);
            int initial=op.equals("dequeue")?n+repeats:n; for(int i=0;i<initial;i++)q.enqueue(i); int target=new Random(2026L + n*31L + op.hashCode() + r).nextInt(n);
            samples[r]=time(()->{for(int k=0;k<repeats;k++){switch(op){case "enqueue"->q.enqueue(-k-1);case "dequeue"->q.dequeue();case "front"->q.front();case "delete"->q.delete(target);}}}); SINK=q.size(); samples[r]/=repeats;
        }
        return median(samples);
    }

    private static void write(String path,List<Row> rows)throws IOException{try(PrintWriter w=new PrintWriter(new BufferedWriter(new FileWriter(path)))){w.println("group,implementation,operation,n,nanoseconds");for(Row x:rows)w.printf(Locale.US,"%s,%s,%s,%d,%.3f%n",x.group,x.implementation,x.operation,x.n,x.nanos);}}

    public static void main(String[] args)throws Exception{
        String out=args.length>0?args[0]:"results/benchmark.csv"; List<Row> rows=new ArrayList<>();
        Map<String,Supplier<ListADT<Integer>>> lists=new LinkedHashMap<>();
        lists.put("SinglyNoTail",SinglyLinkedListNoTail::new); lists.put("SinglyWithTail",SinglyLinkedListWithTail::new);
        lists.put("DoublyNoTail",DoublyLinkedListNoTail::new); lists.put("DoublyWithTail",DoublyLinkedListWithTail::new);
        String[] listOps={"pushFront","pushBack","popFront","popBack","find","erase","addBefore","addAfter"};
        for(var e:lists.entrySet()) for(String op:listOps){
            int max=(op.equals("pushFront")||op.equals("pushBack")||op.equals("popFront")||op.equals("popBack"))?1_000_000:100_000;
            for(int n:SIZES) if(n<=max) rows.add(new Row("List",e.getKey(),op,n,measureList(e.getValue(),op,n)));
        }
        for(String op:new String[]{"push","pop","peek","delete"}) for(int n:SIZES) rows.add(new Row("Stack","DynamicCircularStack",op,n,measureStack(op,n)));
        for(String op:new String[]{"enqueue","dequeue","front","delete"}) for(int n:SIZES) rows.add(new Row("Queue","DynamicCircularQueue",op,n,measureQueue(op,n)));
        write(out,rows); System.out.println("Wrote "+rows.size()+" measurements to "+out);
    }
}
