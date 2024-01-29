package org.leetcode.Solution;

import java.util.ArrayDeque;
import java.util.Deque;

class MyQueue {
    private Deque<Integer> front, back;

    public MyQueue() {
        front = new ArrayDeque<>();
        back = new ArrayDeque<>();
    }

    public void push(int x) {
        back.push(x);
    }

    private void populateFrontIfEmpty() {
        if (front.isEmpty()) {
            // transfer all elements from back to front.
            while (!back.isEmpty()) {
                front.push(back.pop());
            }
        }
    }

    public int pop() {
        populateFrontIfEmpty();
        // all calls will be valid.
        // Hence, do not need to handle the exception here.
        return front.pop();
    }

    public int peek() {
        populateFrontIfEmpty();
        return front.peek();
    }

    public boolean empty() {
        return (front.isEmpty() || back.isEmpty());
    }
}
