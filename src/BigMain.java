import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class BigMain {

	public static void main(String[] args) throws IOException{
		//Simulator simulator = new Simulator("GOOG", "./Data", "GOOG.csv");
		//TradeArray tradeArray = simulator.run();
		
		
		ArrayList<String> symList = new ArrayList<String>();
		BufferedReader bR = null;
		try{
			bR = new BufferedReader(new FileReader("symList.txt"));
		}catch(FileNotFoundException e){
			System.out.println("symList.txt does not exist, exiting");
			System.exit(1);
		}
		
		
		String line = "";
		while((line = bR.readLine()) != null){
			// remove any new line and carriage return characters
			line = line.replaceAll("\\n", "");
			line = line.replaceAll("\\r", "");			
			symList.add(line + ".csv");
		}
		
		
		File directory = new File("./Data");
		if(directory.exists() && directory.isDirectory()){
			float[] stopLossValues = { 2f, 4f, 5f, 10f };
			float[] targetValues = { 2f, 4f, 5f, 10f };
			Stats[] statList = new Stats[stopLossValues.length * targetValues.length];
			
			int listIndex = 0;
			Simulator simulator = new Simulator();
			
			File statsDir = new File("Stats");
			if(!statsDir.exists()){
				statsDir.mkdir();
			}
			
			File tradesDir = new File(statsDir + "/Trades");
			if(!tradesDir.exists()){
				tradesDir.mkdir();
			}
			
			for(float s : stopLossValues){
				for(float t : targetValues){
					TradeArray tradeArray = new TradeArray();
					
					for(String fileName : symList){
						File f = new File("./Data/" + fileName);
						if(f.exists())
							tradeArray.add(simulator.run(f, s, t));
					}
					
					Stats stats = new Stats(statsDir + "/" + "Stats_s" + s + "_t" + t + ".txt", ".", tradeArray);
					stats.calculateStats();
					stats.printToFile();
					
					tradeArray.log(tradesDir + "/" + "Trades_s" + s + "_t" + t + ".csv");
					
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
			
			for(Stats s : statList){
				
				bW.write("StopLoss: " + s.getStopLoss() + " Target: " + s.getTarget() + "\r\n");
				bW.write("AveragePL: " + s.getAveragePL() + "\r\n");
				bW.write("AverageLongPL: " + s.getAverageLongPL() + "\r\n");
				bW.write("AverageShortPL: " + s.getAverageShortPL() + "\r\n");
				bW.write("Wins/NumTrades: " + (float)s.getNumWinners() / (float) s.getNumTrades() + "\r\n");
				
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
				
			}
			
			bW.write("Best AveragePL: " + maxAvgPL + ", from S: " + bestStop + ", T: " + bestTarget + "\r\n");
			bW.write("Worst AveragePL: " + minAvgPL + ", from S: " + worstStop + ", T: " + worstTarget + "\r\n");
			bW.close();	
			
			BufferedWriter bW2 = new BufferedWriter(new FileWriter(statsDir.getName() + "/" + "Stats.csv"));
			String header = "";
			
			for(Field f : statList[0].getClass().getDeclaredFields()){
				if(f.getName() == "mFile" ||
						f.getName() == "mPath" ||
						f.getName() == "tradeArray"){
					continue;
				}
				header += f.getName() + ",";
			}
			
			header += "\n";
			
			bW2.write(header);
			
			for(Stats s : statList){
				try {
					bW2.write(s.getCSVLine());
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
			bW2.close();
			
		}else{
			System.out.println(directory.getAbsolutePath() + " is either not a director or it does not exist! Exiting.");
			System.exit(1);
		}		
	}

}
