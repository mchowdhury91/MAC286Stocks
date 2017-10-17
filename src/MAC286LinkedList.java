
public class MAC286LinkedList<T> {

	private int size;
	private MAC286Node<T> head;
	private MAC286Node<T> tail;

	public MAC286LinkedList() {
		head = null;
		tail = null;
	}

	public MAC286LinkedList(MAC286Node<T> head) {
		this.head = head;

		MAC286Node<T> current = head;
		while (current.hasNext()) {
			current = current.next();
		}

		tail = current;
	}

	public MAC286Node<T> get(int index) {
		int count = 0;
		MAC286Node<T> current = head;

		while (current.hasNext() && count < index) {
			current = current.next();
			count++;
		}

		if (count != index) {
			return null;
		}

		return current;
	}

	public MAC286Node<T> getPrev(int index) {
		return get(index - 1);
	}

	public MAC286Node<T> head() {
		return head;
	}

	public MAC286Node<T> tail() {
		return tail;
	}

	public void insert(T t, int index) {
		// if inserting at head
		if (index == 0) {
			head = new MAC286Node<T>(t, head);
			return;
		}

		MAC286Node<T> temp = get(index - 1);

		// if the index is out of range, just add to the tail
		if (temp == null) {
			tail().setNext(new MAC286Node<T>(t));
			return;
		}

		// if index is the tail
		if (!temp.hasNext()) {
			temp.setNext(new MAC286Node<T>(t));
			return;
		}

		// else we're inserting between 2 elements
		MAC286Node<T> temp2 = temp.next();
		temp.setNext(new MAC286Node<T>(t));
		temp.next().setNext(temp2);
		return;

	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return (head == null);
	}

	/**
	 * isFull() insertHead() insertTail() insert(int index, T t) removeHead()
	 * removeTail() T remove(index) T At(index n)
	 */
}
