package com.crm.test.util;

import java.util.List;

public class ResponseObject {
	private String returnCode;
	private String returnDesc;
	private List<?> lists;
	private Integer rows;

	public String getReturnCode() {
		return returnCode;
	}

	public ResponseObject setReturnCode(String returnCode) {
		this.returnCode = returnCode;
		return this;
	}

	public String getReturnDesc() {
		return returnDesc;
	}

	public ResponseObject setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
		return this;
	}

	public List<?> getLists() {
		return lists;
	}

	public ResponseObject setLists(List<?> lists) {
		this.lists = lists;
		return this;
	}

	public Integer getRows() {
		return rows;
	}

	public ResponseObject setRows(Integer rows) {
		this.rows = rows;
		return this;
	}

}
