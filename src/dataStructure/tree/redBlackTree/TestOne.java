package dataStructure.tree.redBlackTree;

import java.util.Random;

/**
 * 自写 HashMap红黑树的实现 测试 删除节点的后继节点 只会是他父节点的左子，不会是它父节点的右子 这是在debug下进行，同时在删除方法里面打断点
 * @author 李雅翔
 * @date 2017年9月30日
 */
public class TestOne {
	public static void main(String[] args) {
		RBTreeByHashMap<Integer> tree = new RBTreeByHashMap<>();
		Random random = new Random();
		for (int i = 0; i < 500000; i++) {
			
			tree.put(random.nextInt(60000));
			tree.remove(random.nextInt(60000));
		}
		System.out.println(tree.getRoot());
		System.out.println("数据生成完毕");
//		tree.toString();
		for(int i = 0; i < 100000; i++) {
			tree.remove(random.nextInt(60000));
		}
		System.out.println("删除操作完成");
//		tree.toString();
	}
}
