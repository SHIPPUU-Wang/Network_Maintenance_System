package com.ie.test;

public class StringTest {
	public static void main(String[] args) {
		// 空格组成的字符串
		String a = "     ";
		String b = "";
//		System.out.println(a.isEmpty());
//		System.out.println(a.trim().isEmpty());

//		System.out.println(b.isEmpty());

//		String str = "";
		StringBuffer str =new StringBuffer();
		for (int i = 1; i < 10; i++) {
			str.append(i);
		}

		System.out.println(str.toString());
	}
}
