package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>PennFitness 2nd example</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/accordion-menu-v2.css\" />\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("<!--\r\n");
      out.write(".Header {\r\n");
      out.write("\theight: 30px;\r\n");
      out.write("\tvertical-align: middle;\r\n");
      out.write("\tcursor: move;\r\n");
      out.write("\tfont-family:Arial, Helvetica, sans-serif;\r\n");
      out.write("\ttext-align: center;\t\r\n");
      out.write("\tline-height: 30px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".Toolbar {\r\n");
      out.write("\tbackground-color:#FFFFFF;\r\n");
      out.write("\tborder: 1px solid #000000;\r\n");
      out.write("\tfont-family: Verdana, Arial, Helvetica, sans-serif;\r\n");
      out.write("\tfont-size:12px;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#Routes {\r\n");
      out.write("\tposition:absolute;\r\n");
      out.write("\tleft:16px;\r\n");
      out.write("\ttop:49px;\r\n");
      out.write("\twidth:171px;\r\n");
      out.write("\tz-index:1;\r\n");
      out.write("}\r\n");
      out.write("#RHeader {\r\n");
      out.write("\tbackground-color:#00FF00;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("#NewRoute {\r\n");
      out.write("\tposition:absolute;\r\n");
      out.write("\tleft:659px;\r\n");
      out.write("\ttop:55px;\r\n");
      out.write("\twidth:174px;\r\n");
      out.write("\tz-index:2;\r\n");
      out.write("}\r\n");
      out.write("#NRHeader {\r\n");
      out.write("\tbackground-color: #00FFFF;\r\n");
      out.write("}\r\n");
      out.write("#NewRoute #PointsHeader {\r\n");
      out.write("\tbackground-color: #00FFFF;\r\n");
      out.write("}\r\n");
      out.write("#map {\r\n");
      out.write("\tposition:absolute;\r\n");
      out.write("\tleft:5%;\r\n");
      out.write("\ttop:5%;\r\n");
      out.write("\twidth:90%;\r\n");
      out.write("\theight:90%;\r\n");
      out.write("\tz-index:0;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("-->\r\n");
      out.write("</style>\r\n");
      out.write("<link href=\"http://yui.yahooapis.com/2.6.0/build/reset/reset-min.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/yahoo-dom-event/yahoo-dom-event.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/element/element-beta-min.js\"></script> \r\n");
      out.write("<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAa4hhebteU8gfF26s-F8yRxRi_j0U6kJrkFvY4-OX2XYmEAa76BSap98EWKTMZCV7YM0hBbWJRb-A5g\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("<!-- Dependencies --> \r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/utilities/utilities.js\" ></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/slider/slider-min.js\" ></script>\r\n");
      out.write("\r\n");
      out.write("<!-- Color Picker source files for CSS and JavaScript -->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://yui.yahooapis.com/2.6.0/build/colorpicker/assets/skins/sam/colorpicker.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/colorpicker/colorpicker-min.js\" ></script>\r\n");
      out.write("\r\n");
      out.write("<!-- Accordion libraries--> \r\n");
      out.write("<script type=\"text/javascript\" src=\"http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/yahoo_2.0.0-b2.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/event_2.0.0-b2.js\" ></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/dom_2.0.2-b3.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://us.js2.yimg.com/us.js.yimg.com/lib/common/utils/2/animation_2.0.0-b3.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/accordion-menu-v2.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- Drag and Drop source file --> \r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/yahoo-dom-event/yahoo-dom-event.js\" ></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/dragdrop/dragdrop-min.js\" ></script>\r\n");
      out.write("\r\n");
      out.write("<!-- Paginator-->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://yui.yahooapis.com/2.6.0/build/paginator/assets/skins/sam/paginator.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/element/element-min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"http://yui.yahooapis.com/2.6.0/build/paginator/paginator-min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("// Global variables\r\n");
      out.write("var map;\r\n");
      out.write("var poly;\r\n");
      out.write("var count = 0;\r\n");
      out.write("var points = new Array();\r\n");
      out.write("var markers = new Array();\r\n");
      out.write("\r\n");
      out.write("var markersMultiple = new Array();\r\n");
      out.write("var pointsMultiple = new Array();\r\n");
      out.write("\r\n");
      out.write("var lineColor = \"#0000af\";\r\n");
      out.write("var lineWeight = 3;\r\n");
      out.write("var lineOpacity = .8;\r\n");
      out.write("var fillOpacity = .2;\r\n");
      out.write("var distance;\r\n");
      out.write("\r\n");
      out.write("var registeredRoutes = new Array();\r\n");
      out.write("\r\n");
      out.write("var multiple = false;\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("// Our custom drag and drop implementation, extending YAHOO.util.DD\r\n");
      out.write("YAHOO.example.DDOnTop = function(id, sGroup, config) {\r\n");
      out.write("    YAHOO.example.DDOnTop.superclass.constructor.apply(this, arguments);\r\n");
      out.write("};\r\n");
      out.write("\r\n");
      out.write("YAHOO.extend(YAHOO.example.DDOnTop, YAHOO.util.DD, {\r\n");
      out.write("    startDrag: function(x, y) {\r\n");
      out.write("        YAHOO.log(this.id + \" startDrag\", \"info\", \"example\");\r\n");
      out.write("\r\n");
      out.write("        var style = this.getEl().style;\r\n");
      out.write("        // The z-index needs to be set very high so the element will indeed be on top\r\n");
      out.write("        style.zIndex = 999;\r\n");
      out.write("    }\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("// Google map related\r\n");
      out.write("\r\n");
      out.write("function init()\r\n");
      out.write("{\r\n");
      out.write("\r\n");
      out.write("\t// Load Google map\r\n");
      out.write("\tif (GBrowserIsCompatible()) {\r\n");
      out.write("\t\tmap = new GMap2(document.getElementById(\"map\"), {draggableCursor:\"auto\", draggingCursor:\"move\"});\r\n");
      out.write("\t\tmap.setCenter(new GLatLng(39.95005, -75.191674), 15);\r\n");
      out.write("\t\tmap.addControl(new GScaleControl());\r\n");
      out.write("\t\tmap.disableDoubleClickZoom();\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tGEvent.addListener(map, \"click\", leftClick);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar dd1 = new YAHOO.example.DDOnTop(\"Routes\");\r\n");
      out.write("\tdd1.setHandleElId(\"RHeader\");\r\n");
      out.write("\tvar dd2 = new YAHOO.example.DDOnTop(\"NewRoute\");\r\n");
      out.write("\tdd2.setHandleElId(\"NRHeader\");\r\n");
      out.write("\t\r\n");
      out.write("\tdistance = document.getElementById(\"DistanceDiv\");\r\n");
      out.write("\t\r\n");
      out.write("\tYAHOO.pf.route.getRouteNames();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function addMarker(point)\r\n");
      out.write("{\r\n");
      out.write("  // Make markers draggable\r\n");
      out.write("  var marker = new GMarker(point, {draggable:true, bouncy:false});\r\n");
      out.write("  map.addOverlay(marker);\r\n");
      out.write("  marker.content = count;\r\n");
      out.write("  markers.push(marker);\r\n");
      out.write("  marker.tooltip = \"Point \"+ count;\r\n");
      out.write("\r\n");
      out.write("  // Drag listener\r\n");
      out.write("  GEvent.addListener(marker, \"drag\", function() {\r\n");
      out.write("   drawOverlay();\r\n");
      out.write("  });\r\n");
      out.write("\r\n");
      out.write("  // Second click listener\r\n");
      out.write("  GEvent.addListener(marker, \"click\", function() {\r\n");
      out.write("\t  // Find out which marker to remove\r\n");
      out.write("\t  for(var n = 0; n < markers.length; n++) {\r\n");
      out.write("\t   if(markers[n] == marker) {\r\n");
      out.write("\t\tmap.removeOverlay(markers[n]);\r\n");
      out.write("\t\tbreak;\r\n");
      out.write("\t   }\r\n");
      out.write("\t  }\r\n");
      out.write("\t\r\n");
      out.write("\t  // Shorten array of markers and adjust counter\r\n");
      out.write("\t  markers.splice(n, 1);\r\n");
      out.write("\t  if(markers.length == 0) {\r\n");
      out.write("\t\tcount = 0;\r\n");
      out.write("\t  }\r\n");
      out.write("\t   else {\r\n");
      out.write("\t\tcount = markers[markers.length-1].content;\r\n");
      out.write("\t  }\r\n");
      out.write("      drawOverlay();\r\n");
      out.write("  }); // end of second click\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function addMarker2(lat, lng)\r\n");
      out.write("{\r\n");
      out.write("\taddMarker(new GLatLng(lat, lng));\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function leftClick(overlay, point) {\r\n");
      out.write("\r\n");
      out.write(" if(point) {\r\n");
      out.write("  count++;\r\n");
      out.write("\r\n");
      out.write("  addMarker(point);\r\n");
      out.write("\r\n");
      out.write(" drawOverlay();\r\n");
      out.write(" }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function drawOverlay(){\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write(" //if(poly) { map.removeOverlay(poly); }\r\n");
      out.write(" //points.length = 0;\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write(" var pointDiv = document.getElementById(\"PointsDiv\");\r\n");
      out.write(" pointDiv.innerHTML = \"\";\r\n");
      out.write(" var temp, strLatLng;\r\n");
      out.write("\r\n");
      out.write(" for(i = 0; i < markers.length; i++) {\r\n");
      out.write("    points.push(markers[i].getLatLng());\r\n");
      out.write("\ttemp = markers[i].getLatLng();\r\n");
      out.write("\tstrLatLng = \"(\" + parseInt(temp.lat()*100000)/100000 + \",\" + parseInt(temp.lng()*100000)/100000 + \")\";\r\n");
      out.write("\tpointDiv.innerHTML += (i+1) + \". \" + strLatLng + \"<br>\";\r\n");
      out.write("\t\r\n");
      out.write(" }\r\n");
      out.write("  poly = new GPolyline(points, lineColor, lineWeight, lineOpacity);\r\n");
      out.write("  map.addOverlay(poly);\r\n");
      out.write("\r\n");
      out.write("  var length = poly.getLength()/1000;\r\n");
      out.write("  var unit = \" km\";\r\n");
      out.write("\r\n");
      out.write("  distance.innerHTML = \"Distance : \" + length.toFixed(3) + unit;\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("YAHOO.namespace(\"pf.route\");\r\n");
      out.write("\r\n");
      out.write("YAHOO.pf.route.submitForm = function() {\r\n");
      out.write("\t\r\n");
      out.write("\tvar successHandler = function(o) {\r\n");
      out.write("\t\t// alert(o.responseText);\r\n");
      out.write("\t\tif(o.responseText == '1') {\r\n");
      out.write("\t\t\talert(\"Route saved successfully\");\r\n");
      out.write("\t\t\tYAHOO.pf.route.getRouteNames();\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\telse {\r\n");
      out.write("\t\t\talert(\"Route is not saved\");\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar failureHandler = function(o) {\r\n");
      out.write("\t\talert(\"Error + \" + o.status + \" : \" + o.statusText);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar callback = {\r\n");
      out.write("\t\tfailure:failureHandler,\r\n");
      out.write("\t\tsuccess:successHandler,\r\n");
      out.write("\t\ttimeout:3000\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar form = document.getElementById(\"frmNewRoute\");\r\n");
      out.write("\r\n");
      out.write("\t// clear previous pvalues\t\r\n");
      out.write("\twhile((pvalue = document.getElementById(\"pvalue\")) != null)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tform.removeChild(pvalue);\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tvar newPoint;\r\n");
      out.write("\tfor(i = 0 ; i < markers.length ;i++) {\r\n");
      out.write("\r\n");
      out.write("\t\tnewPoint = document.createElement(\"input\");\r\n");
      out.write("\t\tnewPoint.type = \"hidden\";\r\n");
      out.write("\t\tnewPoint.name = \"pvalue\";\r\n");
      out.write("\t\tnewPoint.id = \"pvalue\";\r\n");
      out.write("\t\tnewPoint.value = markers[i].getLatLng().lat() + \",\" + markers[i].getLatLng().lng();\r\n");
      out.write("\t\r\n");
      out.write("\t\tform.appendChild(newPoint);\t\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tYAHOO.util.Connect.setForm(form);\r\n");
      out.write("\t\r\n");
      out.write("\tvar transaction = YAHOO.util.Connect.asyncRequest(\"POST\", \"view/allRoutes.jsp\", callback);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("YAHOO.util.Event.addListener(\"Save\", \"click\", YAHOO.pf.route.submitForm);\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("YAHOO.pf.route.getRouteNames = function() {\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\tvar successHandler = function(o) {\r\n");
      out.write("\t\tstrs = o.responseText.split(\";\");\r\n");
      out.write("\t\trlist = document.getElementById(\"RoutesList\");\r\n");
      out.write("\t\trlist.innerHTML = \"\";\r\n");
      out.write("\t\tfor(i = 0 ; i < strs.length ; i++) {\r\n");
      out.write("\t\t\trlist.innerHTML += (i+1) + \". <input type=checkbox name=c\"+i+\" id=c\"+i+\"> <a href=\\\"javascript:YAHOO.pf.route.getRoute('\" + strs[i] + \"')\\\">\" + strs[i] + \"</a><br>\";\r\n");
      out.write("\t\t\tregisteredRoutes.push(strs[i]);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar failureHandler = function(o) {\r\n");
      out.write("\t\talert(\"Error + \" + o.status + \" : \" + o.statusText);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar callback = {\r\n");
      out.write("\t\tsuccess:successHandler,\r\n");
      out.write("\t\tfailure:failureHandler,\r\n");
      out.write("\t\ttimeout:3000\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar form = document.getElementById(\"frmRoutes\");\r\n");
      out.write("\r\n");
      out.write("\tvar transaction = YAHOO.util.Connect.asyncRequest(\"GET\", \"view/allRoutes.jsp\", callback);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("YAHOO.util.Event.addListener(\"Refresh\", \"click\", YAHOO.pf.route.getRouteNames);\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("YAHOO.pf.route.getMultipleRouteNames = function(){\r\n");
      out.write("\r\n");
      out.write("    var str = \"\";\r\n");
      out.write("\t\r\n");
      out.write("\tfor( i = 0 ; i < registeredRoutes.length; i++){\r\n");
      out.write("\t\tif( document.getElementById(\"c\"+i).checked == true ){ \r\n");
      out.write("\t\t\tYAHOO.pf.route.getRouteMultiple(registeredRoutes[i]);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\telse{\r\n");
      out.write("\t\t\tstr += \"0\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("YAHOO.util.Event.addListener(\"Multiple\", \"click\", YAHOO.pf.route.getMultipleRouteNames);\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function drawOverlayMultiple(){\r\n");
      out.write("\r\n");
      out.write(" var pointDiv = document.getElementById(\"PointsDiv\");\r\n");
      out.write(" pointDiv.innerHTML = \"\";\r\n");
      out.write(" var temp, strLatLng;\r\n");
      out.write("\r\n");
      out.write(" var tmpPoints = new Array();\r\n");
      out.write(" \r\n");
      out.write(" for(i = 0; i < markersMultiple.length; i++) {\r\n");
      out.write("\tfor(j=0; j < markersMultiple.length; j++){\r\n");
      out.write("\t\ttmpPoints.push(markers[i][j].getLatLng());\r\n");
      out.write("\t\ttemp = markers[i][j].getLatLng();\r\n");
      out.write("\t\tstrLatLng = \"(\" + parseInt(temp.lat()*100000)/100000 + \",\" + parseInt(temp.lng()*100000)/100000 + \")\";\r\n");
      out.write("\t\tpointDiv.innerHTML += (i+1) + \". \" + strLatLng + \"<br>\";\r\n");
      out.write("\t}\r\n");
      out.write("\tpointsMultiple.push(tmpPoints);\r\n");
      out.write("\ttmpPoints.length = 0;\r\n");
      out.write(" }\r\n");
      out.write(" \r\n");
      out.write(" for( k=0; k < pointsMultiple.length; k++ ){\r\n");
      out.write("  polyM = new GPolyline(pointsMultiple[k], lineColor, lineWeight, lineOpacity);\r\n");
      out.write("  map.addOverlay(polyM);\r\n");
      out.write(" }\r\n");
      out.write("  var length = poly.getLength()/1000;\r\n");
      out.write("  var unit = \" km\";\r\n");
      out.write("\r\n");
      out.write("  distance.innerHTML = \"Distance : \" + length.toFixed(3) + unit;\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("YAHOO.pf.route.getRouteMultiple = function(route) {\r\n");
      out.write("\tvar successHandler = function(o) {\r\n");
      out.write("// \t\talert(o.responseText);\r\n");
      out.write("\t\tstrs = o.responseText.split(\";\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\talert(strs);\r\n");
      out.write("\t\tdocument.getElementById(\"routeName\").value = strs[0];\r\n");
      out.write("\t\tdocument.getElementById(\"routeColor\").value = strs[1];\r\n");
      out.write("\t\t\r\n");
      out.write("\r\n");
      out.write("\t  var tmpArray = new Array();\r\n");
      out.write("\t  for(i=2;i<strs.length;i++) {\r\n");
      out.write("\t    if(strs[i] == \"\") continue;\r\n");
      out.write("\t    lat = strs[i].split(\",\")[0];\r\n");
      out.write("\t\tlng = strs[i].split(\",\")[1];\r\n");
      out.write("\t    addMarker(new GLatLng(lat, lng));\r\n");
      out.write(" \t  }\r\n");
      out.write("\r\n");
      out.write("\t  drawOverlay();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar failureHandler = function(o) {\r\n");
      out.write("\t\talert(\"Error + \" + o.status + \" : \" + o.statusText);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar callback = {\r\n");
      out.write("\t\tsuccess:successHandler,\r\n");
      out.write("\t\tfailure:failureHandler,\r\n");
      out.write("\t\ttimeout:3000\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar transaction = YAHOO.util.Connect.asyncRequest(\"GET\", \"view/routeByName.jsp?routeName=\" + route, callback);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("YAHOO.pf.route.getRoute = function(route) {\r\n");
      out.write("\tvar successHandler = function(o) {\r\n");
      out.write("// \t\talert(o.responseText);\r\n");
      out.write("\t\tstrs = o.responseText.split(\";\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\talert(strs);\r\n");
      out.write("\t\tdocument.getElementById(\"routeName\").value = strs[0];\r\n");
      out.write("\t\tdocument.getElementById(\"routeColor\").value = strs[1];\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t //clear markers\r\n");
      out.write("\t\tfor(var n = 0; n < markers.length; n++) {\r\n");
      out.write("\t\t\tmap.removeOverlay(markers[n]);\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tmarkers.length = 0;\r\n");
      out.write("\t  //add markers\r\n");
      out.write("\t  for(i=2;i<strs.length;i++) {\r\n");
      out.write("\t    if(strs[i] == \"\") continue;\r\n");
      out.write("\t    lat = strs[i].split(\",\")[0];\r\n");
      out.write("\t\tlng = strs[i].split(\",\")[1];\r\n");
      out.write("\t    addMarker2(lat, lng);\r\n");
      out.write(" \t  }\r\n");
      out.write("\r\n");
      out.write("\t  drawOverlay();\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar failureHandler = function(o) {\r\n");
      out.write("\t\talert(\"Error + \" + o.status + \" : \" + o.statusText);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar callback = {\r\n");
      out.write("\t\tsuccess:successHandler,\r\n");
      out.write("\t\tfailure:failureHandler,\r\n");
      out.write("\t\ttimeout:3000\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\tvar transaction = YAHOO.util.Connect.asyncRequest(\"GET\", \"view/routeByName.jsp?routeName=\" + route, callback);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("YAHOO.pf.route.clear = function(route) {\r\n");
      out.write("\r\n");
      out.write("\tmap.removeOverlay();\r\n");
      out.write("\r\n");
      out.write("      // clear markers\r\n");
      out.write("\t  for(var n = 0; n < markers.length; n++) {\r\n");
      out.write("\t   map.removeOverlay(markers[n]);\r\n");
      out.write("\t  }\r\n");
      out.write("\t  markers.length = 0;\r\n");
      out.write("\r\n");
      out.write("\tdocument.getElementById(\"routeName\").value = \"\";\r\n");
      out.write("\tdocument.getElementById(\"routeColor\").value = \"\";\r\n");
      out.write("\r\n");
      out.write("\tif(poly) { map.removeOverlay(poly); }\r\n");
      out.write("    points.length = 0;\r\n");
      out.write("\t\r\n");
      out.write("  drawOverlay();\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("YAHOO.util.Event.addListener(\"Clear\", \"click\", YAHOO.pf.route.clear);\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("var dd1 = new YAHOO.util.DD(\"my-dl\");\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body class=\"yui-skin-sam\" onLoad=\"init();\" >\r\n");
      out.write("<div class=\"Toolbar\" id=\"Routes\">\r\n");
      out.write("    <form name=\"frmRoutes\" method=\"post\" action=\"\">\r\n");
      out.write("  <div class=\"Header\" id=\"RHeader\">Registered Routes</div>\r\n");
      out.write("  <div id=\"RoutesList\">\r\n");
      out.write("  </div>\r\n");
      out.write("  <div align=\"center\">\r\n");
      out.write("    <p>\r\n");
      out.write("\t  <input name=\"Multiple\" type=\"button\" id=\"Multiple\" value=\"Get Multiple Routes\">\r\n");
      out.write("      <input name=\"Refresh\" type=\"button\" id=\"Refresh\" value=\"Refresh\">\r\n");
      out.write("    </p>\r\n");
      out.write("  </div>\r\n");
      out.write("  </form>\r\n");
      out.write("</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<dl class=\"accordion-menu\" id=\"my-dl\"> \r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<dt class=\"a-m-t\" id=\"my-dt-1\">New Routes </dt>\r\n");
      out.write("\t<dd class=\"a-m-d\">\r\n");
      out.write("\t<div class=\"bd\">\r\n");
      out.write("\t\t<div><b>1)</b> Election path</br></div>\r\n");
      out.write("\t\t<div><b>2)</b> Art museum</br></div>\r\n");
      out.write("\t\t<div><b>3)</b> Downtown3</br></div>\r\n");
      out.write("\t\t<div><b>4)</b> Fairmount</br></div>\r\n");
      out.write("\t\t<div><b>5)</b> Penn's Landing area</br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</dd>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<dt class=\"a-m-t\" id=\"my-dt-2\"> Favourite Routes </dt>\r\n");
      out.write("\t<dd class=\"a-m-d\">\r\n");
      out.write("\t<div class=\"bd\">\r\n");
      out.write("\t\t<div><b>1)</b> Around campus</br></div>\r\n");
      out.write("\t\t<div><b>2)</b> After midterm run</br></div>\r\n");
      out.write("\t\t<div><b>3)</b> Quakers jog</br></div>\r\n");
      out.write("\t\t<div><b>4)</b> Downtown walk</br></div>\r\n");
      out.write("\t\t<div><b>5)</b> Fairmount run</br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</dd>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<dt class=\"a-m-t\" id=\"my-dt-3\"> Upcoming Events</dt>\r\n");
      out.write("\t<dd class=\"a-m-d\">\r\n");
      out.write("\t<div class=\"bd\">\r\n");
      out.write("\t\t<div><b>1)</b> 9am this weekend</br></div>\r\n");
      out.write("\t\t<div><b>2)</b> Around Campus</br></div>\r\n");
      out.write("\t\t<div><b>3)</b> Jogging1</br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</dd>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<dt class=\"a-m-t\" id=\"my-dt-4\"> New Events </dt>\r\n");
      out.write("\t<dd class=\"a-m-d\">\r\n");
      out.write("\t<div class=\"bd\">\r\n");
      out.write("\t\t<div><b>1)</b> Dec Jogging</br></div>\r\n");
      out.write("\t\t<div><b>2)</b> Outdoor1</br></div>\r\n");
      out.write("\t\t<div><b>3)</b> West Philly</br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t\t<div></br></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</dd>\r\n");
      out.write("\t\r\n");
      out.write("</dl>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"Toolbar\" id=\"NewRoute\">\r\n");
      out.write("  <form name=\"frmNewRoute\" id=\"frmNewRoute\" method=\"post\" action=\"#\">\r\n");
      out.write("  <div class=\"Header\" id=\"NRHeader\">Routes</div>\r\n");
      out.write("  <div>\r\n");
      out.write("      <p>Name \r\n");
      out.write("        <input name=\"routeName\" type=\"text\" id=\"routeName\" size=\"10\" maxlength=\"30\">\r\n");
      out.write("        <br>\r\n");
      out.write("      Color # \r\n");
      out.write("      <input name=\"routeColor\" type=\"text\" id=\"routeColor\" size=\"6\" maxlength=\"6\">\r\n");
      out.write("  </p>\r\n");
      out.write("      <p><div id=\"DistanceDiv\">Distance : </div></p>\r\n");
      out.write("  </div>\r\n");
      out.write("  <div align=\"center\" id=\"PointsHeader\">Points</div>\r\n");
      out.write("  <div id=\"PointsDiv\"></div>\r\n");
      out.write("    <div>\r\n");
      out.write("      <p align=\"center\">\r\n");
      out.write("        <input name=\"Save\" type=\"button\" id=\"Save\" value=\"Save\">\r\n");
      out.write("      </p>\r\n");
      out.write("      <p align=\"center\">&nbsp;</p>\r\n");
      out.write("      <p align=\"center\">\r\n");
      out.write("        <input name=\"Clear\" type=\"button\" id=\"Clear\" value=\"Clear\">\r\n");
      out.write("      </p>\r\n");
      out.write("    </div>\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("<div id=\"map\">Map area</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
