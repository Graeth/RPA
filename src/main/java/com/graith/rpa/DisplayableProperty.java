package com.graith.rpa;

public abstract class DisplayableProperty<T> {
	public T value;
	public String stringValue;
	public String label;
	
	public abstract void setValue(String input) throws Exception;
	public T getValue() {
		return value;
	}
	
	public void setValue(T val) throws Exception{
		this.value = val;
		this.stringValue = val.toString();
	}
	
	public DisplayableProperty(String label, T input) {
		this.value = input;
		this.label = label;
		this.stringValue = input.toString();
	}
	
	public String valueProperty() {
		return value.toString();
	}
	
	public String labelProperty() {
		return label;
	}
	
	public String getStringValue() {
		return value.toString();
	}
	
	public void setLabel(String s) {
		this.label = s;
	}
	
	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return getStringValue();
	}
	
}
