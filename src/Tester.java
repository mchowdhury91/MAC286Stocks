import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Tester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, ParseException{
		
		// modify the following variables to change the dates
		String date1Str = "2013-08-30";
		String date2Str = "2017-08-30";
		
		// do not modify any of the below
		Downloader downloader = new Downloader();
		downloader.downloadHistoricalData("GOOGL", date1Str, date2Str, "GOOGL.csv");
		
		ArrayList<Bar> barList = new ArrayList();
		
		try{
			BufferedReader in = new BufferedReader(new FileReader("GOOGL.csv"));
			in.readLine();
			
			String line;
			while((line = in.readLine()) != null){
				barList.add(new Bar(line));
			}
		}catch(Exception e){
			
		}
		
		/**
		int i = 0;
		for(Bar bar : barList){
			System.out.println("No. " + i++);
			bar.display();
			System.out.println("-----------------------");
		}
		**/
		
		int counter = 0;
		MAC286Date last60DayHigh = null;
		Analyzer analyzer = new Analyzer();
		
		for(int i = 59; i < barList.size(); i++){
			if(analyzer.sixtyDayHigh(i, barList) && analyzer.largest5DayRange(i, barList) && analyzer.outsideDay(i, barList)){
				int diff = 0;
				Bar current = barList.get(i);
				if(last60DayHigh != null){
					diff = current.getDate().daysBetween(last60DayHigh);
				}
				
				last60DayHigh = current.getDate();
				
				System.out.println("Excel Row: " + i+2);
				System.out.println("Days Since Last 60DH: " + diff);
				System.out.println(barList.get(i).getDate());				
				System.out.println(barList.get(i).getHigh());
				counter++;
			}
		}
		
		System.out.println(counter + " days satisfy the conditions of Reversal New Highs between " + date1Str + " and " + date2Str);
	}

}
