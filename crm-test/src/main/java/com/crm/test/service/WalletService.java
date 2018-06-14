package com.crm.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.test.constant.WalletStatus;
import com.crm.test.exception.MyException;
import com.crm.test.mapper.WalletMapper;
import com.crm.test.model.Wallet;
import com.crm.test.modelVo.CWithdraw;
import com.crm.test.modelVo.CDeposit;
import com.crm.test.modelVo.CSaveCacc;

@Transactional
@Service
public class WalletService {
	
	@Autowired
	private WalletMapper walletMapper;
	
	public int withdraw(CWithdraw cEnchashment) throws MyException{
		
		Wallet wallet = new Wallet();
		wallet.setCid(cEnchashment.getClientid());
		wallet.setPnsgid(cEnchashment.getPnsgid());
		wallet.setPnsid(cEnchashment.getPnsid());
		wallet.setQuantity(cEnchashment.getQuantity());
		
		Wallet walletObj = walletMapper.getCacc(wallet);
		int freeMargin = walletObj.getFreeMargin();
		int quantity = cEnchashment.getQuantity();
		if (freeMargin < quantity){
			throw new MyException(WalletStatus.WithdrawEnum.QUANTITY_MUST_BE_POSITIVE_NUMBER);
		}
		int returnCode = 0;
		try {
			returnCode = walletMapper.updateBalanceMinus(wallet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new MyException(WalletStatus.WithdrawEnum.FAILED);
		}
		return returnCode;
	}
	
	public int deposit(CDeposit cRecharge){
		
		Wallet wallet = new Wallet();
		wallet.setCid(cRecharge.getClientid());
		wallet.setPnsgid(cRecharge.getPnsgid());
		wallet.setPnsid(cRecharge.getPnsid());
		wallet.setQuantity(cRecharge.getQuantity());
		
		return walletMapper.updateBalancePlus(wallet);
	}
	
	public int saveCacc(CSaveCacc cSaveCacc) throws MyException{
		
		
		Wallet wallet = new Wallet();
		wallet.setCid(cSaveCacc.getClientid());
		wallet.setCgid(cSaveCacc.getCgid());
		wallet.setPnsgid(cSaveCacc.getPnsgid());
		wallet.setPnsid(cSaveCacc.getPnsid());
		int count = walletMapper.countCacc(wallet);
		
		if(count != 0){
			throw new MyException(WalletStatus.SaveCaccEnum.THIS_TYPE_IS_ALREADY_EXITS);
		}
		
		return walletMapper.saveCacc(wallet);
	}
	
}
