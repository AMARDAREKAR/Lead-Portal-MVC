package yoan.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

@Entity(name = "company_tbl")
public class Company {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int companyID;
	@NotEmpty(message= "should not be empty")
	private String companyName,industryType,employeeSize,domain;
	private String sicCode,naicsCode,revenueSize;
	@URL@NotEmpty(message= "should not be empty")
	private String employeeSizeLink,industryTypeLink;
	@URL
	private String revenueSizeLink,sicCodeLink,naicsCodeLink;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="addressID")
	@Valid
	private Address address;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEmployeeSize() {
		return employeeSize;
	}
	public void setEmployeeSize(String employeeSize) {
		this.employeeSize = employeeSize;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getSicCode() {
		return sicCode;
	}
	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}
	public String getNaicsCode() {
		return naicsCode;
	}
	public void setNaicsCode(String naicsCode) {
		this.naicsCode = naicsCode;
	}
	public String getRevenueSize() {
		return revenueSize;
	}
	public void setRevenueSize(String revenueSize) {
		this.revenueSize = revenueSize;
	}
	public String getEmployeeSizeLink() {
		return employeeSizeLink;
	}
	public void setEmployeeSizeLink(String employeeSizeLink) {
		this.employeeSizeLink = employeeSizeLink;
	}
	public String getRevenueSizeLink() {
		return revenueSizeLink;
	}
	public void setRevenueSizeLink(String revenueSizeLink) {
		this.revenueSizeLink = revenueSizeLink;
	}
	public String getIndustryTypeLink() {
		return industryTypeLink;
	}
	public void setIndustryTypeLink(String industryTypeLink) {
		this.industryTypeLink = industryTypeLink;
	}
	public String getSicCodeLink() {
		return sicCodeLink;
	}
	public void setSicCodeLink(String sicCodeLink) {
		this.sicCodeLink = sicCodeLink;
	}
	public String getNaicsCodeLink() {
		return naicsCodeLink;
	}
	public void setNaicsCodeLink(String naicsCodeLink) {
		this.naicsCodeLink = naicsCodeLink;
	}
}
