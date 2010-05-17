package net.switchcase.easymoney.client.test;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class MockDateBox implements HasValue<Date> {
	private Date value;

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Date> handler) {
		return null;
	}

	public void fireEvent(GwtEvent<?> event) {
		
	}

	public void setValue(Date value, boolean fireEvents) {
		this.value = value;
	}
	
}
