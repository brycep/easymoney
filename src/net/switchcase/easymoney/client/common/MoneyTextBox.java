package net.switchcase.easymoney.client.common;

import net.switchcase.easymoney.shared.MoneyTo;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.TextBox;

public class MoneyTextBox extends TextBox implements HasMoneyValue {
	
	private static final String DECIMAL_FORMAT = "0.00";
	
	public MoneyTextBox()  {
		this.addStyleName("money-textbox");
	}
	
	public void setValue(MoneyTo moneyTo)  {
		if (null == moneyTo)  {
			moneyTo = new MoneyTo();
		}
		NumberFormat nformat = NumberFormat.getFormat(DECIMAL_FORMAT);
		double value = (double) moneyTo.getDollars() + ((double)moneyTo.getCents()/ 100.00);
		this.setValue( nformat.format(value));
	}

    public MoneyTo getMoneyValue()  {
		return new MoneyTo(
				NumberFormat.getFormat(DECIMAL_FORMAT).parse(this.getValue())
		);
    }


}
