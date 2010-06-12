package net.switchcase.easymoney.server.domain;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Transaction {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	@Persistent private String description;
	@Persistent private Double gpsLat;
	@Persistent private Double gpsLong;
	@Persistent private Long amount;
	@Persistent private String source;
	@Persistent private Date createTimestamp = new Date();
	@Persistent private Date transactionDate;
	@Persistent private boolean reconsiled = false;
	@Persistent private String debitAccountKey;
	@Persistent private String creditAccountKey;
	
	@Persistent private String expenseCategoryKey;
		
	public Transaction() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getGpsLat() {
		return gpsLat;
	}

	public void setGpsLat(Double gpsLat) {
		this.gpsLat = gpsLat;
	}

	public Double getGpsLong() {
		return gpsLong;
	}

	public void setGpsLong(Double gpsLong) {
		this.gpsLong = gpsLong;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public boolean isReconsiled() {
		return reconsiled;
	}

	public void setReconsiled(boolean reconsiled) {
		this.reconsiled = reconsiled;
	}

	public String getDebitAccountKey() {
		return debitAccountKey;
	}

	public void setDebitAccountKey(String debitAccountKey) {
		this.debitAccountKey = debitAccountKey;
	}

	public String getCreditAccountKey() {
		return creditAccountKey;
	}

	public void setCreditAccountKey(String creditAccountKey) {
		this.creditAccountKey = creditAccountKey;
	}

	public String getExpenseCategoryKey() {
		return expenseCategoryKey;
	}

	public void setExpenseCategoryKey(String expenseCategoryKey) {
		this.expenseCategoryKey = expenseCategoryKey;
	}
	
}
