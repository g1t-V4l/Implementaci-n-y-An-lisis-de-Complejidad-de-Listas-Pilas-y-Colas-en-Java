public final class SinglyLinkedListNoTail<T> extends AbstractLinkedList<T> {
    private static final class Node<E> implements Position<E> {
        E value; Node<E> next;
        Node(E value) { this.value = value; }
        public E value() { return value; }
    }
    private Node<T> head;

    @Override public void pushFront(T value) {
        requireNonNull(value, "value"); Node<T> n = new Node<>(value); n.next = head; head = n; size++;
    }
    @Override public void pushBack(T value) {
        requireNonNull(value, "value"); Node<T> n = new Node<>(value);
        if (head == null) head = n; else { Node<T> p = head; while (p.next != null) p = p.next; p.next = n; }
        size++;
    }
    @Override public T popFront() {
        if (head == null) throw empty(); T v = head.value; head = head.next; size--; return v;
    }
    @Override public T popBack() {
        if (head == null) throw empty();
        if (head.next == null) { T v = head.value; head = null; size--; return v; }
        Node<T> p = head; while (p.next.next != null) p = p.next; T v = p.next.value; p.next = null; size--; return v;
    }
    @Override public Position<T> find(T value) {
        requireNonNull(value, "value"); for (Node<T> p = head; p != null; p = p.next) if (p.value.equals(value)) return p; return null;
    }
    @Override public boolean erase(T value) {
        requireNonNull(value, "value");
        if (head == null) return false;
        if (head.value.equals(value)) { head = head.next; size--; return true; }
        Node<T> p = head; while (p.next != null && !p.next.value.equals(value)) p = p.next;
        if (p.next == null) return false; p.next = p.next.next; size--; return true;
    }
    @Override public boolean addBefore(T target, T value) {
        requireNonNull(target, "target"); requireNonNull(value, "value");
        if (head == null) return false;
        if (head.value.equals(target)) { pushFront(value); return true; }
        Node<T> p = head; while (p.next != null && !p.next.value.equals(target)) p = p.next;
        if (p.next == null) return false; Node<T> n = new Node<>(value); n.next = p.next; p.next = n; size++; return true;
    }
    @Override public boolean addAfter(T target, T value) {
        requireNonNull(target, "target"); requireNonNull(value, "value");
        for (Node<T> p = head; p != null; p = p.next) if (p.value.equals(target)) { Node<T> n = new Node<>(value); n.next = p.next; p.next = n; size++; return true; }
        return false;
    }
    @Override public T front() { if (head == null) throw empty(); return head.value; }
    @Override public T back() { if (head == null) throw empty(); Node<T> p = head; while (p.next != null) p = p.next; return p.value; }
}
