package com.crm.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.test.constant.Constant;
import com.crm.test.model.Customer;
import com.crm.test.service.CustomerService;
import com.crm.test.util.ResponseObject;
import com.crm.test.util.ResponseUtil;

@Controller
@RequestMapping(value = "/customerEdit")
public class CustomerEditController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/modifyPersonalInformation" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject modifyPersonalInformation(Customer customer){
		try {
			customerService.updatePersonalInformation(customer);
		} catch (Exception e) {
			return ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC );
		}
		return ResponseUtil.setResult(Constant.MODIFY_SUCCESS_CODE, Constant.MODIFY_SUCCESS_DESC );
	}
	
	@RequestMapping(value = "/scanPersonalInformation" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject scanPersonalInformation(Customer customer){
		
		List<Customer> lists = customerService.scanPersonalInformation(customer.getUsername());
		
		return ResponseUtil.setResult(Constant.SEARCH_SUCCESS_CODE, Constant.SEARCH_SUCCESS_DESC ,lists , lists.size());
		
	}
}
