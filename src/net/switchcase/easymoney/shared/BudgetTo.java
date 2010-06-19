/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  The Budget class represents a user's monthly budget
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:10:49 PM
 */
@SuppressWarnings("serial")
public class BudgetTo implements Serializable {
	
	private String id;
	private String name;
    private List<IncomeTo> incomes = new ArrayList<IncomeTo>();
    private List<BillTo> monthlyBills = new ArrayList<BillTo>();
    private List<CashEnvelopeTo> envelopes = new ArrayList<CashEnvelopeTo>();

    private String sharedWith;
    private String owner;

    public BudgetTo() {}
    
    public String getId()  {
    	return id;
    }
    
    public void setId(String id)  {
    	this.id = id;
    }
    
    public String getName()  {
    	return name;
    }
    
    public void setName(String name)  {
    	this.name = name;
    }

    public List<IncomeTo> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<IncomeTo> incomes) {
        this.incomes = incomes;
    }

    public List<BillTo> getMonthlyBills() {
        return monthlyBills;
    }

    public void setMonthlyBills(List<BillTo> monthlyBills) {
        this.monthlyBills = monthlyBills;
    }

    public List<CashEnvelopeTo> getExpenses() {
        return getEnvelopes(EnvelopeType.Expense);
    }
    
   	public List<CashEnvelopeTo> getEnvelopes() {
		return envelopes;
	}

	public void setEnvelopes(List<CashEnvelopeTo> envelopes) {
		this.envelopes = envelopes;
	}

	public String getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(String sharedWith) {
		this.sharedWith = sharedWith;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public CashEnvelopeTo getDefaultSavings() {
		return getUniqueEnvelope(EnvelopeType.Expense);
	}

	public MoneyTo calculateExpenseTotal() {
		MoneyTo money = new MoneyTo();
		if (null != getExpenses())  {
			for(CashEnvelopeTo envelope : getExpenses())  {
				money.add(envelope.getAmount());
			}
		}
		return money;
	}
	
	public MoneyTo calculateIncomeTotal()  {
		MoneyTo money = new MoneyTo();
		for(IncomeTo income : incomes)  {
			money.add(income.getAmount());
		}
		return money;
	}

	public MoneyTo calculateBillTotal()  {
		MoneyTo money = new MoneyTo();
		for(BillTo bill : monthlyBills)  {
			money.add(bill.getAmount());
		}
		return money;
	}
	
	public MoneyTo getLeftOverValue()  {
		return new MoneyTo();
	}

	public CashEnvelopeTo getBillsEnvelope() {
		return getUniqueEnvelope(EnvelopeType.DefaultBills);
	}

	private List<CashEnvelopeTo> getEnvelopes(EnvelopeType type)  {
		List<CashEnvelopeTo> results = new ArrayList<CashEnvelopeTo>();
		if (null != envelopes)  {
			for(CashEnvelopeTo envelope : envelopes)  {
				if (null != envelope.getType() )  {
					if (envelope.getType().equals(type))  {
						results.add(envelope);
					}
				}
			}
		}
		return results;
	}
	
	private CashEnvelopeTo getUniqueEnvelope(EnvelopeType type)  {
		List<CashEnvelopeTo> results = getEnvelopes(type);
		if (results.size() == 0) {
			return null;
		}
		return results.iterator().next();
	}
	
}
