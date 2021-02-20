package com.graith.rpa.graph;


import com.graith.rpa.process.LockExecutorThreadSingleton;
import com.graith.rpa.exception.RouteTerminatedException;
import com.graith.rpa.exception.RoutingException;

public class GraphTraverserThread extends Thread {
	Graph g;
	ActionNode current;
	boolean halt = false;
	boolean hasLock = false;
	
	private LockExecutorThreadSingleton eThread;
	
	private String name = "";
	
	public GraphTraverserThread(Graph g) {
		this.g = g;
		this.current = g.getStartNode();
		eThread = LockExecutorThreadSingleton.getInstance();
	}
	
	@Override
	public void run() {
		System.out.println(halt ? name + " Halted, can't loop" : name + " beginning run");
		while(!halt) {
			try {
				Transition trans = current.getNextNodeForQueueing();
				if(trans != null) trans.getActionNode().printData();
				ActionNode nextNode = trans.getActionNode();
				boolean waitForLock = true;
				boolean interruptable = nextNode.isInterruptable();
				if(nextNode.isBenign()) {
					System.out.println(name + " benign");
					waitForLock = false;
				} else if(nextNode.isInterruptable()) {
					System.out.println(name + " Interruptable, has lock ? " + hasLock);
					if(hasLock) {
						eThread.unlock();
						hasLock = false;
					}
				} 
				if(waitForLock && !hasLock) {
					System.out.println(name + " Waiting for lock");
					while(!eThread.acquireLock());
					System.out.println(name + " Lock acquired");
					hasLock = true;
				}
				eThread.pushActionNode(nextNode);
				trans.queueEvent();
				current = trans.getActionNode();
				System.out.println(name + " enqueue");
				if(interruptable && hasLock) {
					eThread.unlock();
					System.out.println(name + " interruptable unlock");
				}
			} catch(RouteTerminatedException e) {
				halt = true; //Can no longer move
				System.out.println(name + " No moves, route terminated.");
				if(hasLock) {
					eThread.unlock();
					hasLock = false;
				}
			} catch(RoutingException e) {
				
			}
		}
	}
	
	public void halt() {
		halt = true;
	}
	
	@Override
	public String toString() {
		return "["+"GTT" +"] "+ name + " \n" + g.toString();
		
	}
	
	public void setGTTName(String name) {
		this.name = name;
	}
	
	public String getGTTName() {
		return name;
	}
	
	
	
}
