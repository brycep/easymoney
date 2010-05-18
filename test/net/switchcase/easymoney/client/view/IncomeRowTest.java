package net.switchcase.easymoney.client.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.switchcase.easymoney.client.test.MockDateBox;
import net.switchcase.easymoney.client.test.MockListBox;
import net.switchcase.easymoney.client.test.MockMoneyTextBox;
import net.switchcase.easymoney.client.test.MockTextBox;
import net.switchcase.easymoney.shared.Frequency;
import net.switchcase.easymoney.shared.IncomeTo;
import net.switchcase.easymoney.shared.MoneyTo;

import org.junit.Test;

public class IncomeRowTest {
	

	@Test
	public void testCreateRow() throws Exception  {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date testDate = format.parse("01/01/2010");
		
		IncomeTo income = new IncomeTo();
		income.setAmount(new MoneyTo(10, 10));
		income.setFrequency(Frequency.BiWeekly);
		income.setName("Income Name");
		income.setNextPayDate(testDate);
		
		IncomeRow row = buildRow(income);
		
		
		assertTrue(new MoneyTo(10, 10).equals(row.getAmount().getMoneyValue()));
		assertEquals("BiWeekly", row.getFrequency().getSelected().getValue());
		assertEquals("Income Name", row.getName().getValue());
		assertEquals(testDate, row.getNextDate().getValue());
	}

	private IncomeRow buildRow(IncomeTo income) {
		IncomeRow row = new IncomeRow(0);
		row.setAmount(new MockMoneyTextBox());
		row.setFrequency(new MockListBox());
		row.setName(new MockTextBox());
		row.setNextDate(new MockDateBox());
		row.setData(income);
		return row;
	}
	
	@Test
	public void testUpdateIncomeModel() throws Exception  {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date testDate = format.parse("01/01/2010");
		
		IncomeTo income = new IncomeTo();
		IncomeRow row = buildRow(income);
		
		row.getAmount().setValue(new MoneyTo(11, 11));
		row.getFrequency().setSelected("Weekly");
		row.getName().setValue("Unit test");
		row.getNextDate().setValue(testDate);
		
		row.updateModel();
		
		assertTrue(new MoneyTo(11,11).equals(income.getAmount()));
		assertEquals(Frequency.Weekly, income.getFrequency());
		assertEquals("Unit test", income.getName());
		assertEquals(testDate, income.getNextPayDate());
	}

	
}
