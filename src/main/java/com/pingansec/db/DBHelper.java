package com.pingansec.db;

import java.sql.Connection;
import java.util.List;

public interface DBHelper {
	
	Connection getConnection();
	
	boolean execute(String sql) throws Exception;

	int[] batchInsert(String tableName, String[] fieldList, List<String[]> data) throws Exception;
}
