package net.switchcase.easymoney.server;

import java.util.Arrays;

import net.switchcase.easymoney.server.domain.Budget;
import net.switchcase.easymoney.server.domain.MoneyToCustomConverter;
import net.switchcase.easymoney.shared.BudgetTo;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

public class Converter {
	
	private static final DozerBeanMapper dozerMapper = new DozerBeanMapper();
	
	public Converter() {
		dozerMapper.setMappingFiles(Arrays.asList("budget-mappings.xml"));
		dozerMapper.setCustomConverters(Arrays.asList((CustomConverter)new MoneyToCustomConverter()));
	}
	
	public BudgetTo toTransferObject(Budget budget)  {
		return dozerMapper.map(budget, BudgetTo.class);
	}
	
	public Budget toDomainObject(BudgetTo budgetTo)  {
		return dozerMapper.map(budgetTo, Budget.class);
	}
	
	public void mergeTransferObjectIntoDomain(Budget budget, BudgetTo budgetTo)  {
		dozerMapper.map(budgetTo, budget);
	}

}
