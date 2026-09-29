public final class DoublyLinkedListWithTail<T> extends AbstractLinkedList<T> {
    private static final class Node<E> implements Position<E>{E value;Node<E> prev,next;Node(E value){this.value=value;}public E value(){return value;}}
    private Node<T> head,tail;
    @Override public void pushFront(T value){requireNonNull(value,"value");Node<T> n=new Node<>(value);n.next=head;if(head!=null)head.prev=n;else tail=n;head=n;size++;}
    @Override public void pushBack(T value){requireNonNull(value,"value");Node<T> n=new Node<>(value);n.prev=tail;if(tail!=null)tail.next=n;else head=n;tail=n;size++;}
    @Override public T popFront(){if(head==null)throw empty();T v=head.value;head=head.next;if(head!=null)head.prev=null;else tail=null;size--;return v;}
    @Override public T popBack(){if(tail==null)throw empty();T v=tail.value;tail=tail.prev;if(tail!=null)tail.next=null;else head=null;size--;return v;}
    @Override public Position<T> find(T value){requireNonNull(value,"value");for(Node<T> p=head;p!=null;p=p.next)if(p.value.equals(value))return p;return null;}
    @Override public boolean erase(T value){requireNonNull(value,"value");Node<T> p=head;while(p!=null&&!p.value.equals(value))p=p.next;if(p==null)return false;unlink(p);return true;}
    private void unlink(Node<T> p){if(p.prev==null)head=p.next;else p.prev.next=p.next;if(p.next==null)tail=p.prev;else p.next.prev=p.prev;size--;}
    @Override public boolean addBefore(T target,T value){requireNonNull(target,"target");requireNonNull(value,"value");Node<T> p=head;while(p!=null&&!p.value.equals(target))p=p.next;if(p==null)return false;Node<T> n=new Node<>(value);n.prev=p.prev;n.next=p;if(p.prev!=null)p.prev.next=n;else head=n;p.prev=n;size++;return true;}
    @Override public boolean addAfter(T target,T value){requireNonNull(target,"target");requireNonNull(value,"value");Node<T> p=head;while(p!=null&&!p.value.equals(target))p=p.next;if(p==null)return false;Node<T> n=new Node<>(value);n.prev=p;n.next=p.next;p.next=n;if(n.next!=null)n.next.prev=n;else tail=n;size++;return true;}
    @Override public T front(){if(head==null)throw empty();return head.value;}
    @Override public T back(){if(tail==null)throw empty();return tail.value;}
}
