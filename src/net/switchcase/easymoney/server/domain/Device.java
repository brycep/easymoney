package net.switchcase.easymoney.server.domain;

import java.util.Date;

import com.google.appengine.api.users.User;

public class Device {
	
	private String id;
	private Date createDate = new Date();
	private User user;
	
	public Device() {
	}
	
	public Device(User user)  {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public User getUser()  {
		return user;
	}
	
	public void setUser(User user)  {
		this.user = user;
	}

}
