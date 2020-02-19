package com.suncorp.banking.application.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suncorp.banking.application.validation.groups.NullNotAllowed;

/**
 * @author uday
 *
 */
@Entity
@Table(name = "bank_accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdDate", "updatedDate" }, allowGetters = true)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	@JsonIgnore
	private long id;

	@Column(name = "first_name")
	@NotNull(message = "First Name can not be null.")
	@Size(min = 3, message = "Fistname should have atleast 3 characters")
	private String firstName;

	@Column(name = "last_name")
	@NotNull(message = "Last Name can not be null")
	@Size(min = 2, message = "Lastname should be minimum 2 characters")
	private String lastName;

	@Column(name = "dob")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Date of birth cannot be null")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dob;

	@Column(name = "record_created_date", nullable = false, updatable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date createdDate;

	@Column(name = "record_updated_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date updatedDate;

	@Column(name = "account_number", unique = true)
	private String accountnumber;

	@Column(name = "account_type")
	@NotNull(message = "Please enter the desired account type for the account process.", groups = NullNotAllowed.class)
	@NotBlank(message = "Please enter the desired account type for the account process.", groups = NullNotAllowed.class)
	private String accountType;

	@Column(name = "acccount_balance", columnDefinition = "Decimal(15,2) default 0")
	private double balance;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(long id,
			@NotNull(message = "First Name can not be null.") @Size(min = 3, message = "Fistname should have atleast 3 characters") String firstName,
			@NotNull(message = "Last Name can not be null") @Size(min = 2, message = "Lastname should be minimum 2 characters") String lastName,
			@NotNull(message = "Date of birth cannot be null") Date dob, Date createdDate, Date updatedDate,
			String accountnumber,
			@NotNull(message = "Please enter the desired account type for the account process.", groups = NullNotAllowed.class) @NotBlank(message = "Please enter the desired account type for the account process.", groups = NullNotAllowed.class) String accountType,
			double balance) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.accountnumber = accountnumber;
		this.accountType = accountType;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", accountnumber=" + accountnumber
				+ ", accountType=" + accountType + ", balance=" + balance + "]";
	}

}
