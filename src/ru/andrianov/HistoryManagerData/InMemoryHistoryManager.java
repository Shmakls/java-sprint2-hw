package ru.andrianov.HistoryManagerData;

import ru.andrianov.data.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private HomeLinkedList viewedTasks;
    private Map<Integer, Node> nodeWithId;


    public InMemoryHistoryManager() {
        viewedTasks = new HomeLinkedList();
        nodeWithId = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        Integer taskId = task.getId();
        if (nodeWithId.containsKey(taskId)) {
            Node node = nodeWithId.get(taskId);
            viewedTasks.removeNode(node);
            viewedTasks.linkLast(task);
            nodeWithId.remove(taskId);
        } else {
            Node node = viewedTasks.linkLast(task);
            nodeWithId.put(taskId, node);
        }

    }

    @Override
    public ArrayList<Task> getHistory() {
        return viewedTasks.getTasks();
    }

    @Override
    public void clear() {
        nodeWithId.clear();
        viewedTasks.clear();
    }

    @Override
    public void removeTaskFromHistoryById(Integer taskId) {
        if (nodeWithId.containsKey(taskId)) {
            Node node = nodeWithId.get(taskId);
            viewedTasks.removeNode(node);
            nodeWithId.remove(taskId);
        }
    }

    class HomeLinkedList {

        private Node head;
        private Node tail;
        private int size;

        public Node linkLast(Task task) {
            Node oldTail = tail;
            Node newNode = new Node(tail, task, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.next = newNode;
            size++;
            return newNode;
        }

        public ArrayList<Task> getTasks() {

            ArrayList<Task> viewedTasks = new ArrayList<>();
            for (Node temp = head; temp != null; temp = temp.next) {
                viewedTasks.add(temp.task);
            }
            return viewedTasks;
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
            this.size--;
        }

        public void clear(){
            for(Node temp = head; temp!=null;) {
                temp.task = null;
                Node node = temp.next;
                temp = temp.next = temp.prev = null;
                temp = node;
                this.size --;
            }

        }

    }
}

