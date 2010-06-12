package net.switchcase.easymoney.shared;

public enum AccountType {
	Savings(DebitCreditType.Credit),
	Expense(DebitCreditType.Credit),
	CheckingAccount(DebitCreditType.Debit);
		
	private DebitCreditType debitCredit;
	
	private AccountType(DebitCreditType dc)  {
		this.debitCredit = dc;
	}

	public DebitCreditType getDebitCredit()  {
		return debitCredit;
	}
}
