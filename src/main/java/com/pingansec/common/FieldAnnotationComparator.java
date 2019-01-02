package com.pingansec.common;

import java.util.Comparator;

import com.pingansec.annotation.ExcelField;

public class FieldAnnotationComparator implements Comparator<FieldAnnotation<ExcelField>> {

	@Override
	public int compare(FieldAnnotation<ExcelField> field1, FieldAnnotation<ExcelField> field2) {
		ExcelField excelField1 = field1.getAnnotation();
		ExcelField excelField2 = field2.getAnnotation();
		
		if(excelField1 == null || excelField2 == null) {
			throw new IllegalStateException("排序注解不能为空！");
		}
		return excelField1.index() - excelField2.index();
	}

}
