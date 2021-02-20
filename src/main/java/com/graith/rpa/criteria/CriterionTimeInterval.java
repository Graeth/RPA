package com.graith.rpa.criteria;

import java.util.Date;
import java.util.List;

import com.graith.rpa.DisplayableProperty;
import com.graith.rpa.action.SVUHandler;
import com.graith.rpa.property.LongProperty;

public class CriterionTimeInterval implements ICriterion {

	private long timeStamp;
	private boolean init = true;
	
	private DisplayableProperty<Long> timeInterval;
	
	private static final String 
		TIME_INTERVAL = "interval";
	
	public CriterionTimeInterval(long timeInterval) {
		this.timeInterval = new LongProperty(TIME_INTERVAL, timeInterval);
	}
	@Override
	public boolean isMet() {
		// TODO Auto-generated method stub
		if(init) {
			return true;
		} else {
			return (new Date()).getTime() - timeStamp >= timeInterval.getValue();
		}
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void enqueued() {
		if(init) init = false;
		timeStamp = (new Date()).getTime();
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Time Interval " + timeInterval ; 
	}
	@Override
	public List<DisplayableProperty> getFields() {
		return SVUHandler.buildSVUList()
				.addProperty(timeInterval)
				.complete();
	}
	

}
