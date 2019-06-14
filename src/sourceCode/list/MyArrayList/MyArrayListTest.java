package sourceCode.list.MyArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyArrayListTest {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		Collections.sort(list);
		System.out.println(list.size());
		ListIterator<String> listIterator = list.listIterator();
		while (listIterator.hasNext()) {
//			String string = (String) listIterator.next();
//			listIterator.add("1");
			System.out.println(listIterator.next());
		}
		list = list.subList(0, 2);
		System.out.println("subList");
		for (String string : list) {
			System.out.println(string);
		}
		
		System.out.println("自写ArrayList 方法");
		MyArrayList<String> myList = new MyArrayList<>();
		myList.add("1");
		myList.add("2");
		myList.add("3");
		myList.add("4");
		myList.add("5");
		//走了迭代器了
		System.out.println(myList);
		myList.remove(1);
		System.out.println(myList);
		Iterator<String> iterator = myList.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			System.out.println(string);
			
		}
//		myList.clear();
		System.out.println(myList);
		@SuppressWarnings("rawtypes")
		List list2 = (MyArrayList)myList.clone();
		System.out.println(list2);
		
		
	}
	

}
