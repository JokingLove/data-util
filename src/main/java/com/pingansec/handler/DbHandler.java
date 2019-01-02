package com.pingansec.handler;

import java.util.List;

public interface DbHandler {
	boolean createTableAndInsertData(List<String[]> data) throws Exception;

	boolean createTableAndInsertData(List<String[]> data, boolean dropOriginTable) throws Exception;

	boolean createTableAndInsertData(List<String[]> data, boolean dropOriginTable, int threadSize) throws Exception;
}
