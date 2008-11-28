package entities;

import javax.servlet.http.HttpServletRequest;

import model.DBConnector;
import model.DBUtilRoute;

public class Paging {

	public static final int DEFAULTRECSPERPAGE = 5;
	public static final int DEFAULTCURRENTPAGE = 1;
	
	private int recsPerPage = DEFAULTRECSPERPAGE;
	private int curPage = DEFAULTCURRENTPAGE;
	
	public Paging(HttpServletRequest req)
	{
		// get recsPerPage
		if(req.getParameter("recsPerPage") != null) {
			recsPerPage = Integer.parseInt(req.getParameter("recsPerPage"));
		}
		
		// get curPage
		if(req.getParameter("curPage") != null) {
			curPage = Integer.parseInt(req.getParameter("curPage"));
		}
		
		
	}
	
	public void test()
	{
		// For paging
		
		/* get recsPerPage */
		String recsPerPageString = req.getParameter("recsPerPage");
		int recsPerPage = recsPerPageString == null ? DBConnector.DEFAULTRECSPERPAGE : 
		
		/* get curPage */
		String curPageString = req.getParameter("curPage");
		int curPage = curPageString == null ? DBConnector.DEFAULTCURRENTPAGE : Integer.parseInt(curPageString);

		/* get total num of records */
		int totalRecordCnt = DBUtilRoute.getSearchForRoutesCount( params );
		
		/* check if exceeding total num pages */
		if( totalRecordCnt == 0 ) {
			curPage = 1;
		}
		else if((curPage-1) * recsPerPage >= totalRecordCnt) {
			curPage = (int)Math.ceil((double)totalRecordCnt/(double)recsPerPage);
		}

	}
	
	
}
