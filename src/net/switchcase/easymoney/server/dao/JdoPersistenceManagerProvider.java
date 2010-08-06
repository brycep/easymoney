package net.switchcase.easymoney.server.dao;

import javax.jdo.PersistenceManager;

public class JdoPersistenceManagerProvider implements PersistenceManagerProvider {

	public PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
