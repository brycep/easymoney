package net.switchcase.easymoney.shared;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoneyTest {

	@Test
	public void testAddInteger()   {
		MoneyTo amount = new MoneyTo();
		amount.setDollars(2);
		amount.setCents(00);
		
		amount.add(2);
		
		assertEquals(4, (int)amount.getDollars());
		assertEquals(0, (int)amount.getCents());
	}

	@Test
	public void testAddIntegerHandlesNegativeValues()  {
		MoneyTo amount = new MoneyTo();
		amount.setDollars(-2);
		amount.setCents(30);
		
		amount.add(4);
		
		assertEquals(2, (int)amount.getDollars());
		assertEquals(30, (int)amount.getCents());
	}
	
	@Test
	public void testAddMoneyVo()  {
		MoneyTo amount = new MoneyTo();
		amount.setDollars(2);
		amount.setCents(30);
		
		amount.add(new MoneyTo(2, 0));
		
		assertEquals(4, (int)amount.getDollars());
		assertEquals(30, (int)amount.getCents());
		
		amount.add(new MoneyTo(3, 85));
		
		assertEquals(8, (int)amount.getDollars());
		assertEquals(15, (int)amount.getCents());
	}

	@Test 
	public void testSubtractMoney()  {
		MoneyTo amount = new MoneyTo(10, 50);
		
		amount.subtract(new MoneyTo(2, 25));
		
		assertEquals(8, (int)amount.getDollars());
		assertEquals(25, (int)amount.getCents());
		
		amount.subtract(new MoneyTo(3, 75));
		
		assertEquals(4, (int)amount.getDollars());
		assertEquals(50, (int)amount.getCents());
	}
	
	@Test
	public void testSubtractMoneyHandlesNegatives()  {
		MoneyTo amount = new MoneyTo(2, 50);
		
		amount.subtract(new MoneyTo(4, 25));
		
		assertEquals(-1, (int)amount.getDollars());
		assertEquals(75, (int)amount.getCents());
	}
	
}

