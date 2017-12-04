package com.fable.insightview.monitor.alarmdispatcher.sendsingle;

import java.util.ArrayList;
import java.util.List;

import com.fable.insightview.monitor.entity.AlarmNode;

public abstract class Handler {
	protected List keys;

	public Handler() {
		keys = new ArrayList();
	}

	protected Handler successor;

	public List getKeys() {
		return keys;
	}

	public void setKeys(List keys) {
		this.keys = keys;
	}

	public abstract boolean handleRequest(AlarmNode a);

	public Handler getSuccessor() {
		return successor;
	}

	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}
}