package com.assignment2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Action {
String userName;
Map<String, Integer> actions= new HashMap<String, Integer>();

public Action(String userName) {
	super();
	this.userName = userName;
}

public String print() {
	StringBuilder bld = new StringBuilder();
	bld=bld.append(userName);
	bld=bld.append("\n");
	System.out.println(userName);
	for(Entry e:actions.entrySet()) {
		bld= bld.append(e.getKey()+":"+e.getValue());
		bld=bld.append(",");
		System.out.println(e.getKey()+"->"+e.getValue());
	}
	if(bld.charAt(bld.length()-1)==',')
		bld.deleteCharAt(bld.length()-1);
	bld=bld.append("\n");
	return bld.toString();
}


public void setActionsMap(Map<String,Integer>map) {
	this.actions=map;
}
	
}
