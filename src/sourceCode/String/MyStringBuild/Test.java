package sourceCode.String.MyStringBuild;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
	public static volatile int i  ;
	private static AtomicInteger count = new AtomicInteger(0);
	public static void main(String[] args) {
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("aa");
//		stringBuilder.trimToSize();
		int[] is = {1,2,3,4,0,0};
		int[] js = {5};
		System.arraycopy(js, 0, is, 4, 1);
		System.out.println(Arrays.toString(is));
		
		System.out.println(count.addAndGet(1));
	}
}
