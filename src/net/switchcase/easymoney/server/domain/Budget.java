/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import net.switchcase.easymoney.shared.EnvelopeType;

import com.google.appengine.api.users.User;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**  The Budget class represents a user's monthly budget
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:10:49 PM
 */
@SuppressWarnings("serial")
@PersistenceCapable
public class Budget implements Serializable {
	private static final long MILLISECS_PER_DAY = 1000 *  // millis / second
												  60 * // seconds / minute
												  60 * // minutes / hour
												  24; // hours / day
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	
	@Persistent private String name;
	
	@Persistent(defaultFetchGroup="true")
	private List<Income> incomes = new ArrayList<Income>();
	
	@Persistent(defaultFetchGroup="true") 
	private List<Bill> monthlyBills = new ArrayList<Bill>();

	@Persistent(defaultFetchGroup="true")
	private List<CashEnvelope> envelopes = new ArrayList<CashEnvelope>();

    @Persistent private User owner;
    @Persistent private String sharedWith;
    @Persistent private Date createDate;
    @Persistent private Date lastAccessed;

    public Budget() {}
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName()  {
    	return name;
    }
    
    public void setName(String name)  {
    	this.name = name;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public List<Bill> getMonthlyBills() {
        return monthlyBills;
    }

    public void setMonthlyBills(List<Bill> monthlyBills) {
        this.monthlyBills = monthlyBills;
    }

    
	public List<CashEnvelope> getEnvelopes() {
		return envelopes;
	}

	public void setEnvelopes(List<CashEnvelope> envelopes) {
		this.envelopes = envelopes;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(String sharedWith) {
		this.sharedWith = sharedWith;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccess) {
		this.lastAccessed = lastAccess;
	}

	public CashEnvelope getDefaultSavings() {
		return getUniqueEnvelope(EnvelopeType.DefaultSavings);
	}

	public void setDefaultSavings(CashEnvelope savingsBox) {
		setUniqueEnvelope(savingsBox, EnvelopeType.DefaultSavings);
	}

	public CashEnvelope getBillsEnvelope() {
		return getUniqueEnvelope(EnvelopeType.DefaultBills);
	}

	public void setBillsEnvelope(CashEnvelope billsBox) {
		setUniqueEnvelope(billsBox, EnvelopeType.DefaultBills);
	}
	
	public List<CashEnvelope> findExpenses()  {
		return getEnvelopes(EnvelopeType.Expense);
	}
	
	public void addExpense(CashEnvelope envelope)  {
		envelopes.add(envelope);
	}

	private void setUniqueEnvelope(CashEnvelope cashBox, EnvelopeType type)  {
		removeEnvelope(type);
		envelopes.add(cashBox);
	}
	
	private void removeEnvelope(EnvelopeType type)  {
		List<CashEnvelope> newBoxList = new ArrayList<CashEnvelope>();
		for(CashEnvelope box : envelopes)  {
			if (!box.getType().equals(type))  {
				newBoxList.add(box);
			}
		}
		
		envelopes = newBoxList;
	}
	
	private CashEnvelope getUniqueEnvelope(EnvelopeType type)  {
		for(CashEnvelope envelope : envelopes)  {
			if (envelope.getType().equals(type))  {
				return envelope;
			}
		}
		return createEnvelope(type);
	}
	
	private List<CashEnvelope> getEnvelopes(final EnvelopeType type)  {
		return Lists.newArrayList(Collections2.filter(envelopes, new Predicate<CashEnvelope>()  {

			public boolean apply(CashEnvelope envelope) {
				return envelope.getType().equals(type);
			}
			
		}) );
	}
	
	private CashEnvelope createEnvelope(EnvelopeType type)  {
		if (EnvelopeType.DefaultBills.equals(type))  {
			return new CashEnvelope("Bills", EnvelopeType.DefaultBills, 0L, 0L, this);
		} else if (EnvelopeType.DefaultSavings.equals(type))  {
			return new CashEnvelope("Savings", EnvelopeType.DefaultSavings, 0L, 0L, this);
		}
		
		return null;
	}

	public CashEnvelope getCashEnvelope(Long keyId, Logger logger)  {
		logger.info("Trying to find keyId: " + keyId);
		for(CashEnvelope envelope : envelopes)  {
			logger.info("Comparing id to envelope with id: " + envelope.getId() + " and keyId: " + envelope.getKeyId());
			if (keyId.equals(envelope.getKeyId()) )  {
				return envelope;
			}
		}
		return null;
	}
	
	public Transfer transfer(CashEnvelope sourceEnvelope, 
							 CashEnvelope destEnvelope, 
							 long amount,
							 String source,
							 User user) 
			throws InsufficientFundsException {
		
		Transfer transfer = new Transfer();
		transfer.setAmount(amount);
		transfer.setCreateTimestamp(new Date());
		transfer.setDescription("Transfer from " + sourceEnvelope.getName() + " to " + 
				destEnvelope.getName());
		transfer.setDestEnvelopeKey(destEnvelope.getId());
		transfer.setSourceEnvelopeKey(sourceEnvelope.getId());
		transfer.setTransferDate(new Date());
		transfer.setSource(source);

		sourceEnvelope.subtractBalance(amount);
		destEnvelope.addBalance(amount);
		return null;
	}

	public Income findIncome(String incomeId) {
		for(Income income : incomes)  {
			if (income.getId().equals(incomeId))  {
				return income;
			}
		}
		return null;
	}
	
	public CashEnvelope findEnvelope(Long keyId)  {
		for(CashEnvelope envelope : envelopes)  {
			if (envelope.getKeyId().equals(keyId))  {
				return envelope;
			}
		}
		return null;
	}
	
	public Bill findBill(String billId)  {
		for(Bill bill : monthlyBills)  {
			if (bill.getId().equals(billId))  {
				return bill;
			}
		}
		return null;
	}
	
	public void payday(Date now)  {

		Income income = getFirstIncome();
		if (income.isPaidToday(now))  {
			income.moveToNextPayDate();
			
			long distributedCash = 0;
			
			long daysUntilNextPay = calculateDaysUntilDate(now, income.getNextPayDate());
			
			distributedCash =+ processBills(daysUntilNextPay, now);
			
			for(CashEnvelope envelope : envelopes)  {
				if (EnvelopeType.Expense.equals(envelope.getType()))  {
					distributedCash += processExpense(envelope);
				}
			}
			
			long leftOver = income.getAmount() - distributedCash;
			this.getDefaultSavings().addBalance(leftOver);
		}	
	}
	
	private long processExpense(CashEnvelope expense)  {
		long newBalance = expense.getBalance() + expense.getAmount();
		expense.setBalance(newBalance);
		return expense.getAmount();
	}
	
	private long processBills(long daysUntilNextPay, Date now)  {
		long distributedCash = 0;
		CashEnvelope billsEnvelope = this.getBillsEnvelope();
		for(Bill bill : monthlyBills)  {
			long daysUntilDue = calculateDaysUntilDate(now, bill.getNextDueDate() );
			if (daysUntilDue < daysUntilNextPay)  {
				bill.setDue(true);
				billsEnvelope.addBalance(bill.getAmount());
				distributedCash += bill.getAmount();
			}
		}
		return distributedCash;
	}
	
	private long calculateDaysUntilDate(Date start, Date end)  {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		
		startCal.setTime(start);
		endCal.setTime(end);
		
		long endL = endCal.getTimeInMillis() + endCal.getTimeZone().getOffset( endCal.getTimeInMillis() ); 
		long startL = startCal.getTimeInMillis() + startCal.getTimeZone().getOffset( startCal.getTimeInMillis() );
		return (endL - startL) / MILLISECS_PER_DAY;
	}
	
	private Income getFirstIncome()  {
		return incomes.iterator().next();
	}
	    
}
