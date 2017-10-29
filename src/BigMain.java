import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BigMain {

	public static void main(String[] args) throws IOException{
		//Simulator simulator = new Simulator("GOOG", "./Data", "GOOG.csv");
		//TradeArray tradeArray = simulator.run();
		
		
		
		File directory = new File("./Data");
		if(directory.exists() && directory.isDirectory()){
			File[] directoryListing = directory.listFiles();
			if(directoryListing != null){
				float[] stopLossValues = { 2f, 4f, 5f, 10f };
				float[] targetValues = { 2f, 4f, 5f, 10f };
				Stats[] statList = new Stats[stopLossValues.length * targetValues.length];
				
				int listIndex = 0;
				Simulator simulator = new Simulator();
				
				File statsDir = new File("Stats");
				if(!statsDir.exists()){
					statsDir.mkdir();
				}
				
				for(float s : stopLossValues){
					for(float t : targetValues){
						TradeArray tradeArray = new TradeArray();
						
						for(File child : directoryListing){
							tradeArray.add(simulator.run(child, s, t));
						}
						
						Stats stats = new Stats(statsDir + "/" + "Stats_s" + s + "_t" + t + ".txt", ".", tradeArray);
						stats.calculateStats();
						stats.printToFile();
						
						statList[listIndex] = stats;
						listIndex++;
					}
				}				
				
				BufferedWriter bW = new BufferedWriter(new FileWriter(statsDir.getName() + "/" + "Master_Stats.txt"));
				double maxAvgPL = Float.MIN_VALUE;
				float bestStop = 2f;
				float bestTarget = 4f;
				
				double minAvgPL = Float.MAX_VALUE;
				float worstStop = 2f;
				float worstTarget = 4f;		
				
				double maxAvgLongPL = Double.MIN_VALUE;
				double maxAvgShortPL = Double.MIN_VALUE;
				
				float winRatio = Float.MIN_VALUE;
				float bestWinStop = 2f;
				float bestWinTarget = 4f;
				
				for(Stats s : statList){
					
					bW.write("StopLoss: " + s.getStopLoss() + " Target: " + s.getTarget() + "\r\n");
					bW.write("AveragePL: " + s.getAveragePL() + "\r\n");
					bW.write("AverageLongPL: " + s.getAverageLongPL() + "\r\n");
					bW.write("AverageShortPL: " + s.getAverageShortPL() + "\r\n");
					bW.write("Win Loss Ratio: " + (float)s.getNumWinners() / (float) s.getNumLosers() + "\r\n");
					
					bW.write("Average Holding Period: " + s.getAvgHoldingPeriod() + "\r\n");
					bW.write("--------------------\r\n\r\n");
					
					if(s.getAveragePL() > maxAvgPL){
						maxAvgPL = s.getAveragePL();
						bestStop = s.getStopLoss();
						bestTarget = s.getTarget();
					}

					if(s.getAveragePL() < minAvgPL){
						minAvgPL = s.getAveragePL();
						worstStop = s.getStopLoss();
						worstTarget = s.getTarget();
					}
					
					if(s.getAverageShortPL() > maxAvgShortPL){
						maxAvgShortPL = s.getAverageShortPL();
					}
					
					if(s.getAverageLongPL() > maxAvgLongPL){
						maxAvgLongPL = s.getAverageLongPL();
					}
					
					if((s.getNumWinners() / (float)s.getNumTrades()) > winRatio){
						winRatio = s.getNumWinners() / (float) s.getNumTrades();
						bestWinStop = s.getStopLoss();
						bestWinTarget = s.getTarget();
					}
					
				}
				
				bW.write("Best AveragePL: " + maxAvgPL + ", from S: " + bestStop + ", T: " + bestTarget + "\r\n");
				bW.write("Worst AveragePL: " + minAvgPL + ", from S: " + worstStop + ", T: " + worstTarget + "\r\n");
//				bW.write("Best AverageLongPL: " + maxAvgLongPL + "\r\n");
//				bW.write("Best AverageShortPL: " + maxAvgShortPL + "\r\n");
//				bW.write("Best Win Ratio: " + winRatio + ", from S: " + bestWinStop + ", T: " + bestWinTarget + "\r\n");
				bW.close();
			}else{
				System.out.println("No files in " + directory.getAbsolutePath() + ". Exiting.");
				System.exit(1);
			}			
		}else{
		
			System.out.println(directory.getAbsolutePath() + " is either not a director or it does not exist! Exiting.");
			System.exit(1);
		}		
	}

}
