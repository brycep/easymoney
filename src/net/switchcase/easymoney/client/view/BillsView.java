/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.shared.Budget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:42:51 PM
 */
public class BillsView extends Composite {
	
	private VerticalPanel billsPanel;
	
	public BillsView()  {
		billsPanel = new VerticalPanel();
		initWidget(billsPanel);
	}
	
	public void setData(Budget budget)  {
		
	}

}
