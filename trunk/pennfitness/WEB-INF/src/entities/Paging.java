package entities;

import javax.servlet.http.HttpServletRequest;

import model.DBConnector;
import model.DBUtilRoute;

public class Paging {

	public static final int DEFAULTRECSPERPAGE = 5;
	public static final int DEFAULTCURRENTPAGE = 1;
	
	private int recsPerPage = DEFAULTRECSPERPAGE;
	private int curPage = DEFAULTCURRENTPAGE;
	private int totalRecordCnt = 0;
	
	public Paging(HttpServletRequest req, int totalRecordCnt)
	{
		this.totalRecordCnt = totalRecordCnt;
		
		// get recsPerPage
		if(req.getParameter("recsPerPage") != null ) {
			try {
				recsPerPage = Integer.parseInt(req.getParameter("recsPerPage"));
			} catch(Exception ex) { /* do nothing  */ }
		}
		
		// get curPage
		if(req.getParameter("curPage") != null ) {
			try {
				curPage = Integer.parseInt(req.getParameter("curPage"));
			} catch(Exception ex) { /* do nothing  */ }
		}
		
		/* check if exceeding total number of pages */
		if( totalRecordCnt == 0 ) {
			curPage = 1;
		}
		else if((curPage-1) * recsPerPage >= totalRecordCnt) {
			curPage = (int)Math.ceil((double)totalRecordCnt/(double)recsPerPage);
		}
	}
	
	public int getTotalRecordCnt()
	{
		return totalRecordCnt;  
	}
	
	public int getRecsPerPage()
	{
		return recsPerPage;
	}
	
	public int getCurPage()
	{
		return curPage;
	}
	
}
