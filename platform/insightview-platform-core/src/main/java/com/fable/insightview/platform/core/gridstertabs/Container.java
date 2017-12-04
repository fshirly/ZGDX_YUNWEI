package com.fable.insightview.platform.core.gridstertabs;

import java.util.LinkedList;
import java.util.List;

public abstract class Container extends Component{

	protected List<Component> components = new LinkedList<Component>();
	
	public void addComponent(Component component){
		this.components.add(component);
		component.setParentContainer(this);
	}

	public StringBuilder draw() {
		StringBuilder sb = new StringBuilder();
		for(Component c : components){
			sb.append(c.draw());
		}
		return sb;
	}
}
