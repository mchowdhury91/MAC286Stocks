
public class MAC286Node<T> {

	private T current;
	private MAC286Node<T> next;
	
	public MAC286Node(T current){
		this.current = current;
		next = null;
	}
	
	public MAC286Node(T current, MAC286Node<T> next){
		this.current = current;
		this.next = next;
	}
	
	public T current(){
		return current;
	}
	
	public MAC286Node<T> next(){
		return next;
	}
	
	public boolean hasNext(){
		return next != null;
	}
	
	public void setCurrent(T current){
		this.current = current;
	}
	
	public void setNext(MAC286Node<T> next){
		this.next = next;
	}
	
	@Override
	public String toString(){
		String str = current.toString();
		return str;
	}
	
	public void display(){
		System.out.println(this.toString());
	}
	
}
