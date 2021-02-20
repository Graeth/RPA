package com.graith.rpa.action;

import java.util.ArrayList;
import java.util.List;

import com.graith.rpa.DisplayableProperty;
import com.graith.rpa.process.DataModel;

public class CompoundAction implements IAction {
	
	private List<IAction> actionList;
	private int actionPointer = 0;
	private boolean inProcess = false;
	
	DataModel dmSaved;
	
	public CompoundAction(List<IAction> actionL) {
		
		this.actionList = actionL;
	}
	
	/***
	 * Executes items in the action list until failure or finish
	 * @param dm The data model the commands will use for processing
	 * @return whether execution was successful
	 */
	public boolean execute(DataModel dm) {
		inProcess = true;
		setActionPointer(0);
		while(actionPointer < actionList.size()) {
			boolean success = executeSingle(dm);
			if(!success) {
				inProcess = false;
				return false;
			}
		}
		inProcess = false;
		return true;
	}
	
	/***
	 * Executes the next action in the actionlist 
	 * @param dm dm The data model the commands will use for processing
	 * @return whether execution was successful
	 */
	public boolean executeSingle(DataModel dm) {
		if(actionPointer >= actionList.size()) return true;
		IAction current = actionList.get(actionPointer);
		boolean success = current.generate(dm);
		if(!success) {
			return false;
		} else {
			success = current.execute();
			if(!success) {
				return false;
			} else {
				actionPointer++;
				return true;
			}
		}
	}
	
	/***
	 * Prints the title of all actions in actionlist
	 */
	public void printData() {
		for(IAction i : actionList) {
			System.out.println(i.getName());
		}
	}
	
	/***
	 * 
	 * @return action list
	 */
	public List<IAction> getActions() {
		return actionList;
	}
	
	/***
	 * Restarts command and executes actionlist again
	 * @param dm the data model for actions
	 */
	public void restart(DataModel dm) {
		setActionPointer(0);
		execute(dm);
	}
	
	/***
	 * 
	 * @param val pointer value
	 */
	private void setActionPointer(int val) {
		actionPointer = val;
	}
	
	/***
	 * 
	 * @param lc List of commands
	 * @return A new command consisting of all action lists of the list of commands
	 */
	public static CompoundAction mergeCommands(List<CompoundAction> lc) {
		List<IAction> la = new ArrayList<IAction>();
		for(CompoundAction c : lc) {
			la.addAll(c.getActions());
		}
		CompoundAction newCommand = new CompoundAction(la);
		return newCommand;
	}
	
	public boolean inProcess() {
		return inProcess;
	}
	
	@Override
	public String toString() {
		String commandStr = "";
		for(int i = 0; i < actionList.size(); i++) {
			IAction action = actionList.get(i);
			commandStr += action.getName();
			//if(i+1 < actionList.size()) {
			commandStr += "\n";
			//}
		}
		return commandStr;
	}

	@Override
	public boolean execute() {
		execute(dmSaved);
		return true;
	}

	@Override
	public boolean generate(DataModel dm) {
		this.dmSaved = dm;
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return toString();
	}

	@Override
	public List<DisplayableProperty> getFields() {
		List<DisplayableProperty> svu = new ArrayList<DisplayableProperty>();
		for(IAction i : actionList) {
			svu.addAll(i.getFields());
		}
		return svu;
	}
}
