package com.crm.test.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.crm.test.constant.ReturnObject;
import com.crm.test.util.ResponseObject;
import com.crm.test.util.ResponseUtil;

public class SecondHandlerInterceptor extends HandlerInterceptorAdapter {
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println("SecondHandlerInterceptor。preHandle，，，，，，，，，，，，，，，");
		boolean flag = false;
		if(flag){
			response.setCharacterEncoding("UTF-8");    
			response.setContentType("application/json; charset=utf-8");  
			PrintWriter out = null ;  
			try{
				ResponseObject respObj = ResponseUtil.setResult(ReturnObject.MESSAGE_TYPE_WRONG.getReturnCode(), ReturnObject.MESSAGE_TYPE_WRONG.getReturnDesc());
			    JSONObject res = (JSONObject) JSONObject.toJSON(respObj);
			    
			    out = response.getWriter();  
			    out.append(res.toString());  
			    return false;  
			}  catch (Exception e){  
			    e.printStackTrace();  
			    response.sendError(500);  
			    return false;  
			}  
		}
		
		return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // TODO Auto-generated method stub
    	System.out.println("SecondHandlerInterceptor。postHandle，，，，，，，，，，，，，，，");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    	System.out.println("SecondHandlerInterceptor。afterCompletion，，，，，，，，，，，，，，，");
    }
}
