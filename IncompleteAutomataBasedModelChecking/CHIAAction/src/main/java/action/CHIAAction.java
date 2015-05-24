package action;

import com.google.common.base.Preconditions;

public abstract class CHIAAction {
	
	private boolean performed;
	private final String name;
	
	public CHIAAction(String name){
		Preconditions.checkNotNull(name, "The name of the action cannot be null");
		performed=false;
		this.name=name;
	}
	
	public boolean isPerformed(){
		return performed;
	}

	
	public void performed(){
		this.performed=true;
	}
	
	public String getName(){
		return this.name;
	}
}
