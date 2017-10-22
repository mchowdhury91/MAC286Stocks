/**
 * @author: Mohammed Chowdhury
 * Description: Downloads historical data for a company from yahoo finance
 * Usage:
 * Create an instance of downloader, and call downloadHistoricalData(String symbol, String start, String end, String filename)
 * start and end should be in the form "yyyy-MM-dd"
 * Example:
 * 
 * Downloader downloader = new Downloader();
 * downloader.downloadHistoricalData("GOOGL", "2016-08-30", "2017-08-30", "GOOGL.csv");
 * 
 * will download the stock data for Google for a year between August 30th 2016 and 2017 to a file called
 * GOOGL.csv
 */
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Dependencies:
 * MAC286Date.java
 */

public class Downloader {
	
	private String getCookie(HttpURLConnection con){
		String cookie = con.getHeaderField("Set-Cookie").split(";")[0];
		return cookie;
	}
	
	private String unicodeDecode(String string){
		
		Pattern pattern = Pattern.compile("\\\\u(\\p{XDigit}{4})");
		Matcher matcher = pattern.matcher(string);
		
		StringBuffer sb = new StringBuffer(string.length());
		
		while(matcher.find()){
			String ch = String.valueOf((char) Integer.parseInt(matcher.group(1), 16));
			matcher.appendReplacement(sb, Matcher.quoteReplacement(ch));
		}
		matcher.appendTail(sb);
		
		string = sb.toString();
//		System.out.println(string);
		return string;
	}
	
	private String getCrumb(HttpURLConnection con) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String regex_crumb = ".*\"CrumbStore\":\\{\"crumb\":\"([^\"]+)\"\\}.*";
		Pattern pattern = Pattern.compile(regex_crumb);
		
		String crumb = null;
		String line = null;
		while(crumb == null && (line = in.readLine()) != null){
			Matcher matcher = pattern.matcher(line);
			if(matcher.matches()){
				crumb = matcher.group(1);
			}
		}
		
		in.close();
		
//		System.out.println("Before decoding: " + crumb);
		
		return unicodeDecode(crumb);
	}
	
	private void downloadData(String urlStr, String cookie, String path, String filename) throws IOException{
		InputStream is = null;
		FileOutputStream fos = null;
		
		URL url2 = new URL(urlStr);
		try{
			HttpURLConnection urlConn = (HttpURLConnection) url2.openConnection();
			urlConn.setRequestProperty("Cookie", cookie);
//			System.out.println(cookie);
			is = urlConn.getInputStream();
			fos = new FileOutputStream(path + "/" + filename);
			
			byte[] buffer = new byte[4096];
			int len;
			
			while((len = is.read(buffer)) > 0){
				fos.write(buffer, 0, len);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try{
				if(is != null){
					is.close();
				}
			}finally{
				if(fos != null){
					fos.close();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param symbol : symbol for each stock, see finance.yahoo.com
	 * @param start : should be in the form yyyy-MM-dd, eg: 2017-08-09
	 * @param end : see start
	 * @param filename : name of file to save data to.
	 * @throws IOException
	 * @throws ParseException
	 */
	public void downloadHistoricalData(String symbol, String start, String end, String path, String filename) throws IOException, ParseException{
		
		// getting cookie and crumb
		String urlStr = String.format("https://finance.yahoo.com/quote/%s/history", symbol);
		
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();		
		
		int responseCode = con.getResponseCode();
		
		// this is getting the actual cookie and storing it in a string
		String cookie = getCookie(con);
		
		System.out.println("\nSending 'GET' request to URL: " + urlStr);
		System.out.println(("Response code : " + responseCode));

		// the following part reads the html file from the get request to url string to get the crumb
		// the crumb is the ending of the url that gives us authorization
		// it is based on the cookie, so we need both the crumb and  the cookie from the
		// GET request to https://finance.yahoo.com/quote/SYMBOL/history to download the csv
		String crumb = getCrumb(con);
		System.out.println("crumb=" + crumb);
		
		// now we have our crumb and cookie in crumb and cookie variables
		// no we generate the url to download the csv file:
		
		String baseURL = "https://query1.finance.yahoo.com/v7/finance/download/";
		
		MAC286Date startDate = new MAC286Date(start);
		MAC286Date endDate = new MAC286Date(end);
		
//		endDate.setDay(endDate.getDay()+1);
		
		long period1 = startDate.getDate().getTimeInMillis() / 1000L;
		long period2 = endDate.getDate().getTimeInMillis() / 1000L;
		
		String urlStr2 = baseURL + symbol + "?period1=" + period1;
		urlStr2 += "&period2=" + period2;
		urlStr2 += "&interval=1d";
		urlStr2 += "&events=history";
		urlStr2 += "&crumb=" + crumb;
		
		System.out.println(urlStr2);
		
		downloadData(urlStr2, cookie, path, filename);
	}

	
}
