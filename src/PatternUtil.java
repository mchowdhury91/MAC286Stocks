import java.util.Vector;

public class PatternUtil {

	public static boolean sixtyDayHigh(int t, DataArray dataArray){
		
		Vector<DataBar> barList = dataArray.getBarVector();
		float max = barList.get(t).getHigh();
		for(int i = t-1; i >= t-59; i--){
			if(barList.get(i).getHigh() >= max){
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean nDayHigh(int t, DataArray dataArray, int n){
		
		if(t < (n-1)){
			System.out.println("For nDayHigh, your initial day must be greater than n-1");
			return false;
		}
		
		Vector<DataBar> barList = dataArray.getBarVector();
		float max = barList.get(t).getHigh();
		for(int i = t-1; i >= 0; i--){
			if(barList.get(i).getHigh() >= max){
				return false;
			}
		}
		
		return true;
	}

	public static boolean sixtyDayLow(int t, DataArray dataArray){
		
		Vector<DataBar> barList = dataArray.getBarVector();
		
		float min = barList.get(t).getLow();
		for(int i = t-1; i >= 0; i--){
			if(barList.get(i).getLow() <= min){
				return false;
			}
		}
		
		return true;
	}	
	
	public static boolean largest5DayRange(int t, DataArray dataArray){
		
		Vector<DataBar> barList = dataArray.getBarVector();
		
		float max = barList.get(t).getRange();
		for(int i = t-1; i >= t-4; i--){
			if(barList.get(i).getRange() >= max){
				return false;
			}
		}
		
		return true;
	}

	public static boolean largestNDayRange(int t, DataArray dataArray, int n){
		
		Vector<DataBar> barList = dataArray.getBarVector();
		
		float max = barList.get(t).getRange();
		for(int i = t-1; i >= t-(n-1); i--){
			if(barList.get(i).getRange() >= max){
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean tradesUnderYesterdaysLow(int t, DataArray dataArray){
		Vector<DataBar> barList = dataArray.getBarVector();
		return (barList.get(t).getLow() < barList.get(t-1).getLow());
		
	}

	public static boolean tradesOverYesterdaysLow(int t, DataArray dataArray){
		Vector<DataBar> barList = dataArray.getBarVector();
		return (barList.get(t).getLow() > barList.get(t-1).getLow());
		
	}
	
	public static boolean tradesOverYesterdaysHigh(int t, DataArray dataArray){
		Vector<DataBar> barList = dataArray.getBarVector();
		return (barList.get(t).getHigh() > barList.get(t-1).getHigh());
	}	
	
	public static boolean tradesUnderYesterdaysHigh(int t, DataArray dataArray){
		Vector<DataBar> barList = dataArray.getBarVector();
		return (barList.get(t).getHigh() < barList.get(t-1).getHigh());
	}		
	
	public static boolean outsideDay(int t, DataArray dataArray){
		return (tradesOverYesterdaysHigh(t, dataArray) && tradesUnderYesterdaysLow(t, dataArray));
	}

	public static boolean insideDay(int t, DataArray dataArray){
		return (tradesUnderYesterdaysHigh(t, dataArray) && tradesOverYesterdaysLow(t, dataArray));
	}
	
}
