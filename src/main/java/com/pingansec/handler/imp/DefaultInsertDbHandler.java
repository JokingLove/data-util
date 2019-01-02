package com.pingansec.handler.imp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CaseFormat;
import com.pingansec.annotation.ExcelField;
import com.pingansec.annotation.ExcelTable;
import com.pingansec.bean.BaseBean;
import com.pingansec.common.ClassAnnotation;
import com.pingansec.common.FieldAnnotation;
import com.pingansec.common.FieldAnnotationComparator;
import com.pingansec.db.DBHelper;
import com.pingansec.executor.BatchInsertExecutor;
import com.pingansec.handler.DbHandler;
import com.pingansec.utils.AnnotationUtils;
import com.pingansec.utils.Assert;
import com.pingansec.utils.ReflectionUtils;
import com.pingansec.utils.StringUtils;
import com.pingansec.utils.ThreadPool;

public class DefaultInsertDbHandler implements DbHandler {
	
	public static final Logger log = LoggerFactory.getLogger(DefaultInsertDbHandler.class);
	
	public static final String ABOUT_ENCODING = "ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;";
	public static final String DROP_TABLE_STR = "DROP TABLE IF EXISTS ";
	public static final String COMMENT_STR = " comment ";
	public static final String VARCHAR_STR = " varchar";
	public static final String DEFAULT_STR = " default ";
	public static final int DEFAULT_VARCHAR_LEN = 255;
	public static final String DEFAULT_VARCHAR_VALUE = "\'\'";
	public static final String FIELD_SPERATOR = ",";
	public static final String ENTER_SPERATOR = "\n";
	public static final String SEMICOLON = ";";
	public static final String LEFT_BRACKETS = "(";
	public static final String RIGHT_BRACKETS = ")";
	public static final int    PRE_BATCH_INSERT_SIZE = 1000;
	public static final int    DEFAULT_THREAD_SIZE = 5;
	
	private final Class<? extends BaseBean> clazz;
	private final Class<? extends DBHelper> dbHelperType;
	private DBHelper dbHelper;
	// excel表的表头
	private String[] commentList;

	public DefaultInsertDbHandler(Class<? extends BaseBean> clazz,
			Class<? extends DBHelper> dbHelperType) {
		super();
		this.clazz = clazz;
		this.dbHelperType = dbHelperType;
		dbHelper = ReflectionUtils.newInstance(dbHelperType);
	}
	

	@Override
	public boolean createTableAndInsertData(List<String[]> data) throws Exception {
		return createTableAndInsertData(data, false);
	}

	@Override
	public boolean createTableAndInsertData(List<String[]> data, boolean dropOriginTable) throws Exception {
		return createTableAndInsertData(data, dropOriginTable, DEFAULT_THREAD_SIZE);
	}
	
	@Override
	public boolean createTableAndInsertData(List<String[]> data, boolean dropOriginTable, int threadSize) throws Exception {
		commentList = data.remove(0);
		createTable(dropOriginTable);
		return insertDataToDb(data, threadSize);
	}
	
	private boolean createTable(boolean dropOriginTable) throws Exception {
		boolean result = false;
		if(dropOriginTable) {
			String dropSql = getDropSql();
			dbHelper.execute(dropSql);
			String createSql = getCreateSql();
			result = dbHelper.execute(createSql);  //true if the first result is a ResultSet object; false if the first result is an update count or there is no result
		}
		return result;
	}
	/**
	 * 获取drop table 语句
	 * @return
	 */
	private String getDropSql() {
		StringBuilder builder = new StringBuilder();
		String tableName = getTableName();
		builder.append(DROP_TABLE_STR).append(tableName).append(SEMICOLON).append(ENTER_SPERATOR);
		return builder.toString();
	}

	/**
	 * 插入数据到数据库
	 * @param data
	 * @param threadSize 
	 * @return
	 * @throws Exception 
	 */
	private boolean insertDataToDb(List<String[]> data, int threadSize) throws Exception {
		if(threadSize <= 0) throw new IllegalArgumentException(String.format("线程个数不能小于1：threadSize【%s】", threadSize));
		String[] dbFieldList = this.getDbFieldList();
		String tableName = getTableName();
		// 多个线程去操作
		int dataSize = data.size();
//		int remainder = dataSize % threadSize;
		int preThreadHandleDataSize = dataSize / threadSize;
		for(int i = 0; i < threadSize; i++) {
			List<String[]> preThreadData = data.subList(i * preThreadHandleDataSize, (i +  1) * preThreadHandleDataSize);
			if(i == threadSize - 1) {
				preThreadData = data.subList(i * preThreadHandleDataSize, dataSize);
			}
			Runnable task = new BatchInsertExecutor(preThreadData, tableName, dbFieldList, ReflectionUtils.newInstance(dbHelperType));;
			ThreadPool.submit(task);
		}
		ThreadPool.shutDown();
		return true;
	}
	



	private String[] getDbFieldList() {
		List<FieldAnnotation<ExcelField>> fieldAnnotations = AnnotationUtils.findFeildAnnotation(clazz, ExcelField.class);
		Collections.sort(fieldAnnotations, new FieldAnnotationComparator());
		String[] fields = new String[fieldAnnotations.size()];
		int index = 0;
		for(FieldAnnotation<ExcelField> fieldAnnotation : fieldAnnotations) {
			ExcelField excelField = fieldAnnotation.getAnnotation();
			Field field = fieldAnnotation.getField();
			String value = excelField.value();
			if(!StringUtils.hasText(value)) {
				value = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
			}
			fields[index++] = value;
		}
		if(log.isInfoEnabled()) {
			log.info(String.format("数据库字段：%s", Arrays.asList(fields)));
		}
		return fields;
	}

	/**
	 * 获取创建表语句
	 * @return
	 */
	private String  getCreateSql() {
		StringBuilder builder = new StringBuilder();
		String tableName = getTableName();
		
		builder.append("create table ").append(tableName).append(LEFT_BRACKETS).append(ENTER_SPERATOR);
		List<FieldAnnotation<ExcelField>> feildAnnotations = AnnotationUtils.findFeildAnnotation(clazz, ExcelField.class);
		Collections.sort(feildAnnotations, new FieldAnnotationComparator());
		if(CollectionUtils.isNotEmpty(feildAnnotations)) {
			int len = feildAnnotations.size();
			for(int i = 0 ; i < len; i++) {
				FieldAnnotation<ExcelField> fa = feildAnnotations.get(i);
				ExcelField excelField = (ExcelField)fa.getAnnotation();
				Field field = fa.getField();
				String value = excelField.value();
				// 长度
				int length = excelField.len();
				if(!StringUtils.hasText(value)) {
					value = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
				}
				if(length == 0) length = DEFAULT_VARCHAR_LEN;
				builder.append(value).append(VARCHAR_STR).append(LEFT_BRACKETS)
				.append(length).append(RIGHT_BRACKETS).append(DEFAULT_STR)
				.append(DEFAULT_VARCHAR_VALUE);
				// 拼接commnet
				if(commentList != null && commentList.length > 0) {
					try {
						builder.append(COMMENT_STR).append("'").append(commentList[i]).append("'");
					} catch (Exception e) {
						//  NOTHING
					}
				}
				if(i < len -1) {
					builder.append(FIELD_SPERATOR);
				}
				builder.append(ENTER_SPERATOR);
			}
		}
		builder.append(RIGHT_BRACKETS).append(ABOUT_ENCODING);
		if(log.isInfoEnabled()) {
			log.info(String.format("获取到建表语句：%s", builder.toString()));
		}
		return builder.toString();
	}
	
	private String getTableName() {
		Assert.notNull(clazz, "bean type can not be null");
		String tableName = "";
		List<ClassAnnotation> classAnntations = AnnotationUtils.findClassAnnotations(clazz, ExcelTable.class);
		if(CollectionUtils.isNotEmpty(classAnntations)) {
			ClassAnnotation classAnnotation = classAnntations.get(0);
			Annotation anntation = classAnnotation.getAnnotation();
			if(anntation.annotationType().equals(ExcelTable.class)) {
				ExcelTable excelTable = (ExcelTable)anntation;
				if(!StringUtils.hasText(excelTable.value())) {
					throw new IllegalArgumentException("table name cant not be empty，please check @ExcelTable on the Bean" + clazz);
				}
				tableName = excelTable.value();
			}
		}
		if(log.isInfoEnabled()) {
			log.info(String.format("获取到表名：%s", tableName));
		}
		return tableName;
	}
	

}
