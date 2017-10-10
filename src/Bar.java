import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Mohammed Chowdhury
 *
 *
 */

/*
 * Dependencies:
 * MAC286Date.java
 */
public class Bar {

	
	MAC286Date date;
	float open, high, low, close, adjClose;
	int volume;
	
	public Bar(String str) throws ParseException {
		
		String[] dataArray = str.split(",");
		
		date = new MAC286Date(dataArray[0]);
		
		open = Float.parseFloat(dataArray[1]);
		high = Float.parseFloat(dataArray[2]);
		low = Float.parseFloat(dataArray[3]);
		close = Float.parseFloat(dataArray[4]);
		adjClose = Float.parseFloat(dataArray[5]);
		
		volume = Integer.parseInt(dataArray[6]);
		
	}

	public MAC286Date getDate() {
		return date;
	}

	public float getOpen() {
		return open;
	}

	public float getHigh() {
		return high;
	}

	public float getLow() {
		return low;
	}

	public float getClose() {
		return close;
	}

	public float getAdjClose() {
		return adjClose;
	}

	public int getVolume() {
		return volume;
	}
	
	public float getRange(){
		return high-low;
	}
	
	public void display(){
		System.out.println("Date: " + this.getDate());
		System.out.println("Open: " + this.getOpen());
		System.out.println("High: " + this.getHigh());
		System.out.println("Low: " + this.getLow());
		System.out.println("Close: " + this.getClose());
		System.out.println("Adj Close: " + this.getAdjClose());
		System.out.println("Volume: " + this.getVolume());
	}
	
}
