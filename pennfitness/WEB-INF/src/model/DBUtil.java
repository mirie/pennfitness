package model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBUtil {

	/**
	 * Some of the search criteria may be empty
	 * So only the ones that are filled are going to be included in 
	 * WHERE clause
	 * 
	 * @param params
	 * @return
	 */
	public static String getSearchCriteria( List<QueryParameter> params ){
		StringBuffer sbuf = new StringBuffer();
		
		 Iterator<QueryParameter> iterator = params.iterator();
		 
		 QueryParameter param;
		 String key, value;
		 while( iterator.hasNext() ){
			 param = iterator.next();
			 key   = param.getField();
			 value = param.getValue();
			 
			 if( !value.contains("'null'") && !value.contains("'%null%'") )
				 sbuf.append( key ).append( value ).append(" AND");
		 }
		 
		if( sbuf.length() == 0 )
			return "1=1";
		else{
			return sbuf.substring(0, sbuf.length()-3/*to remove last AND*/);
		}
	}
	
	public static void addQueryParam(  ){
		
	}
	
}


class QueryParameter{
	
	private String field;
	private String value;
	
	public QueryParameter( String key, String val){
		field = key;
		value = val;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}
}