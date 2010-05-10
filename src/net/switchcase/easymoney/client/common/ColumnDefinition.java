package net.switchcase.easymoney.client.common;

public class ColumnDefinition {
	
	private String headerLabel;
	private String width;
	
	public ColumnDefinition(String headerLabel, String width)  {
		this.headerLabel = headerLabel;
		this.width = width;
	}

	public String getHeaderLabel() {
		return headerLabel;
	}

	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	
}
