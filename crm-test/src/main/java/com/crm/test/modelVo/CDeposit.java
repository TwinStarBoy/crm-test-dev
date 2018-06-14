package com.crm.test.modelVo;

import java.util.UUID;

import com.crm.test.constant.WalletStatus.DepositEnum;


public class CDeposit{
	
	private static final String CDEPOSIT_MESSAGE_ID = "4011";
	
	private String messageid;
	private UUID requestid;
	private int pnsid;
	private int pnsgid;
	private int clientid;
	private int quantity;
	
	public DepositEnum reviewData(){
		if (this.pnsid < 0 ){
			return DepositEnum.PNSID_MSUT_MORE_THAN_ZERO;
		}
		
		if (this.pnsgid < 0){
			return DepositEnum.PNSGID_MSUT_MORE_THAN_ZERO;
		}
		
		if (this.quantity < 0){
			return DepositEnum.QUANTITY_MUST_BE_POSITIVE_NUMBER;
		}
		
		return DepositEnum.SUCCESS;
		
	}
	
	public int getPnsid() {
		return pnsid;
	}
	public void setPnsid(int pnsid) {
		this.pnsid = pnsid;
	}
	public int getPnsgid() {
		return pnsgid;
	}
	public void setPnsgid(int pnsgid) {
		this.pnsgid = pnsgid;
	}
	public int getClientid() {
		return clientid;
	}

	public void setClientid(int clientid) {
		this.clientid = clientid;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public UUID getRequestid() {
		return requestid;
	}

	public void setRequestid(UUID requestid) {
		this.requestid = requestid;
	}
	
	
}
