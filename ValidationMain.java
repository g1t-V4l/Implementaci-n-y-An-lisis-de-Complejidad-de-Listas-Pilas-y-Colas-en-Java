
public final class ValidationMain {
    private static void check(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
    public static void main(String[] args) {
        java.util.List<ListADT<Integer>> lists = java.util.List.of(
            new SinglyLinkedListNoTail<>(), new SinglyLinkedListWithTail<>(),
            new DoublyLinkedListNoTail<>(), new DoublyLinkedListWithTail<>());
        for (ListADT<Integer> l : lists) {
            l.pushBack(2); l.pushFront(1); l.pushBack(4); check(l.addAfter(2,3), "addAfter"); check(l.addBefore(1,0), "addBefore");
            check(l.size()==5 && l.front()==0 && l.back()==4, "list state");
            check(l.find(3)!=null && l.erase(3) && l.size()==4, "find/erase");
            check(l.popFront()==0 && l.popBack()==4 && l.size()==2, "pops");
        }
        MyStack<Integer> s=new DynamicCircularStack<>(2); for(int i=0;i<100;i++)s.push(i); check(s.size()==100&&s.peek()==99,"stack growth"); check(s.delete(50),"stack delete"); for(int i=0;i<99;i++)s.pop(); check(s.isEmpty(),"stack empty");
        MyQueue<Integer> q=new DynamicCircularQueue<>(2); for(int i=0;i<100;i++)q.enqueue(i); check(q.front()==0,"queue front"); for(int i=0;i<30;i++)check(q.dequeue()==i,"queue order"); check(q.delete(50),"queue delete"); for(int i=30;i<100;i++)if(i!=50)q.dequeue(); check(q.isEmpty(),"queue empty");
        System.out.println("All validation assertions passed.");
    }
}
