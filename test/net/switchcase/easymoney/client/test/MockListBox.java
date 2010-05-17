package net.switchcase.easymoney.client.test;

import java.util.ArrayList;
import java.util.List;

import net.switchcase.easymoney.client.common.HasValueList;
import net.switchcase.easymoney.client.common.ListItem;

public class MockListBox implements HasValueList {

	private List<ListItem> itemList = new ArrayList<ListItem>();
	private ListItem selectedItem;
	
	public void setSelected(String selected) {
		for(ListItem item : itemList)  {
			if (selected.equals(item.getValue()))  {
				selectedItem = item;
			}
		}
		
	}

	public ListItem getSelected() {
		return selectedItem;
	}

	public void setList(List<ListItem> itemList) {
		this.itemList = itemList;
	}

}
