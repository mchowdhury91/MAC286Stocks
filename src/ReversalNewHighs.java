
public class ReversalNewHighs extends TradingPattern {
	
	public ReversalNewHighs(){
		dataArray = null;
	}
	
	@Override
	public TradeArray match() {

		tradeArray = new TradeArray();
		
		for (int i = 59; i < dataArray.getSize()-2; i++) {
			// loop ends two days early because we won't have enough data
			// to process the last two days.
			
			PatternUtil patternUtil = new PatternUtil();

			if (patternUtil.sixtyDayHigh(i, dataArray) && patternUtil.outsideDay(i, dataArray)
					&& patternUtil.largest5DayRange(i, dataArray)) {
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
				trade = findLongTradeExit(i, trade);

				trade.close();

				if (Math.abs(trade.getEntryPrice() - trade.getExitPrice()) > 0.001) {
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();
				}

			} else if (patternUtil.sixtyDayLow(i, dataArray) && patternUtil.outsideDay(i, dataArray)
					&& patternUtil.largest5DayRange(i, dataArray)) {

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
				
				trade = findShortExitTrade(i, trade);
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
		float targetPrice = trade.getEntryPrice() * (1f + trade.getTarget() / 100f);
		
		for(int i = entryDay + 2; i < dataArray.getSize(); i++){
			DataBar exitBar = dataArray.get(i);

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
			}
		}
		
		return trade;
	}
	
	private Trade findShortExitTrade(int entryDay, Trade trade){
		
		float stopPrice = trade.getEntryPrice() * (1f + trade.getStopLoss() / 100f);
		float targetPrice = trade.getEntryPrice() * (1f - trade.getTarget() / 100f);

		// loop to check when to sell
		for (int j = entryDay + 2; j < dataArray.getSize(); j++) {
			DataBar exitBar = dataArray.get(j);

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
				break;
			}
		}
		
		return trade;
	}
	
}
