import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Vector;


/*
 * Dependencies:
 * Bar.java
 * MAC286Date.java
 */
public class DataArray implements Iterable<Bar>{

	private Vector<Bar> barVector;
	private String symbol;
	private String path;
	
	/**
	 * 
	 * @param symbol
	 * @param path
	 */
	public DataArray(String symbol, String path){
		barVector = new Vector<Bar>(1000,100);
		this.symbol = symbol;
		this.path = path;
	}
	
	public DataArray(){
		symbol = "";
		path = "";
		barVector = new Vector<Bar>(1000,100);
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
	
	public void addAtHead(Bar b){
		barVector.insertElementAt(b, 0);
	}
	
	public void insert(Bar b, int index){
		barVector.insertElementAt(b, index);
	}
	
	public Bar at(int index){
		try{
			return barVector.get(index);
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	public Bar get(int index){
		try{
			return barVector.get(index);
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	public Bar remove(int index){
		return barVector.remove(index);
	}
	
	public Bar remove(){
		return barVector.remove(0);
	}
	
	public int load(){
		try{			
			BufferedReader in = new BufferedReader(new FileReader(path + "/" + symbol + ".csv"));
			in.readLine();
			
			int count = 0;
			String line = null;
			while((line = in.readLine()) != null){
				barVector.add(new Bar(line));
				count++;
			}
			
			in.close();
			return count;
		}catch(FileNotFoundException e){
			System.out.println("Nothing was loaded because " + path + "/" + symbol + ".csv was not found");
			e.printStackTrace();
			return 0;			
		}
		catch(IOException e){
			System.out.println("Nothing was loaded because of an IOException");
			e.printStackTrace();
			return 0;
		} catch (ParseException e) {
			System.out.println("Nothing was loaded because of a ParseException");
			e.printStackTrace();
			return 0;
		}		
	}

	public int load(File dateFile){
		try{			
			BufferedReader in = new BufferedReader(new FileReader(dateFile));
			in.readLine();
			
			int count = 0;
			String line = null;
			while((line = in.readLine()) != null){
				barVector.add(new Bar(line));
				count++;
			}
			
			in.close();
			return count;
		}catch(FileNotFoundException e){
			e.printStackTrace();
			return 0;			
		}
		catch(IOException e){
			System.out.println("Nothing was loaded because of an IOException");
			e.printStackTrace();
			return 0;
		} catch (ParseException e) {
			System.out.println("Nothing was loaded because of a ParseException");
			e.printStackTrace();
			return 0;
		}		
	}	
	
	public int size(){
		return getSize();
	}
	
	@Override
	public Iterator<Bar> iterator() {
		return barVector.iterator();
	}
}
