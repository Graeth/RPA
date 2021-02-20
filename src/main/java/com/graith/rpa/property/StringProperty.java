package com.graith.rpa.property;

import com.graith.rpa.DisplayableProperty;

public class StringProperty extends DisplayableProperty<String> {

	public StringProperty(String label, String input) {
		super(label, input);
	}

	@Override
	public void setValue(String input) throws Exception {
		this.value = input;
	}

}
