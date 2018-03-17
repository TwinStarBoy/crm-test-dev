package com.crm.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.test.mapper.CustomerMapper;
import com.crm.test.model.Customer;
import com.crm.test.modelVo.CustomerReq;
import com.crm.test.modelVo.CustomerResp;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;
	
	public List<Customer> selectCustomerByUserNameAndPassword(String username,String password){
		
		List<Customer> customers = customerMapper.selectCustomerByUserNameAndPassword(username,password);
		if(customers == null || customers.size() == 0){
			return null;
		}
		
		return customers;
//		return null;
	}
	
	public int insertCustomer(Customer customer){
		int row = customerMapper.insertCustomer(customer);
		return row;
	}
	
	public int countUsername(CustomerReq customerReq){
		return customerMapper.countUsername(customerReq);
	}
	
	public int countEmail(CustomerReq customerReq){
		return customerMapper.countEmail(customerReq);
	}
	
	public int updateCustomerEmailVerify(String email){
		return customerMapper.updateCustomerEmailVerify(email);
	}
	
	public List<Customer> scanPersonalInformation(String username){
		List<Customer> customers = customerMapper.scanPersonalInformation(username);
		
		if(customers == null){
			return new ArrayList<Customer>();
		}
		
		return customers;
	}
	
	public int updatePersonalInformation(Customer customer){
		
		return customerMapper.updatePersonalInformation(customer);
		
	}
	
	public int updatePasswordByEmail(Customer customer){
		return customerMapper.updatePasswordByEmail(customer);
	}
	
	public List<CustomerResp> selectUsernameByEmail(CustomerReq customerReq){
		List<CustomerResp> customerResps = customerMapper.selectUsernameByEmail(customerReq);
		if(customerResps == null){
			return new ArrayList<CustomerResp>();
		}
		return customerResps;
	}
}
