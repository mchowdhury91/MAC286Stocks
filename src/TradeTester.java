import java.text.ParseException;

public class TradeTester {

	public static void main(String[] args) throws ParseException{
		
		MAC286Date entryDate = new MAC286Date("2017-10-03");
		MAC286Date exitDate = new MAC286Date("2017-10-06");
		
		Trade trade = new Trade(1, 391f, entryDate, 291f, exitDate, Direction.SHORT);
		
		System.out.println("Short trade:\nEntry:" + trade.getEntryPrice() + "\nExit: " + trade.getExitPrice() + "\nNumber of Shares: 1");
		System.out.println("Percent PL: " + trade.percentPL());
		System.out.println("PL: " + trade.PL());
		
		trade = new Trade(1, 391f, entryDate, 291f, exitDate, Direction.LONG);
		System.out.println();
		System.out.println("Long trade:\nEntry:" + trade.getEntryPrice() + "\nExit: " + trade.getExitPrice() + "\nNumber of Shares: 1");
		System.out.println("Percent PL: " + trade.percentPL());
		System.out.println("PL: " + trade.PL());

	}
	
}
