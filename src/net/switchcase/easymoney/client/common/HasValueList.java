package net.switchcase.easymoney.client.common;

import java.util.List;

public interface HasValueList {
	
	void setList(List<ListItem> itemList);
	ListItem getSelected();
	void setSelected(String value);

}
