public interface ListADT<T> {
    void pushFront(T value);
    void pushBack(T value);
    T popFront();
    T popBack();
    Position<T> find(T value);
    boolean erase(T value);
    boolean addBefore(T target, T value);
    boolean addAfter(T target, T value);
    boolean isEmpty();
    int size();
    T front();
    T back();
}
