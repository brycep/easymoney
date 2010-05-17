package net.switchcase.easymoney.client.test;

import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.shared.MoneyTo;

public class MockMoneyTextBox implements HasMoneyValue {

	private MoneyTo value;
	
	public MoneyTo getMoneyValue() {
		return value;
	}

	public void setValue(MoneyTo moneyTo) {
		this.value = moneyTo;
	}

}
