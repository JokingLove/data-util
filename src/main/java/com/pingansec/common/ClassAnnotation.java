package com.pingansec.common;

import java.lang.annotation.Annotation;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClassAnnotation {
	/** 属性  */
	private Class<?> clazz;
	/** 注解  */
	private Annotation annotation;
	
}
