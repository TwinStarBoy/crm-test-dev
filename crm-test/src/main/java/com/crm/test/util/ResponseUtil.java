package com.crm.test.util;

import java.util.List;

public class ResponseUtil {
	public static ResponseObject getResponseObject() {
		return new ResponseObject();
	}
	
	public static ResponseObject setResult(String returnCode,String returnDesc,List<?> lists,Integer rows){
		ResponseObject resp = new ResponseObject();
		
		resp.setReturnCode(returnCode).setReturnDesc(returnDesc).setLists(lists).setRows(rows);
		
		return resp;
	}
	
	public static ResponseObject setResult(String returnCode,String returnDesc){
		ResponseObject resp = new ResponseObject();
		
		resp.setReturnCode(returnCode).setReturnDesc(returnDesc);
		
		return resp;
	}
}
