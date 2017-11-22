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
 * Dependencies: MAC286Date.java
 */
public class DataBar {

	private MAC286Date date;
	private float open, high, low, close, adjClose;
	private int volume;

	// TODO: open = open * adjClose / close (make sure close != 0)
	// TODO: same for low, high

	public DataBar() {
		try {
			date = new MAC286Date("2000-01-01");
		} catch (Exception e) {
			System.out.println("default bar constructor threw parse error");
			System.exit(1);
		}
		open = 0f;
		high = 0f;
		low = 0f;
		close = 0f;
		adjClose = 0f;

		volume = 0;
	}

	public DataBar(String str) throws ParseException {
		str = str.trim();
		String[] dataArray = str.split(",");

		date = new MAC286Date(dataArray[0].trim());

		adjClose = Float.parseFloat(dataArray[5].trim());
		close = Float.parseFloat(dataArray[4].trim());
		open = Float.parseFloat(dataArray[1].trim());
		high = Float.parseFloat(dataArray[2].trim());
		low = Float.parseFloat(dataArray[3].trim());

		volume = Integer.parseInt(dataArray[6].trim());

	}

	public void setBar(String str) throws ParseException {
		String[] dataArray = str.split(",");

		date = new MAC286Date(dataArray[0].trim());

		open = Float.parseFloat(dataArray[1].trim());
		high = Float.parseFloat(dataArray[2].trim());
		low = Float.parseFloat(dataArray[3].trim());
		close = Float.parseFloat(dataArray[4].trim());
		adjClose = Float.parseFloat(dataArray[5].trim());

		volume = Integer.parseInt(dataArray[6].trim());
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

	public float getRange() {
		return high - low;
	}

	public void setDate(MAC286Date date) {
		this.date = date;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public void setAdjClose(float adjClose) {
		this.adjClose = adjClose;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String toString() {
		String str = "";
		str += ("Date: " + this.getDate());
		str += ("\nOpen: " + this.getOpen());
		str += ("\nHigh: " + this.getHigh());
		str += ("\nLow: " + this.getLow());
		str += ("\nClose: " + this.getClose());
		str += ("\nAdj Close: " + this.getAdjClose());
		str += ("\nVolume: " + this.getVolume());
		return str;
	}

	public void display() {
		System.out.println("Date: " + this.getDate());
		System.out.println("Open: " + this.getOpen());
		System.out.println("High: " + this.getHigh());
		System.out.println("Low: " + this.getLow());
		System.out.println("Close: " + this.getClose());
		System.out.println("Adj Close: " + this.getAdjClose());
		System.out.println("Volume: " + this.getVolume());
	}

}
