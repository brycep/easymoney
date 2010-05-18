package net.switchcase.easymoney.client.common;

public interface Row {
	
	int getRowIndex();
	void setData(Object dataObject);
	Object getData();
	void updateModel();
}
