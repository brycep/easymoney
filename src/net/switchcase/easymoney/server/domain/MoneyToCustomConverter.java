package net.switchcase.easymoney.server.domain;

import net.switchcase.easymoney.shared.MoneyTo;

import org.dozer.CustomConverter;

public class MoneyToCustomConverter implements CustomConverter {
	
	public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
		if (source == null)  {
			return null;
		}
		
		if (source instanceof MoneyTo)  {
			return ((MoneyTo)source).toInt();
		} else if (source instanceof Integer)  {
			MoneyTo money = new MoneyTo();
			money.setValue((Integer)source);
			return money;
		}
		return null;
	}

}
