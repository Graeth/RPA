package com.graith.rpa.action;

import com.graith.rpa.Displayable;
import com.graith.rpa.process.DataModel;

public interface IAction extends Displayable {
	public boolean execute();
	public boolean generate(DataModel dm);
	public String getName();
	@Override
	public default String getRootName() {
		return getName();
	}
}
