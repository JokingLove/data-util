/*
 * Created on 2006-3-9
 *
 * @author xuji
 * 
 * Copyright (C) 2006, HC SOFTWARE.
 */
package com.pingansec.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

/**
 * 文件操作相关的函数
 * 
 * @author xuji
 * @version 0.9
 * @since 0.9
 */
public class FileUtil
{
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();  
    
    static
    {
        FILE_TYPE_MAP.put("jpg", "FFD8FF"); //JPEG (jpg)     
        FILE_TYPE_MAP.put("png", "89504E47");  //PNG (png)     
        FILE_TYPE_MAP.put("gif", "47494638");  //GIF (gif)     
        FILE_TYPE_MAP.put("tif", "49492A00");  //TIFF (tif)     
        FILE_TYPE_MAP.put("bmp", "424D"); //Windows Bitmap (bmp)     
        FILE_TYPE_MAP.put("dwg", "41433130"); //CAD (dwg)     
        FILE_TYPE_MAP.put("html", "68746D6C3E");  //HTML (html)     
        FILE_TYPE_MAP.put("rtf", "7B5C727466");  //Rich Text Format (rtf)     
        FILE_TYPE_MAP.put("xml", "3C3F786D6C");    
        FILE_TYPE_MAP.put("zip", "504B0304");    
        FILE_TYPE_MAP.put("rar", "52617221");    
        FILE_TYPE_MAP.put("psd", "38425053");  //Photoshop (psd)     
        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A");  //Email [thorough only] (eml)     
        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F");  //Outlook Express (dbx)     
        FILE_TYPE_MAP.put("pst", "2142444E");  //Outlook (pst)     
        FILE_TYPE_MAP.put("xls", "D0CF11E0");  //MS Word     
        FILE_TYPE_MAP.put("doc", "D0CF11E0");  //MS Excel 注意：word 和 excel的文件头一样     
        FILE_TYPE_MAP.put("mdb", "5374616E64617264204A");  //MS Access (mdb)     
        FILE_TYPE_MAP.put("wpd", "FF575043"); //WordPerfect (wpd)      
        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");    
        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");    
        FILE_TYPE_MAP.put("pdf", "255044462D312E");  //Adobe Acrobat (pdf)     
        FILE_TYPE_MAP.put("qdf", "AC9EBD8F");  //Quicken (qdf)     
        FILE_TYPE_MAP.put("pwl", "E3828596");  //Windows Password (pwl)     
        FILE_TYPE_MAP.put("wav", "57415645");  //Wave (wav)     
        FILE_TYPE_MAP.put("avi", "41564920");    
        FILE_TYPE_MAP.put("ram", "2E7261FD");  //Real Audio (ram)     
        FILE_TYPE_MAP.put("rm", "2E524D46");  //Real Media (rm)     
        FILE_TYPE_MAP.put("mpg", "000001BA");  //     
        FILE_TYPE_MAP.put("mov", "6D6F6F76");  //Quicktime (mov)     
        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); //Windows Media (asf)     
        FILE_TYPE_MAP.put("mid", "4D546864");  //MIDI (mid)    
    }
    
    /**
     * 构造输入的两个路径片断为组合路径
     * 
     * @param s1
     * @param s2
     * @return
     */
    public static final String join(String s1, String s2)
    {
        File f = new File(s1, s2);
        return f.getPath();
    }

    /**
     * 以byteArray的形式读文件的全部内容
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static final byte[] readFileAsByteArray(String path) throws Exception
    {
        File file = new File(path);
        FileInputStream fi = new FileInputStream(file);
        
        try
        {
            return inputStreamToByteArray(fi);
        }
        finally
        {
            fi.close();
        }
    }
    
    /**
     * 以Text的形式读文件的全部内容
     * 
     * @param path
     * @return
     * @throws Exception
     */
    public static final String readFileAsText(String path) throws Exception
    {
        FileReader reader = new FileReader(path);
        BufferedReader br = new BufferedReader(reader);
        
        try
        {
            String text = "";
            String line = null;
        
            while((line = br.readLine()) != null)
            {
                text = text + line + "\n";
            }
            
            return text;
        }
        finally
        {
            br.close();
            reader.close();
        }
    }

    /**
     * 以byteArray的形式写入文件
     * 
     * @param path
     * @param data
     * @return
     * @throws Exception
     */
    public static final void writeByteArrayToFile(String path, byte[] data) throws Exception
    {
        File file = new File(path);
        FileOutputStream fo = new FileOutputStream(file);
        try
        {
            fo.write(data);
        }
        finally
        {
            fo.close();
        }
    }
    
    /**
     * 读取InputStream中的byte
     * 
     * @param is 
     * @return byte[]
     * @throws IOException
     */
    public static byte[] inputStreamToByteArray(InputStream is) throws IOException 
    {  
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(); 
        
        try
        {
            int ch = -1;
            
            while ((ch = is.read()) != -1) 
            {  
                byteStream.write(ch);  
            }  
               
            return byteStream.toByteArray();  
        }
        finally
        {
            byteStream.close();  
        }
    }
    
    public static final boolean isImage(InputStream is)
    {  
        boolean ret = false;  
        
        try  
        {  
            BufferedImage bufreader = ImageIO.read(is);  
            int width = bufreader.getWidth();  
            int height = bufreader.getHeight();  
            if(width==0 || height==0)
            {  
                ret = false;  
            }
            else 
            {  
                ret = true;  
            }  
        }  
        catch (IOException e)  
        {  
            ret = false;  
        }
        catch (Exception e) 
        {  
            ret = false;  
        } 
        
        return ret;  
    } 
    
    /**
     * 获取文件格式
     * @param b
     * @return
     */
    public final static String getFileTypeByStream(byte[] b)
    {
        String filetypeHex = String.valueOf(getFileHexString(b));
        Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
        while (entryiterator.hasNext())
        {
            Entry<String, String> entry = entryiterator.next();
            String fileTypeHexValue = entry.getValue();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue))
            {
                return entry.getKey();
            }
        }
        return null;
    }     
    
    private static String getFileHexString(byte[] b)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0)
        {
            return null;
        }
        for (int i = 0; i < b.length; i++)
        {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2)
            {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

}
