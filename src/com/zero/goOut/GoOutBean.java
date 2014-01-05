package com.zero.goOut;

import java.util.List;

public class GoOutBean {
	
	
	private String location;
	private List<String> listLine;
	private boolean direction;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public List<String> getListLine() {
		return listLine;
	}
	public void setListLine(List<String> listLine) {
		this.listLine = listLine;
	}
	public boolean isDirection() {
		return direction;
	}
	public void setDirection(boolean direction) {
		this.direction = direction;
	}
	
}
