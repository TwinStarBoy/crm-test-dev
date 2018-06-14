package com.crm.test.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.test.constant.WalletStatus;
import com.crm.test.exception.MyException;
import com.crm.test.modelVo.CWithdraw;
import com.crm.test.modelVo.CWithdrawAns;
import com.crm.test.modelVo.CDeposit;
import com.crm.test.modelVo.CDepositAns;
import com.crm.test.modelVo.CSaveCacc;
import com.crm.test.modelVo.CSaveCaccAns;
import com.crm.test.service.WalletService;

@Controller
public class WalletController {
	
	private static Logger logger = Logger.getLogger(WalletController.class);
	
	@Autowired
	private WalletService walletService;
	
	@RequestMapping(value = "/deposit" )
	@ResponseBody
	public CDepositAns deposit(CDeposit cDeposit){

		CDepositAns cRechargeAns = new CDepositAns();
		
		cRechargeAns.setQuantity(cDeposit.getQuantity());
		cRechargeAns.setClientId(cDeposit.getClientid());
		cRechargeAns.setPnsgid(cDeposit.getPnsgid());
		cRechargeAns.setPnsid(cDeposit.getPnsid());
		
		WalletStatus.DepositEnum plusBalance = cDeposit.reviewData();
		
		if (plusBalance != WalletStatus.DepositEnum.SUCCESS){
			cRechargeAns.setStatus(plusBalance);
			return cRechargeAns;
		}

		try {
			walletService.deposit(cDeposit);
			cRechargeAns.setStatus(WalletStatus.DepositEnum.SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			cRechargeAns.setStatus(WalletStatus.DepositEnum.FAILED);
		}
		
		
		return cRechargeAns ;
		
	}
	
	@RequestMapping(value = "/withdraw" ,method = RequestMethod.POST)
	@ResponseBody
	public CWithdrawAns withdraw(CWithdraw cWithdraw){
		
		CWithdrawAns enchashmentResp = new CWithdrawAns();
		
		enchashmentResp.setQuantity(cWithdraw.getQuantity());
		enchashmentResp.setClientId(cWithdraw.getClientid());
		enchashmentResp.setPnsgid(cWithdraw.getPnsgid());
		enchashmentResp.setPnsid(cWithdraw.getPnsid());
		
		WalletStatus.WithdrawEnum minusBalance = cWithdraw.reviewData();
		
		if (minusBalance != WalletStatus.WithdrawEnum.SUCCESS){
			enchashmentResp.setStatus(minusBalance);
			return enchashmentResp;
		}
		
		try {
			walletService.withdraw(cWithdraw);
			enchashmentResp.setStatus(WalletStatus.WithdrawEnum.SUCCESS);
		} catch (MyException e) {
			logger.error(e.getMessage(), e);
			enchashmentResp.setStatus(e.getStatusEnum());
		}
		
		return enchashmentResp ;
		
	}
	
	@RequestMapping(value = "/saveCacc" )
	@ResponseBody
	public CSaveCaccAns saveCacc(CSaveCacc cSaveCacc){
		
		CSaveCaccAns cSaveCaccAns = new CSaveCaccAns();
		
		cSaveCaccAns.setPnsgid(cSaveCacc.getPnsgid());
		cSaveCaccAns.setPnsid(cSaveCacc.getPnsid());
		cSaveCaccAns.setClientid(cSaveCacc.getClientid());
		cSaveCaccAns.setCgid(WalletStatus.CUSTOMER_GROUP_ID);//默认分组，为1

		
		WalletStatus.SaveCaccEnum saveCacc = cSaveCacc.reviewData();
		
		if (saveCacc != WalletStatus.SaveCaccEnum.SUCCESS){
			cSaveCaccAns.setStatus(saveCacc);
			return cSaveCaccAns;
		}
		
		try {
			walletService.saveCacc(cSaveCacc);
			cSaveCaccAns.setStatus(WalletStatus.DepositEnum.SUCCESS);
		} catch (MyException e) {
			logger.error(e.getMessage(), e);
			cSaveCaccAns.setStatus(e.getStatusEnum());
		}
		
		return cSaveCaccAns ;
		
	}
}
