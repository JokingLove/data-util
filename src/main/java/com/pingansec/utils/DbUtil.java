package com.pingansec.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DbUtil {	
	
	private static DruidDataSource dataSource = new DruidDataSource();
	private static Logger log = LoggerFactory.getLogger(DbUtil.class);
	private static Connection conn = null;
	private static PreparedStatement pst = null;
	private static ResultSet rs = null;
	private static void close() {
		try {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			if (conn != null) {
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			throw new RuntimeException("释放资源失败");
		}
	}

	private static void iniConnection() {
		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}
		} catch (SQLException e) {
			throw new RuntimeException("初始化链接失败");
		}
	}

	private static void iniPreparedStatment(String sql) {
		if (conn == null) {
			iniConnection();
		}
		try {
			pst = conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new RuntimeException("初始化编译对象失败");
		}
	}
     static int update(String sql, Object... params) {
		iniConnection();
		iniPreparedStatment(sql);
		log.debug(sql);
		int row = 0;
		try {
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i+1, params[i]);
			}
			row = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				close();
		}
		return row;
	}

	 static List<Map<String, Object>> select(String sql, Object... param) {
		iniConnection();
		iniPreparedStatment(sql);
		log.debug(sql);
		List<Map<String, Object>> rsTable = new ArrayList<Map<String, Object>>();
		try {
			for (int i = 0; i < param.length; i++) {
				pst.setObject(i + 1, param[i]);
			}
			rs = pst.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsm = rs.getMetaData();
				while (rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					for (int i = 1; i <= rsm.getColumnCount(); i++) {
						row.put(rsm.getColumnName(i), rs.getObject(i));
					}
					rsTable.add(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				close();
		}
		return rsTable;
	}
	

}
