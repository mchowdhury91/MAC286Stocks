import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class DownloadAll {

	public static void main(String[] args) throws IOException, ParseException {
		
		// modify the following variables to change the dates
		String date1Str = "2006-01-01";
		String date2Str = "2016-12-31";
		
		// do not modify any of the below
		Downloader downloader = new Downloader();
		
		String saveDirectory = "Data";
		File directory = new File(saveDirectory);
		
		if(!directory.exists()){
			System.out.println("Creating directory " + directory.getAbsolutePath());
			
			try{
				directory.mkdir();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		BufferedReader bR = new BufferedReader(new FileReader("indices.txt"));
		String line;

		while((line = bR.readLine()) != null){
			// remove any new line and carriage return characters
			line = line.replaceAll("\\n", "");
			line = line.replaceAll("\\r", "");
			File f = new File(saveDirectory + "/" + line + ".csv");
			if(!f.exists())
				downloader.downloadHistoricalData(line, date1Str, date2Str, saveDirectory, line + ".csv");
		}
		
		bR.close();
		
		bR = new BufferedReader(new FileReader("stocks.txt"));
		while((line = bR.readLine()) != null){
			// remove any new line and carriage return characters
			line = line.replaceAll("\\n", "");
			line = line.replaceAll("\\r", "");
	
			File f = new File(saveDirectory + "/" + line + ".csv");
			if(!f.exists())
				downloader.downloadHistoricalData(line, date1Str, date2Str, saveDirectory, line + ".csv");
		}
		
		bR.close();
	}

}
