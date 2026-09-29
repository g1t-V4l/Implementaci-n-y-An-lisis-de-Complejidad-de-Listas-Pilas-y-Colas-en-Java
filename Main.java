
public final class Main {
    public static void main(String[] args) {
        ListADT<Integer> list = new DoublyLinkedListWithTail<>();
        list.pushFront(2); list.pushBack(4); list.addAfter(2, 3); list.addBefore(2, 1);
        System.out.println("List: " + list.front() + " .. " + list.back() + ", size=" + list.size());
        list.erase(3); System.out.println("Find 4: " + (list.find(4) != null));

        MyStack<Integer> stack = new DynamicCircularStack<>();
        stack.push(10); stack.push(20); System.out.println("Stack peek=" + stack.peek() + ", pop=" + stack.pop());

        MyQueue<Integer> queue = new DynamicCircularQueue<>();
        queue.enqueue(10); queue.enqueue(20); System.out.println("Queue front=" + queue.front() + ", dequeue=" + queue.dequeue());
    }
}
