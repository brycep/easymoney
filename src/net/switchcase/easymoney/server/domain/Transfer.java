package net.switchcase.easymoney.server.domain;

import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class Transfer {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String id;
	@Persistent private String description;
	@Persistent private Long amount;
	@Persistent private String source;
	@Persistent private Date createTimestamp = new Date();
	@Persistent private Date transferDate;
	
	@Persistent private String sourceEnvelopeKey;
	@Persistent private String destEnvelopeKey;
	
	public Transfer() {
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
	public Date getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public String getSourceEnvelopeKey() {
		return sourceEnvelopeKey;
	}
	public void setSourceEnvelopeKey(String sourceEnvelopeKey) {
		this.sourceEnvelopeKey = sourceEnvelopeKey;
	}
	public String getDestEnvelopeKey() {
		return destEnvelopeKey;
	}
	public void setDestEnvelopeKey(String destEnvelopeKey) {
		this.destEnvelopeKey = destEnvelopeKey;
	}
	
	

}
