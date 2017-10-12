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
public class Trade {

	Direction direction;
	
	private MAC286Date entryDate, exitDate;
	private float entryPrice, exitPrice;
	private int numberOfShares;
	private boolean on;
	
	public Trade(){
		this.numberOfShares = 1;
		
		direction = Direction.LONG;
		on = true;
	}
	
	public Trade(int numberOfShares, 
			     float entryPrice, MAC286Date entryDate,
			     float exitPrice, MAC286Date exitDate,
			     Direction direction){
		
		this.numberOfShares = numberOfShares;
		
		this.entryDate = entryDate;
		this.entryPrice = entryPrice;
		
		this.exitDate = exitDate;
		this.exitPrice = exitPrice;
		
		this.direction = direction;
		on = false;
	}
	
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
	}
	
	public Trade(int numberOfShares, Direction direction){
		this.numberOfShares = numberOfShares;
		
		entryPrice = exitPrice = 0f;
		entryDate = null;
		exitDate = null;
		
		on = true;
		this.direction = direction;	
	}
	
	public Trade(MAC286Date entryDate, float entryPrice, Direction direction){
		numberOfShares = 1;
		this.entryPrice = entryPrice;
		this.entryDate = entryDate;
		
		on = true;
		this.direction = direction;			
	}
	
	public float PL(){
		if(on){
			// trade not closed
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
	
	public String toString(){
		//TODO: toString
		return "";
	}
	
	
}
