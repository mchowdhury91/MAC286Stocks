/**
 * 
 * @author: Mohammed Chowdhury
 * 
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MAC286Date implements Comparable<MAC286Date>{

	Calendar date;
	
	// format yyyy-M-dd
	public MAC286Date(String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		Date d = sdf.parse(dateString);
		date = Calendar.getInstance();
		date.setTime(d);
	}
	
	public MAC286Date(long millis){
		
		Date d = new Date(millis);
		date = Calendar.getInstance();
		date.setTime(d);
		
	}

	public MAC286Date(int year, int month, int day){
		date = Calendar.getInstance();
		date.set(year, month-1, day);
	}
	
	public void setDay(int day){
		date.set(getYear(), getMonth(), day);
	}
	
	public int getDay(){		
		return date.get(Calendar.DATE);
	}

	public void setMonth(int month){
		date.set(getYear(), month-1, getDay());
	}	
	
	public int getMonth(){
		return date.get(Calendar.MONTH) + 1;
	}
	
	public void setYear(int year){
		date.set(year, getMonth(), getDay());
	}
	
	public int getYear(){
		return date.get(Calendar.YEAR);
	}
	
	public void setDate(String dateString) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
		Date d = sdf.parse(dateString);
		
		date.setTime(d);		
	}

	public void setDate(long millis){
		Date d = new Date(millis);
		date = Calendar.getInstance();
		date.setTime(d);		
	}
	
	public Calendar getDate(){
		return date;
	}
	
	public int daysBetween(MAC286Date d2){
		
		return (int)((date.getTimeInMillis() - d2.getDate().getTimeInMillis()) / (1000L * 60 * 60 * 24)); 
		
	}
	
	public String toString(){
		int month = getMonth();
		String str;
		if(month < 10){
			str = getYear() + "-0" + getMonth();			
		}else{
			str = getYear() + "-" + getMonth();
		}
		
		int day = getDay();
		
		if(day < 10){
			str += "-0" + getDay();
		}else{
			str += "-" + getDay();
		}
		
		return str;
	}

	@Override
	public int compareTo(MAC286Date o) {
		// TODO Auto-generated method stub
		
		return this.getDate().compareTo(o.getDate());
	}
}
