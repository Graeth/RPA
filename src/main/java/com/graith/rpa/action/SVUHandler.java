package com.graith.rpa.action;

import java.util.ArrayList;
import java.util.List;

import com.graith.rpa.DisplayableProperty;

public class SVUHandler {
	
	public static SVUBuilder buildSVUList() {
		return new SVUBuilder();
	}
	
	
	public static class SVUBuilder {
		List<DisplayableProperty> svu;
		public SVUBuilder() {
			svu = new ArrayList<DisplayableProperty>();
		}
		
//		public SVUBuilder addInt(String s, int val) {
//			svu.add(new DisplayableProperty(s, String.valueOf(val)));
//			return this;
//		}
//		
//		public SVUBuilder addDouble(String s, double val) {
//			svu.add(new DisplayableProperty(s, String.valueOf(val)));
//			return this;
//		}
//		
//		public SVUBuilder addLong(String s, long val) {
//			svu.add(new DisplayableProperty(s, String.valueOf(val)));
//			return this;
//		}
//		
//		public SVUBuilder addBoolean(String s, boolean val) {
//			svu.add(new DisplayableProperty(s, String.valueOf(val)));
//			return this;
//		}
//		
//		public SVUBuilder addString(String s, String val) {
//			svu.add(new DisplayableProperty(s, val));
//			return this;
//		}
		
		public SVUBuilder addProperty(DisplayableProperty<?> dp) {
			svu.add(dp);
			return this;
		}
		
		public List<DisplayableProperty> complete() {
			return svu;
		}
	}
	
	
}
