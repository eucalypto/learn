package net.eucalypto.stack;

public class BoundedStack implements Stack {
    private int size = 0;
    private int capacity;
    private int[] elements;

    private BoundedStack(int capacity) {
        this.capacity = capacity;
        elements = new int[capacity];
    }

    public static Stack Make(int capacity) {
        if (capacity < 0)
            throw new IllegalCapacity();
        if (capacity == 0)
            return new ZeroCapacityStack();

        return new BoundedStack(capacity);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void push(int element) {
        if (size == capacity)
            throw new Overflow();

        elements[size] = element;
        size++;
    }

    @Override
    public int pop() {
        if (isEmpty())
            throw new Underflow();

        int currentElement = elements[size - 1];
        size--;
        return currentElement;
    }

    @Override
    public int top() {
        if (isEmpty())
            throw new Empty();

        return elements[size - 1];
    }

    @Override
    public Integer find(int element) {
        for (int i = 0; i < size; i++)
            if (elements[size - 1 - i] == element)
                return i;

        return null;
    }

    private static class ZeroCapacityStack implements Stack {
        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public void push(int element) {
            throw new Overflow();
        }

        @Override
        public int pop() {
            throw new Underflow();
        }

        @Override
        public int top() {
            throw new Empty();
        }

        @Override
        public Integer find(int element) {
            return null;
        }
    }
}
