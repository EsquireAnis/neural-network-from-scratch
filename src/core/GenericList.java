package core;

public class GenericList <E> {
    private E head;
    private GenericList tail = null;

    GenericList(E head, GenericList tail) {
        this.head = head;
        this.tail = tail;
    }

    public E get () {
        return head;
    }

    public void append (E e) {
        tail = new GenericList(head, tail);
        head = e;
    }

    public void printList () {
        System.out.print(head + " ");
        if (tail == null)
            System.out.println();
        else
            tail.printList();
    }
}
