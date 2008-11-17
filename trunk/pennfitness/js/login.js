// Login
// Inseob Lee
// version 11/12/2008


//  ***********************************************************************
//  Variables
//  ***********************************************************************
// YAHOO.namespace("login");


function Login() {
	if (YAHOO.util.Dom.get("userID").value == "") {
		alert("Please input user ID!");
		YAHOO.util.Dom.get("userID").focus();
		return;
	}

	if (YAHOO.util.Dom.get("password").value == "") {
		alert("Please input user password!");
		YAHOO.util.Dom.get("password").focus();
		return;
	}
	
	YAHOO.util.Dom.get("loginBtn").disabled = true;
		
	var successHandler = function(o) {
		if (o.responseText != -1) { // welcome message passed back
			YAHOO.util.Dom.get("user").innerHTML = o.responseText + 
				"<input type=\"button\" name=\"logout\" id=\"logoutBtn\" value=\"Logout\" onclick=\"Logout()\"/>";
		}
		else {
			alert("invalid user/password!");
			YAHOO.util.Dom.get("loginBtn").disabled = false;
		}
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
		YAHOO.util.Dom.get("loginBtn").disabled = false;
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}

	var strData = "userID=" + YAHOO.util.Dom.get("userID").value +"&password=" + YAHOO.util.Dom.get("password").value;

	var transaction = YAHOO.util.Connect.asyncRequest("POST", "loginUser.do", callback, strData);
}

function Logout() {

	var successHandler = function(o) {
		if (o.responseText != 1) {
			//TODO: should do something here? 
		}
		makeUserLoginForm();
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);

		// assume that the user is logged out
		makeUserLoginForm();
	}

	var makeUserLoginForm = function() {
		YAHOO.util.Dom.get("user").innerHTML = 
		"ID: <input type=\"text\" name=\"userID\" id=\"userID\" size=5 maxlength=\"10\" style=\"width:5em\"/>" +
		"PASS: <input type=\"password\" name=\"password\" id=\"password\" size=\"10\" maxlength=\"10\" style=\"width:5em\"/>" +
		"<input type=\"button\" name=\"login\" id=\"loginBtn\" value=\"Login\" onclick=\"Login()\"/>";
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}

	var transaction = YAHOO.util.Connect.asyncRequest("POST", "loginUser.do", callback, "action=logout");

}

