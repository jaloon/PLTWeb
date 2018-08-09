package com.tipray.core.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipray.bean.Message;
import com.tipray.bean.User;
import com.tipray.constant.LogDescriptionEnum;
import com.tipray.core.ThreadVariable;
import com.tipray.core.annotation.LogAnno;

/**
 * 公共日志切面
 * 
 * @author chenlong
 * @version 1.0 2018-04-18
 *
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	/** 切点 */
	@Pointcut("@annotation(com.tipray.core.annotation.LogAnno)")
	private void logCut() {
	}

	/**
	 * 日志打印HTTP请求参数
	 * 
	 * @param joinPoint
	 */
	@Before("logCut()")
	public void doBeforeForParams(JoinPoint joinPoint) {
		User user = ThreadVariable.getUser();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		LogDescriptionEnum logDescription = method.getAnnotation(LogAnno.class).value();
		String[] parameterNames = signature.getParameterNames();
		Object[] parameterValues = joinPoint.getArgs();
		StringBuffer logInfo = new StringBuffer();
		logInfo.append('【').append(user.getName()).append('（').append(user.getAccount()).append('）').append('：')
				.append(logDescription).append('[').append(method).append(']').append('】').append('：');
		for (String parameterName : parameterNames) {
			logInfo.append(parameterName).append("={}, ");
		}
		logInfo.deleteCharAt(logInfo.length() - 1);
		logInfo.deleteCharAt(logInfo.length() - 1);
		logger.info(logInfo.toString(), parameterValues);
	}

	/**
	 * 日志打印HTTP请求结果
	 * 
	 * @param joinPoint
	 * @param returnValue
	 */
	@AfterReturning(value = "logCut()", returning = "returnValue", argNames = "returnValue")
	public void doAfterForResullt(JoinPoint joinPoint, Object returnValue) {
		if (returnValue instanceof Message) {
			User user = ThreadVariable.getUser();
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			LogDescriptionEnum logDescription = method.getAnnotation(LogAnno.class).value();
			Message message = (Message) returnValue;
			String result = message.getMsg();
			switch (result) {
			case "success":
				logger.info("【{}（{}）：{}】：成功！", user.getName(), user.getAccount(), logDescription, method);
				break;
			case "error":
				logger.error("【{}（{}）：{}[{}]】：失败：{}", user.getName(), user.getAccount(), logDescription, method,
						message.getE());
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 日志打印HTTP请求异常
	 * 
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(value = "logCut()", throwing = "exception", argNames = "exception")
	public void doThrowsException(JoinPoint joinPoint, Exception exception) {
		User user = ThreadVariable.getUser();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		LogDescriptionEnum logDescription = method.getAnnotation(LogAnno.class).value();
		logger.error("【{}（{}）：{}[{}]】：异常：{}", user.getName(), user.getAccount(), logDescription, method, exception.toString());
	}
}
