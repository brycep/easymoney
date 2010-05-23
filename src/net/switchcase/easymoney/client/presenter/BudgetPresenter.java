/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.client.presenter;

import net.switchcase.easymoney.client.EasyMoneyServiceAsync;
import net.switchcase.easymoney.client.common.HasMoneyValue;
import net.switchcase.easymoney.client.common.Row;
import net.switchcase.easymoney.client.event.HasRowValueChangeHandler;
import net.switchcase.easymoney.client.event.RowValueChangeHandler;
import net.switchcase.easymoney.shared.BillTo;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.ExpenseCategoryTo;
import net.switchcase.easymoney.shared.IncomeTo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: bryce
 * Date: May 8, 2010
 * Time: 3:25:53 PM
 */
public class BudgetPresenter implements Presenter {

    private final EasyMoneyServiceAsync easyMoneyService;
//    private final HandlerManager eventBus;
    private final Display display;

    private BudgetTo budget;
    
    public interface Display {

        String getBudgetName(); 
        HasClickHandlers getSaveButton();

        SummaryDisplay getSummaryView();
        IncomeDisplay getIncomeView();
        BillsDisplay getBillsView();
        ExpenseDisplay getExpenseCategoriesView();

        void setData(BudgetTo budget);
        void addExpenseCategory(ExpenseCategoryTo expenseCategory);
        void addIncome(IncomeTo income);
        void addBill(BillTo bill);
        
        void disableSaveButton();
        void enableSaveButton();

        Widget asWidget();
    }
    
    public interface SummaryDisplay {
    	HasText getOwner();
    	HasValue<String> getSharedWith();
    	HasMoneyValue getIncomeTotal();
    	HasMoneyValue getBillTotal();
    	HasMoneyValue getExpenseTotal();
    	HasMoneyValue getLeftOver();
    }

    public interface IncomeDisplay  {
    	HasRowValueChangeHandler getIncomeTable();
    	HasClickHandlers getAddIncomeButton();
    	HasMoneyValue getTotalLabel();
    	void updateModel();
    }
    
    public interface BillsDisplay  {
    	HasRowValueChangeHandler getBillsTable();
    	HasClickHandlers getAddBillButton();
    	HasMoneyValue getTotalLabel();
    	void updateModel();
    }
    
    public interface ExpenseDisplay  {
    	HasRowValueChangeHandler getExpenseTable(); 
    	HasMoneyValue getTotalExpenseLabel();
    	HasClickHandlers getAddExpenseCategoryButton();
    	void updateModel();
    }
    
    public BudgetPresenter(EasyMoneyServiceAsync easyMoneyService,
                           HandlerManager eventBus,
                           Display display) {
        this.easyMoneyService = easyMoneyService;
//        this.eventBus = eventBus;
        this.display = display;
    }
    
    public Display getDisplay()  {
    	return display;
    }

    public void bind()  {
    	
    	display.getSaveButton().addClickHandler(
				new ClickHandler()  {
					public void onClick(ClickEvent event) {
						saveBudget();
					}	
				}
  		);
    	
    	display.getExpenseCategoriesView().getExpenseTable().addRowValueChangeHandler(
    			new RowValueChangeHandler()  {
					public void onRowValueChanged(Row row) {
						row.updateModel();
						expenseAmountsChanged();
					}
    			}
    	);
    	
    	display.getIncomeView().getIncomeTable().addRowValueChangeHandler(
    			new RowValueChangeHandler()  {
    				public void onRowValueChanged(Row row)  {
    					row.updateModel();
    					incomeAmountsChanged();
    				}
    			}
    	);
    	
    	display.getBillsView().getBillsTable().addRowValueChangeHandler(
    			new RowValueChangeHandler()  {
    				public void onRowValueChanged(Row row)  {
    					row.updateModel();
    					billAmountsChanged();
    				}
    			}
    	);
    	
    	display.getIncomeView().getAddIncomeButton().addClickHandler(
    			new ClickHandler()  {
    				public void onClick(ClickEvent event)  {
    					IncomeTo income = new IncomeTo();
    					BudgetPresenter.this.budget.getIncomes().add(income);
    					BudgetPresenter.this.display.addIncome(income);
    				}
    			}
    	);
    	
    	display.getBillsView().getAddBillButton().addClickHandler(
    			new ClickHandler()  {
    				public void onClick(ClickEvent event)  {
    					BillTo bill = new BillTo();
    					BudgetPresenter.this.budget.getMonthlyBills().add(bill);
    					BudgetPresenter.this.display.addBill(bill);
    				}
    			}
    	);
    	
    	display.getExpenseCategoriesView().getAddExpenseCategoryButton().addClickHandler(
    			new ClickHandler()  {
    				public void onClick(ClickEvent event) {
    					ExpenseCategoryTo category = new ExpenseCategoryTo();
    					BudgetPresenter.this.budget.getCategories().add(category);
    					BudgetPresenter.this.display.addExpenseCategory(category);
    				}
    			}
    	);
    	
    
    }

    public void go(HasWidgets container) {
    	bind();
        container.clear();
        container.add(display.asWidget());
        retrieveBudget();
    }

    public void incomeAmountsChanged()  {
    	display.getIncomeView().getTotalLabel().setValue(budget.calculateIncomeTotal());
    }

    public void billAmountsChanged()  {
    	display.getBillsView().getTotalLabel().setValue(budget.calculateBillTotal());
    }
    
    public void expenseAmountsChanged()  {
    	display.getExpenseCategoriesView().getTotalExpenseLabel().setValue(budget.calculateExpenseTotal());
    }
    
    public BudgetTo getBudget()  {
    	return budget;
    }
    
    public void setBudget(BudgetTo budgetTo)  {
    	this.budget = budgetTo;
    }
    
    public void updateModel()  {
    	budget.setSharedWith(display.getSummaryView().getSharedWith().getValue());
    	display.getIncomeView().updateModel();
    	display.getBillsView().updateModel();
    	display.getExpenseCategoriesView().updateModel();
    }
    
    public void saveBudget()  {
    	display.disableSaveButton();
    	this.updateModel();
    	
    	easyMoneyService.saveBudget(budget, new AsyncCallback<Void>()  {
    		public void onSuccess(Void result)  {
    			display.enableSaveButton();
    			
    		}
    		
    		public void onFailure(Throwable caught)  {
    			Window.alert("Could not save your budget");
    		}
    	});
    }
    
    private void retrieveBudget()  {
    	easyMoneyService.getActiveBudget(new AsyncCallback<BudgetTo>()  {

			public void onFailure(Throwable caught) {
				Window.alert("Could not retrieve your active budget.");
			}

			public void onSuccess(BudgetTo result) {
				BudgetPresenter.this.setBudget(result);
				display.setData(result);
			}
    		
    	});
    }
}
