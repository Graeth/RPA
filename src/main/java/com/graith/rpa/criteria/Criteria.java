package com.graith.rpa.criteria;

import java.util.List;

import com.graith.rpa.DisplayableProperty;

public class Criteria implements ICriterion {
	List<ICriterion> criteria;

	public Criteria(List<ICriterion> criteria) {
		this.criteria = criteria;
	}
	@Override
	public boolean isMet() {
		boolean met = true;
		for(ICriterion ic : criteria) {
			if(!ic.isMet()) {
				met = false;
			}
		}
		return met;
	}

	@Override
	public boolean isTerminated() {
		boolean term = false;
		for(ICriterion ic : criteria) {
			if(ic.isTerminated()) {
				term = true;
			}
		}
		return term;
	}

	@Override
	public void enqueued() {
		for(ICriterion ic : criteria) {
			ic.enqueued();
		}
		//System.out.println("Enqueue");
	}
	
	@Override
	public String getName() {
		String name = "Criteria (";
		for(int i = 0; i < criteria.size(); i++) {
			name += criteria.get(i).getName();
			if(i + 1 != criteria.size()) {
				name += ",";
			}
		}
		name += ")";
		return name;
	}
	@Override
	public List<DisplayableProperty> getFields() {
		// TODO Auto-generated method stub
		return null;
	}
}
