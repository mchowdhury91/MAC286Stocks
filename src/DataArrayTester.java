import java.io.IOException;
import java.text.ParseException;

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
		String date1Str = "2017-07-30";
		String date2Str = "2017-08-30";
		
		// do not modify any of the below
		Downloader downloader = new Downloader();
		downloader.downloadHistoricalData("GOOGL", date1Str, date2Str, "GOOGL_daily.csv");
		
		DataArray dA = new DataArray("GOOGL", ".");
		
		dA.load();
		
		int count = 0;
		for(Bar b: dA){
			count++;
			System.out.println("Bar " + count + ": ");
			b.display();
			System.out.println("--------------");
		}
	}
}
