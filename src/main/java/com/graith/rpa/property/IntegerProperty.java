package com.graith.rpa.property;

import com.graith.rpa.DisplayableProperty;

public class IntegerProperty extends DisplayableProperty<Integer> {

	public IntegerProperty(String label, Integer input) {
		super(label, input);
	}

	@Override
	public void setValue(String input) throws Exception {
		try {
			this.value = Integer.parseInt(input);
		} catch(Exception e) {
			throw new Exception("Input is not an integer.");
		}
	}

}
