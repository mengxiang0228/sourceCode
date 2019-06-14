package sourceCode.list.MyVector;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class MyVectorTest {

	public static void main(String[] args) {
		Vector<String> list = new Vector<>();
		list.add("1");
		list.add("1");
		list.add("1");
		System.out.println(list);
//		list.setSize(20);
		System.out.println(list);
		System.out.println("自写Vector");
		List<String> myList = new MyVector<>();
		myList.add("1");
		myList.add("2");
		myList.add("3");
		myList.add("4");
		myList.add("5");
		System.out.println(myList);
		myList.set(1, "9");
		System.out.println(myList);
		Collections.sort(myList);
		System.out.println(myList);
	}

}
