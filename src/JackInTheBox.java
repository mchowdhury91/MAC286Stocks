import java.util.Vector;

public class JackInTheBox extends TradingPattern {

	private int holdLimit;
	
	public JackInTheBox() {
		holdLimit = 0;
	}

	public JackInTheBox(int holdLimit) {
		this.holdLimit = holdLimit;
	}
	
	
	public void setHoldLimit(int holdLimit){
		this.holdLimit = holdLimit;
	}
	
	public int getHoldLimit(){
		return holdLimit;
	}
	
	@Override
	public TradeArray match() {
		tradeArray = new TradeArray();

		for (int i = 59; i < dataArray.getSize() - 2; i++) {
			// i = day 1
			// inside day is day 2
			// entryDay is day 3

			if (PatternUtil.sixtyDayHigh(i, dataArray) && PatternUtil.largestNDayRange(i, dataArray, 9)
					&& PatternUtil.insideDay(i + 1, dataArray)) {
				// if the bar for the i'th day meets the pattern criteria for a
				// long trade

				// buy at opening of next day
				int entryDay = i + 2;
				// bar for when we enter trade
				DataBar entryBar = dataArray.get(entryDay);
				DataBar day1Bar = dataArray.get(i);
				
				float entryPrice = 0f;
				
				if(entryBar.getOpen() >= day1Bar.getHigh() + 1){
					entryPrice = entryBar.getOpen();
				}else if(entryBar.getHigh() >= day1Bar.getHigh() + 1){
					entryPrice = day1Bar.getHigh() + 1;
				}else{
					continue;
				}
				
				Trade trade = new Trade(entryBar.getDate(), entryPrice, Direction.LONG);
				trade.setSymbol(dataArray.getSymbol());
				trade.setStopLoss(stopLoss);
				trade.setTarget(target);

				trade = findLongTradeExit(entryDay, trade);

				if (Math.abs(trade.getEntryPrice() - trade.getExitPrice()) > 0.001) {
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();
				}

			} else if (PatternUtil.sixtyDayLow(i, dataArray) && PatternUtil.largestNDayRange(i, dataArray, 9)
					&& PatternUtil.insideDay(i + 1, dataArray)) {

				//SHORT
				
				int entryDay = i + 2;
				// bar for when we enter trade
				DataBar entryBar = dataArray.get(entryDay);
				DataBar day1Bar = dataArray.get(i);
				
				float entryPrice = 0f;
				
				if(entryBar.getOpen() <= day1Bar.getLow() - 1){
					entryPrice = entryBar.getOpen();
				}else if(entryBar.getLow() <= day1Bar.getLow() - 1){
					entryPrice = day1Bar.getLow() - 1;
				}else{
					continue;
				}
				
				Trade trade = new Trade(entryBar.getDate(), entryPrice, Direction.SHORT);
				trade.setSymbol(dataArray.getSymbol());
				trade.setStopLoss(stopLoss);
				trade.setTarget(target);

				trade = findShortTradeExit(entryDay, trade);

				if (Math.abs(trade.getEntryPrice() - trade.getExitPrice()) > 0.001) {
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();
				}
			}

		}
		return tradeArray;
	}

	private Trade findLongTradeExit(int entryDay, Trade trade) {
		
		float stopPrice = trade.getEntryPrice() * (1f - trade.getStopLoss() / 100f);
		float targetPrice = trade.getEntryPrice() * (1f + trade.getTarget() / 100f);
		int holdingPeriod = 0;
		
		int i;
		
		// if entryPrice and the open for the entryDay are the same,
		// we entered at the open so it is safe to close later in the entryDay 
		if(Math.abs(trade.getEntryPrice() - dataArray.get(entryDay).getOpen()) < 0.0001){
			i = entryDay;
		}else{
			// otherwise it is safer to just start from the day after entryDay
			i = entryDay + 1;
		}		
		
		for( ; i < dataArray.getSize(); i++){
			DataBar exitBar = dataArray.get(i);
			holdingPeriod++;
			
			if(holdLimit > 0 && holdingPeriod >= holdLimit){
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(exitBar.getOpen());
				break;
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

	private Trade findShortTradeExit(int entryDay, Trade trade) {
		float stopPrice = trade.getEntryPrice() * (1f + trade.getStopLoss() / 100f);
		float targetPrice = trade.getEntryPrice() * (1f - trade.getTarget() / 100f);
		int holdingPeriod = 0;
		
		int j;
		
		// if entryPrice and the open for the entryDay are the same,
		// we entered at the open so it is safe to close later in the entryDay 
		if(Math.abs(trade.getEntryPrice() - dataArray.get(entryDay).getOpen()) < 0.0001){
			j = entryDay;
		}else{
			// otherwise it is safer to just start from the day after entryDay
			j = entryDay + 1;
		}
		
		for ( ; j < dataArray.getSize(); j++) {
			DataBar exitBar = dataArray.get(j);
			holdingPeriod++;
			
			if(holdLimit > 0 && holdingPeriod > holdLimit){
				System.out.println("Holding Period " + holdingPeriod + " exceeded holdLimit " + holdLimit);
				trade.setExitDate(exitBar.getDate());
				trade.setExitPrice(exitBar.getOpen());
				break;
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
