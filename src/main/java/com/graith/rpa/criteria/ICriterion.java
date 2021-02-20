package com.graith.rpa.criteria;

import com.graith.rpa.Displayable;

public interface ICriterion extends Displayable {
	public abstract boolean isMet();
	public abstract boolean isTerminated();
	public abstract void enqueued();
	public abstract String getName();
	@Override
	public default String getRootName() {
		return getName();
	}
}
