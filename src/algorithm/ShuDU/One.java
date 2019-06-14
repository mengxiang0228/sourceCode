package algorithm.ShuDU;

import java.util.Arrays;

/**
 * 第一次的代码
 * @author 李雅翔
 * @date 2018年3月5日
 */
public class One {
	public static int[][] initial_data = new int[9][9];//初始二维数组
	public static int[][] initial_data1 = new int[9][9];//存放结果二维数组
	
	public static void main(String[] args) {
		
//		int[] t1 = {1,2,3,4,6,7,8,9};
//		int[] t2 = {1,4,9};
//		int[] t3 = {1,2,4,7,8};
//		String[] strings = {
//				"950004008",
//				"246070059",
//				"780609234",
//				"860713002",
//				"300000007",
//				"100942083",
//				"598206071",
//				"410080396",
//				"600400025"};
		
//		String[] strings = {
//				"700508000",
//				"040000000",
//				"000030506",
//				"080050360",
//				"096000180",
//				"010003200",
//				"000000600",
//				"000740001",
//		"270009000"};
				
	}
	
	/**
	 * 寻找数组中缺少的数字
	 * @param num
	 * @return
	 */
	public static int t1_N(int num) {
		return num ^ 511;
	}
	
	/**
	 * 寻找指定位置可能的值
	 * @param t1
	 * @param t2
	 */
	public static int[] t2(int[] t1, int[] t2, int[] t3) {
		t21(t1, t2);
		t21(t1, t3);
		System.out.println(Arrays.toString(t1));
		return t1;
	}
	
	/**
	 * 寻找指定位置可能的值
	 * @param t1
	 * @param t2
	 */
	public static int t2_N(int t1, int t2, int t3) {
		return t1 ^ t2 ^ t3;
	}
	/**
	 * 寻找两个数组都有的值
	 * @param t1
	 * @param t2
	 */
	public static void t21(int[] t1, int[] t2) {
		for (int i = 0; i < 9; i++) {
			if (t1[i] == 1 && t2[i] == 1)
				continue;
			t1[i] = 0;
		}
		t2 = null;
	}
	
}
