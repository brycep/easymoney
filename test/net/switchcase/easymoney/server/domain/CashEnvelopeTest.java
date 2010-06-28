package net.switchcase.easymoney.server.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class CashEnvelopeTest {
	
	@Test
	public void testSubtractValueFromEnvelope() throws Exception {
		CashEnvelope cashEnvelope = new CashEnvelope();
		cashEnvelope.setBalance(4000);
		
		cashEnvelope.subtractBalance(100);
		
		assertEquals(3900, cashEnvelope.getBalance());
	}

}
