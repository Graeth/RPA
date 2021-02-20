package com.graith.rpa;
import java.util.List;

import javafx.scene.control.TreeItem;

public class DisplayableObject {
	private int x, y;
	
	private String field, value;
	
	Displayable displayable;
	
	List<TreeItem<DisplayableProperty>> properties;
	
	public DisplayableObject(Displayable displayable) {
		this.displayable = displayable;
		
	}
	
	
}
