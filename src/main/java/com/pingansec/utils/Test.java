package com.pingansec.utils;

import java.net.IDN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {
		String url = "http://xn--jpqw3kxthtxk9skmzzknv.com/Travel.aspx";
		String url1 = "xn--jpqw3kxthtxk9skmzzknv.com/Travel.aspx";
/*		String str = "呼伦贝尔旅游网.com";
		String regEx = "[\\u4e00-\\u9fa5]";
		String reg1 = "^[http|https]://(www)?\\.\\w+$";
		Pattern p =  Pattern.compile(reg1);
		Matcher m =  p.matcher(url);
		if(m.find()){
			System.out.println("aaa");
			str = IDN.toASCII(url);
		}
		
		System.out.println(str);*/
		System.out.println(IDN.toUnicode(url1));
	}
}
