/*
 * Copyright (c) 2010. switch{case} LLC
 * The content within is considered confidential intellectual property
 * of switch{case} LLC.
 */

package net.switchcase.easymoney.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

/**  The Budget class represents a user's monthly budget
 * 
 * User: bryce
 * Date: May 4, 2010
 * Time: 7:10:49 PM
 */
@SuppressWarnings("serial")
@PersistenceCapable
public class Budget implements Serializable {
	
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
	private List<ExpenseCategory> categories = new ArrayList<ExpenseCategory>();
	
	@Persistent private Integer balance;
	
	@Persistent private Integer savings;
	
	@Persistent private Integer monthlySavings;

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

    public List<ExpenseCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ExpenseCategory> category) {
        this.categories = category;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getSavings() {
        return savings;
    }

    public void setSavings(Integer savings) {
        this.savings = savings;
    }

    public Integer getMonthlySavings() {
        return monthlySavings;
    }

    public void setMonthlySavings(Integer monthlySavings) {
        this.monthlySavings = monthlySavings;
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
	
//	public void copyValues(Budget source)  {
//		name = source.getName();
//		monthlySavings = source.getMonthlySavings();
//		savings = source.getSavings();
//		sharedWith = source.getSharedWith();
//		
//		copyIncomes(source.getIncomes());
//		copyBills(source.getMonthlyBills());
//		copyCategories(source.getCategories());
//	}
	
//	private void copyIncomes(List<Income> incomes)  {
//		for(Income source : incomes)  {
//			Income target = findIncome(source);
//			if (null == target)  {
//				incomes.add(source);
//			} else  {
//				target.copyValues(source);
//			}
//		}
//	}
//	
//	private void copyBills(List<Bill> bills)  {
//		
//	}
//	
//	private void copyCategories(List<ExpenseCategory> categories)  {
//		
//	}
//    
//	private Income findIncome(Income source)  {
//		for(Income item : incomes)  {
//			if ((item.getId() != null) && 
//				(item.getId().equals(source.getId())) )  {
//				return item;
//			}
//		}
//		return null;
//	}
    
}
