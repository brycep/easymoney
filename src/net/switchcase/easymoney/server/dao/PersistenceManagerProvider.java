package net.switchcase.easymoney.server.dao;

import javax.jdo.PersistenceManager;

public interface PersistenceManagerProvider {
	
	PersistenceManager getPersistenceManager();

}
