package net.switchcase.easymoney.client.common;

import net.switchcase.easymoney.shared.MoneyTo;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.TextBox;

public class MoneyTextBox extends TextBox implements HasMoneyValue {
		
	public void setValue(MoneyTo moneyTo)  {
		NumberFormat nformat = NumberFormat.getCurrencyFormat();
		double value = (double) moneyTo.getDollars() + ((double)moneyTo.getCents()/ 100.00);
		this.setValue( nformat.format(value));
	}

    public MoneyTo getMoneyValue()  {
		return new MoneyTo(
				NumberFormat.getCurrencyFormat().parse(this.getValue())
		);
    }


}
