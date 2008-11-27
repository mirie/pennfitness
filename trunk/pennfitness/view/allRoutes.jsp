
<%@ page import="java.util.List, java.math.*, java.text.*, java.util.Iterator, model.DBConnector, model.DBUtilRoute, entities.Route, org.json.simple.JSONObject" %>

<% 
	// paging

	/* get recsPerPage */
	String recsPerPageString = request.getParameter("recsPerPage");
	int recsPerPage = recsPerPageString == null ? DBConnector.DEFAULTRECSPERPAGE : Integer.parseInt(recsPerPageString);
	
	/* get curPage */
	String curPageString = request.getParameter("curPage");
	int curPage = curPageString == null ? DBConnector.DEFAULTCURRENTPAGE : Integer.parseInt(curPageString);

	/* get total num of records */
	int totalRecordCnt = DBUtilRoute.getAllRoutesCount();
	
	/* check if exceeding total num pages */
	if( totalRecordCnt == 0 ) {
		curPage = 1;
	}
	else if((curPage-1) * recsPerPage >= totalRecordCnt) {
		curPage = (int)Math.ceil((double)totalRecordCnt/(double)recsPerPage);
	}

	StringBuffer sb = new StringBuffer();
	
	if( totalRecordCnt > 0 ) {
		List<Route> routes = DBUtilRoute.getAllRoutes(recsPerPage, curPage); 	
		Route route;
		Iterator<Route> iterator = routes.iterator();
		int cnt = (curPage-1)*recsPerPage + 1;
	
		while(iterator.hasNext()){
			route = iterator.next();
			
			sb.append("<div class=\"AllRouteResultItem\">\n").
			   append((cnt++)+ ". <a href=\"javascript:YAHOO.pennfitness.float.getRoute(" + route.getId() + ")\" class=\"ARRrouteName\">" + route.getName() + "</a> by <span class=\"ARRuserID\">" + route.getCreatorID() + "</span>\n</div>\n");
		}
	}
	
	JSONObject data = new JSONObject();
	data.put("ROUTES", sb.toString());
	data.put("CURPAGE", curPage);
	data.put("TOTALRECCNT", totalRecordCnt);
	
  	JSONObject result = new JSONObject();
  	// Can fail??
  	result.put("STATUS","Success");
  	result.put("MSG", "");
  	
  	result.put("DATA",data );
  	
	
	out.println(result);
%>