/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.view;

import net.switchcase.easymoney.shared.BudgetTo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:42:27 PM
 */
public class SummaryView extends Composite  {
	
	private VerticalPanel summaryPanel;
	
	public SummaryView()  {
		
		summaryPanel = new VerticalPanel();
		initWidget(summaryPanel);
		
	}
	
	public void setData(BudgetTo budget)  {
		
	}
}
