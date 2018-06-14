package com.crm.test.exception;

public class MyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4960557485036809002L;
	
	public MyException(Enum statusEnum){
		this.statusEnum = statusEnum;
	}
	
	private Enum statusEnum;

	public Enum getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(Enum statusEnum) {
		this.statusEnum = statusEnum;
	}

}
