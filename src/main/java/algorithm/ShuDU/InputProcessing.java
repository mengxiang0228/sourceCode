package algorithm.ShuDU;

/**
 * 对输入的数独题进行处理
 * @author 李雅翔
 * @date 2018年2月26日
 */
public class InputProcessing {

	public static int[][] initial_data = new int[9][9];//初始二维数组
	public static int[][] initial_data1 = new int[9][9];//存放结果二维数组
//	public static int[] small_nine_palace = new int[9];//每个小九宫格存放数字
//	public static int[] line = new int[9];//每一行存放数字
//	public static int[] column = new int[9];//每一列存放数字
	
	
	public static void main(String[] args) {
//		String[] strings = {"950004008","246070059","780609234",
//				"860713002","300000007","100942083",
//				"598206071","410080386","600400025"};
		
//		int[][] t1 = {{9, 5, 0, 0, 0, 4, 0, 0, 8},
//				  {2, 4, 6, 0, 7, 0, 0, 5, 9},
//				  {7, 8, 0, 6, 0, 9, 2, 3, 4},
//				  {8, 6, 0, 7, 1, 3, 0, 0, 2},
//				  {3, 0, 0, 0, 0, 0, 0, 0, 7},
//				  {1, 0, 0, 9, 4, 2, 0, 8, 3},
//				  {5, 9, 8, 2, 0, 6, 0, 7, 1},
//				  {4, 1, 0, 8, 0, 3, 8, 6, 0},
//				  {6, 0, 0, 4, 0, 0, 0, 2, 5}};
		String[] strings = {
				"700508000",
				"040000000",
				"000030506",
				"080050360",
				"096000180",
				"010003200",
				"000000600",
				"000740001",
		"270009000"};
//		int[][] t2 = {{7,0,0,5,0,8,0,0,0},
//				{0,4,0,0,0,0,0,0,0},
//				{0,0,0,0,3,0,5,0,6},
//				{0,8,0,0,5,0,3,6,0},
//				{0,9,6,0,0,0,1,8,0},
//				{0,1,0,0,0,3,2,0,0},
//				{0,0,0,0,0,0,6,0,0},
//				{0,0,0,7,4,0,0,0,1},
//				{2,7,0,0,0,9,0,0,0}};
		t3(strings);
		
		for (int i = 0; i < 9; i++) {
			System.out.print("{");
			for (int j = 0; j < 9; j++) {
				System.out.print(initial_data[i][j]);
				if (j != 8) {
					System.out.print(",");
				}
			}
			System.out.println("},");
		}
//		System.out.println(Arrays.toString(column));
		
	}
	
	/**
	 * 将输入的数独题进行转换
	 * @param strings
	 */
	public static int[][] t3(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			initial_data[i] = t31(strings[i]);
		}
		return initial_data;
	}
	
	/**
	 * 将字符串数字拆分开 如 950004008 -> {9,5,0,0,0,4,0,0,8}
	 * @param string
	 * @return
	 */
	public static int[] t31(String string) {
		String[] strings = string.split("");
		int[] intL = new int[9];
		for (int i = 0; i < strings.length; i++) {
			intL[i] = Integer.valueOf(strings[i]);
		}
		strings = null;
		return intL;
	}
	
	
	
	/**
	 * 每一行的数字
	 * @param t1
	 * @return
	 */
	public static int[] lineNumber(int[][] t1) {
		int[] intL = new int[9];
		int[] line = new int[9];
		int place = 0;//行位置
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
					intL[j] = t1[i][j];
			}
//			System.out.println(Arrays.toString(intL));
			line[place++] = Two.t51(intL);
		}
		intL = null;
		return line;
	}
	
	/**
	 * 每一列的数字
	 * @param t1
	 * @return
	 */
	public static int[] columnNumber(int[][] t1) {
		int[] intL = new int[9];
		int[] column = new int[9];
		int place = 0;//列位置
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
					intL[j] = t1[j][i];
			}
//			System.out.println(Arrays.toString(intL));
			column[place++] = Two.t51(intL);
		}
		intL = null;
		return column;
	}
	

}
