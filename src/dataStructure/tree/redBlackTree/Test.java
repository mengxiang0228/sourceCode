package dataStructure.tree.redBlackTree;

public class Test {
	public static void main(String[] args) {
//		HashMap<K, V>
//		Red_BlackTree<Integer> tree = new Red_BlackTree<>();
		RBTreeByHashMap<Integer> tree = new RBTreeByHashMap<>();
		long start = System.currentTimeMillis();
//		int[] is = {20,90,70,80,85,84};
//		int[] is = {20,30,40,50};
//		for (int i : is) {
//			tree.put(i);
//		}
		for (int i = 0; i < 20; i++) {
			tree.put(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("插入耗时:"+(end-start)+"毫秒");

		tree.toString();
//		System.out.println(tree.getRoot());
//		System.out.println(tree.get(60000));
		tree.remove(3);
//		System.out.println(tree.get(60000));
//		System.out.println(tree.get(50000));
//		tree.toString();
//		list.root1();
	}
	
	public static void test1(int i ) {
		
	}
	
}
