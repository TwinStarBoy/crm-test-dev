package com.crm.test.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.test.constant.Constant;
import com.crm.test.constant.ReturnObject;
import com.crm.test.model.Customer;
import com.crm.test.modelVo.CustomerReq;
import com.crm.test.modelVo.CustomerResp;
import com.crm.test.service.CustomerService;
import com.crm.test.util.EmailUtil;
import com.crm.test.util.ResponseObject;
import com.crm.test.util.ResponseUtil;
import com.crm.test.util.StringUtil;

@Controller
@RequestMapping(value = "/onlineManage")
public class OnlineController {

	private static Logger logger = Logger.getLogger(OnlineController.class);
	
	@Autowired
    private EmailUtil emailUtil;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/register" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject register(CustomerReq customerReq,HttpServletRequest request){
		
		System.out.println(customerReq.getUsername());
		
		int countUsername = customerService.countUsername(customerReq);
		
		if(countUsername > 0){
			return ResponseUtil.setResult(Constant.REGISTER_USERNAME_EXIST_CODE, Constant.REGISTER_USERNAME_EXIST_DESC);
		}
		
		int countEmail = customerService.countEmail(customerReq);
		
		if(countEmail > 0){
			return ResponseUtil.setResult(Constant.REGISTER_EAMIL_EXIST_CODE, Constant.REGISTER_EMIAL_EXIST_DESC);
		}
		
		try {
			createCustomer(customerReq,request);
		} catch (Exception e) {
			return ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC);
		}
		
		return ResponseUtil.setResult(Constant.REGISTER_SUCCESS_CODE, Constant.REGISTER_SUCCESS_DESC);
	}

	@Transactional
	private void createCustomer(CustomerReq customerReq,HttpServletRequest request) throws Exception {
		int rowns = customerService.insertCustomer(customerReq);
		
		if(1 != rowns){
			throw new Exception("insert failed ");
		}
		
//		String emailVerifyCode = "123456";
//		
//		String message = "welcome register , your verify code is "+ emailVerifyCode +" , please complete register in 5 minutes , thank you !";
		
		String random = Math.random() + "";
		
		String message = "http://localhost:8880/crm-test/webpage/emailVerifyAutoLogin.html?username="+customerReq.getUsername()+"&token="+random;//It will be modified in the future
		
		
		emailUtil.sendTemplateMail(customerReq.getEmail(), message);
		
		String key = customerReq.getUsername() + Constant.FORGET_PASSWORD_KEY_SUFFIX;
		
		request.getSession().setAttribute(key, random);//it will be setted in redis in the future 
		
//		request.getSession().setAttribute(customerReq.getUsername(), emailVerifyCode);//it will be setted in redis in the future 
//		
//		request.getSession().setAttribute(customerReq.getEmail(), emailVerifyCode);//it will be setted in redis in the future
	}
	
	@RequestMapping(value = "/emailAutoLogin" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject emailAutoLogin(CustomerReq customerReq,HttpServletRequest request){
		String username = customerReq.getUsername();
		String token = customerReq.getToken();
		
		String key = customerReq.getUsername() + Constant.EMAIL_LOGIN_KEY_SUFFIX;
		
		String tokenCache = (String) request.getSession().getAttribute(key);
		
		if(StringUtil.isNull(token)){
			return ResponseUtil.setResult(ReturnObject.EMAILTOKENISNULL.getReturnCode(), ReturnObject.EMAILTOKENISNULL.getReturnDesc());
		}
		
		//parameter token compares to cache token 
		if(!token.equals(tokenCache)){
			return ResponseUtil.setResult(ReturnObject.EMAILTOKENWRONG.getReturnCode(), ReturnObject.EMAILTOKENWRONG.getReturnDesc());
		}
		
		//login successfully 
		List<CustomerResp> customerResps = null;
		
		try {
			customerResps = customerService.selectCustomerByUserName(customerReq.getUsername());
			
			String email = customerResps.get(0).getEmail();
			
			customerService.updateCustomerEmailVerify(email);
			
			request.getSession().removeAttribute(key);// in case that repeat login

			request.getSession().setAttribute("username", username);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC);
		}
		
		return ResponseUtil.setResult(Constant.LOGIN_SUCCESS_CODE, Constant.LOGIN_SUCCESS_DESC,customerResps,customerResps.size());
		
	}
	
	@RequestMapping(value = "/checkUsernameUnique" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject checkUsernameUnique(CustomerReq customerReq){
		String username = customerReq.getUsername();
		
		if(StringUtil.isNull(username)){
			return ResponseUtil.setResult(ReturnObject.USERNAME_IS_NULL.getReturnCode(), ReturnObject.USERNAME_IS_NULL.getReturnDesc());
		}
		
		int count = 0;
		try {
			count = customerService.countUsername(customerReq);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.setResult(ReturnObject.GENERAL_FAILED.getReturnCode(), ReturnObject.GENERAL_FAILED.getReturnDesc());
		}
		
		if(count > 0){
			return ResponseUtil.setResult(ReturnObject.USERNAME_IS_ALREADY_EXIST.getReturnCode(), ReturnObject.USERNAME_IS_ALREADY_EXIST.getReturnDesc());
		}
		
		return ResponseUtil.setResult(ReturnObject.GENERAL_SUCCESS.getReturnCode(), ReturnObject.GENERAL_SUCCESS.getReturnDesc());
	}
	
	@RequestMapping(value = "/checkEmailUnique" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject checkEmailUnique(CustomerReq customerReq){
		String email = customerReq.getEmail();
		
		if(StringUtil.isNull(email)){
			return ResponseUtil.setResult(ReturnObject.EMAIL_IS_NULL.getReturnCode(), ReturnObject.EMAIL_IS_NULL.getReturnDesc());
		}
		
		int count = 0;
		try {
			count = customerService.countEmail(customerReq);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.setResult(ReturnObject.GENERAL_FAILED.getReturnCode(), ReturnObject.GENERAL_FAILED.getReturnDesc());
		}
		
		if(count > 0){
			return ResponseUtil.setResult(ReturnObject.EMAIL_IS_ALREADY_EXIST.getReturnCode(), ReturnObject.EMAIL_IS_ALREADY_EXIST.getReturnDesc());
		}
		
		return ResponseUtil.setResult(ReturnObject.GENERAL_SUCCESS.getReturnCode(), ReturnObject.GENERAL_SUCCESS.getReturnDesc());
	}
	
	
	@RequestMapping(value = "/emailVerify" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject emailVerify(HttpServletRequest request){
		
		System.out.println(request.getParameter("emailVerifyCode"));
		
        String emailParameter = request.getParameter("email");
		
		if(emailParameter == null){
			ResponseUtil.setResult(Constant.EMAIL_VERIFY_FAILED_CODE, Constant.EMAIL_VERIFY_FAILED_DESC);
		}
		
        String emailVerifyCodeParameter = request.getParameter("emailVerifyCode");
		
		if(emailVerifyCodeParameter == null){
			ResponseUtil.setResult(Constant.EMAIL_VERIFY_FAILED_CODE, Constant.EMAIL_VERIFY_FAILED_DESC);
		}
		
		String emailVerifyCodeCache = (String) request.getSession().getAttribute(emailParameter); // it will be gotten in the redis in future
		
		if(!emailVerifyCodeParameter.equals(emailVerifyCodeCache)){
			
			return ResponseUtil.setResult(Constant.EMAIL_VERIFY_FAILED_CODE, Constant.EMAIL_VERIFY_FAILED_DESC);
			
		}
		
		try {
			customerService.updateCustomerEmailVerify(emailParameter);
		} catch (Exception e) {
			ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC);
		}
		return ResponseUtil.setResult(Constant.EMAIL_VERIFY_SUCCESS_CODE, Constant.EMAIL_VERIFY_SUCCESS_DESC);
	}
	
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject login(Customer customer,HttpServletRequest request){
		String username = customer.getUsername();
		String password = customer.getPassword();
		System.out.print(username);
		List<Customer> customers = null;
		
		try {
			customers = customerService.selectCustomerByUserNameAndPassword(
					username, password);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.setResult(ReturnObject.GENERAL_FAILED.getReturnCode(), ReturnObject.GENERAL_FAILED.getReturnDesc());
		}
		if(null == customers || customers.size() == 0){
			return ResponseUtil.setResult(Constant.LOGIN_FAILED_CODE, Constant.LOGIN_FAILED_DESC);
		}
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("username"));
		
		session.setAttribute("username", username);
		
		return ResponseUtil.setResult(Constant.LOGIN_SUCCESS_CODE, Constant.LOGIN_SUCCESS_DESC,customers,customers.size());
	}
	
	@RequestMapping(value = "/logout" ,method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		
		System.out.println(session.getAttribute("username"));
		
		session.removeAttribute("username");

		return ResponseUtil.setResult(Constant.LOGOUT_SUCCESS_CODE, Constant.LOGOUT_SUCCESS_DESC);
	}
	
	@RequestMapping(value = "/forgetPassword" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject forgetPassword(HttpServletRequest request,CustomerReq customerReq){
		HttpSession session = request.getSession();
		
		System.out.println(session.getAttribute("username"));
		
		String email = request.getParameter("email");
		
		int count = 0;
		
		try {
			count = customerService.countEmail(customerReq);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.setResult(ReturnObject.GENERAL_FAILED.getReturnCode(), ReturnObject.GENERAL_FAILED.getReturnDesc());
		}
		
		if(count == 0){
			
		}
		
		String random = Math.random() + "";
		
		String message = "http://localhost:8880/crm-test/webpage/resetPassword.html?email="+email+"&token="+random;//It will be modified in the future
		
		try {
			
			emailUtil.sendTemplateMail(email, message);
			
			String key = email + Constant.FORGET_PASSWORD_KEY_SUFFIX;
			
			session.setAttribute(key, random);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC);
		}
		
		return ResponseUtil.setResult(Constant.COMMON_SUCCESS_CODE, Constant.COMMON_SUCCESS_DESC);
	}
	
	@RequestMapping(value = "/resetPassword" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseObject resetPassword(HttpServletRequest request,CustomerReq customerReq){
		//check email 
		String email = customerReq.getEmail();
		
		if(StringUtil.isNull(email)){
			return ResponseUtil.setResult(ReturnObject.EMAIL_IS_NULL.getReturnCode(), ReturnObject.EMAIL_IS_NULL.getReturnDesc());
		}
		
		//check parameter token
		String token = customerReq.getToken();

		if(StringUtil.isNull(token)){
			return ResponseUtil.setResult(ReturnObject.EMAILTOKENISNULL.getReturnCode(), ReturnObject.EMAILTOKENISNULL.getReturnDesc());
		}
		
		//get cache token
		String key = email + Constant.FORGET_PASSWORD_KEY_SUFFIX;
		
		HttpSession session = request.getSession();
		
		String tokenCache = (String) session.getAttribute(key);
		
		//parameter token compares to cache token 
		if(!token.equals(tokenCache)){
			return ResponseUtil.setResult(ReturnObject.EMAILTOKENWRONG.getReturnCode(), ReturnObject.EMAILTOKENWRONG.getReturnDesc());
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
		
		List<CustomerResp> customerResps = new ArrayList<CustomerResp>();
		
		try {
			customerService.updatePasswordByEmail(customerReq);
			customerResps = customerService.selectUsernameByEmail(customerReq);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseUtil.setResult(ReturnObject.MODIFYPASSWORDFAILED.getReturnCode(), ReturnObject.MODIFYPASSWORDFAILED.getReturnDesc());
		}
		return ResponseUtil.setResult(ReturnObject.MODIFYPASSWORDSUCCESS.getReturnCode(), ReturnObject.MODIFYPASSWORDSUCCESS.getReturnDesc(),customerResps,customerResps.size());
	}
	
	@RequestMapping(value = "/sendEmail" ,method = RequestMethod.POST)
	@ResponseBody
	public String sendEmail(HttpServletRequest request){
		String acceptorAccount = request.getParameter("acceptorAccount");
		try {
			emailUtil.sendTemplateMail(acceptorAccount, "test email");
		} catch (Exception e) {
			e.printStackTrace();
			return "send email fail !";
		}
		return "send email success !";
	}
}
