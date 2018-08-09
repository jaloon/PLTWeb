package com.tipray.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tipray.constant.LogDescriptionEnum;

/**
 * 启用日志注解
 * 
 * @author chenlong
 * @version 1.0 2018-04-18
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnno {
	/**
	 * 日志描述
	 * 
	 * @return
	 */
	LogDescriptionEnum value();
}
