import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

public class DataArray {

	private Vector<Bar> barVector;
	private String symbol;
	private String path;
	
	public DataArray(String symbol, String path){
		barVector = new Vector<Bar>(1000,100);
		this.symbol = symbol;
		this.path = path;
	}
	
	public String getSymbol(){ return symbol; }
	public String getPath() { return path;	}
	public Vector<Bar> getBarVector(){ return barVector; }
	
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	
	public void setPath(String path){
		this.path = path;
	}
	
	public int getSize(){
		return barVector.size();
	}
	
	public void resize(int newSize){
		barVector.setSize(newSize);
	}
	
	public boolean isEmpty(){
		return barVector.isEmpty();
	}
	
	public boolean isFull(){
		return isFull();
	}
	
	public void add(Bar b){
		barVector.add(b);
	}
	
	public Bar at(int index){
		return barVector.get(index);
	}
	
	public Bar get(int index){
		return barVector.get(index);
	}
	
	public int load() throws IOException, ParseException{
		BufferedReader in = new BufferedReader(new FileReader(path + "\\" + symbol + "_daily.csv"));
		in.readLine();
		
		int count = 0;
		String line = null;
		while((line = in.readLine()) != null){
			barVector.add(new Bar(line));
			count++;
		}
		
		return count;
		
	}
}
