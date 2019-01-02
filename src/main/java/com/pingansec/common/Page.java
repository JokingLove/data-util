/**
 * @(#)Page.java
 *
 * @author huawei
 * @version 1.0 2015-2-9
 *
 * Copyright (C) 2012,2015 , PING' AN, Inc.
 */
package com.pingansec.common;

import java.util.List;

/**
 * 
 * Purpose:
 * 
 * @see	    
 * @since   1.1.0
 */
public class Page<T>
{
	public static final Integer PAGENUM_INITIAL_VALUE = new Integer(10);//展示
    private int currentPage = 1; //当前的页数
    private int totalCount; //总共多少行
    private int pageNum;    //每一页多少行数据
    private int pageItemNum=10; //分页条目 显示多少条 1 2 3 4 5 默认按每一页多少条数据来
    private int pageCount;  //一共多少页
    private List<T> data; //当前页的数据
    private boolean hasNext;//是否有下一页
    private boolean hasPre;//是否有上一页
    private int begin;
    private int end;
    private String orderBy;
    private String groupBy;
    private boolean isAsc = false;
    private boolean hasPaging;
    private boolean reloadTotleCount = true;
    

	public boolean isHasPaging()
    {
        return  hasPaging;
    }

    public void setHasPaging(boolean hasPaging)
    {
        this.hasPaging = hasPaging ;
    }

    public Page()
    {
    }
    public Page(Integer currentPage,int pageNum,Integer totalCount)
    {
        this.currentPage = currentPage;
        this.pageNum = pageNum;
        this.totalCount = totalCount;
    }
    public Page(int pageNum)
    {
        this.pageNum = pageNum;
        if(this.pageItemNum == 0)
        	this.pageItemNum = pageNum;
        this.currentPage = 1;
    }
   

    public boolean getHasNext(){
        if(getPageCount() == currentPage)
            hasNext = false;
        else
            hasNext = true;
        return hasNext;   
    }
    public boolean getHasPre(){
        if(currentPage == 1)
            hasPre = false;
        else
            hasPre = true;
        return hasPre;
    }
    
    public int getCurrentPage()
    {
        return currentPage;
    }
    public void setCurrentPage(int currentPage)
    {   
        this.currentPage = currentPage;
    }
    public int getPageCount()
    {
        return pageCount;
    }
    public int getTotalCount()
    {
        return totalCount;
    }
    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
        compute();
        this.setHasPaging(getPageCount() > 1);
    }
    
    public boolean getIsAsc()
    {
        return isAsc;
    }
    public void setIsAsc(boolean isAsc)
    {
        this.isAsc = isAsc;
    }
    public List<T> getData()
    {
        return data;
    }
    public void setData(List<T> data)
    {
        this.data = data;
    }
    public int getPageNum()
    {
        return pageNum;
    }
    public boolean hasInit(){
        return pageNum != 0;
    }
    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
        if(this.pageItemNum == 0)
            this.pageItemNum = pageNum;
       
    }
    public int getPageStart() 
    {
        if (currentPage <= 0) 
        {
            return 0;
        }
        if(currentPage > getPageCount()) 
        {
            currentPage = getPageCount();
        }
        return pageNum * (currentPage - 1);
    }
    private void compute(){
    	if( this.pageNum == 0 ) {
    		pageNum = PAGENUM_INITIAL_VALUE;
    	}
        if(totalCount == 0)
        {
            pageCount = 0;
            begin = 0;
            end = 0;
            return ;
        }
        if(totalCount % pageNum == 0){
            pageCount = totalCount/pageNum;
        }
        else
        {
           pageCount = (totalCount/pageNum) +1;
        }
        
        begin = computeBegin(this.currentPage,this.pageItemNum);//+1;
        
        end = computeEnd(pageCount, currentPage, this.pageItemNum);
//        System.out.println(begin);
//        System.out.println(end);
//        System.out.println(pageCount);
//        System.out.println(totalCount);
    }
    
   
    private  int computeBegin(int currentPage,int pageNum){
//        if(currentPage%pageNum  == 0)
//        {
//            return currentPage/pageNum *pageNum - pageNum;
//        }
//        return (currentPage/pageNum)*pageNum;
    	if(pageCount < pageItemNum)
			return 1;
    	if(currentPage <= pageItemNum / 2){
			return 1;
		}
		if(currentPage > pageItemNum / 2 )
			return Math.min(currentPage - pageItemNum / 2,pageCount - pageItemNum+1);
		
		if(currentPage >= pageCount){
			return  pageCount - pageItemNum +1;
		}
		return 1;
    }
    
    
    private  int computeEnd(int pageCount,int currentPage,int pageItemNum){
//        if(currentPage%pageNum == 0){
//            return currentPage; 
//        }
//        if((currentPage / pageNum) < (pageCount/pageNum)){
//            return (currentPage/pageNum)*pageNum+pageNum; 
//        }
//        if((currentPage / pageNum) == (pageCount/pageNum)){
//            if(currentPage == pageCount)
//                return currentPage;
//            else {
//                return pageCount; 
//            }
//        }
//      return 0;
    	if(pageCount < pageItemNum)
			return pageCount;
		
		if(currentPage <= pageItemNum /2)
		{
			return pageItemNum;
		}
		if(currentPage >= pageCount){
			return  pageCount;
		}
		if(currentPage > pageItemNum / 2 ) //针对不整除的情况和整除的情况
			return Math.min(currentPage + new Long(Math.round(pageItemNum / 2.0)).intValue() -1,pageCount);
		
		return pageCount;

    }
    
    
    public int getPageItemNum()
    {
        return pageItemNum;
    }

    public void setPageItemNum(int pageItemNum)
    {
        this.pageItemNum = pageItemNum;
    }

    public  int getBegin(){
        return begin;
    }
    public  int getEnd(){
        return end;
    }
    public String getGroupBy()
    {
        return groupBy;
    }
    public void setGroupBy(String groupBy)
    {
        this.groupBy = groupBy;
    }
    public String getOrderBy()
    {
        return orderBy;
    }
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

	public boolean getReloadTotleCount() {
		return reloadTotleCount;
	}

	public void setReloadTotleCount(boolean reloadTotleCount) {
		this.reloadTotleCount = reloadTotleCount;
	}

}


/**
 * $Log: Page.java,v $
 * 
 * @version 1.0 2015-2-9 
 *
 */