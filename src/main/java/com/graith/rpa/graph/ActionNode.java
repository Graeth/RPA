package com.graith.rpa.graph;

import java.util.ArrayList;
import java.util.List;

import com.graith.rpa.process.DataModel;
import com.graith.rpa.Displayable;
import com.graith.rpa.DisplayableProperty;
import com.graith.rpa.action.CompoundAction;
import com.graith.rpa.action.IAction;
import com.graith.rpa.criteria.Criteria;
import com.graith.rpa.criteria.ICriterion;
import com.graith.rpa.exception.RouteTerminatedException;
import com.graith.rpa.exception.RoutingException;

public class ActionNode implements Displayable {
	private List<ActionEdge> edges;
	private List<IAction> actions;
	
	private boolean isInterruptable = false;
	private boolean isBenign = false;
	
	String id = "";
	
	public static final boolean INTERRUPTABLE = true;
	public static final boolean NOT_INTERRUPTABLE = false;
	public static final boolean BENIGN = true;
	public static final boolean NOT_BENIGN = false;

	private ActionNode() {
		this((IAction) null, false, false);
	}
	
	private ActionNode(boolean isInterruptable, boolean isBenign) {
		this((IAction) null, isInterruptable, isBenign);
	}
	
	private ActionNode(IAction command) {
		this(command, false, false);
	}
	
	private ActionNode(IAction command, boolean isInterruptable) {
		this(command, isInterruptable, false);
	}
	
	private ActionNode(IAction command, boolean isInterruptable, boolean isBenign) {
		List<IAction> commands = new ArrayList<IAction>();
		commands.add(command);
		this.actions = commands;
		this.isInterruptable = isInterruptable;
		this.isBenign = isBenign; // will run regardless of lock
	}
	
	private ActionNode(List<IAction> commands, boolean isInterruptable, boolean isBenign) {
		edges = new ArrayList<ActionEdge>();
		this.actions = commands;
		this.isInterruptable = isInterruptable;
		this.isBenign = isBenign; // will run regardless of lock
	}
	
	public Transition getNextNodeForQueueing() throws RoutingException, RouteTerminatedException {
		boolean allTerm = true;
		if(edges == null) throw new RoutingException("This node has no edges.");
		for(ActionEdge m : edges) {
			if(m.isMet()) {
				return new Transition(m.getNodeMapping(), m);
			} else {
				if(!m.isTerminated()) {
					allTerm = false;
				}
			}
		}
		if(allTerm) {
			throw new RouteTerminatedException("Route terminated");
		}
		throw new RoutingException("No command criteria is met");
	}
	
	public boolean isInterruptable() {
		return isInterruptable;
	}
	
	public boolean isBenign() {
		return isBenign;
	}
	
	public IAction getCommand() {
		if(actions.size() > 1) {
			return new CompoundAction(actions);
		} else {
			if(actions.size() > 0) {
				return actions.get(0);
			}
		}
		return new CompoundAction(actions);
	}
	
	public List<ActionEdge> getEdges() {
		return edges;
	}
	
	public boolean execute(DataModel dm) {
		IAction c = new CompoundAction(actions);
		c.generate(dm);
		return c.execute();
	}
	
	public void printData() {
		IAction c = new CompoundAction(actions);
		System.out.println(c.getName());
	}

	public void setEdges(List<ActionEdge> edges) {
		this.edges = edges;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		String nodeStr = "["+"ActionNode"+"]" +" : " + id +"\n" + new CompoundAction(actions).toString();
		for(int i = 0; i < edges.size(); i++) {
			ActionEdge ae = edges.get(i);
			nodeStr += id + " -> " + ae.toString();
			//if(i+1 < edges.size()) {
				nodeStr += "\n";
			//}
		}
		return nodeStr;
	}
	
	public void addCommand(IAction command) {
		actions.add(command);
	}
	
	public void setActions(List<IAction> actionList) {
		actions = actionList;
	}

	public void setInterruptable(boolean isInterruptable) {
		this.isInterruptable = isInterruptable;
	}

	public void setBenign(boolean isBenign) {
		this.isBenign = isBenign;
	}
	
	public interface UninitializedNode {
		ActionNode getUnbuiltNode();
		AddActionStep begin();
	}
	
	public interface AddActionStep {
		AddEdgeStep addAction(IAction c);
	}
	
	public interface AddEdgeStep {
		AddConstraintStep startMakingEdgeTo(ActionNode n);
		CompleteStep noEdges();
	}
	
	public interface AddConstraintStep {
		CompleteEdgeStep addConstraint(ICriterion ic);
	}
	
	public interface CompleteEdgeStep {
		CompleteEdgeStep addConstraint(ICriterion ic);
		CompleteStep completeEdge();
	}
	
	public interface CompleteStep {
		AddConstraintStep startMakingEdgeTo(ActionNode n);
		CompleteStep setId(String id);
		CompleteStep setInterruptable(boolean yes);
		CompleteStep setBenign(boolean yes);
		ActionNode complete();
	}
	
	public static UninitializedNode builder() {
		return new ActionBuilder();
	}
	
	public static class ActionBuilder implements AddActionStep, AddEdgeStep, AddConstraintStep, CompleteEdgeStep, CompleteStep, UninitializedNode {

		ActionNode buildingNode;
		
		List<IAction> actions = new ArrayList<IAction>();
		List<ActionEdge> edge = new ArrayList<ActionEdge>();
		
		ActionNode activeNode;
		List<ICriterion> activeCriterion = new ArrayList<ICriterion>();
		
		String id = "";
		
		private boolean isInterruptable = false;
		private boolean isBenign = false;
		
		public ActionBuilder() {
			this.buildingNode = new ActionNode();
		}
		
		public ActionNode getUnbuiltNode() {
			return buildingNode;
		}
		
		@Override
		public AddActionStep begin() {
			return this;
		}
		
		@Override
		public AddEdgeStep addAction(IAction c) {
			actions.add(c);
			return this;
		}
		
		@Override
		public AddConstraintStep startMakingEdgeTo(ActionNode n) {
			this.activeNode = n;
			return this;
		}
		
		@Override
		public CompleteEdgeStep addConstraint(ICriterion ic) {
			activeCriterion.add(ic);
			return this;
		}
		
		@Override
		public CompleteStep completeEdge() {
			Criteria c = new Criteria(activeCriterion);
			ActionEdge e = new ActionEdge(activeNode, c);
			edge.add(e);
			return this;
		}

		@Override
		public CompleteStep setId(String id) {
			this.id = id;
			return this;
		}
		
		@Override
		public CompleteStep setInterruptable(boolean yes) {
			// TODO Auto-generated method stub
			this.isInterruptable = yes;
			return this;
		}

		@Override
		public CompleteStep setBenign(boolean yes) {
			this.isBenign = yes;
			return this;
		}
		
		@Override
		public CompleteStep noEdges() {
			return this;
		}

		@Override
		public ActionNode complete() {
			// TODO Auto-generated method stub
			return create(this);
		}
		
		public List<IAction> getActions() {
			return actions;
		}

		public List<ActionEdge> getEdges() {
			return edge;
		}

		public String getId() {
			return id;
		}

		public boolean isInterruptable() {
			return isInterruptable;
		}

		public boolean isBenign() {
			return isBenign;
		}

		
	}
	
	private static ActionNode create(ActionBuilder ab) {
		ActionNode n = ab.getUnbuiltNode();
		n.setActions(ab.getActions());
		n.setInterruptable(ab.isInterruptable());
		n.setBenign(ab.isBenign());
		n.setId(ab.getId());
		n.setEdges(ab.getEdges());
		return n;
	}

	@Override
	public List<DisplayableProperty> getFields() {
		return getCommand().getFields();
	}

	@Override
	public String getRootName() {
		return id;
	}
	
	
	
}
