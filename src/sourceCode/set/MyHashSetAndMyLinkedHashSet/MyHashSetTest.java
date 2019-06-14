package sourceCode.set.MyHashSetAndMyLinkedHashSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashSetTest {
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		set.add("3");
		set.add("4");
		set.add("5");
		System.out.println(set);
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
	}
}
