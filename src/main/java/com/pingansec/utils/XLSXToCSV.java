package com.pingansec.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.opencsv.CSVReader;  

/**
 * xlsx转csv，防止读取大数据量xlsx时内存溢出
 * @author zhxl
 *
 */
public class XLSXToCSV {
	
	public static final Logger log = LoggerFactory.getLogger(XLSXToCSV.class);
	
	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF-8";
    /** 
     * Uses the XSSF Event SAX helpers to do most of the work 
     * of parsing the Sheet XML, and outputs the contents 
     * as a (basic) CSV. 
     */  
    private class SheetToCSV implements SheetContentsHandler {  
        private boolean firstCellOfRow = false;  
        private int currentRow = -1;  
        private int currentCol = -1;  
        private StringBuffer buf = new StringBuffer();
  
        private void outputMissingRows(int number) {  
            for (int i = 0; i < number; i++) {  
                for (int j = 0; j < minColumns; j++) {  
                	buf.append(',');  
                }  
                buf.append('\n');  
            }  
        }  
  
        @Override  
        public void startRow(int rowNum) {  
            // If there were gaps, output the missing rows  
            outputMissingRows(rowNum - currentRow - 1);  
            // Prepare for this row  
            firstCellOfRow = true;  
            currentRow = rowNum;  
            currentCol = -1;  
        }  
  
        @Override  
        public void endRow(int rowNum) {  
            // Ensure the minimum number of columns  
            for (int i = currentCol; i < minColumns; i++) {  
            	buf.append(',');  
            }  
            buf.append('\n');  
            if(buf != null && StringUtils.isNotBlank(buf.toString())) {
            	//保存buf
            	output.add(buf.toString().split(","));
            	//重置stringbuffer
            	buf = new StringBuffer();
            }
        }  
  
        @Override  
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {  
            if (firstCellOfRow) {  
                firstCellOfRow = false;
            } else {  
            	buf.append(',');  
            }  
  
            // gracefully handle missing CellRef here in a similar way as XSSFCell does  
            if (cellReference == null) {  
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();  
            }  
  
            // Did we miss any cells?  
            int thisCol = (new CellReference(cellReference)).getCol();  
            int missedCols = thisCol - currentCol - 1;  
            for (int i = 0; i < missedCols; i++) {  
            	buf.append(',');  
            }  
            currentCol = thisCol;  
  
            buf.append(formattedValue.replaceAll(",|，|\'", ""));   
        }  
  
        @Override  
        public void headerFooter(String text, boolean isHeader, String tagName) {  
            // Skip, no headers or footers in CSV  
        }  
    }

  
    ///////////////////////////////////////  
  
    private final OPCPackage xlsxPackage;  
  
    /** 
     * Number of columns to read starting with leftmost 
     */  
    private final int minColumns;  
  
    /** 
     * Destination for data 
     */  
    private List<String[]> output;  
  
    /** 
     * Creates a new XLSX -> CSV converter 
     * 
     * @param pkg        The XLSX package to process 
     * @param output     The PrintStream to output the CSV to 
     * @param minColumns The minimum number of columns to output, or -1 for no minimum 
     */  
    public XLSXToCSV(OPCPackage pkg, List<String[]> output, int minColumns) {  
        this.xlsxPackage = pkg;  
        this.output = output;  
        this.minColumns = minColumns;  
    }  
  
    /** 
     * Parses and shows the content of one sheet 
     * using the specified styles and shared-strings tables. 
     * 
     * @param styles 
     * @param strings 
     * @param sheetInputStream 
     */  
    public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream) throws IOException, ParserConfigurationException, SAXException {  
        DataFormatter formatter = new DataFormatter();  
        InputSource sheetSource = new InputSource(sheetInputStream);  
        try {  
            XMLReader sheetParser = SAXHelper.newXMLReader();  
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);  
            sheetParser.setContentHandler(handler);  
            sheetParser.parse(sheetSource);  
        } catch (ParserConfigurationException e) {  
            throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());  
        }  
    }  
  
    /** 
     * Initiates the processing of the XLS workbook file to CSV. 
     * 
     * @throws IOException 
     * @throws OpenXML4JException 
     * @throws ParserConfigurationException 
     * @throws SAXException 
     */  
    public List<String[]> process() throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {  
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);  
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);  
        StylesTable styles = xssfReader.getStylesTable();  
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();  
        while (iter.hasNext()) {  
            InputStream stream = iter.next();  
            processSheet(styles, strings, new SheetToCSV(), stream);  
            stream.close();  
        }
        
        return output;
    }  
    
    public static List<String[]> getExcelOrCsvList(String path) throws Exception {
    	List<String[]> list = null;
    	if(ExcelUtil.isXlsx(path)) {
    		list = getXlsxToCsvList(path);
    	} else if(ExcelUtil.isCsv(path)) {
    		list = getCsvList(path, getEncoding(path));
    	} else {
    		throw new IllegalArgumentException("格式不支持，支持格式为：【xlsx,csv】,xls格式文件，请用getXls方法！");
    	}
    	return list;
    }
    
    public static String getEncoding(String path) {
    	
        try {
        	InputStream in = new FileInputStream(path);
        	byte[] b = new byte[3];
            in.read(b);
			in.close();
			if (b[0] == -17 && b[1] == -69 && b[2] == -65)
		            return UTF8;
		} catch (IOException e) {
			log.error("检测编码出错：" + e.getMessage());
		}
       
        return GBK;
    }
    
    /**
     * xlxs转csv并返回读取结果
     * @param path
     * @return
     */
    public static List<String[]> getXlsxToCsvList(String path) {
    	List<String[]> list = null;
    	try {
    		OPCPackage p = OPCPackage.open(new File(path).getPath(), PackageAccess.READ);  
            XLSXToCSV xlsx2csv = new XLSXToCSV(p, new ArrayList<String[]>(), -1);  
            list = xlsx2csv.process();  
            p.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return list;
    }
    
    public static List<String[]> getCsvList(String filePath, String encoding) throws Exception{
    	if(StringUtils.isEmpty(encoding)) encoding = UTF8;
    	InputStream is = new FileInputStream(filePath);
  		InputStreamReader read = new InputStreamReader(is,encoding);
    	CSVReader reader = new CSVReader(read);
    	return reader.readAll();
    }
    
    //读csv
  	public static List<String[]> getCsv(InputStream inputStream, String encoding) throws IOException {
  		if(StringUtils.isEmpty(encoding)) encoding = UTF8;
  		InputStreamReader read = new InputStreamReader(inputStream,encoding);
        BufferedReader bufferedReader = new BufferedReader(read);
        List<String[]> datas = new ArrayList<String[]>();
        String lineTxt = null;
        int i = 0;
        while((lineTxt = bufferedReader.readLine()) != null){
        	/*if(i != 0) {
        		String[] o = lineTxt.split(",", -1);
                datas.add(o);
        	}*/
        	String[] o = lineTxt.split(",", -1);
            datas.add(o);
        	i++;
        }
        read.close();
  		return datas;
  	}
    
  	/**
     * CSV文件生成方法
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static File createCSVFile(String[] head, List<String[]> dataList, String filename) {

        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(filename);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GBK"), 1024);
            // 写入文件头部
            if(head != null) {
            	String str = "";
            	for(String s : head) {
            		str += StringUtils.isNotBlank(str) ? ("," + s) : s;
            	}
            	csvWtriter.write(str);
            	csvWtriter.newLine();
            }

            // 写入文件内容
            for (String[] arrs : dataList) {
            	String str = "";
            	for(String s : arrs) {
            		if(StringUtils.isNotBlank(s)) {
            			s = s.replaceAll(",|，|\'", "");
            		} else {
            			s= "";
            		}
            		
            		str += StringUtils.isNotBlank(str) ? ("," + s) : s;
            	}
            	csvWtriter.write(str);
            	csvWtriter.newLine();
            	str = "";
            }
            csvWtriter.flush();
            System.out.println("    csv文件导出成功！");
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("    csv文件导出失败！错误：【" + e.getMessage() + "】");
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
    
    
    //读xls
  	public static List<Object[]> getXls(InputStream inputStream) throws Exception {
  		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
  		List<Object[]> datas = new ArrayList<Object[]>();
  		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
  		if (hssfSheet != null) {
  			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
  				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
  				if(hssfRow == null) {
  					 break;
  				}
  				int cellNum = hssfRow.getLastCellNum();
  				Object [] objects  = new Object[cellNum];
  				if(hssfRow.getCell(0) != null && !hssfRow.getCell(0).toString().equals("") && (hssfRow.getCell(cellNum - 1) == null || !hssfRow.getCell(cellNum - 1).toString().equals("1"))) {
  					for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
  						objects[cellIndex] = hssfRow.getCell(cellIndex);
  					}
  					datas.add(objects);
  				}
  			}
  		}
  		
  		hssfWorkbook.close();
  		inputStream.close();
  		return datas;
  	}
  	
  	 //读xls
  	public static List<String[]> getXlsAsString(InputStream inputStream) throws Exception {
  		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
  		List<String[]> datas = new ArrayList<String[]>();
  		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
  		if (hssfSheet != null) {
  			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
  				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
  				if(hssfRow == null) {
  					 break;
  				}
  				int cellNum = hssfRow.getLastCellNum();
  				String [] rows  = new String[cellNum];
  				if(hssfRow.getCell(0) != null && !hssfRow.getCell(0).toString().equals("") && (hssfRow.getCell(cellNum - 1) == null || !hssfRow.getCell(cellNum - 1).toString().equals("1"))) {
  					for (int cellIndex = 0; cellIndex < cellNum; cellIndex++) {
  						rows[cellIndex] =  StringUtils.valueOf(hssfRow.getCell(cellIndex));
  					}
  					datas.add(rows);
  				}
  			}
  		}
  		
  		hssfWorkbook.close();
  		inputStream.close();
  		return datas;
  	}
  	
  	public static void main(String[] args) {
		String filePath = "C:\\Users\\JOKING\\Desktop\\桌面\\pingan\\aic\\湖南\\长沙\\长沙工商\\提交网监处企业数据批量查询\\text.csv";
  		try {
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String [] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				System.out.println(nextLine.length);
				System.out.println(Arrays.asList(nextLine));
//				Arrays.stream(nextLine).forEach(System.out::println);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	}
    
    
}
