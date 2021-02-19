package algorithm.ShuDU;

/**
 * 确认每个九宫格中的值以及一些处理小方法
 * @author 李雅翔
 * @date 2018年2月26日
 */
public class Two {

	/**
	 * 确定每个小九宫格的值,转化为数字存储
	 * @param t1
	 */
	public static int[] t5(int[][] t1) {
		int[] is = new int[9];
		int[] small_nine_palace = new int[9];
		int num = 0;
		int place = 0;
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 9; i++) {
				for (int j = k * 3; j < k * 3+3; j++) {
					is[num++] = t1[j][i];
				}
				if ((i+1) % 3 == 0) {
					num = 0;
//					System.out.println(Arrays.toString(is));
					small_nine_palace[place++] = t51(is);
					is = new int[9];	
				}
			}
		}
		return small_nine_palace;
	}
	
	/**
	 * 将存放 1-9个数字 的数组 转化为 0-1的形式,
	 * 将 0-1的形式 的数组以二进制转化为 十进制数字
	 * @param t1
	 */
	public static int t51(int[] t1) {
		int[] intL = new int[9];
		// 将存放 1-9个数字 的数组 转化为 0-1的形式
		for (int i : t1) {
			if (i != 0) {
				intL[i-1] = 1;
			}
		}
		// 将 0-1的形式 的数组以二进制转化为 十进制数字
		return g1(intL);
	}
	
	
	
	/**
	 * 返回每个小九宫格中的数
	 * @param t1 输入的数组
	 * @param t2 存放的数组
	 * @return
	 */
	public static void t52(int[][] t1,int[][] t2) {
		int[] is = new int[9];
		int num = 0;
		int place = 0;
		for (int k = 0; k < 3; k++) {
			for (int i = 0; i < 9; i++) {
				for (int j = k * 3; j < k * 3+3; j++) {
					is[num++] = t1[j][i];
				}
				if ((i+1) % 3 == 0) {
					num = 0;
//					System.out.println(Arrays.toString(is));
					t2[place++] = is;
					is = new int[9];	
				}
			}
		}
	}
	
	/**
	 * 将 00000010（二进制） 转化为 数字 2（十进制）
	 * @param t1 输入的二进制各位置的值
	 * @return
	 */
	public static int g1(int[] t1) {
		String number = "";
		for (int i : t1)
			number += i;
		return Integer.valueOf(number, 2);
	}
	
	/**
	 * 将数字转化为1-9的数组 g2为测试使用,直接打印出来
	 * @param t1
	 */
	public static void g2(int num) {
		if (num == 0) {
			System.out.println(0);
			return;
		}
		int[] intL = new int[9];
		String numberStr = Integer.toBinaryString(num);
		String[] strings = numberStr.split("");
		int j = 8;
		for (int i = strings.length; i >0; i--) {
			intL[j--] = Integer.valueOf(strings[i-1]);
		}
		String string = "";
		for (int i = 0; i < intL.length; i++) {
			if (intL[i] == 1) {
				string += (i+1+",");
			}
		}
		System.out.println(string);
	}
	
	
	/**
	 * 将数字转化为1-9的数字 ，正式使用
	 * @param t1
	 */
	public static int g3(int num) {
		if (Integer.bitCount(num) != 1) {
			return 0;
		}
		int[] intL = new int[9];
		String numberStr = Integer.toBinaryString(num);
		String[] strings = numberStr.split("");
		int j = 8;
		for (int i = strings.length; i >0; i--) {
			intL[j--] = Integer.valueOf(strings[i-1]);
		}
		for (int i = 0; i < intL.length; i++) {
			if (intL[i] == 1) {
				return (i+1);
			}
		}
		return 0;
	}	
	
	/**
	 * 将十进制数转化为二进制数组
	 * @param num
	 * @return
	 */
	public static int[] g4(int num) {
		int[] intL = new int[9];
		String numberStr = Integer.toBinaryString(num);
		String[] strings = numberStr.split("");
		int j = 8;
		for (int i = strings.length; i >0; i--) {
			intL[j--] = Integer.valueOf(strings[i-1]);
		}
		return intL;
	}
	
	
	
}
