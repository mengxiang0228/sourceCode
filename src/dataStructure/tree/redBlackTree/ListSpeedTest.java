package dataStructure.tree.redBlackTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * LinkedList 和 ArrayList 插入的速度以及查询速度
 * @author 李雅翔
 * @date 2017年9月30日
 */
public class ListSpeedTest {
	public static void main(String[] args) {
		linkListTest();
		arrayListTest();
		vectorTest();
	}
	public static void linkListTest() {
		System.out.println("----------------   LinkedList   ---------------------");
		List<Integer> list = new LinkedList<>();
//		List<Integer> list = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list.add(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("插入耗时:"+(end-start)+"毫秒");

		start = System.currentTimeMillis();
		list.indexOf(1);
		end = System.currentTimeMillis();
		System.out.println("查找  1 耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.indexOf(999999);
		end = System.currentTimeMillis();
		System.out.println("查找  999999 耗时:"+(end-start)+"毫秒");
		

		
		start = System.currentTimeMillis();
		list.remove(0);
		end = System.currentTimeMillis();
		System.out.println("删除 0 耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.remove(578943);
		end = System.currentTimeMillis();
		System.out.println("删除 578943 耗时:"+(end-start)+"毫秒");
	}
	
	public static void arrayListTest() {
		System.out.println("----------------   ArrayList   ---------------------");
		List<Integer> list = new ArrayList<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list.add(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("插入耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.indexOf(1);
		end = System.currentTimeMillis();
		System.out.println("查找  1 耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.indexOf(999999);
		end = System.currentTimeMillis();
		System.out.println("查找  999999 耗时:"+(end-start)+"毫秒");
		

		
		start = System.currentTimeMillis();
		list.remove(0);
		end = System.currentTimeMillis();
		System.out.println("删除 0 耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.remove(578943);
		end = System.currentTimeMillis();
		System.out.println("删除 578943 耗时:"+(end-start)+"毫秒");
	}
	
	public static void vectorTest() {
		System.out.println("----------------   Vector   ---------------------");
		List<Integer> list = new Vector<>();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			list.add(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("插入耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.indexOf(1);
		end = System.currentTimeMillis();
		System.out.println("查找  1 耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.indexOf(999999);
		end = System.currentTimeMillis();
		System.out.println("查找  999999 耗时:"+(end-start)+"毫秒");
		

		
		start = System.currentTimeMillis();
		list.remove(0);
		end = System.currentTimeMillis();
		System.out.println("删除 0 耗时:"+(end-start)+"毫秒");
		
		start = System.currentTimeMillis();
		list.remove(578943);
		end = System.currentTimeMillis();
		System.out.println("删除 578943 耗时:"+(end-start)+"毫秒");
	}
}
