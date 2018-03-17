package com.crm.test.util;

public class StringUtil {
	
	public static final boolean TRUE = true;
	public static final boolean FALSE = false;
	
	public static boolean isNull(String param) {
		if(param == null) {
			return TRUE;
		}
		
		if("".equals(param)){
			return TRUE;
		}
		
		return FALSE;
	}
}
