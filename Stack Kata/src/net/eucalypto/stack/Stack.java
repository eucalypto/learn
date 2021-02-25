package net.eucalypto.stack;

public interface Stack {
    boolean isEmpty();

    int getSize();

    void push(int element);

    int pop();

    int top();

    Integer find(int element);

    class Overflow extends RuntimeException {
    }

    class Underflow extends RuntimeException {
    }

    class IllegalCapacity extends RuntimeException {
    }

    class Empty extends RuntimeException {
    }
}
