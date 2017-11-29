import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Vector;


/*
 * Dependencies:
 * DataBar.java
 * MAC286Date.java
 */
public class DataArray implements Iterable<DataBar>{

	private Vector<DataBar> barVector;
	private String symbol;
	private String path;
	
	/**
	 * 
	 * @param symbol
	 * @param path
	 */
	public DataArray(String symbol, String path){
		barVector = new Vector<DataBar>(1000,100);
		this.symbol = symbol;
		this.path = path;
	}
	
	public DataArray(){
		symbol = "";
		path = "";
		barVector = new Vector<DataBar>(1000,100);
	}
	
	public String getSymbol(){ return symbol; }
	public String getPath() { return path;	}
	public Vector<DataBar> getBarVector(){ return barVector; }
	
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
	
	public void add(DataBar b){
		barVector.add(b);
	}
	
	public void addAtHead(DataBar b){
		barVector.insertElementAt(b, 0);
	}
	
	public void insert(DataBar b, int index){
		barVector.insertElementAt(b, index);
	}
	
	public DataBar at(int index){
		try{
			return barVector.get(index);
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	public DataBar get(int index){
		try{
			return barVector.get(index);
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	public DataBar remove(int index){
		return barVector.remove(index);
	}
	
	public DataBar remove(){
		return barVector.remove(0);
	}
	
	public int load(){
		try{			
			BufferedReader in = new BufferedReader(new FileReader(path + "/" + symbol + ".csv"));
			in.readLine();
			
			int count = 0;
			String line = null;
			while((line = in.readLine()) != null){
				barVector.add(new DataBar(line));
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

	public int load(File dataFile){
		try{	
			
			BufferedReader in = new BufferedReader(new FileReader(dataFile));
			in.readLine();
			
			int count = 0;
			String line = null;
			while((line = in.readLine()) != null){
				DataBar dataBar;
				try{
					dataBar = new DataBar(line);
				}catch(NumberFormatException e){
					String newline = System.getProperty("line.separator");
					System.out.println("DATABAR ERROR: " + line);
					BufferedWriter bW = new BufferedWriter(new FileWriter("errors.txt", true));
					bW.write(line + newline);
					bW.write(dataFile.getName() + newline);
					bW.close();
					dataBar = null;
				}
				
				if(dataBar != null){
					barVector.add(new DataBar(line));
					count++;
				}
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
	public Iterator<DataBar> iterator() {
		return barVector.iterator();
	}
}
