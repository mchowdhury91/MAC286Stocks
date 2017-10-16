import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
public class Stats {
	private int numTrades, numLongTrades, numShortTrades, numWinners, numLosers;
	private int numLongWinners, numLongLosers, numShortWinners, numShortLosers;
	private double averagePL, averageLongPL, averageShortPL, maxPL, minPL;
	String mFile, mPath;
	
	private TradeArray tradeArray;
	
	public Stats(String f, String path, TradeArray tradeArray) {
		mFile = f;
		mPath = path;
		numTrades= numLongTrades= numShortTrades= numWinners= numLosers= 0;
		numLongWinners= numLongLosers= numShortWinners= numShortLosers= 0;
		averagePL= averageLongPL= averageShortPL= maxPL= minPL = 0;
		
		this.tradeArray = tradeArray;
	}
	
	public void setFileName(String f, String path) {
		mFile = f;
		mPath = path;
	}

	public Stats(TradeArray tradeArray) {
		mFile = "test.txt";
		mPath = "./";
		numTrades= numLongTrades= numShortTrades= numWinners= numLosers= 0;
		numLongWinners= numLongLosers= numShortWinners= numShortLosers= 0;
		averagePL= averageLongPL= averageShortPL= maxPL= minPL = 0;
		
		this.tradeArray = tradeArray;
	}
	
	public String toString() {
		//You print all these!!!!
		String str = new String();
		
		for(Field field : getClass().getDeclaredFields()){
			str += field.getName() + ": ";
			try {
				str += field.get(this);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			str += "\r\n";
		}
	
		return str;
	}
	
	public void calculateStats(){
		

		numLongTrades= numShortTrades= numWinners= numLosers= 0;
		numLongWinners= numLongLosers= numShortWinners= numShortLosers= 0;
		averagePL= averageLongPL= averageShortPL;
		
		numTrades = tradeArray.size();
		
		minPL = Double.MAX_VALUE;
		maxPL = Double.MIN_VALUE;
		
		for(Trade t : tradeArray){
			
			double tempPL = t.percentPL();
			
			if(t.getDirection() == Direction.LONG){
				// long trade
				numLongTrades++;
				if(tempPL > 0){
					numLongWinners++;
				}else{
					numLongLosers++;
				}
				
				averageLongPL += tempPL;
				
			}else{
				// short trade
				numShortTrades++;
				if(tempPL > 0){
					numShortWinners++;
				}else{
					numShortLosers++;
				}
				
				averageShortPL += tempPL;
			}
			
			averagePL += tempPL;
			if(tempPL > maxPL){
				maxPL = tempPL;
			}
			
			if(tempPL < minPL){
				minPL = tempPL;
			}
		}
		
		numWinners = numLongWinners + numShortWinners;
		numLosers = numLongLosers + numShortLosers;
		
		averagePL /= numTrades;
		averageLongPL /= numLongTrades;
		averageShortPL /= numShortTrades;
		
	}
	
	public boolean printToFile() {
		//open the file as a bufferedWriter 
		BufferedWriter bW;
		try {
			bW = new BufferedWriter(new FileWriter(mPath + mFile));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			bW.write(this.toString());
			bW.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
