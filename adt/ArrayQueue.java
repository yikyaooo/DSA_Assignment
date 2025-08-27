/**
 *
 * @author GAN WEI JIAN
 */
package adt;

import java.util.NoSuchElementException;
import entity.Event;
import java.util.Iterator;

public class ArrayQueue<T> implements ArrayQueueInterface<T> {
    private static final int DEFAULT_CAPACITY = 50;
    private T[] queue;
    private int front;
    private int rear;
    private int size;


    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        queue = (T[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = -1;
        size = 0;
    }

    @Override
    public void enqueue(T newEntry) {
        if (size == queue.length) {
            ensureCapacity();
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = newEntry;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null; // Return null if the queue is empty
        }
        T frontEntry = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        size--;
        return frontEntry;
    }

    @Override
    public T getFront() {
        if (isEmpty()) {
            return null; // Return null if the queue is empty
        }
        return queue[front];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    private void ensureCapacity() {
        T[] oldQueue = queue;
        int oldSize = oldQueue.length;
        queue = (T[]) new Object[oldSize * 2];
        System.arraycopy(oldQueue, 0, queue, 0, oldSize);

    }
    
    @Override
        public int size() {
        return size;
    }

    // Method to get the element at a specific index
    @Override
    public T get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        int index = (front + i) % queue.length;
        return queue[index];
    }

    @Override
public Iterator<T> iterator() {
    return new Iterator<T>() {
        private int current = front;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = queue[current];
            current = (current + 1) % queue.length;
            count++;
            return item;
        }
    };
  }
}
