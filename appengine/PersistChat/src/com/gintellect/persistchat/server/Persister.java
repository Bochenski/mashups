package com.gintellect.persistchat.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public final class Persister {
	
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	private Persister(){}
	
	public static PersistenceManagerFactory get()
	{
		return pmfInstance;
	}
	
	public static PersistenceManager getPersistenceManager(){
		return get().getPersistenceManager();
	}
}
