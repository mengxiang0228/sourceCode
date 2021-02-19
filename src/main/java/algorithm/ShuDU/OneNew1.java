package algorithm.ShuDU;

import java.text.DecimalFormat;

/**
 * 这个可以使用，正常没有问题
 * @author 李雅翔
 * @date 2018年3月5日
 */
public class OneNew1 {
	
	public static DecimalFormat df=new DecimalFormat("000000000");
	public static long start = System.currentTimeMillis();
	public static boolean b = false;
	
	public static void main(String[] args) {
		
//		int[][] t1 = {
//				{7,0,0,5,0,8,0,0,0},
//				{0,4,0,0,0,0,0,0,0},
//				{0,0,0,0,3,0,5,0,6},
//				{0,8,0,0,5,0,3,6,0},
//				{0,9,6,0,0,0,1,8,0},
//				{0,1,0,0,0,3,2,0,0},
//				{0,0,0,0,0,0,6,0,0},
//				{0,0,0,7,4,0,0,0,1},
//				{2,7,0,0,0,9,0,0,0}};
		String[] strings = {
				"800000000",
				"070090200",
				"003600000",
				"050007000",
				"000045700",
				"000100030",
				"001000068",
				"008500010",
		        "090000400"};
		int[][] t1 = InputProcessing.t3(strings);
		System.out.println("所有可能的值全部填入");
		t11(t1);//可能的值
		print(t1);
		t19(t1);
		t17(t1);
		
		long end = System.currentTimeMillis();
		System.out.println("运行耗时:"+(end-start)+"ms");
	}
	
	
	
	
	/**
	 * 初步筛查
	 */
	//TODO 继续添加 对可填入数字的列表进行修正
	//FIXME 未对值进行验证
	public static boolean t9_N(int[][] t1) {
		boolean b1 = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				//如果候选词只有一个
				if ((Integer.bitCount(t1[i][j])) == 1) {
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
	 * 删除指定数对应行、列、九宫格中的候选数
	 * @param i
	 * @param j
	 * @param num
	 * 有数据被修改
	 */
	public static boolean t101_N(int i, int j, int[][] t1) {
		boolean b1 = false;
//		System.out.println("line_mb[0][3] : "+Integer.toBinaryString(line_mb[0][3]));
		//删除同一行,列中的候选数
		for (int k = 0; k < 9; k++) {
			/**
			 * 100110  100010
			 * 000100  000100
			 * 100110  100110
			 */
			if (k != j && Integer.bitCount(t1[i][k]) != 1 && ((t1[i][k]|t1[i][j]) == t1[i][k])) {
				b1 = true;
				if (Integer.bitCount(t1[i][k] ^= t1[i][j]) == 1) {
					t101_N(i, k, t1);
				}
				
			}
			if (k != i  && Integer.bitCount(t1[k][j]) != 1 && ((t1[k][j]|t1[i][j]) == t1[k][j])) {
				b1 = true;
				if (Integer.bitCount(t1[k][j] ^= t1[i][j]) == 1) {
					t101_N(k, j, t1);
				}
			}
		}
		
//		if (line_mb[0][3] == Integer.valueOf("001000000",2) ) {
//			
//			System.out.println("i:"+i+"  j:"+j+"  t1:"+Two.g3(t1));
//			System.out.println("测试2");
//			print();
//			System.exit(0);
//		}
		for (int k = i/3*3; k <= i/3*3+2; k++) {
			for (int l = j/3*3; l <= j/3*3+2; l++) {
				if ((k != i || l != j) && Integer.bitCount(t1[k][l]) != 1 && ((t1[k][l]|t1[i][j]) == t1[k][l])) {
					b1 = true;
					if (Integer.bitCount(t1[k][l] ^= t1[i][j]) == 1) {
						t101_N(k, l, t1);
					}
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
					t1[i][j] = num;
				}else {
					t1[i][j] = Two.t51(new int[] {t1[i][j]});
				}
			}
		}
		
	}
	
	/**
	 * 行唯一值
	 */
	public static boolean t14(int[][] t1) {
		int line = 0;
		boolean b1 = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Integer.bitCount(t1[i][j]) != 1) {
					line += Integer.valueOf(Integer.toBinaryString(t1[i][j]));
				}				
			}
			int index = df.format(line).indexOf("1");
			if (index != -1) {
				b1 = true;
				for (int j = 0; j < 9; j++) {
					if ((t1[i][j] & 256>>index) == 256>>index) {
						//256 1 0000 0000
//						if (i == 3 && j ==3) {
//							print();
//							System.out.print("未改之前的值：");Two.g2(t1[j][i]);
//							System.out.print("要修改的值：");Two.g2(256>>index);
//							System.out.println("num:"+line);
//							for (int js : t1[3]) {
//								System.out.println(df.format(Integer.valueOf(Integer.toBinaryString(js))));
//							}
//						}
						t1[i][j] = 256>>index;
						t101_N(i, j, t1);
						break;
					}
				}
			}
			line = 0;
		}
		return b1;
	}
	
	/**
	 * 列唯一值
	 */
	public static boolean t15(int[][] t1) {
		int num = 0;
		boolean b1 = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Integer.bitCount(t1[j][i]) != 1) {
					num += Integer.valueOf(Integer.toBinaryString(t1[j][i]));
				}
			}
			int index = df.format(num).indexOf("1");
			if (index != -1) {
				b1 = true;
				for (int j = 0; j < 9; j++) {
					if ((t1[j][i] & 256>>index) == 256>>index) {
//						if (i == 5 && j ==3) {
//							System.out.print("未改之前的值：");Two.g2(t1[j][i]);
//							Two.g2(t1[j][i]);
//						}
						t1[j][i] = 256>>index;

						//FIXME 行列判断
						t101_N(j, i, t1);
//						if (t1[3][5] == Two.t51(new int[] {1})) {
//							System.out.println("方法进入查看");
//							System.out.println("行:"+j+"列:"+i);
//							for (int js : t1[3]) {
//								System.out.println(df.format(Integer.valueOf(Integer.toBinaryString(js))));
//							}
//							print();
//							System.exit(0);
//						}
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
	public static boolean t16(int[][] t1) {
		int[][] small_nine_palace_mb = new int[9][9];
		Two.t52(t1, small_nine_palace_mb);//确定每个小宫格中可能的数
		int num = 0;
		boolean b1 = false;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Integer.bitCount(small_nine_palace_mb[i][j]) != 1) {
					num += Integer.valueOf(Integer.toBinaryString(small_nine_palace_mb[i][j]));
				}
			}
			int index = df.format(num).indexOf("1");
			if (index != -1) {
				
				b1 = true;
				for (int j = 0; j < 9; j++) {
					if ((small_nine_palace_mb[i][j] & 256>>index) == 256>>index ) {
						int h = i/3*3+j%3;//九宫格数字所在行
						int l = i%3*3+j/3;//九宫格数字所在列
						t1[h][l] = 256>>index;
						t101_N(h, l, t1);
					}
				}
			}
			num = 0;
		}
		return b1;
	}
	
	/**
	 * 循环遍历每一个可能的值，最会输出所有的数独解的结果
	 * @param t1
	 */
	public static void t17(int[][] t1) {
		int[] numPlace = t18(t1);
		int h = numPlace[0];//行
		int l = numPlace[1];//列
		if (h == -1 && l == -1) {
			System.out.println("数独有解");
			print(t1);
			long end = System.currentTimeMillis();
			System.out.println("数独解计算出时间："+(end-start)+"ms");
			return;
		}
		int num = t1[h][l];
		int count = Integer.bitCount(num);
		for (int i = 0; i < count; i++) {
			int testValue = Integer.highestOneBit(num);
//			10010
//			10000
//			00010
			num ^= testValue;
			int[][] linShi = new int[9][9];
			arrayCopy(t1, linShi);
			linShi[h][l] = testValue;
			t101_N(h, l, linShi);
			t19(linShi);
			if (!check(linShi)) {
//				System.out.println("实验错误一次");
				continue;
			};//校验
			
			
			t17(linShi);
		}
	}
 
	/**
	 * 检查输入的二维数组是否符合规则
	 * @param t1
	 * @return
	 */
	public static boolean check(int[][] t1) {
		int line = 0;
		int column = 0;
		int small_nine = 0;
		for (int i = 0; i < t1.length; i++) {
			line = 0;
			column = 0;
			small_nine = 0;
			for (int j = 0; j < t1.length; j++) {
				if (Integer.bitCount(t1[i][j]) == 1) {
					if ((line | t1[i][j]) == line) {
						return false;
					}
					line |= t1[i][j];
					
				}
				if (Integer.bitCount(t1[j][i]) == 1) {
					if ((column | t1[j][i]) == column) {
						return false;
					}
					column |= t1[j][i];;
				}
			}
			
			for (int k = i/3*3; k <= i/3*3+2; k++) {
				for (int l = i/3*3; l <= i/3*3+2; l++) {
					if (Integer.bitCount(t1[k][l]) == 1) {
						if ((small_nine | t1[k][l]) == small_nine) {
							return false;
						}
						small_nine |= t1[k][l];;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 从可填入数最少的位置进入
	 * @return
	 */
	public static int[] t18(int[][] t1) {
		int count = 9;
		int[] results = new int[] {-1, -1};
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (Integer.bitCount(t1[i][j]) > 1 && Integer.bitCount(t1[i][j]) < count ) {
					count = Integer.bitCount(t1[i][j]);
					results[0] = i;
					results[1] = j;
				}
			}
		}
		
		return results;
		
	}
	
	/**
	 * 根据规则修改每个位置的值,相当于一个 判断器
	 * @param t1 
	 */
	public static void t19(int[][] t1){
		int i = 0;
		int j = 0;
		int num = 9;
		boolean isNext = true;
		while (isNext) {
			isNext = false;
			while (t9_N(t1)) {
//				System.out.println("进行了一次 初步 筛查");
				isNext = true;
			}
			if (t1[i][j] == Two.t51(new int[] {num})) {
				System.out.println("进入了t9");
				System.exit(0);
			}
			
			while (t14(t1)) {
//				System.out.println("进行了一次 行 筛查");
				isNext = true;
			}
			if (t1[i][j] == Two.t51(new int[] {num})) {
				System.out.println("进入了t14");
				System.exit(0);
			}
			while (t15(t1)) {
//				System.out.println("进行了一次 列 筛查");
				isNext = true;
			}
			if (t1[i][j] == Two.t51(new int[] {num})) {
				System.out.println("进入了t15");
				System.exit(0);
			}
			while (t16(t1)) {
//				System.out.println("进行了一次 九宫格 筛查");
				isNext = true;
			}
			if (t1[i][j] == Two.t51(new int[] {num})) {
				System.out.println("进入了t16");
				System.exit(0);
			}
		}
	}
	
	/**
	 * 数组复杂
	 * @param t1
	 * @param t2
	 */
	public static void arrayCopy(int[][] t1, int[][] t2) {
		for (int i = 0; i < 9; i++) {
			t2[i] = t1[i].clone();//一维数组，克隆的是具体内容
		}
	}
	
	/**
	 * 打印指定的二维数组
	 * @param t1
	 */
	public static void print(int[][] t1) {
		System.out.println();
		for (int[] is : t1) {
			for (int i : is) {
				System.out.print(Two.g3(i)+" " );
			}
			System.out.println();
		}
	}
}
