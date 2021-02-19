package sourceCode.list.MyLinkedList;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedListTest {

	public static void main(String[] args) {
		List<Integer> list = new LinkedList<Integer>();

		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(1);
		System.out.println(list);
		//测试 linkBefore
		list.add(2, 9);
		System.out.println(list);
		
		
		System.out.println("自写");
		List<Integer> myList = new MyLinkedList<Integer>();
		myList.add(1);
		myList.add(2);
		myList.add(3);
		myList.add(4);
		myList.add(5);
		myList.add(6);
		System.out.println("读取自写 ： "+myList);

		
	}

}
