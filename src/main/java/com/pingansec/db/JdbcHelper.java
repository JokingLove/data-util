package com.pingansec.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pingansec.utils.JdbcUtil;
import com.pingansec.utils.StringUtils;

public class JdbcHelper implements DBHelper{
	
	private final Logger log = LoggerFactory.getLogger(JdbcHelper.class);
	
	public static final int PRE_INSERT_SIZE = 1000;
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
	@Override
	public Connection getConnection() {
		return JdbcUtil.getConnection();
	}

	@Override
	public boolean execute(String sql) throws Exception {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = JdbcUtil.getConnection();
			statement = connection.createStatement();
			return statement.execute(sql);
		} catch (SQLException e) {
			log.error("sql exception : " + e.getMessage());
			throw e;
		} finally {
			JdbcUtil.close(connection, statement, null);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public int[] batchInsert(String tableName, String[] dbFieldList, List<String[]> data) throws Exception {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		String threadName = Thread.currentThread().getName();
		String insertSql = getInsertSql(tableName, dbFieldList); 
		int[] result = null;
		try{
			connection = JdbcUtil.getConnection();
			JdbcUtil.beginTransaction(connection);
			prepareStatement = connection.prepareStatement(insertSql);
			int dataSize = data.size();
			for(int rowIndex = 0; rowIndex < dataSize; rowIndex ++) {
				String[] rowData = data.get(rowIndex);
				for(int i = 0; i < rowData.length; i++) {
					prepareStatement.setString(i + 1, rowData[i] == null ? StringUtils.EMPTY : StringUtils.trim(rowData[i]));
				}
				
				// 参数补齐
				int dataLen = rowData.length, fieldLen = dbFieldList.length;
				if(dataLen < fieldLen) {
					for(int i = dataLen; i < fieldLen; i++) {
						prepareStatement.setString(i + 1, StringUtils.EMPTY);
					}
					if(log.isDebugEnabled()) {
						log.debug(String.format("补齐了【%s】：%s", fieldLen - dataLen, Arrays.asList(rowData)));
					}
				}
				if(log.isDebugEnabled()) {
					log.debug(String.format("第【%s】条数据，参数长度：%s，字段长度：%s， 数据：%s", rowIndex, dataLen, fieldLen, Arrays.asList(rowData)));
				}
				// 添加到 batch 中
				prepareStatement.addBatch();
				// 提交执行语句
				if(((rowIndex + 1) % PRE_INSERT_SIZE) == 0 || rowIndex >= dataSize - 1) {
					log.info(String.format("【%s】批量插入数据【%s】,最后一条数据：%s", threadName, rowIndex + 1, Arrays.asList(rowData)));
					try {
						result = prepareStatement.executeBatch();
						// 手动提交
						JdbcUtil.commitTransaction(connection);
						// 清空batch
						prepareStatement.clearBatch();
					}catch (Exception e) {
//						result = prepareStatement.executeBatch(1);
						for(String[] item : data) {
							String shopName = item[1];
							if(!StringUtils.isEmpty(shopName) && shopName.length() > 1024) {
								System.out.println("异常数据：" + Arrays.asList(item));
							}
						}
						throw e;
					}
					log.info(String.format("批量执行结果：%s", result.length));
				}
			}
			log.info(String.format("【%s】批量执行完成!", threadName));
			
		} catch (Exception e) {
			log.error("批量插入出错：" + e.getMessage());
			throw e;
		} finally {
			JdbcUtil.close(connection, prepareStatement, null);
		}
		
		return result;
	}
	
	/**
	 * 获取插入语句
	 * @param dbFieldList
	 * @return
	 */
	private  String getInsertSql(String tableName, String[] dbFieldList) {
		StringBuilder builder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();
		builder.append("INSERT INTO ").append(tableName).append(LEFT_BRACKETS);
		valueBuilder.append(" VALUES").append(LEFT_BRACKETS); 
		int length = dbFieldList.length;
		for(int i = 0; i < dbFieldList.length; i++) {
			String dbField = dbFieldList[i];
			// 拼接字段名
			builder.append(dbField);
			// 拼接占位符
			valueBuilder.append("?");
			if(i < length -1) {
				builder.append(FIELD_SPERATOR);
				valueBuilder.append(FIELD_SPERATOR);
			}
		}
		builder.append(RIGHT_BRACKETS).append(valueBuilder).append(RIGHT_BRACKETS);
		if(log.isInfoEnabled()) {
			log.info(String.format("插入语句：%s", builder.toString()));
		}
		return builder.toString();
	}

	/**
	 * 动态去修改数据库中字段长度
	 * @param tableName
	 * @param e
	 */
	@SuppressWarnings("unused")
	private void alterTableLength(String tableName, Exception e) {
		StringBuilder builder = new StringBuilder();
		builder.append("alter table ").append(tableName).append(" modify column ");
		
	}
	
	public static void main(String[] args) {
		String str = "Data too long for column 'business_scope' at row 1 ";
		String regex = "^.*'(.+)*'+.*$";
		System.out.println(com.pingansec.utils.StringUtils.subUtil(str, regex));
	}
	
}
