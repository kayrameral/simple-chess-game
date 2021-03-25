package com.company;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Scanner m = new Scanner(System.in);
		String k = m.nextLine();
		String t="";
		String a = " a?li veli smsms ali okula kos";
		int count=0;
		for(int s=0;s<k.length();s++)
			if(k.charAt(s)=='\\'&& k.charAt(s+1)=='?')
				count++;
		for(int y=0;y<k.length();y++){
			if(k.charAt(y)=='?' && y>0){
				if(k.charAt(y-1)!='\\')
		 t = k.replaceAll("\\?", "\\.");
			else
					t = k;
			}
		}
		//ystem.out.println(count);
		/*for(int y=0;y<a.length();y++)
			if(a.charAt(y)=='[' ||a.charAt(y)==']')
				if(a.charAt(y-1)!='\\')
		String stk = t.substring(c + 1, d);
		stk = stk.replaceAll(",", "");
		t = stk.replaceAll("\\[.*?\\]",sty.charAt(.*));
		System.out.println(t);*/
		for (int i = 0; i < a.length() - t.length(); i++) {

			String str = a.substring(i, i + t.length()-count);
			//System.out.println(str);
			if (str.matches(t)) System.out.println("x");
		}

	}

}
