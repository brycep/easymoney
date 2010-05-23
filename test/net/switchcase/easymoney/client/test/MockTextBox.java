package net.switchcase.easymoney.client.test;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class MockTextBox implements HasValue<String> {
	
	private String value;
	
	public MockTextBox() {}
	public MockTextBox(String value)  {
		this.value = value;
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return null;
	}

	public void fireEvent(GwtEvent<?> event) {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value, boolean fireEvents) {
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
