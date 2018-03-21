package com.crm.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.test.model.Customer;
import com.crm.test.modelVo.CustomerReq;
import com.crm.test.modelVo.CustomerResp;

public interface CustomerMapper {
	public List<Customer> selectCustomer(Customer customer);
	
	public List<Customer> selectCustomerByUserNameAndPassword(@Param(value = "username") String username,@Param(value = "password") String password);
	
	public List<CustomerResp> selectCustomerByUserName(@Param(value = "username") String username);
	
	public int insertCustomer(Customer customer);
	
	public int countUsername(CustomerReq customerReq);
	
	public int countEmail(CustomerReq CustomerReq);
	
	public int updateCustomerEmailVerify(@Param(value = "email") String email);
	
	public List<Customer> scanPersonalInformation(@Param(value = "username") String username);
	
	public int updatePersonalInformation(Customer customer);
	
	public int updatePasswordByEmail(Customer customer);
	
	public List<CustomerResp> selectUsernameByEmail(CustomerReq customerReq);
}
