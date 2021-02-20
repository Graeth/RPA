package com.graith.rpa.property;

import com.graith.rpa.DisplayableProperty;

public class LongProperty extends DisplayableProperty<Long> {

	public LongProperty(String label, Long input) {
		super(label, input);
	}

	@Override
	public void setValue(String input) throws Exception {
		this.value = Long.parseLong(input);
	}

}
