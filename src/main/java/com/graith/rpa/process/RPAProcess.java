package com.graith.rpa.process;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.graith.rpa.action.CompoundAction;
import com.graith.rpa.command.ActionLoader;
import com.graith.rpa.criteria.Criteria;
import com.graith.rpa.criteria.CriterionActiveTime;
import com.graith.rpa.criteria.CriterionCount;
import com.graith.rpa.criteria.CriterionTimeInterval;
import com.graith.rpa.criteria.ICriterion;
import com.graith.rpa.graph.ActionEdge;
import com.graith.rpa.graph.ActionNode;
import com.graith.rpa.graph.ActionNode.ActionBuilder;
import com.graith.rpa.graph.ActionNode.*;
import com.graith.rpa.graph.GraphTraverserThread;

public class RPAProcess {
	
	LockExecutorThreadSingleton est;
	RPAApplicationBuilder pb;
	List<GraphTraverserThread> gtt;
	private boolean terminate = false;
	
	public RPAProcess() {
		est = LockExecutorThreadSingleton.getInstance();
		this.pb = new RPAApplicationBuilder();
		buildTest();
		start();
		run();
	}
	
	public void buildTest() {
		//ActionNode moveNode = new ActionNode(ActionNode.NOT_INTERRUPTABLE, ActionNode.NOT_BENIGN);
		//ActionNode clickNode = new ActionNode(ActionNode.INTERRUPTABLE, ActionNode.BENIGN);
		
		UninitializedNode moveNodeBuilder = ActionNode.builder();
		UninitializedNode clickNodeBuilder = ActionNode.builder();
		
		ActionNode clickNode = clickNodeBuilder
				.begin()
				.addAction(ActionLoader.CLICK_COMMAND)
				.startMakingEdgeTo(moveNodeBuilder.getUnbuiltNode())
				.addConstraint(new CriterionCount(15))
				.addConstraint(new CriterionTimeInterval(1400))
				.addConstraint(new CriterionActiveTime(LocalDateTime.of(2021, 2, 15, 20, 33, 10), 
						LocalDateTime.of(2021, 2, 15, 20, 33, 15), true, ChronoUnit.SECONDS))
				.completeEdge()
				.setId("ClickNode")
				.complete();
		
		ActionNode moveNode = moveNodeBuilder.begin()
				.addAction(ActionLoader.MOVE_MOUSE_COMMAND)
				.startMakingEdgeTo(clickNode)
				.addConstraint(new CriterionCount(4))
				.completeEdge()
				.setId("MoveNode")
				.complete();
		
		
		List<ActionNode> anList = new ArrayList<ActionNode>();
		anList.add(clickNode);
		anList.add(moveNode);
		
		List<GraphTraverserThread> gtt = pb.build(anList);
		int i = 0;
		for(GraphTraverserThread gtrav : gtt) {
			gtrav.setGTTName(String.valueOf(i++));
			System.out.println(gtrav);
		}
		this.gtt = gtt;
		//System.out.println("Test built.");
	}
	
	public void start() {
		est.start();
		for(GraphTraverserThread gThread : gtt) {
			//System.out.println("gtt starting");
			gThread.start();
		}
		
		run();
	}
	
	public void run() {
            
		while(!terminate) {
			
		}
	}
	
	
}
