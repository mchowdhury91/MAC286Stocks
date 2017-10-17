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
		downloader.downloadHistoricalData("GOOGL", date1Str, date2Str, "GOOGL_daily.csv");
		
		DataArray dA = new DataArray("GOOGL", ".");
		
		dA.load();
		
		int count = 0;
		
		Analyzer analyzer = new Analyzer();
		for(int i = 59; i < dA.getSize(); i++){
			Vector<Bar> barV = dA.getBarVector();
			if(analyzer.outsideDay(i, barV)
					&& analyzer.sixtyDayHigh(i, barV)
					&& analyzer.largest5DayRange(i, barV)){
				count++;
				barV.get(i).display();
				System.out.println("------------------------\n");
			}
		}
		
		System.out.println(count + " days meet the pattern requirements");
	}
}
