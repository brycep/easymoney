package net.switchcase.easymoney.shared;

import junit.framework.TestCase;

import org.junit.Test;

public class MoneyTest extends TestCase {

	@Test
	public void testAddInteger()   {
		Money amount = new Money();
		amount.setDollars(2);
		amount.setCents(00);
		
		amount.add(2);
		
		assertEquals(4, (int)amount.getDollars());
		assertEquals(0, (int)amount.getCents());
	}

	@Test
	public void testAddIntegerHandlesNegativeValues()  {
		Money amount = new Money();
		amount.setDollars(-2);
		amount.setCents(30);
		
		amount.add(4);
		
		assertEquals(2, (int)amount.getDollars());
		assertEquals(30, (int)amount.getCents());
	}
	
	@Test
	public void testAddMoneyVo()  {
		Money amount = new Money();
		amount.setDollars(2);
		amount.setCents(30);
		
		amount.add(new Money(2, 0));
		
		assertEquals(4, (int)amount.getDollars());
		assertEquals(30, (int)amount.getCents());
		
		amount.add(new Money(3, 85));
		
		assertEquals(8, (int)amount.getDollars());
		assertEquals(15, (int)amount.getCents());
	}

	@Test 
	public void testSubtractMoney()  {
		Money amount = new Money(10, 50);
		
		amount.subtract(new Money(2, 25));
		
		assertEquals(8, (int)amount.getDollars());
		assertEquals(25, (int)amount.getCents());
		
		amount.subtract(new Money(3, 75));
		
		assertEquals(4, (int)amount.getDollars());
		assertEquals(50, (int)amount.getCents());
	}
	
	@Test
	public void testSubtractMoneyHandlesNegatives()  {
		Money amount = new Money(2, 50);
		
		amount.subtract(new Money(4, 25));
		
		assertEquals(-1, (int)amount.getDollars());
		assertEquals(75, (int)amount.getCents());
	}
}

