package com.graith.rpa.criteria;

import java.util.List;

import com.graith.rpa.DisplayableProperty;
import com.graith.rpa.action.SVUHandler;
import com.graith.rpa.property.IntegerProperty;

public class CriterionCount implements ICriterion {
	
	DisplayableProperty<Integer> maxCount, minCount, count;
	
	private static final String 
		MAX_COUNT = "maxCount",
		MIN_COUNT = "minCount",
		CUR_COUNT = "curCount";
	
	public CriterionCount(int maxCount) {
		this(0, maxCount);
	}
	public CriterionCount(int minCount, int maxCount) {
		this.maxCount = new IntegerProperty(MAX_COUNT, maxCount);
		this.minCount = new IntegerProperty(MIN_COUNT, minCount);
		this.count = new IntegerProperty(CUR_COUNT, 0);
	}
	@Override
	public boolean isMet() {
		// TODO Auto-generated method stub
		return count.getValue() < maxCount.getValue() && count.getValue() >= minCount.getValue();
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return count.getValue() >= maxCount.getValue();
	}

    @Override
	public void enqueued() {
    	//System.out.println("Count: " + count);
		try {
			count.setValue(Integer.valueOf(count.getValue() + 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    @Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Count " + minCount + "<i<" + maxCount ; 
	}
	@Override
	public List<DisplayableProperty> getFields() {
		return SVUHandler.buildSVUList()
				.addProperty(maxCount)
				.addProperty(minCount)
				.addProperty(count)
				.complete();
	}

}
