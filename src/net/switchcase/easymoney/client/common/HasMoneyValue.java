package net.switchcase.easymoney.client.common;

import net.switchcase.easymoney.shared.MoneyTo;

public interface HasMoneyValue {
	void setValue(MoneyTo moneyTo);
    MoneyTo getMoneyValue();
}
