package cn.promore.bf.bean;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 *  分页类
 */
public class Page {
	
	private Integer pageSize 		= 10;
	private Integer totalRecords 	= 0;
	private Integer totalPages 		= 0;
	private Integer currentPage 	= 1;
	
/*	private Integer startPage 		= 1;
	private Integer endPage   		= 1;
	private Integer pageNum 		= 5;*/
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Integer getTotalPages() {
		this.totalPages = (totalRecords%pageSize==0)?totalRecords/pageSize:(totalRecords/pageSize)+1;
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
/*	public Integer getStartPage() {
		if(currentPage<=pageNum/2+1){
			startPage = 1;
			endPage	  = totalPages;
		}
		if(currentPage>pageNum/2+1){
			startPage = currentPage - pageNum/2;
			endPage   = currentPage + pageNum/2;
			if(endPage>totalPages)endPage = totalPages;
		}
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}*/
/*	public static void main(String[] args) {
		Page page = new Page();
		page.setTotalRecords(11);
		page.setCurrentPage(1);
		System.out.println("CurrentPage:"+page.getCurrentPage());
		System.out.println("TotalPage:"+page.getTotalPages());
		System.out.println("startPage:"+page.getStartPage());
		System.out.println("endPage:"+page.getEndPage());
	}*/
}
