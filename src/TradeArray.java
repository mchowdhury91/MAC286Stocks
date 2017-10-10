import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class TradeArray implements Iterable<Trade>, Collection<Trade>{

	private Vector<Trade> tradeVector;
	
	public TradeArray(){
		tradeVector = new Vector<Trade>(1000,100);
	}
	
	public TradeArray(int capacity, int increment){
		tradeVector = new Vector<Trade>(capacity, increment);
	}
	
	@Override
	public boolean add(Trade trade){
		return tradeVector.add(trade);
	}
	
	public void addAtHead(Trade trade){
		tradeVector.insertElementAt(trade, 0);
	}
	
	public void addAtTail(Trade trade){
		tradeVector.add(trade);
	}
	
	public void insert(Trade trade, int index){
		tradeVector.insertElementAt(trade, index);
	}
	
	//
	public void add(TradeArray tradeArray){
		tradeVector.addAll(tradeArray.getVector());
	}
	
	public boolean remove(int index){
		return tradeVector.remove(index) != null;
	}
	
	public Vector<Trade> getVector(){
		return tradeVector;
	}
	
	public Trade at(int index){
		return tradeVector.elementAt(index);
	}
	
	public Trade elementAt(int index){
		return tradeVector.elementAt(index);
	}
	
	@Override
	public Iterator<Trade> iterator() {
		return tradeVector.iterator();
	}

	@Override
	public boolean addAll(Collection<? extends Trade> c) {
		return tradeVector.addAll(c);
	}

	@Override
	public void clear() {
		tradeVector.clear();		
	}

	@Override
	public boolean contains(Object o) {
		return tradeVector.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return tradeVector.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return tradeVector.isEmpty();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
