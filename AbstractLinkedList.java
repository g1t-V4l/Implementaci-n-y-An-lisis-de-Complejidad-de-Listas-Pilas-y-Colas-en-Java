import java.util.NoSuchElementException;

public abstract class AbstractLinkedList<T> implements ListADT<T> {
    protected int size;

    protected static void requireNonNull(Object value, String name) {
        if (value == null) throw new NullPointerException(name + " cannot be null");
    }

    protected NoSuchElementException empty() {
        return new NoSuchElementException("The list is empty");
    }

    @Override public final boolean isEmpty() { return size == 0; }
    @Override public final int size() { return size; }
    @Override public abstract T front();
    @Override public abstract T back();
}
