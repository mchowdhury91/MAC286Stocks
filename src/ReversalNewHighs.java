
public class ReversalNewHighs extends TradingPattern {
	
	private int holdLimit;
	
	public ReversalNewHighs(){
		dataArray = null;
		holdLimit = 0;
	}
	
	public ReversalNewHighs(int holdLimit){
		dataArray = null;
		this.holdLimit = holdLimit;
	}
	
	public int getHoldLimit(){
		return holdLimit;
	}
	
	public void setHoldLimit(int holdLimit){
		this.holdLimit = holdLimit;
	}
	
	@Override
	public TradeArray match() {
		tradeArray = new TradeArray();
		
		for (int i = 59; i < dataArray.getSize()-2; i++) {
			// loop ends two days early because we won't have enough data
			// to process the last two days.

			if (PatternUtil.sixtyDayHigh(i, dataArray) && PatternUtil.outsideDay(i, dataArray)
					&& PatternUtil.largest5DayRange(i, dataArray)) {
				// if the bar for the i'th day meets the pattern criteria for a long trade
				
				// buy at opening of next day
				int nextDay = i + 1;
				// bar for when we enter trade
				DataBar entryBar = dataArray.get(nextDay);

				Trade trade = new Trade(entryBar.getDate(), entryBar.getOpen(), Direction.LONG);
				trade.setSymbol(dataArray.getSymbol());
				trade.setStopLoss(stopLoss);
				trade.setTarget(target);

				// loop to check when to sell
				// i = day we found a match for the pattern
				// nextDay = day we entered
				trade = findLongTradeExit(nextDay, trade);

				if (Math.abs(trade.getEntryPrice() - trade.getExitPrice()) > 0.001) {
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();
				}

			} else if (PatternUtil.sixtyDayLow(i, dataArray) && PatternUtil.outsideDay(i, dataArray)
					&& PatternUtil.largest5DayRange(i, dataArray)) {

				// SHORT
				int nextDay = i + 1;
				if (nextDay >= dataArray.getSize() || nextDay + 1 >= dataArray.getSize()) {
					continue;
				}

				DataBar entryBar = dataArray.get(nextDay);

				Trade trade = new Trade(entryBar.getDate(), entryBar.getOpen(), Direction.SHORT);
				trade.setSymbol(dataArray.getSymbol());
				trade.setStopLoss(stopLoss);
				trade.setTarget(target);
				
				// nextDay = day we entered trade
				trade = findShortTradeExit(nextDay, trade);
				trade.close();
				if (Math.abs(trade.getEntryPrice() - trade.getExitPrice()) > 0.001) {
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();
				}
			}

		}
		return tradeArray;
	}

	private Trade findLongTradeExit(int entryDay, Trade trade){
		
		float stopPrice = trade.getEntryPrice() * (1f - trade.getStopLoss() / 100f);
		float originalSP = stopPrice;
		float targetPrice = trade.getEntryPrice() * (1f + trade.getTarget() / 100f);
		int holdingPeriod = 0;
		
		
		for(int i = entryDay + 1; i < dataArray.getSize(); i++){
			DataBar exitBar = dataArray.get(i);
			holdingPeriod++;
			
			if(holdLimit > 0 && holdingPeriod >= holdLimit){
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(exitBar.getOpen());
				break;
			}
			
			// trailing stop
			if(exitBar.getOpen() > trade.getEntryPrice()){
				stopPrice = exitBar.getOpen() * (1f - trade.getStopLoss() / 100f);
			}
			
			if(exitBar.getOpen() >= targetPrice || exitBar.getOpen() <= stopPrice){
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(exitBar.getOpen());
				break;
			}
			
			if (exitBar.getLow() <= stopPrice) {
				System.out.println("exitBar low: " + exitBar.getLow());
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(stopPrice);
				break;
			}

			if (exitBar.getHigh() >= targetPrice) {
				System.out.println("exitBar high: " + exitBar.getHigh());
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(targetPrice);
				break;
			}

			if (i == dataArray.getSize() - 1) {
				trade.setExitDate(trade.getEntryDate());
				trade.setExitPrice(trade.getEntryPrice());
				holdingPeriod = 0;
				break;
			}
			
		}
		
		trade.setHoldingPeriod(holdingPeriod);
		trade.close();
		return trade;
	}
	
	private Trade findShortTradeExit(int entryDay, Trade trade){
		
		float stopPrice = trade.getEntryPrice() * (1f + trade.getStopLoss() / 100f);
		float targetPrice = trade.getEntryPrice() * (1f - trade.getTarget() / 100f);
		int holdingPeriod = 0;
		
		// loop to check when to sell
		for (int j = entryDay + 1; j < dataArray.getSize(); j++) {
			DataBar exitBar = dataArray.get(j);
			holdingPeriod++;
			
			if(holdLimit > 0 && holdingPeriod > holdLimit){
				System.out.println("Holding Period " + holdingPeriod + " exceeded holdLimit " + holdLimit);
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(exitBar.getOpen());
				break;
			}
			
			// trailing stop
			if(exitBar.getOpen() < trade.getEntryPrice()){
				stopPrice = exitBar.getOpen() * (1f + trade.getStopLoss() / 100f);
			}
			
			if(exitBar.getOpen() <= targetPrice || exitBar.getOpen() >= stopPrice){
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(exitBar.getOpen());
				break;
			}			
			
			if (exitBar.getHigh() >= stopPrice) {
				System.out.println("exitBar low: " + exitBar.getLow());
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(stopPrice);
				break;
			}

			if (exitBar.getLow() <= targetPrice) {
				System.out.println("exitBar high: " + exitBar.getHigh());
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(targetPrice);
				break;
			}

			if (j == dataArray.getSize() - 1) {
				trade.setExitDate(trade.getEntryDate());
				trade.setExitPrice(trade.getEntryPrice());
				holdingPeriod = 0;
				break;
			}
		}
		
		trade.setHoldingPeriod(holdingPeriod);
		trade.close();
		return trade;
	}
	
}
