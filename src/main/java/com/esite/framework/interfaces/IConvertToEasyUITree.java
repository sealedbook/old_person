package com.esite.framework.interfaces;

public interface IConvertToEasyUITree<T> {
	
	public String getId();
	
	public String getText();
	
	public String getState();
	
	public boolean getChecked();
	
	public Iterable<T> getChildren();
	
}
