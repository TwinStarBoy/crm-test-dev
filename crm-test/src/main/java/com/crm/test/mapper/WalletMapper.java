package com.crm.test.mapper;

import com.crm.test.model.Wallet;

public interface WalletMapper {
	
	int updateBalancePlus(Wallet wallet);
	
	int updateBalanceMinus(Wallet wallet);
	
	Wallet getCacc(Wallet wallet);
	
	int saveCacc(Wallet wallet);
	
	int countCacc(Wallet wallet);
	
}
