import java.text.DecimalFormat;
import java.text.ParseException;

/*
 * 
 * Direction: the trade was it long or short (enum)

entryDate: when did you buy (or sold short)

entryPrice: the price at which the stock is bought (or sold in case short)

numberOfShares: how many share were exchanged

exitPrice: the price at which it is sold (or bought in case short)

exitDate: when did you close the trade

setters and getters

Method to return profit or loss float PL(); negative for a loss and positive for a win.  
 */


/*
 * Dependencies:
 * Direction.java
 * MAC286Date
 */
public class Trade {

	Direction direction;
	
	private MAC286Date entryDate, exitDate;
	private float entryPrice, exitPrice, stopLoss, target;
	private int numberOfShares;
	private int holdingPeriod;
	private boolean on;
	
	private String symbol;
	
	/***************** Constructors *******************/
	//incomplete trade
	public Trade(){
		this.numberOfShares = 1;
		holdingPeriod = 0;
		direction = Direction.LONG;
		on = true;
		symbol = "";
		exitDate = null;
		entryDate = null;
		entryPrice = 0f;
		exitPrice = 0f;
		stopLoss = 0f;
		target = 0f;
	}
	
	// complete trade, will be closed
	public Trade(int numberOfShares, 
			     float entryPrice, MAC286Date entryDate,
			     float exitPrice, MAC286Date exitDate,
			     float stopLoss, float target,
			     Direction direction,
			     String symbol){
		
		this.numberOfShares = numberOfShares;
		
		this.entryDate = entryDate;
		this.entryPrice = entryPrice;
		
		this.exitDate = exitDate;
		this.exitPrice = exitPrice;
		
		this.stopLoss = stopLoss;
		this.target = target;
		
		this.direction = direction;
		on = false;
		holdingPeriod = Math.abs(entryDate.daysBetween(exitDate));
		this.symbol = symbol;
	}
	
	// incomplete trade
	public Trade(int numberOfShares){
		this.numberOfShares = numberOfShares;
		
		entryPrice = exitPrice = 0f;
		try{
			entryDate = new MAC286Date("2000-01-01");
		}catch(ParseException e){
			entryDate = null;
			exitDate = null;
		}
		
		direction = Direction.LONG;
		on = true;
		
		holdingPeriod = 0;
		symbol = "";
	}
	
	// incomplete trade
	public Trade(int numberOfShares, Direction direction){
		this.numberOfShares = numberOfShares;
		
		entryPrice = exitPrice = 0f;
		entryDate = null;
		exitDate = null;
		
		on = true;
		this.direction = direction;	
		holdingPeriod = 0;
		symbol = "";
	}
	
	/**
	 * 
	 * @param entryDate
	 * @param entryPrice
	 * @param direction
	 */
	public Trade(MAC286Date entryDate, float entryPrice, Direction direction){
		numberOfShares = 1;
		this.entryPrice = entryPrice;
		this.entryDate = entryDate;

		exitPrice = 0f;
		exitDate = null;
		holdingPeriod = 0;
		
		on = true;
		this.direction = direction;		
		symbol = "";
		
	}
	
	
	/****************** Special Functions ********************/
	public float PL(){
		if(on){
			// trade not closed
			System.out.println("Trade was not closed");
			return 0f;
		}
		
		float pl = 0;
		if(direction == Direction.LONG){
			pl = ((exitPrice) - (entryPrice)) * numberOfShares;			
		}else{
			//short
			pl = ((entryPrice) - (exitPrice)) * numberOfShares;
		}
		
		return pl;
	}
	
	public float percentPL(){
		if(on){
			// trade not closed
			return 0f;
		}
		float totalExitPrice = exitPrice * numberOfShares;
		float totalEntryPrice = entryPrice * numberOfShares;
		if(direction == Direction.LONG){
			return ((totalExitPrice - totalEntryPrice)/(totalEntryPrice))*100;			
		}else{
			//short
			return ((totalEntryPrice - totalExitPrice)/(totalEntryPrice))*100;
		}
	}

	public boolean close(){
		if(symbol == "" || symbol == null){
			on = true;
			System.out.println("Set the symbol for this trade before closing!");
			return false;
		}
		
		if(exitPrice > 0f && exitDate != null){
			on = false;
			holdingPeriod = Math.abs(entryDate.daysBetween(exitDate));
			return true;
		}else{
			System.out.println("exitPrice or exitDate not set, can't close trade");
			return false;
		}
	}	
	
	/************************* Getters and Setters ***************************/
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public MAC286Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(MAC286Date entryDate) {
		this.entryDate = entryDate;
	}
	public MAC286Date getExitDate() {
		return exitDate;
	}
	public void setExitDate(MAC286Date exitDate) {
		this.exitDate = exitDate;
	}
	public float getEntryPrice() {
		return entryPrice;
	}
	public void setEntryPrice(float entryPrice) {
		this.entryPrice = entryPrice;
	}
	public float getExitPrice() {
		return exitPrice;
	}
	public void setExitPrice(float exitPrice) {
		this.exitPrice = exitPrice;
	}
	public int getNumberOfShares() {
		return numberOfShares;
	}
	public void setNumberOfShares(int numberOfShares) {
		this.numberOfShares = numberOfShares;
	}
	
	public void setStopLoss(float stopLoss){
		this.stopLoss = stopLoss;
	}
	
	public void setTarget(float target){
		this.target = target;
	}
	
	public float getStopLoss(){
		return stopLoss;
	}
	
	public float getTarget(){
		return target;
	}
	
	public int getHoldingPeriod(){
		return holdingPeriod;
	}
	
	public void setHoldingPeriod(int holdingPeriod){
		this.holdingPeriod = holdingPeriod;
	}
	
	public String getSymbol(){
		return symbol;
	}
	
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	
	/*********************** Overrides **************************************/
	
	@Override
	public String toString(){
		DecimalFormat df = new DecimalFormat("##.##");
		
		String string = "";
		string += "Direction: " + direction;
		string += "\nentryDate: " + entryDate;
		string += "\nentryPrice: " + entryPrice;
		string += "\nexitDate: " + exitDate;
		string += "\nexitPrice: " + exitPrice;
		string += "\nPL: " + df.format(percentPL());
		return string;
	}
	
	
}
