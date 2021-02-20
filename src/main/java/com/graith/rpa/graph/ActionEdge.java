package com.graith.rpa.graph;

import com.graith.rpa.criteria.Criteria;

public class ActionEdge {
	ActionNode router;
	Criteria criteria;
	public ActionEdge(ActionNode router, Criteria criteria) {
		this.router = router;
		this.criteria = criteria;
	}
	
	public boolean isMet() {
		return criteria.isMet();
	}
	
	public boolean isTerminated() {
		return criteria.isTerminated();
	}
	
	public ActionNode getNodeMapping() {
		return router;
	}
	
	public void eventQueued() {
		criteria.enqueued();
	}
	
	@Override
	public String toString() {
		String aeString = router.getId() + " : " + criteria.getName();
		return aeString;
	}
}
