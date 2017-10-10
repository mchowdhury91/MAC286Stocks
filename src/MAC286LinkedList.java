
public class MAC286LinkedList<T> {

	MAC286Node<T> head;
	MAC286Node<T> tail;
	
	public MAC286LinkedList(){
		head = null;
		tail = null;
	}
	
	public MAC286LinkedList(MAC286Node<T> head){
		this.head = head;
		
		MAC286Node<T> current = head;
		while(current.hasNext()){
			current = current.next();
		}
		
		tail = current;
	}
	
	public MAC286Node<T> get(int index){
		int count = 0;
		MAC286Node<T> current = head;

		while(current.hasNext() && count < index){
			current = current.next();
			count++;
		}
		
		if(count != index){
			return null;
		}
		
		return current;
	}
	
	public MAC286Node<T> head(){
		return head;
	}
	
	public MAC286Node<T> tail(){
		return tail;
	}
	
}
