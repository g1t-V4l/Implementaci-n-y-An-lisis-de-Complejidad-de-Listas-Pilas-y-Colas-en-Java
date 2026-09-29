
public final class DoublyLinkedListNoTail<T> extends AbstractLinkedList<T> {
    private static final class Node<E> implements Position<E>{ E value; Node<E> prev,next; Node(E value){this.value=value;} public E value(){return value;} }
    private Node<T> head;
    @Override public void pushFront(T value){requireNonNull(value,"value");Node<T> n=new Node<>(value);n.next=head;if(head!=null)head.prev=n;head=n;size++;}
    @Override public void pushBack(T value){requireNonNull(value,"value");Node<T> n=new Node<>(value);if(head==null){head=n;}else{Node<T> t=head;while(t.next!=null)t=t.next;t.next=n;n.prev=t;}size++;}
    @Override public T popFront(){if(head==null)throw empty();T v=head.value;head=head.next;if(head!=null)head.prev=null;size--;return v;}
    @Override public T popBack(){if(head==null)throw empty();Node<T> t=head;while(t.next!=null)t=t.next;T v=t.value;if(t.prev==null)head=null;else t.prev.next=null;size--;return v;}
    @Override public Position<T> find(T value){requireNonNull(value,"value");for(Node<T> p=head;p!=null;p=p.next)if(p.value.equals(value))return p;return null;}
    @Override public boolean erase(T value){requireNonNull(value,"value");Node<T> p=head;while(p!=null&&!p.value.equals(value))p=p.next;if(p==null)return false;unlink(p);return true;}
    private void unlink(Node<T> p){if(p.prev==null)head=p.next;else p.prev.next=p.next;if(p.next!=null)p.next.prev=p.prev;size--;}
    @Override public boolean addBefore(T target,T value){requireNonNull(target,"target");requireNonNull(value,"value");Node<T> p=head;while(p!=null&&!p.value.equals(target))p=p.next;if(p==null)return false;Node<T> n=new Node<>(value);n.prev=p.prev;n.next=p;if(p.prev!=null)p.prev.next=n;else head=n;p.prev=n;size++;return true;}
    @Override public boolean addAfter(T target,T value){requireNonNull(target,"target");requireNonNull(value,"value");Node<T> p=head;while(p!=null&&!p.value.equals(target))p=p.next;if(p==null)return false;Node<T> n=new Node<>(value);n.next=p.next;n.prev=p;p.next=n;if(n.next!=null)n.next.prev=n;size++;return true;}
    @Override public T front(){if(head==null)throw empty();return head.value;}
    @Override public T back(){if(head==null)throw empty();Node<T> t=head;while(t.next!=null)t=t.next;return t.value;}
}
