package net.switchcase.easymoney.server;

import java.util.Arrays;

import net.switchcase.easymoney.server.domain.Bill;
import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.CashEnvelope;
import net.switchcase.easymoney.server.domain.Income;
import net.switchcase.easymoney.shared.BillTo;
import net.switchcase.easymoney.shared.BudgetTo;
import net.switchcase.easymoney.shared.CashEnvelopeTo;
import net.switchcase.easymoney.shared.IncomeTo;

import org.dozer.DozerBeanMapper;

public class Converter {
	
	private static final DozerBeanMapper dozerMapper = new DozerBeanMapper();
	
	public Converter() {
		dozerMapper.setMappingFiles(Arrays.asList("budget-mappings.xml"));
	}
	
	public BudgetTo toTransferObject(Budget budget)  {
		return dozerMapper.map(budget, BudgetTo.class);
	}
	
	public Budget toDomainObject(BudgetTo budgetTo)  {
		return dozerMapper.map(budgetTo, Budget.class);
	}
	
	public void mergeTransferObjectIntoDomain(Budget budget, BudgetTo budgetTo)  {
		budget.setName(budgetTo.getName());
		budget.setSharedWith(budgetTo.getSharedWith());
		transformIncomes(budget, budgetTo);
		transformBills(budget, budgetTo);
		transformEnvelopes(budget, budgetTo);
	}

	private void transformIncomes(Budget budget, BudgetTo budgetTo) {
		for(IncomeTo incomeTo : budgetTo.getIncomes())  {
			Income income;
			if (incomeTo.getId() != null)  {
				income = budget.findIncome(incomeTo.getId());
			} else  {
				income = new Income();
				budget.getIncomes().add(income);
			}
			dozerMapper.map(incomeTo, income);
		}
	}
	
	private void transformBills(Budget budget, BudgetTo budgetTo)  {
		for(BillTo billTo : budgetTo.getMonthlyBills())  {
			Bill bill;
			if (null != billTo.getId())  {
				bill = budget.findBill(billTo.getId());
			} else  {
				bill = new Bill();
				budget.getMonthlyBills().add(bill);
			}
			dozerMapper.map(billTo, bill);
		}
	}
	
	private void transformEnvelopes(Budget budget, BudgetTo budgetTo)  {
		for(CashEnvelopeTo envelopeTo : budgetTo.getEnvelopes())  {
			CashEnvelope envelope;
			if (null != envelopeTo.getId())  {
				envelope = budget.findEnvelope(envelopeTo.getId());
			} else  {
				envelope = new CashEnvelope();
				budget.getEnvelopes().add(envelope);
			}
			dozerMapper.map(envelopeTo, envelope);
		}
	}

}
