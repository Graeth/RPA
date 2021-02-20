package com.graith.rpa;

import java.util.List;

public interface Displayable {
	public List<DisplayableProperty> getFields();
	public String getRootName();
}
