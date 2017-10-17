import java.text.ParseException;

public class TradeArrayTester {

	public static void main(String[] args) throws ParseException{
		
		MAC286Date entryDate = new MAC286Date("2017-10-03");
		MAC286Date exitDate = new MAC286Date("2017-10-06");
		
		float stopLoss = 2;
		float target = 4;
		
		Trade trade = new Trade(1, 391f, entryDate, 291f, exitDate, stopLoss, target, Direction.SHORT);
		
		System.out.println("Short trade:\nEntry:" + trade.getEntryPrice() + "\nExit: " + trade.getExitPrice() + "\nNumber of Shares: 1");
		System.out.println("Percent PL: " + trade.percentPL());
		System.out.println("PL: " + trade.PL());
		System.out.println();
		
		Trade trade2 = new Trade(1, 391f, entryDate, 291f, exitDate, stopLoss, target, Direction.LONG);
		System.out.println();
		System.out.println("Long trade:\nEntry:" + trade2.getEntryPrice() + "\nExit: " + trade2.getExitPrice() + "\nNumber of Shares: 1");
		System.out.println("Percent PL: " + trade2.percentPL());
		System.out.println("PL: " + trade2.PL());
		System.out.println();
		
		Trade trade3 = new Trade(10, 200f, entryDate, 391f, exitDate, stopLoss, target, Direction.LONG);
		System.out.println();
		System.out.println("Long trade:\nEntry:" + trade3.getEntryPrice() + "\nExit: " + trade3.getExitPrice() + "\nNumber of Shares: 1");
		System.out.println("Percent PL: " + trade3.percentPL());
		System.out.println("PL: " + trade3.PL());
		System.out.println();

		TradeArray tA = new TradeArray();
		tA.add(trade);
		tA.add(trade2);
		tA.add(trade3);
		
		TradeArray tA2 = new TradeArray();
		
		for(int i = 0; i < 100; i++){
			int m = (int) (Math.random()*12 + 1);
			int d = (int) (Math.random()*30 + 1); // this makes it impossible to get a 31st date, but it's not important
			
			MAC286Date tEntryDate = new MAC286Date("2017-" + m + "-" + d);
			
			m = (int) (Math.random()*12 + 1);
			d = (int) (Math.random()*30 + 1); // this makes it impossible to get a 31st date, but it's not important
			
			MAC286Date tExitDate = new MAC286Date("2017-" + m + "-" + d);
			
			int numShares = (int) (Math.random() * 100 + 1);
			float entryP = (float) (Math.random() * 1000 + 1);
			float exitP = (float) (Math.random() * 1000 + 1);
			
			Direction dir;
			
			if(Math.random() <= 0.7){
				dir = Direction.LONG;
			}else{
				dir = Direction.SHORT;
			}
			
			Trade t = new Trade(numShares, entryP, tEntryDate, exitP, tExitDate, stopLoss, target, dir);
			tA2.add(t);
		}
		
		tA.add(tA2);
		tA.stats();
		
	}
	
}
