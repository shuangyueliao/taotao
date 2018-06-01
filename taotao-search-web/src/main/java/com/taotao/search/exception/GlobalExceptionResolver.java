package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		logger.info("进入全局异常处理器");
		logger.debug("测试handler的类型:"+handler.getClass());
		e.printStackTrace();
		logger.error("系统发生异常",e);
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.addObject("message","系统发生异常，请稍后重试");
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
