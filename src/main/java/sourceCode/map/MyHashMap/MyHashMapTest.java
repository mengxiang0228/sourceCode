package sourceCode.map.MyHashMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyHashMapTest {

	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<>();
		
		for (int i = 0; i < 16; i++) {
			map.put(i, i+"a");
		}

		
//		map.keySet().forEach(e -> System.out.println(e));
//		
//		map.forEach((e1,e2)->{
//			System.out.println(e1+"   "+e2);
//		});

		MyHashMap<Integer, String> map1 = new MyHashMap<>();
		
		for (int i = 0; i < 100; i++) {
			map1.put(i, i+"a");
		}
//		map1.keySet().forEach(e -> System.out.println(e));
		Iterator<Integer> iterator =map1.keySet().iterator();
		System.out.println("---------------");
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			System.out.println(integer);
			
		}
		
	}

	static int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}
}
