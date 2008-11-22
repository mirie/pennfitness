/* common functions */

/*
 * Parses the JSON-formatted server response and check whether it is successful
 * Returns null in case of exception
 * Returns the parsed string
 */
var parseByJSON = function(oResponse)
{
	var jResponse = null;
	
    // Use the JSON Utility to parse the data returned from the server
    try {
       jResponse = YAHOO.lang.JSON.parse(oResponse); 
    }
    catch (x) {
        alert("Server reponse invalid");
    }

	return jResponse;
}

/*
 * Check whether the server response is successful
 * If successful, returns true
 * If failed, alert MSG and returns false 
 */
var checkSuccess = function(jString)
{
	if( jString.STATUS != "Success" )
	{
		alert(jString.MSG);
		return false;
	}
	
	return true;
}

/*
 * Parses the JSON-formatted server response and check whether it is successful
 * If successful, returns the parsed string
 * If failed, alert MSG and returns null
 */
var parseNCheckByJSON = function(oResponse)
{
	if( (jResponse = parseByJSON(oResponse)) == null ) return null;
	
	if( checkSuccess(jResponse) ) return jResponse;
}










