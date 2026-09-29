import java.util.NoSuchElementException;

public final class DynamicCircularStack<T> implements MyStack<T> {
    private Object[] data;
    private int head;
    private int size;
    private static final int MIN_CAPACITY = 8;

    public DynamicCircularStack(){ this(MIN_CAPACITY); }
    public DynamicCircularStack(int capacity){ if(capacity<1)throw new IllegalArgumentException("capacity must be positive"); data=new Object[Math.max(MIN_CAPACITY,capacity)]; }
    private int index(int logical){ return (head+logical)%data.length; }
    private void grow(){ resize(data.length*2); }
    private void resize(int capacity){Object[] next=new Object[capacity];for(int i=0;i<size;i++)next[i]=data[index(i)];data=next;head=0;}
    @Override public void push(T x){if(x==null)throw new NullPointerException("x cannot be null");if(size==data.length)grow();data[index(size)]=x;size++;}
    @SuppressWarnings("unchecked") @Override public T pop(){if(size==0)throw new NoSuchElementException("stack is empty");int i=index(size-1);T x=(T)data[i];data[i]=null;size--;if(size==0)head=0;return x;}
    @SuppressWarnings("unchecked") @Override public T peek(){if(size==0)throw new NoSuchElementException("stack is empty");return (T)data[index(size-1)];}
    @Override public boolean isEmpty(){return size==0;}
    @Override public int size(){return size;}
    @Override public boolean delete(T value){if(value==null)throw new NullPointerException("value cannot be null");for(int i=0;i<size;i++)if(data[index(i)].equals(value)){for(int j=i;j<size-1;j++)data[index(j)]=data[index(j+1)];data[index(size-1)]=null;size--;return true;}return false;}
    public int capacity(){return data.length;}
}
