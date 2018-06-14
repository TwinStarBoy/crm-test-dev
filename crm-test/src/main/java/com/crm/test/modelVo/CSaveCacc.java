package com.crm.test.modelVo;

import java.util.UUID;

import com.crm.test.constant.WalletStatus.SaveCaccEnum;

public class CSaveCacc {
	
	private String messageid;
	private UUID requestid;
	private int clientid;
	private int cgid;
	private int pnsgid;
	private int pnsid;
	
	public SaveCaccEnum reviewData(){
		if (this.pnsid < 0 ){
			return SaveCaccEnum.PNSID_MSUT_MORE_THAN_ZERO;
		}
		
		if (this.pnsgid < 0){
			return SaveCaccEnum.PNSGID_MSUT_MORE_THAN_ZERO;
		}
		
		return SaveCaccEnum.SUCCESS;
	}
	
	public int getClientid() {
		return clientid;
	}
	public void setClientid(int clientid) {
		this.clientid = clientid;
	}
	public int getCgid() {
		return cgid;
	}
	public void setCgid(int cgid) {
		this.cgid = cgid;
	}
	public int getPnsgid() {
		return pnsgid;
	}
	public void setPnsgid(int pnsgid) {
		this.pnsgid = pnsgid;
	}
	public int getPnsid() {
		return pnsid;
	}
	public void setPnsid(int pnsid) {
		this.pnsid = pnsid;
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
