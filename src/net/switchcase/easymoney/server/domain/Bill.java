package net.switchcase.easymoney.server.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Bill {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	@Persistent private String name;
	@Persistent private boolean reminderActive;
	@Persistent private Integer dayOfMonth;
	@Persistent private Integer reminderDay;
	@Persistent private Integer amount;
	
	public Bill(){}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isReminderActive() {
		return reminderActive;
	}

	public void setReminderActive(boolean reminderActive) {
		this.reminderActive = reminderActive;
	}

	public Integer getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public Integer getReminderDay() {
		return reminderDay;
	}

	public void setReminderDay(Integer reminderDay) {
		this.reminderDay = reminderDay;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
