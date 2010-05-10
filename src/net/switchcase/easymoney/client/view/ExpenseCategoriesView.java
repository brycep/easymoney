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
 * Time: 3:43:05 PM
 */
public class ExpenseCategoriesView extends Composite {
	
	private VerticalPanel expensePanel;
	
	public ExpenseCategoriesView()  {
		expensePanel = new VerticalPanel();
		initWidget(expensePanel);
	}
	
	public void setData(Budget budget)  {
		
	}
}
