package com.graith.rpa.command;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.graith.rpa.action.CompoundAction;
import com.graith.rpa.action.IAction;
import com.graith.rpa.action.MouseClick;
import com.graith.rpa.action.MouseMove;
import com.graith.rpa.criteria.CriterionActiveTime;
import com.graith.rpa.criteria.CriterionCount;
import com.graith.rpa.criteria.CriterionTimeInterval;
import com.graith.rpa.graph.ActionNode;

public class ActionLoader {
	public static final CompoundAction MOVE_MOUSE_COMMAND = new CompoundAction(Arrays.asList((IAction) new MouseMove(123, 123)));
	public static final CompoundAction DEFAULT_COMMAND1 = new CompoundAction(Arrays.asList((IAction) new MouseMove(234, 234)));
	
	public static final List<CompoundAction> DEFAULT_COMMAND_LIST = Arrays.asList(MOVE_MOUSE_COMMAND);
	public static final CompoundAction CLICK_COMMAND = new CompoundAction(Arrays.asList((IAction) new MouseClick(134, 234)));
	
	public static ActionNode genericClickNode() {
		ActionNode clickNode = (ActionNode.builder())
				.begin()
				.addAction(ActionLoader.CLICK_COMMAND)
				.noEdges()
				.setId("ClickNode")
				.complete();
		return clickNode;
	}
}
