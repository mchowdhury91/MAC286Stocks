import java.io.File;

public class Simulator {

	TradeArray tradeArray;
	String symbol, path, filename;
	
	
	public Simulator() {
		tradeArray = new TradeArray();
		symbol = filename = null;
		path = ".";
	}

	public Simulator(String symbol, String path, String filename){
		tradeArray = new TradeArray();
		this.symbol = symbol;
		this.path = path;
		this.filename = filename;
	}
	
	public TradeArray getTrades(){
		return tradeArray;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public TradeArray run(){
	    return run(2f, 4f);
		
	}	

	public TradeArray run(float stop, float target){
	    
		DataArray dataArray = new DataArray(symbol, path);
		
		dataArray.load();
		return processData(dataArray, stop, target);
		
	}		
	
	public TradeArray run(File dataFile){
		
	    return run(dataFile, 2f, 4f);
	}		

	public TradeArray run(File dataFile, float stop, float target){
		
		if(!dataFile.exists()){
			System.out.println(dataFile.getAbsolutePath() + " does not exist!");
			return null;
		}
		
		DataArray dataArray = new DataArray(symbol, path);
		
		dataArray.load(dataFile);
		return processData(dataArray, stop, target);

	}		
	
	private TradeArray processData(DataArray dataArray, float stop, float target){
		
		tradeArray = new TradeArray();
		
		for(int i = 59; i < dataArray.getSize(); i++){
			Bar bar = dataArray.get(i);
			Analyzer analyzer = new Analyzer();
			
			if(analyzer.sixtyDayHigh(i, dataArray)
					&& analyzer.outsideDay(i, dataArray)
					&& analyzer.largest5DayRange(i, dataArray)
					&& bar.getOpen() >= 30){
				
				int nextDay = i+1;
				if(nextDay >= dataArray.getSize() || nextDay+1 >= dataArray.getSize()){
					continue;
				}
				
				Bar entryBar = dataArray.get(nextDay);
				
				Trade trade = new Trade(entryBar.getDate(), entryBar.getOpen(), Direction.LONG);
				trade.setStopLoss(stop);
				trade.setTarget(target);
				
				float stopPrice = trade.getEntryPrice() * (1f - trade.getStopLoss() / 100f);
				float targetPrice = trade.getEntryPrice() * (1f + trade.getTarget() / 100f);
				
				// loop to check when to sell
				for(int j = nextDay+1; j < dataArray.getSize(); j++){
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
				
				trade.close();
				tradeArray.add(trade);
				System.out.println(trade);
				System.out.println();
			}else if(analyzer.sixtyDayLow(i, dataArray)
					&& analyzer.outsideDay(i, dataArray)
					&& analyzer.largest5DayRange(i, dataArray)
					&& bar.getOpen() >= 30){
				
				// SHORT
				int nextDay = i+1;
				if(nextDay >= dataArray.getSize() || nextDay+1 >= dataArray.getSize()){
					continue;
				}
				
				Bar entryBar = dataArray.get(nextDay);
				
				Trade trade = new Trade(entryBar.getDate(), entryBar.getOpen(), Direction.SHORT);
				trade.setStopLoss(stop);
				trade.setTarget(target);
				
				float stopPrice = trade.getEntryPrice() * (1f + trade.getStopLoss() / 100f);
				float targetPrice = trade.getEntryPrice() * (1f - trade.getTarget() / 100f);
				
				// loop to check when to sell
				for(int j = nextDay+1; j < dataArray.getSize(); j++){
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
				
				trade.close();
				if(trade.getEntryPrice() != trade.getExitPrice()){
					tradeArray.add(trade);
					System.out.println(trade);
					System.out.println();					
				}
			}
			
		}
		
		return tradeArray;		
	}
	
}
