import java.io.File;

public class Simulator {

	TradeArray tradeArray;
	String symbol, path, filename;
	TradingPattern pattern;
	
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
	
	public Simulator(String symbol, String path, String filename, TradingPattern pattern){
		tradeArray = new TradeArray();
		this.symbol = symbol;
		this.path = path;
		this.filename = filename;
		this.pattern = pattern;
	}	
	
	
	public void setTradingPattern(TradingPattern pattern){
		this.pattern = pattern;
	}
	
	public TradingPattern getTradingPattern(){
		return pattern;
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
		if(pattern == null){
			System.out.println("Must set pattern before using run function");
			return null;
		}
		DataArray dataArray = new DataArray(symbol, path);
		
		dataArray.load();
		
		pattern.setDataArray(dataArray);
		pattern.setStopLoss(stop);
		pattern.setTarget(target);
		return pattern.match();
		
	}		
	
	public TradeArray run(File dataFile){
		
	    return run(dataFile, 2f, 4f);
	}		

	public TradeArray run(File dataFile, float stop, float target){
		
		if(pattern == null){
			System.out.println("Must set pattern before using run function");
			return null;
		}
		
		if(!dataFile.exists()){
			System.out.println(dataFile.getAbsolutePath() + " does not exist!");
			return null;
		}
		
		symbol = dataFile.getName();
		symbol = symbol.replaceAll(".csv", "");
		
		DataArray dataArray = new DataArray(symbol, dataFile.getPath());
		
		System.out.println(dataArray.getSymbol());
		
		dataArray.load(dataFile);
		
		pattern.setDataArray(dataArray);
		pattern.setStopLoss(stop);
		pattern.setTarget(target);
		return pattern.match();

	}		
	
}
