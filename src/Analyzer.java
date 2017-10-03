import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Analyzer {

	public Analyzer() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean sixtyDayHigh(int t, ArrayList<Bar> barList){
		
		float max = barList.get(t).getHigh();
		for(int i = t-1; i >= 0; i--){
			if(barList.get(i).getHigh() >= max){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean largest5DayRange(int t, ArrayList<Bar> barList){
		
		float max = barList.get(t).getRange();
		for(int i = t-1; i >= t-4; i--){
			if(barList.get(i).getRange() >= max){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean tradesUnderYesterdaysLow(int t, ArrayList<Bar> barList){
		
		return (barList.get(t).getLow() < barList.get(t-1).getLow());
		
	}

	public boolean tradesOverYesterdaysHigh(int t, ArrayList<Bar> barList){
		
		return (barList.get(t).getHigh() > barList.get(t-1).getHigh());
	}	
	
	public boolean outsideDay(int t, ArrayList<Bar> barList){
		return (tradesOverYesterdaysHigh(t, barList) && tradesUnderYesterdaysLow(t, barList));
	}

}
