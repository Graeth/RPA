package com.graith.rpa.graph;

import java.util.List;

public class Graph {
	List<ActionNode> nodes;
	ActionNode start;
	public Graph(List<ActionNode> nodes, ActionNode start) {
		this.nodes = nodes;
		assignDefaultNames();
		
		this.start = start;
	}
	public List<ActionNode> getNodes() {
		return nodes;
	}
	public ActionNode getStartNode() {
		return start;
	}
	
	public void assignDefaultNames() {
		int i = 0;
		for(ActionNode an : nodes) {
			if(an.getId().equals("")) {
				an.setId(String.valueOf(i++));
			}
		}
	}
	
	@Override
	public String toString() {
		String graphStr = "["+"Graph"+"]"+"\n";
		for(ActionNode n : nodes) {
			graphStr += n.toString() + "\n";
		}
		return graphStr;
	}
	
}
