public class MyPrioQueue {
    Node head;

    public void enqueue(int priority, String data) {
        Node prev = null, current = head;
        while (true) {
            if (current == null) {  // end of queue
                if (prev != null)
                    prev.next = new Node(priority, data, null);
                else    // empty queue
                    head = new Node(priority, data, null);
                return;
            }

            check:
            if (current.priority >= priority) {  // found a spot
                if (current.priority == priority && current.data.compareTo(data) < 0)
                    break check;
                else if (prev == null) {  // beginning of a queue
                    head = new Node(priority, data, current);
                } else {
                    prev.next = new Node(priority, data, current);
                }
                return;
            }

            prev = current;
            current = current.next;
        }
    }

    public Node dequeue() {
        Node result = head;
        if (head != null)
            head = head.next;
        return result;
    }

    public int count() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public static class Node {
        int priority;
        String data;
        Node next;

        public Node(int priority, String data, Node next) {
            this.priority = priority;
            this.data = data;
            this.next = next;
        }
    }
}
