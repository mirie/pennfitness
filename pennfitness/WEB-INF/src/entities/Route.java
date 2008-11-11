package entities;

public class Route {

	private String id;
	private String name;
	private String color;
	private String routeInfo;
	
	public Route( String id, String name, String color, String routeInfo){
		this.name = name;
		this.color = color;
		this.routeInfo = routeInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRouteInfo() {
		return routeInfo;
	}

	public void setRouteInfo(String routeInfo) {
		this.routeInfo = routeInfo;
	}
	
}
