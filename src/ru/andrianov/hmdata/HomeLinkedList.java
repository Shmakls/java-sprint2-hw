package ru.andrianov.hmdata;

import ru.andrianov.data.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeLinkedList {

    private Node head;
    private Node tail;

    public Node linkLast(Task task) {
        Node oldTail = tail;
        Node newNode = new Node(tail, task, null);
        tail = newNode;

        if (oldTail == null) {
            head = newNode;
        }
        else {
            oldTail.next = newNode;
        }
        return newNode;
    }

    public Collection<Task> getTasks() {

        if (head == null) {
            return null;
        } else {
            List<Task> viewedTasks = new ArrayList<>();
            for (Node temp = head; temp != null; temp = temp.next) {
                viewedTasks.add(temp.task);
            }
            return viewedTasks;
        }
    }

    public void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        if (prev == null) {
            this.head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            this.tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.task = null;
    }

    public void clear() {
        for(Node temp = head; temp!=null;) {
            temp.task = null;
            Node node = temp.next;
            temp = temp.next = temp.prev = null;
            temp = node;
        }
        head = tail = null;
    }

}
