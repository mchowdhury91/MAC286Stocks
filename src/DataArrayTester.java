import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

/*
 * Dependencies:
 * Bar.java
 * DataArray.java
 * Downloader.java
 * MAC286Date.java
 */
public class DataArrayTester {
	public static void main(String[] args) throws IOException, ParseException{
		
		// modify the following variables to change the dates
		String date1Str = "2007-10-16";
		String date2Str = "2017-10-16";
		
		// do not modify any of the below
		Downloader downloader = new Downloader();
		downloader.downloadHistoricalData("^DJI", date1Str, date2Str, "^DJI_daily.csv");
		
		DataArray dA = new DataArray("^DJI", ".");
		
		dA.load();
		
		int count = 0;
		
		Analyzer analyzer = new Analyzer();
		Vector<Bar> barV = dA.getBarVector();
		for(int i = 59; i < barV.size(); i++){
			if(analyzer.outsideDay(i, barV)
					&& analyzer.sixtyDayHigh(i, barV)
					&& analyzer.largest5DayRange(i, barV)
					&& barV.get(i).getOpen() >= 30){
				
				count++;
				barV.get(i).display();
				System.out.println("------------------------\n");
				
				for(int j = i+1; j < barV.size(); j++){
					
				}
				
			}
		}
		
		System.out.println(count + " days meet the pattern requirements");
	}
}
