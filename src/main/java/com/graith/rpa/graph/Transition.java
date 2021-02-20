package com.graith.rpa.graph;

public class Transition {
	private ActionNode an;
	private ActionEdge ae;
	public Transition(ActionNode an, ActionEdge ae) {
		this.an = an;
		this.ae = ae;
	}
	public ActionNode getActionNode() {
		return an;
	}
	public ActionEdge getActionEdge() {
		return ae;
	}
	public void queueEvent() {
		ae.eventQueued();
	}
	
	
}
