/**   
 * @Title: JobExceptionHandler.java 
 * @Package com.holly.job.exception 
 * @Description: 异常相关的包
 * @author holly.wang wangxfholly@126.com   
 * @date 2017年3月2日 上午11:14:00 
 * @version V1.0   
 */
package com.holly.job.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.holly.job.entity.ResResult;

/**
 * @ClassName: JobExceptionHandler
 * @Description: 此项目的统一异常处理类 实现HandlerExceptionResolver 接口自定义异常处理器
 * @author holly.wang wangxfholly@126.com
 * @date 2017年3月2日 上午11:14:00
 * 
 */
@Component
public class JobExceptionHandler implements HandlerExceptionResolver {
	private static final Logger log = LoggerFactory
			.getLogger(JobExceptionHandler.class);

	/*
	 * (非 Javadoc) <p>Title: resolveException</p> <p>Description: 这里不做返回页面的处理
	 * 统一返回JSON格式</p>
	 * 
	 * @param request
	 * 
	 * @param response
	 * 
	 * @param handler
	 * 
	 * @param ex
	 * 
	 * @return
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerExceptionResolver#resolveException
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = new ModelAndView();
		/* 使用response返回 */
		response.setStatus(HttpStatus.OK.value()); // 设置状态码
		response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
		response.setCharacterEncoding("UTF-8"); // 避免乱码
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			ResResult result = new ResResult();
			if(ex instanceof JobClassNotFoundException) {
				result.setCode(((JobClassNotFoundException) ex).getCode());
				result.setMsg(ex.getMessage());
			} else if(ex instanceof SchedulerException ) {
				result.setCode(ExceptionCode.DURIBLE.getCode());
				result.setMsg(ex.getMessage());
			}
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			log.error("与客户端通讯异常:" + e.getMessage(), e);
		}
		log.debug("异常:" + ex.getMessage(), ex);
		return mv;
	}

}
