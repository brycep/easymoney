package net.switchcase.easymoney.server.domain;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/** This class is used to store general statistics about our application
 * 
 * @author bryce
 *
 *
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */
public class Statistics {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;

	@Persistent long numberOfUsers = 0;
	@Persistent long numberOfDevices = 0;
	@Persistent long daysBeforeUserIsInactive = 365;
	@Persistent long activeUsers = 0;
	@Persistent long numberOfLogins = 0;

	public Statistics()  {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(long numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public long getNumberOfDevices() {
		return numberOfDevices;
	}

	public void setNumberOfDevices(long numberOfDevices) {
		this.numberOfDevices = numberOfDevices;
	}

	public long getDaysBeforeUserIsInactive() {
		return daysBeforeUserIsInactive;
	}

	public void setDaysBeforeUserIsInactive(long daysBeforeUserIsInactive) {
		this.daysBeforeUserIsInactive = daysBeforeUserIsInactive;
	}

	public long getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(long activeUsers) {
		this.activeUsers = activeUsers;
	}

	public long getNumberOfLogins() {
		return numberOfLogins;
	}

	public void setNumberOfLogins(long numberOfLogins) {
		this.numberOfLogins = numberOfLogins;
	}

	
}
