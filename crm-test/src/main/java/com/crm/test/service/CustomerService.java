package com.crm.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.test.constant.Constant;
import com.crm.test.mapper.CustomerMapper;
import com.crm.test.model.Customer;
import com.crm.test.modelVo.CustomerReq;
import com.crm.test.modelVo.CustomerResp;
import com.crm.test.util.EmailUtil;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
    private EmailUtil emailUtil;
	
	public List<Customer> selectCustomerByUserNameAndPassword(String username,String password){
		
		List<Customer> customers = customerMapper.selectCustomerByUserNameAndPassword(username,password);
		if(customers == null || customers.size() == 0){
			return null;
		}
		
		return customers;
//		return null;
	}
	
	public List<CustomerResp> selectCustomerByUserName(String username){
		List<CustomerResp> customerResps = customerMapper.selectCustomerByUserName(username);
		if(customerResps == null || customerResps.size() == 0){
			CustomerResp customerResp = new CustomerResp();
			customerResps = new ArrayList<CustomerResp>();
			customerResps.add(customerResp);
			return customerResps;
		}
		return customerResps;
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
	
	public int updatePersonalInformation(CustomerReq customerReq){
		
		return customerMapper.updatePersonalInformation(customerReq);
		
	}

	public int updatePasswordByUsername(CustomerReq customerReq){
		return customerMapper.updatePasswordByUsername(customerReq);
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
	
	@Transactional(rollbackFor={RuntimeException.class, Exception.class})
	public void createCustomer(CustomerReq customerReq,HttpServletRequest request) throws Exception {
		int rowns = insertCustomer(customerReq);
		
		if(1 != rowns){
			throw new Exception("insert failed ");
		}
		
//		String emailVerifyCode = "123456";
//		
//		String message = "welcome register , your verify code is "+ emailVerifyCode +" , please complete register in 5 minutes , thank you !";
		
		sendVerifyEmail(customerReq, request,Constant.PLEASE_EMAIL_VERIFY);
		
//		request.getSession().setAttribute(customerReq.getUsername(), emailVerifyCode);//it will be setted in redis in the future 
//		
//		request.getSession().setAttribute(customerReq.getEmail(), emailVerifyCode);//it will be setted in redis in the future
	}

	public void sendVerifyEmail(CustomerReq customerReq,
			HttpServletRequest request,String emailTitle) throws Exception {
		
		String random = Math.random() + "";
		
		String message = "http://localhost:8880/crm-test/webpage/emailVerifyAutoLogin.html?username="+customerReq.getUsername()+"&token="+random;//It will be modified in the future
		
		
		emailUtil.sendTemplateMail(customerReq.getEmail(), message,emailTitle);
		
		String key = customerReq.getUsername() + Constant.FORGET_PASSWORD_KEY_SUFFIX;
		
//		request.getSession().setAttribute(key, random);//it will be setted in redis in the future 
		
		stringRedisTemplate.opsForValue().set(key, random, 180, TimeUnit.SECONDS);
	}
}
