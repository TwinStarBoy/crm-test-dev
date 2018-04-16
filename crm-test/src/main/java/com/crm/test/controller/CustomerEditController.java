package com.crm.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.test.constant.Constant;
import com.crm.test.constant.ReturnObject;
import com.crm.test.model.Customer;
import com.crm.test.modelVo.CustomerReq;
import com.crm.test.service.CustomerService;
import com.crm.test.util.ResponseObject;
import com.crm.test.util.ResponseUtil;
import com.crm.test.util.StringUtil;

@Controller
@RequestMapping(value = "/customerEdit")
public class CustomerEditController {
	private static Logger logger = Logger.getLogger(CustomerEditController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/modifyPersonalInformation" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject modifyPersonalInformation(CustomerReq customerReq){
		/*String password = customerReq.getPassword();
		if(password == null || "".equals(password)){
			return ResponseUtil.setResult(ReturnObject.PASSWORD_COULD_NOT_BE_NULL.getReturnCode(),ReturnObject.PASSWORD_COULD_NOT_BE_NULL.getReturnDesc());
		}
		
		String confirmPassword = customerReq.getConfirmPassword();
		if(!password.equals(confirmPassword)){
			return ResponseUtil.setResult(ReturnObject.PASSWORD_NOT_EQUALS_CONFIRM.getReturnCode(),ReturnObject.PASSWORD_NOT_EQUALS_CONFIRM.getReturnDesc());
		}*/
		
		try {
			customerService.updatePersonalInformation(customerReq);
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
	
	@RequestMapping(value = "/modifyPassword" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject modifyPassword(CustomerReq customerReq){
		String oldPassword = customerReq.getOldPassword();
		
		if(StringUtil.isNull(oldPassword)){
			return ResponseUtil.setResult(ReturnObject.NEWPASSWORDISNULL.getReturnCode(), ReturnObject.NEWPASSWORDISNULL.getReturnDesc());
		}
		
		List<Customer> customers = null;
		String username = customerReq.getUsername();
		
		try {
			customers = customerService.selectCustomerByUserNameAndPassword(
					username, oldPassword);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResponseUtil.setResult(ReturnObject.GENERAL_FAILED.getReturnCode(), ReturnObject.GENERAL_FAILED.getReturnDesc());
		}
		
		if(null == customers || customers.size() == 0){
			return ResponseUtil.setResult(ReturnObject.OLD_PASSWORD_WRONG.getReturnCode(), ReturnObject.OLD_PASSWORD_WRONG.getReturnDesc());
		}
		
		String newPassword = customerReq.getNewPassword();
		
		if(StringUtil.isNull(newPassword)){
			return ResponseUtil.setResult(ReturnObject.NEWPASSWORDISNULL.getReturnCode(), ReturnObject.NEWPASSWORDISNULL.getReturnDesc());
		}
		
		String confirmPassword = customerReq.getConfirmPassword();
		
		if(StringUtil.isNull(confirmPassword)){
			return ResponseUtil.setResult(ReturnObject.CONFIRMPASSWORDISNULL.getReturnCode(), ReturnObject.CONFIRMPASSWORDISNULL.getReturnDesc());
		}
		
		if(!newPassword.equals(confirmPassword)){
			return ResponseUtil.setResult(ReturnObject.NEWPASSWORD_NOT_EQUALS_CONFIRMPASSWORD.getReturnCode(), ReturnObject.NEWPASSWORD_NOT_EQUALS_CONFIRMPASSWORD.getReturnDesc());
		}
		try {
			customerService.modifyPasswordByUsername(customerReq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC );
		}
		
		return ResponseUtil.setResult(Constant.MODIFY_SUCCESS_CODE, Constant.MODIFY_SUCCESS_DESC );
		
	}
}
