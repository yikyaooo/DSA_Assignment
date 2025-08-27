/**
 *
 * @author raphael
 */
package adt;

import java.util.Iterator;

public class CircularLinkedList<T> implements CircularLinkedListInterface<T> {

    private Node firstNode;
    private Node lastNode;
    private int numofEntry;

    public CircularLinkedList() {
        firstNode = null;
        lastNode = null;
        numofEntry = 0;

    }

    @Override
    public boolean add(T newEntry) {
        Node currentNode = new Node(newEntry);
        if (this.isEmpty()) {
            firstNode = currentNode;
            lastNode = currentNode;
            lastNode.next = firstNode;
        } else {
            currentNode.next = lastNode.next;
            lastNode.next = currentNode;
            lastNode = currentNode;
        }
        numofEntry++;
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        Node currentNode = new Node(newEntry);
        if (newPosition > numofEntry + 1 || newPosition < 1) {
            return false;
        } else {
            if (newPosition == 1) {
                if (this.isEmpty()) {
                    firstNode = currentNode;
                    lastNode = currentNode;
                    lastNode.next = firstNode;
                } else {
                    currentNode.next = firstNode;
                    firstNode = currentNode;
                    lastNode.next = firstNode;
                }
            } else if (newPosition == numofEntry + 1) {
                currentNode.next = lastNode.next;
                lastNode.next = currentNode;
                lastNode = currentNode;
            } else {
                Node nodeBefore = firstNode;
                for (int i = 1; i < newPosition - 1; i++) {
                    nodeBefore = nodeBefore.next;
                }
                currentNode.next = nodeBefore.next;
                nodeBefore.next = currentNode;
            }
        }
        numofEntry++;
        return true;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if (givenPosition > numofEntry || givenPosition < 1) {
            return result;
        } else if (givenPosition == 1) {
            result = firstNode.data;
            if (numofEntry == 1) {
                firstNode = null;
                lastNode = null;
            } else {
                firstNode = firstNode.next;
                lastNode.next = firstNode;
            }
        } else {
            Node nodeBefore = firstNode;
            for (int i = 1; i < givenPosition - 1; i++) {
                nodeBefore = nodeBefore.next;
            }
            result = nodeBefore.next.data;
            nodeBefore.next = nodeBefore.next.next;
            if (givenPosition == numofEntry) {
                lastNode = nodeBefore;
            }
        }
        numofEntry--;
        return result;
    }

    @Override
    public boolean delete(T entry) {
        boolean deleted = false;

        // Handle the case where the list is empty
        if (isEmpty()) {
            return deleted;
        }

        // Special case for removing the first node
        if (firstNode.data.equals(entry)) {
            if (numofEntry == 1) {
                // If there is only one node, reset the list
                firstNode = null;
                lastNode = null;
            } else {
                firstNode = firstNode.next;
                lastNode.next = firstNode;
            }
            numofEntry--;
            deleted = true;
        } else {
            // Find the node to remove
            Node currentNode = firstNode;
            Node nodeBefore = null;

            do {
                nodeBefore = currentNode;
                currentNode = currentNode.next;

                // If the node is found
                if (currentNode.data.equals(entry)) {
                    nodeBefore.next = currentNode.next;

                    // Update lastNode if the last node is being removed
                    if (currentNode == lastNode) {
                        lastNode = nodeBefore;
                    }

                    numofEntry--;
                    deleted = true;
                    break;
                }
            } while (currentNode != firstNode);
        }

        return deleted;
    }

    @Override
    public void clear() {
        if (!this.isEmpty()) {
            Node currentNode = firstNode;
            Node nextNode;

            do {
                nextNode = currentNode.next;
                currentNode.next = null;
                currentNode = nextNode;
            } while (currentNode != firstNode);
            firstNode = null;
            lastNode = null;
        }
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        // Check if the position is valid
        if (givenPosition < 1 || givenPosition > numofEntry) {
            return false;
        }

        Node currentNode = firstNode;

        // Traverse to the node at the given position
        for (int i = 1; i < givenPosition; i++) {
            currentNode = currentNode.next;
        }

        // Replace the data of the node
        currentNode.data = newEntry;

        return true;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;
        if (givenPosition < 1 || givenPosition > numofEntry) {
            return result;
        }

        Node currentNode = firstNode;
        for (int i = 1; i < givenPosition; i++) {
            currentNode = currentNode.next;
        }
        result = currentNode.data;
        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        Node currentNode = firstNode;
        for (int i = 1; i <= numofEntry; i++) {
            if (currentNode.data.equals(anEntry)) {
                return true;
            }
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public int getNumberOfEntries() {
        return numofEntry;
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public Iterator<T> getIterator() {
        return new CircularListIterator();
    }

    private class CircularListIterator implements Iterator<T> {

    private Node currentNode;
    private int currentPosition;

    // Constructor to initialize the iterator state
    public CircularListIterator() {
        this.currentNode = firstNode;
        this.currentPosition = 0;
    }

    @Override
    public boolean hasNext() {
        // Only iterate up to the number of entries, respecting the circular list
        return currentPosition < numofEntry;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException("No more elements in the list.");
        }
        T data = currentNode.data;
        currentNode = currentNode.next; // Move to the next node, respecting circular nature
        currentPosition++;
        return data;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove operation is not supported.");
    }
}


    private class Node {

        private T data; //the value
        private Node next; //the cnnector or pointer

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
