package com.crm.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CommonHandlerInterceptor extends HandlerInterceptorAdapter{
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        int type = (Integer) request.getSession().getAttribute("userType");
//        if (type == 1) {
//            return true;
//        } else {
//            String result = ResultUtils.getErrorResult("当前登录用户无操作权限！");
//            response.getOutputStream().write(result.getBytes());
//            response.setStatus(HttpStatus.OK.value());
//            return false;
//        }
		System.out.println("CommonHandlerInterceptor。preHandle，，，，，，，，，，，，，，，");
		return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // TODO Auto-generated method stub
    	System.out.println("CommonHandlerInterceptor。postHandle，，，，，，，，，，，，，，，");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    	System.out.println("CommonHandlerInterceptor。afterCompletion，，，，，，，，，，，，，，，");
    }
}
