package com.graith.rpa.action;


import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;

import com.graith.rpa.DisplayableProperty;
import com.graith.rpa.process.DataModel;
import com.graith.rpa.property.IntegerProperty;

public class MouseMove implements IAction {

	DisplayableProperty<Integer> x, y;
	
	private static final String
		X_LABEL = "x",
		Y_LABEL = "y";
	
	public MouseMove(int x, int y) {
		this.x = new IntegerProperty(X_LABEL, x);
		this.y = new IntegerProperty(Y_LABEL, y);
	}
	
	@Override
	public boolean execute() {
		try {
			Robot r = new Robot();
			r.mouseMove(x.getValue(), y.getValue());
		} catch (AWTException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean generate(DataModel dm) {
		
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MouseMove" + "(" + x + "," + y + ")";
	}

	@Override
	public List<DisplayableProperty> getFields() {
		return SVUHandler.buildSVUList()
				.addProperty(x)
				.addProperty(y)
				.complete();
	}

}
