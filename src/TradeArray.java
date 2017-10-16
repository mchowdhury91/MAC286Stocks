import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/*
 * Dependencies:
 * Trade.java
 * MAC286Date
 * 
 */
public class TradeArray implements Iterable<Trade>{

	private Vector<Trade> tradeVector;
	
	public TradeArray(){
		tradeVector = new Vector<Trade>(1000,100);
	}
	
	public TradeArray(int capacity, int increment){
		tradeVector = new Vector<Trade>(capacity, increment);
	}
	
	public boolean add(Trade trade){
		return tradeVector.add(trade);
	}
	
	public void addAtHead(Trade trade){
		tradeVector.insertElementAt(trade, 0);
	}
	
	public boolean addAtTail(Trade trade){
		return tradeVector.add(trade);
	}
	
	public void insert(Trade trade, int index){
		tradeVector.insertElementAt(trade, index);
	}
	
	public boolean insert(TradeArray tA){
		return tradeVector.addAll(tA.getVector());
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
	
	public int size(){
		return tradeVector.size();
	}
	
	public Stats stats(){
		Stats stats = new Stats(this);
		stats.calculateStats();
		stats.printToFile();
		return stats;
	}
	
	@Override
	public Iterator<Trade> iterator() {
		return tradeVector.iterator();
	}	
	
}
