import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
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
		//stats.printToFile();
		return stats;
	}
	
	public void log(String fileName, String path) throws IOException{
		fileName = path + "/" + fileName;
		BufferedWriter bW = new BufferedWriter(new FileWriter(fileName));
		bW.write("Symbol, Direction, Entry Date, Entry Price, Number of Shares, Stop Loss, Target, Exit Date, Exit Price, Holding Period, percentPL\n");
		DecimalFormat df = new DecimalFormat("##.##");
		
		for(Trade trade : tradeVector){
			
			String str = trade.getSymbol() + "," + 
					trade.getDirection() + "," +
					trade.getEntryDate() + "," +
					df.format(trade.getEntryPrice()) + "," +
					trade.getNumberOfShares() + "," +
					trade.getStopLoss() + "," +
					trade.getTarget() + "," +
					trade.getExitDate() + "," +
					df.format(trade.getExitPrice()) + "," +
					trade.getHoldingPeriod() + "," +
					df.format(trade.percentPL()) + "\n";
			
			
			bW.write(str);
		}
		
		bW.close();
	}
	
	@Override
	public Iterator<Trade> iterator() {
		return tradeVector.iterator();
	}	
	
}
