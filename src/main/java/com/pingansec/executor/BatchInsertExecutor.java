package com.pingansec.executor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pingansec.db.DBHelper;

public class BatchInsertExecutor implements Executor{
	
	private final Logger log = LoggerFactory.getLogger(BatchInsertExecutor.class);

	private final DBHelper dbHelper;
	private final String tableName;
	private final String[] dbFieldList;
	private final List<String[]> dataList;
	
	public BatchInsertExecutor(List<String[]> dataList, String tableName, String[] dbFieldList, DBHelper dbHelper) {
		this.dataList = dataList;
		this.tableName = tableName;
		this.dbFieldList = dbFieldList;
		this.dbHelper = dbHelper;
	}
	
	
	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		long beginTimeMillis = System.currentTimeMillis();
		try {
			log.info(String.format("线程【%s】开始执行！", threadName));
			int[] result = dbHelper.batchInsert(tableName, dbFieldList, dataList);
		} catch (Exception e) {
			log.error(String.format("线程【%s】执行异常：%s", threadName, e.getMessage()), e);
		}
		long endTimeMillis = System.currentTimeMillis();
		log.info(String.format("线程【%s】执行完毕，耗时：%s ms", threadName, endTimeMillis - beginTimeMillis));
	}
	
}
