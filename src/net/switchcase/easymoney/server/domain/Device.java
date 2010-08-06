package net.switchcase.easymoney.server.domain;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable
public class Device {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;

	@Persistent private Date createDate = new Date();
	@Persistent private User user;
	@Persistent private String name;
	@Persistent private String iphonePushNotificationKey;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIphonePushNotificationKey() {
		return iphonePushNotificationKey;
	}

	public void setIphonePushNotificationKey(String iphonePushNotificationKey) {
		this.iphonePushNotificationKey = iphonePushNotificationKey;
	}

}
