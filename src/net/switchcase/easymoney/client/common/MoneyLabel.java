package net.switchcase.easymoney.client.common;

import net.switchcase.easymoney.shared.MoneyTo;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Label;

public class MoneyLabel extends Label implements HasMoneyValue {
	
	private static final String DECIMAL_FORMAT = "0.00";
	
	public MoneyLabel()  {
		this.addStyleName("money-textbox");
	}
	
	public void setValue(MoneyTo moneyTo)  {
		if (null == moneyTo)  {
			return;
		}
		
		NumberFormat nformat = NumberFormat.getFormat(DECIMAL_FORMAT);
		double value = (double) moneyTo.getDollars() + ((double)moneyTo.getCents()/ 100.00);
		this.setText( nformat.format(value));
	}

    public MoneyTo getMoneyValue()  {
    	try  {
	    	return new MoneyTo(
					NumberFormat.getFormat(DECIMAL_FORMAT).parse(this.getText())
			);
    	} catch(Exception exp)  {
    		return new MoneyTo();
    	}
    }


}
