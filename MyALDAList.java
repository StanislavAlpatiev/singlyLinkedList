package ALDAList;

import java.util.Iterator;

public class MyALDAList<E> implements ALDAList<E> {
    private Node<E> firstNode;
    private Node<E> lastNode;
    private int listSize;
    private int modCount;

    public MyALDAList() {
        clear();
    }

    @Override
    public void add(E element) {
        if (size() == 0) {
            firstNode.nextNode = new Node<E>(element, lastNode);
            listSize++;
        } else {
            Node<E> tempNode = firstNode;
            for (int i = 0; i < size(); i++) {
                tempNode = tempNode.nextNode;
            }
            Node<E> newNode = new Node(element, lastNode);
            tempNode.nextNode = newNode;
            listSize++;
            modCount++;
        }
    }

    @Override
    public void add(int index, E element) {
        if (index == 0 && firstNode == null) {
            add(element);
        }

        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            Node<E> newNode = new Node(element, firstNode.nextNode);
            firstNode.nextNode = newNode;
            listSize++;
            modCount++;
            return;
        }

        Node<E> tempNode = getNodeBeforeIndex(index);
//        //sets tempNode to the node prior to index
//        for (int i = 0; i < index; i++) {
//            tempNode = tempNode.nextNode;
//        }
        Node<E> newNode = new Node(element, tempNode.nextNode);
        tempNode.nextNode = newNode;
        listSize++;
        modCount++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size() - 1) {
            throw new IndexOutOfBoundsException();
        }

        //This node is going to have the value of the node prior the index given as parameter
        Node<E> tempNode = getNodeBeforeIndex(index);

//        //sets tempnode to the note prior to index
//        for (int i = 0; i < index; i++) {
//            tempNode = tempNode.nextNode;
//        }

        //Data of Node at index
        E nodeAtIndexData = tempNode.nextNode.data;

        //TempNode is not prior to index, this makes it to that its next node is now the next node of node at index.
        tempNode.nextNode = tempNode.nextNode.nextNode;
        listSize--;
        modCount++;
        return nodeAtIndexData;
    }

    @Override
    public boolean remove(E element) {
        Node<E> tempNode = firstNode;
        //Loops temp node through the list indexes
        for (int i = 0; i < size(); i++) {
            //Peek the data on nextNode, if it matches the element - remove that node from list
            if (tempNode.nextNode.data == element) {
                tempNode.nextNode = tempNode.nextNode.nextNode;
                listSize--;
                modCount++;
                return true;
            } else {
                tempNode = tempNode.nextNode;
            }
        }
        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size() - 1 || size() == 0) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> tempNode = getNodeBeforeIndex(index);
//        //sets the tempNode to the node prior to index
//        for (int i = 0; i < index; i++) {
//            tempNode = tempNode.nextNode;
//        }
        return tempNode.nextNode.data;
    }



    @Override
    public boolean contains(E element) {
        if (size() == 0) {
            return false;
        } else {
            Node<E> tempNode = firstNode;
            for (int i = 0; i < size(); i++) {
                //Peek the data on nextNode
                if (tempNode.nextNode.data == element) {
                    return true;
                } else {
                    //Move tempNode 1 index forward
                    tempNode = tempNode.nextNode;
                }
            }
        }
        return false;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            throw new NullPointerException();
        } else {
            int currentIndex = 0;
            Node<E> tempNode = firstNode;
            for (int i = 0; i < size(); i++, currentIndex++) {
                //Peek the data on nextNode
                if (tempNode.nextNode.data == element) {
                    return currentIndex;
                } else {
                    //Move tempNode 1 index forward
                    tempNode = tempNode.nextNode;
                }
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        this.lastNode = new Node<E>(null, null);
        this.firstNode = new Node<E>(null, lastNode);
        listSize = 0;
        modCount++;
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if(size() != 0) {
            Node<E> tempNode = firstNode;
            for (int i = 0; i < size() - 1; i++) {
                sb.append(tempNode.nextNode.data);
                sb.append(", ");
                tempNode = tempNode.nextNode;
            }
            sb.append(tempNode.nextNode.data);
        }
        sb.append("]");

        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyALDAListIterator();
    }

    public Node<E> getNodeBeforeIndex(int index) {
//        if (index < 0 || index > size() - 1) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        if (index == 0 && size() != 0) {
//            return firstNode.nextNode;
//        }

        Node<E> tempNode = firstNode;
        //sets tempNode to the node prior to the index
        for (int i = 0; i < index; i++) {
            tempNode = tempNode.nextNode;
        }
        return tempNode;
    }

    private static class Node<E> {
        public E data;
        public Node<E> nextNode;

        public Node(E data, Node<E> nextNode) {
            this.data = data;
            this.nextNode = nextNode;
        }
    }

    private class MyALDAListIterator implements java.util.Iterator<E> {
        private Node<E> nodePriorToCurrent;
        private Node<E> current = firstNode;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext() {
            if (size() == 0) {
                return false;
            }
            return current.nextNode != lastNode;
        }

        public E next() {
            if (modCount != expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            nodePriorToCurrent = current;
            current = current.nextNode;
            okToRemove = true;
            E temp = current.data;
            return temp;
        }

        public void remove() {
            if (modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if (!okToRemove)
                throw new IllegalStateException();
            if(nodePriorToCurrent == null) {
                firstNode.nextNode = current.nextNode;
            } else {
                nodePriorToCurrent.nextNode = current.nextNode;
                listSize--;
                modCount++;
                expectedModCount++;
                okToRemove = false;
            }
        }
    }
}
