
<%@ page import="java.util.List, java.text.*, java.util.Iterator, model.DBUtilRoute, entities.Route, entities.Paging, org.json.simple.JSONObject" %>

<% 
	// For paging
	Paging paging = new Paging(request, DBUtilRoute.getAllRoutesCount() ); 

	String content = "";
	
	if( paging.getTotalRecordCnt() > 0 ) {

		content = DBUtilRoute.getAllRoutesHTML(paging.getRecsPerPage(), paging.getCurPage());
/*		
		List<Route> routes = DBUtilRoute.getAllRoutes(paging.getRecsPerPage(), paging.getCurPage()); 	
		Route route;
		Iterator<Route> iterator = routes.iterator();
		int cnt = (paging.getCurPage()-1)*paging.getRecsPerPage() + 1;
	
		while(iterator.hasNext()){
			route = iterator.next();
			
			sb.append("<div class=\"AllRouteResultItem\">\n").
			   append((cnt++)+ ". <a href=\"javascript:YAHOO.pennfitness.float.getRoute(" + route.getId() + ", false)\" class=\"ARRrouteName\">" + route.getName() + "</a> by <span class=\"ARRuserID\">" + route.getCreatorID() + "</span>\n</div>\n");
		}
*/		
	}
	
	JSONObject data = new JSONObject();
	data.put("CONTENT", content);
	data.put("CURPAGE", paging.getCurPage());
	data.put("TOTALRECCNT", paging.getTotalRecordCnt());
	
  	JSONObject result = new JSONObject();
  	// Can fail??
  	result.put("STATUS","Success");
  	result.put("MSG", "");
  	
  	result.put("DATA",data );
  	
	
	out.println(result);
%>