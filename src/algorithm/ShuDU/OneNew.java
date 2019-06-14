package algorithm.ShuDU;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 第二次的代码，没有遍历方法，所以复杂数组得不出结果
 * @author 李雅翔
 * @date 2018年3月5日
 */
public class OneNew {
	
	public static int[][] initial_data = new int[9][9];//初始二维数组
//	public static int[][] initial_data2 = new int[9][9];//存放结果二维数组
	
//	public static int[] line = new int[9];//每一行存放数字
//	public static int[] column = new int[9];//每一列存放数字
//	public static int[] small_nine_palace = new int[9];//每个小九宫格存放数字
	
	public static int[][] line_mb = new int[9][9];//存放中间过程二维数组
//	public static int[][] column_mb = new int[9][9];//每一列存放可能的数字
//	public static int[][] small_nine_palace_mb = new int[9][9];//每个小九宫格存放可能的数字
	
	public static DecimalFormat df=new DecimalFormat("000000000");
	
	public static boolean b = false;
	public static boolean isNext = true;
	
	
	public static void main(String[] args) {
//		int[][] t1 = {{7,0,0,5,0,8,0,0,0},
//				{0,4,0,0,0,0,0,0,0},
//				{0,0,0,0,3,0,5,0,6},
//				{0,8,0,0,5,0,3,6,0},
//				{0,9,6,0,0,0,1,8,0},
//				{0,1,0,0,0,3,2,0,0},
//				{0,0,0,0,0,0,6,0,0},
//				{0,0,0,7,4,0,0,0,1},
//				{2,7,0,0,0,9,0,0,0}};
		
		int[][] t1 = {
				  {9, 5, 0, 0, 0, 4, 0, 0, 8},
				  {2, 4, 6, 0, 7, 0, 0, 5, 9},
				  {7, 8, 0, 6, 0, 9, 2, 3, 4},
				  {8, 6, 0, 7, 1, 3, 0, 0, 2},
				  {3, 0, 0, 0, 0, 0, 0, 0, 7},
				  {1, 0, 0, 9, 4, 2, 0, 8, 3},
				  {5, 9, 8, 2, 0, 6, 0, 7, 1},
				  {4, 1, 0, 8, 0, 3, 8, 6, 0},
				  {6, 0, 0, 4, 0, 0, 0, 2, 5}};
//		initial_data2 = t1.clone();//二维数组的克隆 ，给过去的是地址
//		small_nine_palace = Two.t5(t1);
//		line = InputProcessing.lineNumber(t1);
//		column = InputProcessing.columnNumber(t1);
		for (int[] is : t1) {
			System.out.println(Arrays.toString(is));
		}
		t11(t1);//可能的值
		print();
		
		System.out.print("line_mb[0][3]值为：");
		Two.g2(line_mb[0][3]);
//		while (t9_N()) {
//			
//		}
		
//		t9_N();
//		print();
		
//		while (isNext) {
//			isNext = false;
//			while (t12()) {
//				isNext = true;
////				t9();
//			}
////			if (initial_data2[8][7] == 51) {
////				System.out.println("进入了1");
////			}
//			while (t14()) {
//				isNext = true;
//			}
////			if (initial_data2[8][7] == 51) {
////				System.out.println("进入了2");
////			}
//			while (t15()) {
//				isNext = true;
//			}
////			if (initial_data2[8][7] == 51) {
////				System.out.println("进入了3");
////			}
//			while (t16()) {
//				isNext = true;
//			}
//		}

		System.out.println();
//		for (int[] is : initial_data2) {
//			System.out.println(Arrays.toString(is));
//		}
//		for (int i = 1; i <= 9; i++) {
//			for (int[] is : initial_data2) {
//				for (int value : is) {
//					if (value == i) {
//						num++;
//					}
//				}
//			}
//			System.out.println(i+":"+num);
//			num = 0;
//		}
		
	}
	
	//打印结果数组
	public static void print() {
		System.out.println();
		for (int[] is : line_mb) {
			for (int i : is) {
				System.out.print(Two.g3(i)+" " );
			}
			System.out.println();
		}
	}
	
	/**
	 * 初步筛查
	 */
	//TODO 继续添加 对可填入数字的列表进行修正
	//FIXME 未对值进行验证
//	public static void t9() {
//		int t1 = 0;
//		for (int i = 0; i < 9; i++) {
//			for (int j = 0; j < 9; j++) {
//				//如果候选词只有一个
//				if (Integer.bitCount(line_mb[i][j]) == 1) {
////					initial_data2[i][j] = Two.g3(t1);
//					t10(i, j, t1);
//				}
//			}
//		}
//	}
	
	/**
	 * 初步筛查
	 */
	//TODO 继续添加 对可填入数字的列表进行修正
	public static boolean t9_N() {
		int t1 = 0;
		boolean b1 = false;
		Two.g2(line_mb[0][7]);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//如果候选词只有一个
				if ((Integer.bitCount(t1 = line_mb[i][j])) == 1) {
					if (b1) {
						t101_N(i, j, t1);
					}else {
						b1 = t101_N(i, j, t1);
					}
				}
			}
		}
		return b1;
	}
	/**
	 * 删除指定位置对应行，对应列、对应九宫格中 已经排除的数字
	 * @param i
	 * @param j
	 */
	//TODO T10
//	public static void t10(int i, int j, int t1) {
//		
//		int ninePlace = Two.t7(i, j);//九宫格位置
//		line[i] |= t1;
//		column[j] |= t1;
//		small_nine_palace[ninePlace] |=  t1;
//		line_mb[i][j] = 0;
////		000101
////		000100
////		000001
//	} 
	
	/**
	 * 删除候选数中 已经 排除的数
	 * @param is
	 * @param place
	 * 
	//	00010001 17   00010001 17
	//	00010000 16   00001000 08
	//	00000001 01   00010001 17
	 */
//	public static void t101(int[] is,int t1) {
//		int place = Two.g3(t1) -1;
//		int [] intL1 = new int[9];
//		for (int i = 0; i < is.length; i++) {
//			intL1 = Two.g4(is[i]);
//			intL1[place] = 0;
//			is[i] = Two.g1(intL1);
//		}
//	}
	
	/**
	 * 删除指定数对应行、列、九宫格中的候选数
	 * @param i
	 * @param j
	 * @param t1
	 * 有数据被修改
	 */
	public static boolean t101_N(int i, int j, int t1) {
		boolean b1 = false;
		//删除同一行,列中的候选数
		for (int k = 0; k < 9; k++) {
			if (k != j && ((line_mb[i][k]|t1) == line_mb[i][k])) {
				b1 = true;
				line_mb[i][k] ^= t1;
			}
			if (k != i  && ((line_mb[k][j]|t1) == line_mb[k][j])) {
				b1 = true;
				line_mb[k][j] ^= t1;
			}
		}
//		if (line_mb[0][3] == Integer.valueOf("001000000",2) ) {
//			System.out.println("i:"+i+"  j:"+j+"  t1:"+Two.g3(t1));
//			System.out.println("测试2");
//			print();
//			System.exit(0);
//		}
		for (int k = i/3*3; k <= i/3*3+2; k++) {
			for (int l = j/3*3; l <= j/3*3+2; l++) {
				if ((k != i || l != j) && ((line_mb[k][l]|t1) == line_mb[k][l])) {
					b1 = true;
					line_mb[k][l] ^= t1; 
				}
			}
		}
//		if (line_mb[0][0] != 1) {
//			System.out.println("测试3");
//		}
		return b1;
	}
	
	/**
	 * 在各位置添加可能的值
	 * @param t1 原数组 
	 */
	
	public static void t11(int[][] t1) {
		int[] line = InputProcessing.lineNumber(t1);
		int[] column = InputProcessing.columnNumber(t1);
		int[] small_nine_palace = Two.t5(t1);
		int num = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (t1[i][j] == 0) {
					int ninePlace = j/3+i/3*3;
					// 511 = 1 1111 1111
					int one = line[i] ^ 511;
					int two = column[j] ^ 511;
					int three = small_nine_palace[ninePlace] ^ 511;
					num = one & two & three;//确定可能位置的值
					line_mb[i][j] = num;
					if (i == 0 && j == 4) {
						System.out.println("方法添加可能的值");
						Two.g2(num);
					}
				}else {
					line_mb[i][j] = Two.t51(new int[] {t1[i][j]});
				}
			}
		}
		
	}
	
	/**
	 * 判断 数独中是否还有独立的一个数
	 * @return
	 */
//	public static boolean t12() {
//		for (int i = 0; i < 9; i++) {
//			for (int j = 0; j < 9; j++) {
//				if (Integer.bitCount(line_mb[i][j]) == 1) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	
//	/**
//	 * 每次修改完一个值以后 ，对行、列、九宫格可能值重新确定
//	 */
//	public static void t13() {
//		int num = 0;
//		for (int i = 0; i < 9; i++) {
//			for (int j = 0; j < 9; j++) {
//				int ninePlace = Two.t7(i, j);
//				int place = j%3*3 +i %3;//小九宫格中的位置	
//				num = line_mb[i][j] & column_mb[j][i] & small_nine_palace_mb[ninePlace][place];
//				line_mb[i][j] = num;
//				column_mb[j][i] = num;
//				small_nine_palace_mb[ninePlace][place] = num;
//			}
//		}
//	}
	
	/**
	 * 行唯一值
	 */
	public static boolean t14() {
		int num = 0;
		boolean b1 = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				num += Integer.valueOf(Integer.toBinaryString(line_mb[i][j]));
			}
			int index = df.format(num).indexOf("1");
			if (index != -1) {
				b1 = true;
				for (int j = 0; j < 9; j++) {
					if (Two.g4(line_mb[i][j])[index] == 1 ) {
						//256 1 0000 0000
						line_mb[i][j] = 256>>index;
						t101_N(i, j, 256>>index);
						break;
					}
				}
			}
			num = 0;
		}
		return b1;
	}
	
	/**
	 * 列唯一值
	 */
	public static boolean t15() {
		int num = 0;
		boolean b1 = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				num += Integer.valueOf(Integer.toBinaryString(line_mb[j][i]));
			}
			int index = df.format(num).indexOf("1");
			if (index != -1) {
				b1 = true;
				for (int j = 0; j < 9; j++) {
					if (Two.g4(line_mb[j][i])[index] == 1 ) {
						
						line_mb[j][i] = 256>>index;
						//FIXME 行列判断
						t101_N(i, j, 256>>index);
						break;
					}
				}
			}
			num = 0;
		}
		return b1;
	}
	
	/**
	 * 小九宫格唯一值
	 */
	public static boolean t16() {
		int[][] small_nine_palace_mb = new int[9][9];
		Two.t52(line_mb, small_nine_palace_mb);//确定每个小宫格中可能的数
		int num = 0;
		boolean b1 = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				num += Integer.valueOf(Integer.toBinaryString(small_nine_palace_mb[i][j]));
			}
			int index = df.format(num).indexOf("1");
			if (index != -1) {
				b1 = true;
				for (int j = 0; j < 9; j++) {
					if (Two.g4(small_nine_palace_mb[i][j])[index] == 1 ) {
						int h = i/3*3+j%3;//九宫格数字所在行
						int l = i%3*3+j/3;//九宫格数字所在列
						line_mb[h][l] = 256>>index;
						t101_N(h, l, 256>>index);
					}
				}
			}
			num = 0;
		}
		return b1;
	}
	
	
	public static boolean t17(int start,int end) {
		int[] numPlace = t18();
		int h = numPlace[0];//行
		int l = numPlace[1];//列
		int num = line_mb[h][l];
		int count = Integer.bitCount(num);
		for (int i = 0; i < count; i++) {
			int testValue = Integer.highestOneBit(num);
//			10010
//			10000
//			00010
			num ^= testValue;
			int[][] linShi = line_mb.clone();
			linShi[h][l] = 0;
//			t10(h, l, testValue);
		}
		return b;
	}
 
//	11111100
//	00010000
//	00010000
	public static boolean check(int i, int j, int num) {
//		int place = Two.t7(i, j);
//		if (((line[i]&num) == num) || ((column[i]&num) == num) || ((small_nine_palace[place]&num) == num)) {
//			return false;
//		}
		return true;
	}
	
	/**
	 * 从可填入数最少的位置进入
	 * @return
	 */
	public static int[] t18() {
		int count = 9;
		int[] results = new int[2];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Integer.bitCount(line_mb[i][j]) < count && line_mb[i][j] != 0 ) {
					count = Integer.bitCount(line_mb[i][j]);
					results[0] = i;
					results[1] = j;
				}
			}
		}
		return results;
		
	}
}
