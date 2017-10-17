import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

public class Analyzer {

	public Analyzer() {
	}
	
	public boolean sixtyDayHigh(int t, Vector<Bar> barList){
		
		float max = barList.get(t).getHigh();
		for(int i = t-1; i >= 0; i--){
			if(barList.get(i).getHigh() >= max){
				return false;
			}
		}
		
		return true;
	}

	public boolean sixtyDayLow(int t, Vector<Bar> barList){
		
		float min = barList.get(t).getLow();
		for(int i = t-1; i >= 0; i--){
			if(barList.get(i).getLow() <= min){
				return false;
			}
		}
		
		return true;
	}	
	
	public boolean largest5DayRange(int t, Vector<Bar> barList){
		
		float max = barList.get(t).getRange();
		for(int i = t-1; i >= t-4; i--){
			if(barList.get(i).getRange() >= max){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean tradesUnderYesterdaysLow(int t, Vector<Bar> barList){
		
		return (barList.get(t).getLow() < barList.get(t-1).getLow());
		
	}

	public boolean tradesOverYesterdaysHigh(int t, Vector<Bar> barList){
		
		return (barList.get(t).getHigh() > barList.get(t-1).getHigh());
	}	
	
	public boolean outsideDay(int t, Vector<Bar> barList){
		return (tradesOverYesterdaysHigh(t, barList) && tradesUnderYesterdaysLow(t, barList));
	}

}
