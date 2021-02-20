package com.graith.rpa.process;

import java.util.ArrayList;
import java.util.List;

import com.graith.rpa.command.*;
import com.graith.rpa.graph.ActionEdge;
import com.graith.rpa.graph.ActionNode;
import com.graith.rpa.graph.Graph;
import com.graith.rpa.graph.GraphTraverserThread;
public class RPAApplicationBuilder {
	public RPAApplicationBuilder() {
		
	}
	
	public List<GraphTraverserThread> build(List<ActionNode> nodes) {
		List<GraphTraverserThread> travThreadList = new ArrayList<GraphTraverserThread>();
		List<Graph> graphs = generateGraphs(nodes);
		System.out.println("Graphs : " + graphs.size());
		for(Graph g : graphs) {
			GraphTraverserThread travThread = new GraphTraverserThread(g);
			travThreadList.add(travThread);
		}
		return travThreadList;
	}
	
	private List<Graph> generateGraphs(List<ActionNode> nodes) {
		/*if(nodes.size() != edgeLists.size()) {
			throw new IllegalArgumentException("Nodes and mappings list size unequal");
		}*/
		//List<ActionNode> nodesCopy = new ArrayList<>(nodes);
		List<Graph> graphs = new ArrayList<Graph>(); //to return
		
		// removes ActionNodes from nodes as it discovers loops
		int i = 0;
		while(nodes.size() > 0) { // while left to process
			ActionNode currentNode = nodes.get(0); //take first of next loop
			graphs.add(generateGraph(currentNode)); //process it to graph
			//System.out.println(graphs.get(i).getNodes());
			//System.out.println(nodes.get(i));
			nodes.removeAll(graphs.get(i).getNodes());
			i++;
			/*List<ActionNode> toProcess = new ArrayList<ActionNode>();
			toProcess.add(currentNode);
			for(int j = 0; j < toProcess.size(); j++) {
				for(ActionEdge ae : toProcess.get(j).getEdges()) {
					ActionNode an = ae.getNodeMapping();
					an.printData();
					if(nodes.contains(an)) {
						try { 
							System.out.println("Removing " + nodes.toString());
							nodes.remove(an);
						} catch(UnsupportedOperationException e) { //fixed-size list
							throw e; 
						}
					}
				}
			}
			toProcess.clear();*/
		}
		
		return graphs;
	}
	private Graph generateGraph(ActionNode start) {
		List<ActionNode> graph = new ArrayList<ActionNode>();
		graph.add(start);
		for(int i = 0; i < graph.size(); i++) {
			List<ActionEdge> aeList = graph.get(i).getEdges();
			if(aeList != null) {
				for(int j = 0; j < aeList.size(); j++) {
					//System.out.println(aeList.size() + " " + j);
					ActionEdge ae = aeList.get(j);
					ActionNode curActNode = ae.getNodeMapping();
					if(!graph.contains(curActNode)) {
						graph.add(curActNode);
					}
				}
			}
		}
		return new Graph(graph, start);
	}
}
