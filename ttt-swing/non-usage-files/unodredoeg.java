public class unodredoeg {
    public static void main(String[] args) {
        DoublyLinkedListExample list = new DoublyLinkedListExample();
        list.addToBack(1, 1, 'X');
        list.addToBack(2, 2, 'O');
        list.addToBack(3, 3, 'X');

        list.printList();

        list.undo();
        list.printList();
        list.redo();
        list.printList();
        list.undo();
        list.printList();
        list.addToBack(3, 2, 'O');
        list.redo();
        list.printList();
        System.out.println(list.row());
    }
}

class DoublyLinkedListExample {
    class Node {
        int row;
        int col;
        char curPlayer;
        Node prev;
        Node next;

        public Node(int row, int col, char curPlayer) {
            this.row = row;
            this.col = col;
            this.curPlayer = curPlayer;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head;
    
    private Node current;

    public DoublyLinkedListExample() {
        this.head = null;
       
        this.current = null;

    }
    public int row(){
        return current.row;
    }
    public int col(){
        return current.col;
    }
    public char curPlayer(){
        return current.curPlayer;
    }

    public void addToBack(int row, int col, char curPlayer) {

        Node newNode = new Node(row, col, curPlayer);
        if (head == null) {
            head = newNode;
            current = newNode;
        } else {
            newNode.prev = current;
            current.next = newNode;
            current = newNode;

        }

    }

    public void undo() {
        if (current.prev != null) {
            current = current.prev;
            System.out.println(" Undo Move excuted --> Row: " + current.row + ", Col: " + current.col
                    + ", Current Player: " + current.curPlayer);
        } else {
            System.out.println("No more moves to undo");
        }

    }

    public void redo() {
        if (current.next != null) {
            current = current.next;
            System.out.println(" Redo Move excuted --> Row: " + current.row + ", Col: " + current.col
                    + ", Current Player: " + current.curPlayer);
        } else {
            System.out.println("No more moves to redo");
        }

    }

    public void printList() {
        System.out.println("\n Printing the list from head to tail: \n");
        Node temp = head;
        while (temp != current.next) {
            System.out.println("Row: " + temp.row + ", Col: " + temp.col + ", Current Player: " + temp.curPlayer);
            temp = temp.next;
        }
        System.out.println("\n Printed the list from tail to head: \n");
    }

}
