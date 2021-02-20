package com.graith.rpa.property;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.graith.rpa.DisplayableProperty;

public class LocalDateTimeProperty extends DisplayableProperty<LocalDateTime> {
	
	private String absPattern = "yyyy/MM/dd HH:mm:ss";
	
	public LocalDateTimeProperty(String label, LocalDateTime input) {
		super(label, input);
	}
	
	@Override
	public void setValue(String input) throws Exception {
		this.value = LocalDateTime.parse(input, DateTimeFormatter.ofPattern(absPattern));
	}
	
	@Override
	public String getStringValue() {
		return value.format(DateTimeFormatter.ofPattern(absPattern));
	}
	
}
