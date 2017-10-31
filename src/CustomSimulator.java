import java.io.File;

public class CustomSimulator extends Simulator{

	TradeArray tradeArray;
	String symbol, path, filename;
	private int daysToSellAfter;
	
	public CustomSimulator() {
		tradeArray = new TradeArray();
		symbol = filename = null;
		path = ".";
	}

	public CustomSimulator(String symbol, String path, String filename){
		tradeArray = new TradeArray();
		this.symbol = symbol;
		this.path = path;
		this.filename = filename;
	}
	
	public void setDaysToSellAfter(int n){
		daysToSellAfter = n;
	}
	
	
	@Override
	protected TradeArray processData(DataArray dataArray, float stop, float target){
		
		tradeArray = new TradeArray();
		
		for(int i = 59; i < dataArray.getSize(); i++){
			Bar bar = dataArray.get(i);
			Analyzer analyzer = new Analyzer();
			
			if(analyzer.sixtyDayHigh(i, dataArray)
					&& analyzer.outsideDay(i, dataArray)
					&& analyzer.largest5DayRange(i, dataArray)
					){
				
				// LONG
				int nextDay = i+1;
				if(nextDay >= dataArray.getSize() || nextDay+1 >= dataArray.getSize()){
					continue;
				}
				
				Bar entryBar = dataArray.get(nextDay);
				
				Trade trade = new Trade(entryBar.getDate(), entryBar.getOpen(), Direction.LONG);
				trade.setSymbol(dataArray.getSymbol());
				trade.setStopLoss(stop);
				trade.setTarget(target);
				
				float stopPrice = trade.getEntryPrice() * (1f - trade.getStopLoss() / 100f);
				float targetPrice = trade.getEntryPrice() * (1f + trade.getTarget() / 100f);
				
				// loop to check when to sell
				
				for(int j = nextDay+1; j <= nextDay + 1 + daysToSellAfter; j++){
					if(j >= dataArray.size()){
						break;
					}
					
					Bar exitBar = dataArray.get(j);
					
					if(exitBar.getLow() <= stopPrice){
						System.out.println("exitBar low: " + exitBar.getLow());
						trade.setExitDate(exitBar.getDate());
						trade.setExitPrice(stopPrice);
						break;
					}
					
					if(exitBar.getHigh() >= targetPrice){
						System.out.println("exitBar high: " + exitBar.getHigh());
						trade.setExitDate(exitBar.getDate());
						trade.setExitPrice(targetPrice);
						break;
					}
					
					if(j == dataArray.getSize()-1){
						trade.setExitDate(trade.getEntryDate());
						trade.setExitPrice(trade.getEntryPrice());
					}
				}
				
				if(trade.getExitDate() == null && 
						(nextDay + 1 + daysToSellAfter) < dataArray.size()){
					// day limit reached, need to exit at 5th day price
					Bar exitBar = dataArray.get(nextDay + 1 + daysToSellAfter);
					
					trade.setExitDate(exitBar.getDate());
					trade.setExitPrice(exitBar.getClose());
				}
				
				trade.close();
				
				if(Math.abs(trade.getEntryPrice() - trade.getExitPrice()) > 0.001){
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();					
				}
				
			}else if(analyzer.sixtyDayLow(i, dataArray)
					&& analyzer.outsideDay(i, dataArray)
					&& analyzer.largest5DayRange(i, dataArray)){
				
				// SHORT
				int nextDay = i+1;
				if(nextDay >= dataArray.getSize() || nextDay+1 >= dataArray.getSize()){
					continue;
				}
				
				Bar entryBar = dataArray.get(nextDay);
				
				Trade trade = new Trade(entryBar.getDate(), entryBar.getOpen(), Direction.SHORT);
				trade.setSymbol(dataArray.getSymbol());
				trade.setStopLoss(stop);
				trade.setTarget(target);
				
				float stopPrice = trade.getEntryPrice() * (1f + trade.getStopLoss() / 100f);
				float targetPrice = trade.getEntryPrice() * (1f - trade.getTarget() / 100f);
				
				// loop to check when to sell
				for(int j = nextDay+1; j < nextDay + 1 + daysToSellAfter; j++){
					
					if(j >= dataArray.size()){
						break;
					}
					
					Bar exitBar = dataArray.get(j);
					
					if(exitBar.getHigh() >= stopPrice){
						System.out.println("exitBar low: " + exitBar.getLow());
						trade.setExitDate(exitBar.getDate());
						trade.setExitPrice(stopPrice);
						break;
					}
					
					if(exitBar.getLow() <= targetPrice){
						System.out.println("exitBar high: " + exitBar.getHigh());
						trade.setExitDate(exitBar.getDate());
						trade.setExitPrice(targetPrice);
						break;
					}
					
					if(j == dataArray.getSize()-1){
						trade.setExitDate(trade.getEntryDate());
						trade.setExitPrice(trade.getEntryPrice());
						break;
					}
				}
				
				if(trade.getExitDate() == null && 
						(nextDay + 1 + daysToSellAfter) < dataArray.size()){
					// day limit reached, need to exit at 5th day price
					Bar exitBar = dataArray.get(nextDay + 1 + daysToSellAfter);
					
					trade.setExitDate(exitBar.getDate());
					trade.setExitPrice(exitBar.getClose());
				}
				
				trade.close();
				if(Math.abs(trade.getEntryPrice() - trade.getExitPrice()) > 0.001){
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();					
				}
			}
			
		}
		
		return tradeArray;		
	}
	
}
