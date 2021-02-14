package net.eucalypto.stack;

public class Stack {
    private int size = 0;
    private int capacity;
    private int[] elements;

    private Stack(int capacity) {
        this.capacity = capacity;
        elements = new int[capacity];
    }

    public static Stack Make(int capacity) {
        if (capacity < 0)
            throw new IllegalCapacity();

        return new Stack(capacity);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void push(int element) {
        if (size == capacity)
            throw new Overflow();

        elements[size] = element;
        size++;
    }

    public int pop() {
        if (size == 0)
            throw new Underflow();

        int currentElement = elements[size - 1];
        size--;
        return currentElement;
    }

    public static class Overflow extends RuntimeException {
    }

    public static class Underflow extends RuntimeException {
    }

    public static class IllegalCapacity extends RuntimeException {
    }
}
