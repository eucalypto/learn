package net.eucalypto.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    private Stack stack;

    @BeforeEach
    void setUp() {
        stack = BoundedStack.Make(2);
    }

    @Test
    void newlyCreatedStack_IsEmpty() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.getSize());
    }

    @Test
    void afterOnePush_StackSizeIsOne() {
        stack.push(0);
        assertEquals(1, stack.getSize());
        assertFalse(stack.isEmpty());
    }

    @Test
    void afterOnePushAndOnePop_IsEmpty() {
        stack.push(1);
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    void whenPushedPastLimit_StackOverflows() {
        stack.push(1);
        stack.push(1);

        assertThrows(Stack.Overflow.class, () ->
                stack.push(1));
    }

    @Test
    void whenEmptyStackIsPopped_ShouldThrowUnderflow() {
        assertThrows(Stack.Underflow.class, () ->
                stack.pop());
    }

    @Test
    void whenOneIsPushed_OneIsPopped() {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @Test
    void whenOneAndTwoArePushed_TwoAndOneArePopped() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    void whenCreatingStackWithNegativeSize_ThrowIllegalCapacity() {
        assertThrows(Stack.IllegalCapacity.class, () ->
                BoundedStack.Make(-1));
    }

    @Test
    void whenCreatingStackWithZeroCapacity_AnyPushShouldOverflow() {
        stack = BoundedStack.Make(0);
        assertThrows(BoundedStack.Overflow.class, () ->
                stack.push(1));
    }

    @Test
    void whenOneIsPushed_OneIsOnTop() {
        stack.push(1);
        assertEquals(1, stack.top());
    }

    @Test
    void whenStackIsEmpty_topThrowsEmpty() {
        assertThrows(Stack.Empty.class, () ->
                stack.top());
    }

    @Test
    void withZeroCapacityStack_topThrowsEmpty() {
        stack = BoundedStack.Make(0);
        assertThrows(Stack.Empty.class, () ->
                stack.top());
    }

    @Test
    void givenStackWithOneTwoPushed_findOneAndTwo() {
        stack.push(1);
        stack.push(2);
        assertEquals(1, stack.find(1));
        assertEquals(0, stack.find(2));
    }

    @Test
    void givenStackWithNo2_findReturnsNull() {
        assertNull(stack.find(2));
    }

}
