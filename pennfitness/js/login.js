// Login
// Inseob Lee
// version 11/12/2008


//  ***********************************************************************
//  Variables
//  ***********************************************************************
// YAHOO.namespace("login");

var userRegPanel;

YAHOO.util.Event.onDOMReady(setupUserReg);

function setupUserReg() {

	// Define various event handlers for Dialog
	var handleSubmit = function() {
		this.submit();
	};
	var handleCancel = function() {
		this.cancel();
	};
	var handleSuccess = function(o) {
		if( (jResponse = parseNCheckByJSON(o.responseText)) == null ) return false;

		alert("Successfully registered");
	}; 
	var handleFailure = function(o) { 
		alert("Submission failed: " + o.status); 
	}; 
	
	// Instantiate the Dialog
	userRegPanel = new YAHOO.widget.Dialog("userRegDialog", 
				{ width : "400px",
				  fixedcenter : true,
				  visible : false, 
				  modal : true,
				  constraintoviewport : true,
				  buttons : [ { text:"Register", handler:handleSubmit, isDefault:true },
							  { text:"Cancel", handler:handleCancel } ]
				 } );	

	// Validate the entries in the form to require that both first and last name are entered 

	userRegPanel.validate = function() { 

		var data = this.getData(); 

		if (data.userID == "") { 
			alert("Please enter user ID."); 
			return false; 
		} 
		else if(data.userName == "") {
			alert("Please enter user name."); 
			return false; 
		}
		else if(data.password == "") {
			alert("Please enter user password."); 
			return false; 
		}
		else if(data.email == "") {
			alert("Please enter your email address."); 
			return false; 
		}

 		return true;
	}; 

	// Wire up the success and failure handlers 
	userRegPanel.callback = { success: handleSuccess, 
						     failure: handleFailure }; 


	userRegPanel.render("bd");

	YAHOO.util.Event.addListener("userRegDialogBtn", "click", userRegPanel.show, userRegPanel, true); 

}

function ShowMyAccountDialog() {
	YAHOO.example.container.InfoToSearch();
}



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
	
	//YAHOO.util.Dom.get("loginBtn").disabled = true;
		
	var successHandler = function(o) {
		if( (response = parseByJSON(o.responseText)) == null ) return;
	    
	    if (response.STATUS != 'Success') {
			alert(response.MSG);		
			YAHOO.util.Dom.get("loginBtn").disabled = false;
	    }
	    else {	    	
			YAHOO.util.Dom.get("user").innerHTML = response.DATA + 
   				"<a href=\"javascript:ShowMyAccountDialog()\"/>My Account</a> " +
   				"<a href=\"javascript:createRt()\"/>Create a New Route</a> " + 
				"<a href=\"javascript:Logout()\"/>Logout</a> ";
		}
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);
		//YAHOO.util.Dom.get("loginBtn").disabled = false;
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
		if( (response = parseNCheckByJSON(o.responseText)) == null ) return;

		makeUserLoginForm();
	}

	var failureHandler = function(o) {
		alert("Error + " + o.status + " : " + o.statusText);

		// assume that the user is logged out
		makeUserLoginForm();
	}

	var makeUserLoginForm = function() {
		YAHOO.util.Dom.get("user").innerHTML = 
		"username: <input type=\"text\" name=\"userID\" id=\"userID\" size=5 maxlength=\"10\" style=\"width:5em\"/> " +
		"password: <input type=\"password\" name=\"password\" id=\"password\" size=\"10\" maxlength=\"10\" style=\"width:5em\"/> " +
		"<a href=\"javascript:Login()\"/>Login</a> " +
		"<a href=\"javascript:ShowUserRegDialog()\"/>Sign Up!</a> ";
	}

	var callback = {
		failure:failureHandler,
		success:successHandler,
		timeout:3000,
	}

	var transaction = YAHOO.util.Connect.asyncRequest("POST", "loginUser.do", callback, "action=logout");

}

// Displays the user registration dialog
function ShowUserRegDialog() {

	userRegPanel.show();


}

