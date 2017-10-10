import java.text.ParseException;

public class NodeTester {

	public static void main(String[] args){
		Bar b0 = null;
		Bar b1 = null;
		Bar b2 = null;
		try {
			b0 = new Bar("2008-01-03,20,21,19,20.5,20.5,1000000");
			b1 = new Bar("2008-01-04,21,22,20,21,21,1500000");
			b2 = new Bar("2008-01-05,21.5,22,18,18.5,18.5,2000000");
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		MAC286Node<Bar> node0 = new MAC286Node<Bar>(b2, null);
		node0 = new MAC286Node<Bar>(b1, node0);
		node0 = new MAC286Node<Bar>(b0, node0);
		
		
//		MAC286Node<Bar> current = node0;
//		
//		while(current.hasNext()){
//			current.current().display();
//			current = current.next();
//			System.out.println();
//		}
//		
//		current.current().display();
		
		MAC286LinkedList<Bar> list = new MAC286LinkedList<Bar>(node0);
		
		list.get(0).display();
		list.get(1).display();
		list.get(2).display();
	}
	
}
