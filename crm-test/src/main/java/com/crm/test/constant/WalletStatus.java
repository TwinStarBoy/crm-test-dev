package com.crm.test.constant;

public class WalletStatus {

	public static final int CUSTOMER_GROUP_ID = 1 ;
	
	public static enum DepositEnum {
		PNSID_MSUT_MORE_THAN_ZERO,
		PNSGID_MSUT_MORE_THAN_ZERO,
		QUANTITY_MUST_BE_POSITIVE_NUMBER,
		FAILED,
		SUCCESS,
	}
	
	public static enum WithdrawEnum {
		PNSID_MSUT_MORE_THAN_ZERO,
		PNSGID_MSUT_MORE_THAN_ZERO,
		QUANTITY_MUST_BE_POSITIVE_NUMBER,
		FAILED,
		SUCCESS,
	}
	
	public static enum SaveCaccEnum {
		PNSID_MSUT_MORE_THAN_ZERO,
		PNSGID_MSUT_MORE_THAN_ZERO,
		FAILED,
		SUCCESS,
		THIS_TYPE_IS_ALREADY_EXITS
	}
}
