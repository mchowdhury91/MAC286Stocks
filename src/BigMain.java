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

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> createSymListArray() {
		ArrayList<String> symList = new ArrayList<String>();
		BufferedReader bR = null;
		try {
			bR = new BufferedReader(new FileReader("symList.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("symList.txt does not exist, exiting");
			System.exit(1);
		}

		String line = "";
		try {
			while ((line = bR.readLine()) != null) {
				// remove any new line and carriage return characters
				line = line.replaceAll("\\n", "");
				line = line.replaceAll("\\r", "");
				
				// add extension because symList is a list of file names
				symList.add(line + ".csv");
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}

		return symList;
	}

	/**
	 * 
	 * @param dirName
	 * @return File
	 * 
	 *         Returns a directory at path dirName. If the directory does not
	 *         exist, it will create the directory. If a file with that name
	 *         already exists, but it is not a directory, it will exit the program.
	 */
	public File initDirectory(String dirName) {
		File dir = new File(dirName);

		if (!dir.exists()) {
			dir.mkdir();
		}
		if (!dir.isDirectory()) {
			System.out.println(dirName + " is not a directory!");
			System.exit(3);
		}

		return dir;
	}

	public void createStatsSummary(Stats[] statsList, File statsDir, String statsFileName) throws IOException{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(statsDir.getName() + "/" + statsFileName));
		String header = "";

		// loop through the Fields of the Stats class and write them as the header for the csv file
		// ignoring mFile, mPath and tradeArray
		for (Field f : statsList[0].getClass().getDeclaredFields()) {
			if (f.getName() == "mFile" || f.getName() == "mPath" || f.getName() == "tradeArray") {
				continue;
			}
			header += f.getName() + ",";
		}

		header += "\n";
		bufferedWriter.write(header);

		// loop through each Stat object in statsList and write the data to the csv file
		for (Stats s : statsList) {
			try {
				bufferedWriter.write(s.getCSVLine());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		bufferedWriter.close();		
	}
	
	public static void main(String[] args) throws IOException {
		BigMain bigMain = new BigMain();

		ArrayList<String> symList = bigMain.createSymListArray();
		File directory = bigMain.initDirectory("./Data");
		
		Simulator simulator = new Simulator();
		ReversalNewHighs rhn = new ReversalNewHighs();
		simulator.setTradingPattern(rhn);
		
		float[] stopLossValues = { 2f, 4f, 5f, 10f };
		float[] targetValues = { 2f, 4f, 5f, 10f };
		int[] holdLimits = { 0, 5, 10, 15 };
		
		File statsDir = bigMain.initDirectory("./Stats");

		File tradesDir = bigMain.initDirectory(statsDir + "/Trades");

		// nested loop to go through all combinations of stopLoss and targets and holdLimits
		for(int holdLimit : holdLimits){
			Stats[] statsList = new Stats[stopLossValues.length * targetValues.length];
			int listIndex = 0;
			rhn.setHoldLimit(holdLimit);
			
			for (float stopLoss : stopLossValues) {
				for (float target : targetValues) {
					TradeArray tradeArray = new TradeArray();
	
					// loop through all the symbols and run the simulator for each symbol
					for (String symFileName : symList) {
						System.out.println("symFileName: " + symFileName);
						File symFile = new File("./Data/" + symFileName);
						if (symFile.exists())
							tradeArray.add(simulator.run(symFile, stopLoss, target));
					}
	
					String statsLogPath = statsDir.getAbsolutePath();
					String statsFileName = "Stats_s" + stopLoss + "_t" + target + "_hL" + holdLimit + ".txt";
					// example: stats for a stopLoss of 5% and target of 10% will be saved as:
					// ./Stats/Stats_s5_t10.txt
					
					Stats stats = new Stats(statsFileName, statsLogPath, tradeArray);
					
					stats.calculateStats();
					//stats.printToFile();
	
					String tradeArrayLogPath = tradesDir.getAbsolutePath();
					String tradeArrayLogFileName = "Trades_s" + stopLoss + "_t" + target + "_hL" + holdLimit + ".csv";
					// example: all trades for a stopLoss of 5% and target of 10% will be saved as:
					// ./Stats/Trades/Trades_s5_t10.txt			
					
					tradeArray.log(tradeArrayLogFileName, tradeArrayLogPath);
	
					statsList[listIndex] = stats;
					listIndex++;
				}
			}
			
			try{
				bigMain.createStatsSummary(statsList, statsDir, "Stats_hL" + holdLimit + ".csv");
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
		}
		
	}

}
