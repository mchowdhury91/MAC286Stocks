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
		
		MAC286Date date = new MAC286Date("2017-08-30");
		long time = date.getDate().getTimeInMillis() / 1000L;
		
		System.out.println(time);
	}

}
