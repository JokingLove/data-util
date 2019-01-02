package com.pingansec.common;

import java.lang.reflect.Field;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FieldAnnotation<T> {
	/** 属性  */
	private Field field;
	/** 注解  */
	private T annotation;
	
	
}
