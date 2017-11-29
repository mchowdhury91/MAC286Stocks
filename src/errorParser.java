import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class errorParser {

	public errorParser() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		
		/**
		ArrayList<String> symbolList = new ArrayList<String>();
		
		try{
			BufferedReader bR = new BufferedReader(new FileReader("errors.txt"));
			
			String line = "";
			while((line = bR.readLine()) != null){
				if(line.contains(".csv") && !symbolList.contains(line)){
					symbolList.add(line);
					System.out.println(line);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		**/
		
		
		BigMain bm = new BigMain();
		
		ArrayList<String> symList = bm.createSymListArray();
		String nL = System.getProperty("line.separator");
		BufferedWriter bW = null;
		
		try {
			bW = new BufferedWriter(new FileWriter("discrepancies.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		
		for(String symFileName : symList){
			File symFile = new File("./Data/" + symFileName);
			try{
				DataArray dA = new DataArray();
				dA.load(symFile);
				
				for(int i = 1; i < dA.getSize(); i++){
					if(Math.abs(dA.get(i).getOpen() - dA.get(i-1).getOpen()) > 30f){
						System.out.println(dA.get(i));
						bW.write(dA.get(i) + symFileName + nL);
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		try {
			bW.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
