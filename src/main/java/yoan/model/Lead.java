package yoan.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="lead_tbl")
public class Lead {
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int leadID;
	@Temporal(TemporalType.DATE)@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date=new Date();	
	private String leadStatus,department;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name ="agentID")
	private Employee agent;	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name ="teamLeadID")
	private Employee teamLead;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="companyID")
	@Valid
	private Company company;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="campaignID")
	@Valid
	private Campaign campaign;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="assetID")
	@Valid
	private Asset asset;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="prospectID")
	@Valid
	private Prospect prospect;
	
	@Transient
	private List<Answer> answer;
	
	@Transient
	private List<String> answerSet;

	public int getLeadID() {
		return leadID;
	}

	public void setLeadID(int leadID) {
		this.leadID = leadID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Employee getAgent() {
		return agent;
	}

	public void setAgent(Employee agent) {
		this.agent = agent;
	}

	public Employee getTeamLead() {
		return teamLead;
	}

	public void setTeamLead(Employee teamLead) {
		this.teamLead = teamLead;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Prospect getProspect() {
		return prospect;
	}

	public void setProspect(Prospect prospect) {
		this.prospect = prospect;
	}

	public List<Answer> getAnswer() {
		return answer;
	}

	public void setAnswer(List<Answer> answer) {
		this.answer = answer;
	}


	public List<String> getAnswerSet() {
		return answerSet;
	}

	public void setAnswerSet(List<String> answerSet) {
		this.answerSet = answerSet;
	}

	public String toString()
	{
		return "LeadID : "+ leadID +" Agent EmployeeID :"+ agent.getEmployeeId()+" Team Lead EmployeeID :"+ teamLead.getEmployeeId();
	}
}