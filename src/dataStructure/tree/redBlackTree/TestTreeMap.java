package dataStructure.tree.redBlackTree;

public class TestTreeMap {

	public static void main(String[] args) {
		RBTreeByTreeMap<Integer, Integer> MytreeMap = new RBTreeByTreeMap<>();
//		TreeMap<Integer, Integer> treeMap = new TreeMap<>();
		int[] is = {20,90,70,80,85,82};
		for (int i : is) {
			System.out.println(i);
			MytreeMap.put(i, i);
		}


		MytreeMap.remove(70);

	}

}
