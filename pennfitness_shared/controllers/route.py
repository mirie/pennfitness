import MySQLdb

username = "maiirie"
passwd = "mTmC439T"
db = "maiirie"
host = "fling-l.seas.upenn.edu"

#username = "appdev1"
#passwd = "appdev1"
#db = "appdev1"

# ahahahah

# try something like
def index(): return dict(message="hello from route.py")

def RouteList():
# returns (routeID, routeName);(routeID,routeName);...    get route id and names from route table.
    conn = MySQLdb.connect(host, username, passwd, db)
    cursor = conn.cursor()
    cursor.execute("SELECT routeID, name from route_temp");
    rows = cursor.fetchall()
    cursor.close()
    conn.close()
    return dict(rows=rows)

def getRouteData():
# input = routeID
# returns routeName;distance;(lat,lng);(lat,lang);... get route name, distance and points information of given route
    return "route data for a particular route"
    
def getFavoriteRouteList():
# input = userID
# returns  (routeID, routeName);(routeID,routeName);...    get favorite route id and names of given user
    return "favorite Route list"
    
def getPopularRouteList():
# returns (routeID, routeName);(routeID,routeName);...    get popular route id and names
    return "popular Route list"
    
def getNewRouteList():
# returns  (routeID, routeName);(routeID,routeName);...    get new route id and names
    return "new Route list"

def getTotalNumOfRoute():
# returns  numberOfRoutes  get the total number of routes registered
    return "total number of routes registered"
       
def registerRoute():
	# input = routeName,description, routeColor, points
	#conn = MySQLdb.connect(host, username, passwd, db)
    #cursor = conn.cursor()
    #cursor = conn.cursor()
    str = request.vars
	# returns resultCode  register a new route to route table
    return str # ************ no error handling yet!!!! *******************
    
def modifyRoute():
# input = routeID, routeName, description, routeColor, points 
# returns resultCode  modify route information. only the creator can modify his route information
    return "result code for modify route information"
    
def deleteRoute():
# input = routeID 
# returns resultCode  delete the given route
    return "result code for deleting a route"
    
def searchRoutes():
# input = userID, userName, keyword, fromDate, toDate, rate, fromDistance, toDistance 
# returns (routeID, routeName, distance, creatorID, description, rates);...   search routes on given criteria
    return "search results"
    
def rateRoute():
# input = routeID, rates
# return resultCode  rate a route
    return "result code for rating a route"