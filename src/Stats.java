import java.lang.reflect.Field;

public class Stats {
	private int a, b;
	
	public Stats(){
		a = 1;
		b = 2;
	
	}
	
	public void print() throws IllegalArgumentException, IllegalAccessException{
		for(Field f: getClass().getDeclaredFields()){
			System.out.println(f.get(this));
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
		Stats a = new Stats();
		a.print();
	}
}
