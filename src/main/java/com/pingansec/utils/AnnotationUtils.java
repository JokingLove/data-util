package com.pingansec.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.pingansec.annotation.ExcelField;
import com.pingansec.bean.excel.CompanyBaseInfo;
import com.pingansec.common.ClassAnnotation;
import com.pingansec.common.FieldAnnotation;


public class AnnotationUtils {

	public static <A extends Annotation> List<FieldAnnotation<A>> findFeildAnnotation(Class<?> clazz, Class<A> annotationType) {
		Assert.notNull(annotationType, "anntation is null");
		Assert.notNull(clazz, "type is null to find Annotation [" + clazz + "]");
		
		List<FieldAnnotation<A>> fieldAnnotations = new ArrayList<>();
		Field[] fields = clazz.getDeclaredFields();
		if(ArrayUtils.isNotEmpty(fields)) {
			for(Field field : fields) {
				Annotation[] annotations = field.getAnnotations();
				for(Annotation ann : annotations) {
					if(ann.annotationType().equals(annotationType)) {
						FieldAnnotation<A> fa = new FieldAnnotation<A>();
						fa.setAnnotation((A)ann);
						fa.setField(field);
						fieldAnnotations.add(fa);
					}
				}
			}
		}
		
		return fieldAnnotations;
	}


	public static List<ClassAnnotation> findClassAnnotations(Class<?> clazz,
			Class<?> ... annotationTypes) {
		Assert.notNull(annotationTypes, "anntation is null");
		Assert.notNull(clazz, "type is null to find Annotation [" + clazz + "]");
		
		Set<Class<?>> annotationTypeSet = new HashSet<>(Arrays.asList(annotationTypes));
		
		List<ClassAnnotation> classAnnotations = Lists.newArrayList();
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		for(Annotation ann : annotations) {
			if(annotationTypeSet.contains(ann.annotationType())) {
				ClassAnnotation ca = new ClassAnnotation();
				ca.setAnnotation(ann);
				ca.setClazz(clazz);
				classAnnotations.add(ca);
			}
		}
		return classAnnotations;
	}
	
	
	public static void main(String[] args) {
		List<FieldAnnotation<ExcelField>> feildAnnotations = findFeildAnnotation(CompanyBaseInfo.class, ExcelField.class);
//		feildAnnotations.forEach(System.out::println);
	}
}
