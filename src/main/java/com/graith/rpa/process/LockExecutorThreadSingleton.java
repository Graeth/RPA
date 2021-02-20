package com.graith.rpa.process;

import java.util.ArrayList;
import java.util.List;

import com.graith.rpa.graph.ActionNode;

public class LockExecutorThreadSingleton extends Thread {
	
	private static LockExecutorThreadSingleton eSingleton;
	
	private List<ActionNode> toExecute;
	private int commandCount = 0;
	
	private boolean halt = false; 
	private boolean halted = true;
	
	private boolean locked = false;

	
	public synchronized static LockExecutorThreadSingleton getInstance() {
		if(eSingleton == null) {
			eSingleton = new LockExecutorThreadSingleton();
		}
		return eSingleton;
	}
	
	private LockExecutorThreadSingleton() {
		toExecute = new ArrayList<ActionNode>();
	}
	
	public void halt() {
		halt = true;
	}
	
	@Override
	public void run() {
		halted = false;
		while(!halt) {
			runNext();
		}
		halted = true;
	}
	public synchronized boolean acquireLock() {
		if(!locked) {
			locked = true;
			return true;						
		}
		return false;
	}
	public synchronized void unlock() {
		locked = false;
	}
	public boolean isHalted() {
		return halted;
	}
	
	private synchronized void runNext() {
    	//if(commandPointer != masterQueue.size()) {
    	//	System.out.println(commandPointer + " " + masterQueue.size());
    	//}
    	if(toExecute.size() > 0) {
    		//System.out.println(masterQueue.size() + " " + commandPointer);
    		ActionNode currentActionNode = toExecute.get(0);
    		System.out.println("Executing Command #" + commandCount++ + " AN : " + currentActionNode.getId());
    		//currentActionNode.printData();
    		boolean success = currentActionNode.execute(new DataModel());
    		if(success) {
    			toExecute.remove(currentActionNode);
    			
    		} else {
    			System.out.println("Failed to execute command.");
    			System.exit(0);
    		}
    	} 
    }
	
	public synchronized void pushActionNode(ActionNode an) {
		toExecute.add(an);
	}
	
}
