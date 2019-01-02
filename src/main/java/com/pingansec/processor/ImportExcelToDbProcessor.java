package com.pingansec.processor;

import java.util.List;

import org.slf4j.Logger;

import com.pingansec.bean.excel.HljShop;
import com.pingansec.bean.excel.SqShopData;
import com.pingansec.bean.excel.WebSiteInfo;
import com.pingansec.db.JdbcHelper;
import com.pingansec.handler.DbHandler;
import com.pingansec.handler.imp.DefaultInsertDbHandler;
import com.pingansec.utils.XLSXToCSV;

public class ImportExcelToDbProcessor implements Processor{
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ImportExcelToDbProcessor.class);
	
	public static final int THREAD_SIZE = 10;

	public Object process(String[] args) {
		String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\江苏\\宿迁\\宿迁工商\\网站1.CSV";
		Class<SqShopData> beanClass = SqShopData.class;
		Class<JdbcHelper> dbHelperClass = JdbcHelper.class;
/*		String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\总局\\黑龙江\\黑龙江核查网站-0716.xlsx";
		Class<HljShop> beanClass = HljShop.class;
		Class<JdbcHelper> dbHelperClass = JdbcHelper.class;
*/		/*String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\湖南\\长沙\\长沙工商\\提交网监处企业数据批量查询\\企业登记数据批量查询-年报信息-网站与网店信息.xlsx";
		Class<WebSiteInfo> beanClass = WebSiteInfo.class;
		Class<JdbcHelper> dbHelperClass = JdbcHelper.class;*/
		/**  基本信息  **/
		/*String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\湖南\\长沙\\长沙工商\\提交网监处企业数据批量查询\\企业登记数据批量查询-基本信息.xlsx";
		Class<CompanyBaseInfo> beanClass = CompanyBaseInfo.class;
		Class<JdbcHelper> dbHelperClass = JdbcHelper.class;*/
		/** 年报 -企业  **/
		/*String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\湖南\\长沙\\长沙工商\\提交网监处企业数据批量查询\\企业登记数据批量查询-年报信息(企业).xlsx";
		Class<YearReportEnteInfo> beanClass = YearReportEnteInfo.class;
		Class<JdbcHelper> dbHelperClass = JdbcHelper.class;*/
		/** 年报  - 个体  **/
		/*String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\湖南\\长沙\\长沙工商\\提交网监处企业数据批量查询\\企业登记数据批量查询-年报信息(农专).xlsx";
		Class<YearReportFarmerInfo> beanClass = YearReportFarmerInfo.class;
		Class<JdbcHelper> dbHelperClass = JdbcHelper.class;*/
		/** 年报 - 农专  **/
//		String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\湖南\\长沙\\长沙工商\\提交网监处企业数据批量查询\\企业登记数据批量查询-年报信息(个体).csv";
		/*Class<YearReportIndividualInfo> beanClass = YearReportIndividualInfo.class;
		Class<JdbcHelper> dbHelperClass = JdbcHelper.class;*/
		// [\\t\\n\\r]
		try {
			log.info("开始读取excel文件...");
			List<String[]> data = XLSXToCSV.getExcelOrCsvList(filePath);
			int length = data == null ? 0 : data.size();
			log.info("读取到数据条数：" + length);
			DbHandler dbHandler = new DefaultInsertDbHandler(beanClass, dbHelperClass);
			boolean result = dbHandler.createTableAndInsertData(data, false, THREAD_SIZE);
			
		} catch (Exception e) {
			log.error(String.format("processor exception : [%s]", e.getMessage()));
		}
		return null;
	}

	public static void main(String[] args) {
		Processor processor = new ImportExcelToDbProcessor();
		processor.process(args);
	}

}
