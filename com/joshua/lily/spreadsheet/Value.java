package com.joshua.lily.spreadsheet;

import java.text.DecimalFormat;

public class Value {
	String sVal = "";
	double dVal = 0;
	String tag = "dbl";//can be str dbl or inv
	
	//return a formatted string of double null if invalad
	public String toString(){
		String s = null;
		if(tag == "str"){
		s = String.format("%10s", sVal);//create a string length 10
		}else if(tag == "dbl"){
			DecimalFormat df = new  DecimalFormat ("0.####");//make a new decimal format
			s = df.format(dVal);//format the double store in a string
			s = String.format("%10s", s);
		}else if(tag == "inv"){
			s = null;
		}
		return s;
	}
}
