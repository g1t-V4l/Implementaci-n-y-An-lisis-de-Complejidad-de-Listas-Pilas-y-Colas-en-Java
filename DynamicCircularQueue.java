import java.util.NoSuchElementException;

public final class DynamicCircularQueue<T> implements MyQueue<T> {
    private Object[] data;
    private int head;
    private int size;
    private static final int MIN_CAPACITY = 8;

    public DynamicCircularQueue(){this(MIN_CAPACITY);}
    public DynamicCircularQueue(int capacity){if(capacity<1)throw new IllegalArgumentException("capacity must be positive");data=new Object[Math.max(MIN_CAPACITY,capacity)];}
    private int index(int logical){return (head+logical)%data.length;}
    private void grow(){resize(data.length*2);}
    private void resize(int capacity){Object[] next=new Object[capacity];for(int i=0;i<size;i++)next[i]=data[index(i)];data=next;head=0;}
    @Override public void enqueue(T x){if(x==null)throw new NullPointerException("x cannot be null");if(size==data.length)grow();data[index(size)]=x;size++;}
    @SuppressWarnings("unchecked") @Override public T dequeue(){if(size==0)throw new NoSuchElementException("queue is empty");T x=(T)data[head];data[head]=null;head=(head+1)%data.length;size--;if(size==0)head=0;return x;}
    @SuppressWarnings("unchecked") @Override public T front(){if(size==0)throw new NoSuchElementException("queue is empty");return (T)data[head];}
    @Override public boolean isEmpty(){return size==0;}
    @Override public int size(){return size;}
    @Override public boolean delete(T value){if(value==null)throw new NullPointerException("value cannot be null");for(int i=0;i<size;i++)if(data[index(i)].equals(value)){for(int j=i;j<size-1;j++)data[index(j)]=data[index(j+1)];data[index(size-1)]=null;size--;if(size==0)head=0;return true;}return false;}
    public int capacity(){return data.length;}
}
