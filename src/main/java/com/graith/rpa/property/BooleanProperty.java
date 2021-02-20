package com.graith.rpa.property;

import com.graith.rpa.DisplayableProperty;

public class BooleanProperty extends DisplayableProperty<Boolean> {

	public BooleanProperty(String label, Boolean input) {
		super(label, input);
	}

	@Override
	public void setValue(String input) throws Exception {
		this.value = Boolean.parseBoolean(input);
	}

}
