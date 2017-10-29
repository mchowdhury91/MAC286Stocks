import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
public class Stats {
	private int numTrades, numLongTrades, numShortTrades, numWinners, numLosers;
	private int numLongWinners, numLongLosers, numShortWinners, numShortLosers;
	private int maxHoldingPeriod, minHoldingPeriod;
	private float avgHoldingPeriod;
	private double averagePL, averageLongPL, averageShortPL, maxPL, minPL;
	private float stopLoss, target;
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
		mPath = ".";
		numTrades= numLongTrades= numShortTrades= numWinners= numLosers= 0;
		numLongWinners= numLongLosers= numShortWinners= numShortLosers= 0;
		averagePL= averageLongPL= averageShortPL= maxPL= minPL = 0;
		
		this.tradeArray = tradeArray;
	}
	
	@Override
	public String toString() {
		//You print all these!!!!
		String str = new String();
		DecimalFormat df = new DecimalFormat("##.##");
		
		for(Field field : getClass().getDeclaredFields()){
			if(field.getName() == "mPath" ||
					field.getName() == "tradeArray" ||
					field.getName() == "mFile"){
				continue;
			}
			
			str += field.getName() + ": ";
			try {
				str += df.format(field.get(this));
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
		averagePL= averageLongPL= averageShortPL = 0;
		avgHoldingPeriod = 0;
		
		numTrades = tradeArray.size();
		
		minHoldingPeriod = Integer.MAX_VALUE;
		maxHoldingPeriod = Integer.MIN_VALUE;
		
		minPL = Double.MAX_VALUE;
		maxPL = Double.MIN_VALUE;
		
		stopLoss = tradeArray.getVector().get(0).getStopLoss();
		target = tradeArray.getVector().get(0).getTarget();
		
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
			
			avgHoldingPeriod += t.getHoldingPeriod();
			if(t.getHoldingPeriod() > maxHoldingPeriod)
				maxHoldingPeriod = t.getHoldingPeriod();
			
			if(t.getHoldingPeriod() < minHoldingPeriod)
				minHoldingPeriod = t.getHoldingPeriod();	
			
		}
		
		numWinners = numLongWinners + numShortWinners;
		numLosers = numLongLosers + numShortLosers;
		
		averagePL /= numTrades;
		averageLongPL /= numLongTrades;
		averageShortPL /= numShortTrades;
		
		avgHoldingPeriod /= numTrades;
	}
	
	public float getAvgHoldingPeriod(){
		return avgHoldingPeriod;
	}
	
	public boolean printToFile() {
		//open the file as a bufferedWriter 
		BufferedWriter bW;
		try {
			bW = new BufferedWriter(new FileWriter(mPath + "/" + mFile));
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

	public int getNumTrades() {
		return numTrades;
	}

	public int getNumLongTrades() {
		return numLongTrades;
	}

	public int getNumShortTrades() {
		return numShortTrades;
	}

	public int getNumWinners() {
		return numWinners;
	}

	public int getNumLosers() {
		return numLosers;
	}

	public int getNumLongWinners() {
		return numLongWinners;
	}

	public int getNumLongLosers() {
		return numLongLosers;
	}

	public int getNumShortWinners() {
		return numShortWinners;
	}

	public int getNumShortLosers() {
		return numShortLosers;
	}

	public double getAveragePL() {
		return averagePL;
	}

	public double getAverageLongPL() {
		return averageLongPL;
	}

	public double getAverageShortPL() {
		return averageShortPL;
	}

	public double getMaxPL() {
		return maxPL;
	}

	public double getMinPL() {
		return minPL;
	}

	public String getmFile() {
		return mFile;
	}

	public String getmPath() {
		return mPath;
	}

	public TradeArray getTradeArray() {
		return tradeArray;
	}
	public float getStopLoss(){
		return stopLoss;
	}
	public float getTarget(){
		return target;
	}


}
