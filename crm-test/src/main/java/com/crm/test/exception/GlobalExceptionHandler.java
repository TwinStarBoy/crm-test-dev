package com.crm.test.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.test.constant.Constant;
import com.crm.test.util.ResponseObject;
import com.crm.test.util.ResponseUtil;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value=Exception.class) 
	@ResponseBody
    public ResponseObject allExceptionHandler(HttpServletRequest request,  
            Exception exception) throws Exception  
    {  
        logger.error(exception.getMessage(), exception);
		exception.printStackTrace();
        System.out.println("我报错了："+exception.getLocalizedMessage());
        System.out.println("我报错了："+exception.getCause());
        System.out.println("我报错了："+exception.getSuppressed());
        System.out.println("我报错了："+exception.getMessage());
        System.out.println("我报错了："+exception.getStackTrace());
        
        return ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC);
    }  
	
	int i = 0;
	@ExceptionHandler(value=NullPointerException.class) 
	@ResponseBody
    public ResponseObject NullPiontExceptionHandler(HttpServletRequest request,  
            Exception exception) throws Exception  
    {  
        logger.error(exception.getMessage(), exception);
        i++;
        System.out.println(i);
        System.out.println("NullPiontExceptionHandler.............");        
        return ResponseUtil.setResult(Constant.BACKGROUND_SERVER_WRONG_CODE, Constant.BACKGROUND_SERVER_WRONG_DESC);
    } 
}
