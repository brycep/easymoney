package net.switchcase.easymoney.client.common;

import java.util.List;

import com.google.gwt.user.client.ui.ListBox;

public class ValueListBox extends ListBox implements HasValueList {

	public void setList(List<ListItem> itemList) {
		for(ListItem item : itemList)  {
			this.addItem(item.getDescription(), item.getValue());
		}
	}

	public void setSelected(String value) {
		for(int index = 0; index < this.getItemCount(); index++)  {
			if (this.getValue(index).equals(value))  {
				this.setSelectedIndex(index);
			}
		}
	}

	public ListItem getSelected() {
		ListItem item = new ListItem();
		item.setValue(this.getValue(this.getSelectedIndex()) );
		item.setDescription(this.getItemText(this.getSelectedIndex()));
		return item;
	}

}
