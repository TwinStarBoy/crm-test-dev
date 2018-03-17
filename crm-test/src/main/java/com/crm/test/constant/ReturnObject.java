package com.crm.test.constant;

public enum ReturnObject {

	GENERAL_SUCCESS("00000","successfully"),
	EMAIL_IS_NULL("00005","email could not be null"),
	EMAILTOKENISNULL("00006","email token could not be null"),
	EMAILTOKENWRONG("00007","email token is wrong , please check it"),
	NEWPASSWORDISNULL("00008","new password could not be null"),
	CONFIRMPASSWORDISNULL("00009","confirm password could not be null"),
	MODIFYPASSWORDSUCCESS("00000","modify password successfully"),
	MODIFYPASSWORDFAILED("00010","modify password failed"),
	NEWPASSWORD_NOT_EQUALS_CONFIRMPASSWORD("00011","new password doesn't equal confirm password"),
	USERNAME_IS_NULL("00012","username could not be null"),
	USERNAME_IS_ALREADY_EXIST("00013","username is already exist"),
	EMAIL_IS_ALREADY_EXIST("00014","email is already exist");

	private String returnCode;
	private String returnDesc;
	
	private ReturnObject(String returnCode,String returnDesc){
		this.returnCode = returnCode;
		this.returnDesc = returnDesc;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnDesc() {
		return returnDesc;
	}
	
}