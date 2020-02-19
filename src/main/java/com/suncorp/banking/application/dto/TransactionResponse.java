package com.suncorp.banking.application.dto;

/**
 * @author uday
 *
 */
public class TransactionResponse {

	private String referenceid, referencemsg;

	public TransactionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransactionResponse(String referenceid, String referencemsg) {
		super();
		this.referenceid = referenceid;
		this.referencemsg = referencemsg;
	}

	public String getReferenceid() {
		return referenceid;
	}

	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}

	public String getReferencemsg() {
		return referencemsg;
	}

	public void setReferencemsg(String referencemsg) {
		this.referencemsg = referencemsg;
	}

}
