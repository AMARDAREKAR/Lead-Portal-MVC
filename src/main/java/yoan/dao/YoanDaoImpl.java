package yoan.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Repository;

import yoan.model.Answer;
import yoan.model.Asset;
import yoan.model.Campaign;
import yoan.model.Employee;
import yoan.model.Lead;
import yoan.model.Option;
import yoan.model.Question;

@Repository
public class YoanDaoImpl implements YoanDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override	
	public Employee login(int employeeId) {
		if(sessionFactory.getCurrentSession().get(Employee.class, employeeId)==null)
		{
			return new Employee();
		}
		else
		{
			return sessionFactory.getCurrentSession().get(Employee.class, employeeId);
		}
	}
	
	// GET ALL ROWS FROM TABLE
	public List<Lead> getAllLeads() {		
		return sessionFactory.getCurrentSession().createQuery("from Lead",Lead.class).getResultList();
	}
	@Override
	public List<Employee> getAllEmployees() {
		return sessionFactory.getCurrentSession().createQuery("from Employee",Employee.class).getResultList();
	}
	@Override
	public List<Campaign> getAllCampaigns() {
		return sessionFactory.getCurrentSession().createQuery("from Campaign",Campaign.class).getResultList();
	}
	@Override
	public List<Asset> getAllAssets() {
		return sessionFactory.getCurrentSession().createQuery("from Asset",Asset.class).getResultList();
	}	
	private List<Question> getAllQuestions() {
		return sessionFactory.getCurrentSession().createQuery("from Question",Question.class).getResultList();
	}
	private List<Option> getAllOptions() {
		return sessionFactory.getCurrentSession().createQuery("from Option",Option.class).getResultList();
	}
	private List<Answer> getAllAnswers() {
		return sessionFactory.getCurrentSession().createQuery("from Answer",Answer.class).getResultList();
	}
	
	// SAVE ROW
	@Override
	public void save(Lead lead,List<Answer> answerObjects) {
			
		Session session = null;  
		Transaction tx = null;  
		try
		{  
				session = sessionFactory.openSession(); 
				tx = session.beginTransaction();		
				for(Answer answer : answerObjects)
				session.save(answer);
				session.save(lead);
				tx.commit();  				  
		}
		catch (Exception ex) 
		{  
			ex.printStackTrace();  
			tx.rollback();  
		}  
		finally 
		{
			session.close();
		}  
	}
	@Override
	public void save(Campaign project) {
		sessionFactory.getCurrentSession().save(project);
	}
	@Override
	public void save(Asset asset) {
		sessionFactory.getCurrentSession().save(asset);
	}
	@Override
	public void save(Employee employee){  
		sessionFactory.getCurrentSession().save(employee);
	}
	@Override
	public void save(Question question){  
		sessionFactory.getCurrentSession().save(question);
	}
	@Override
	public void save(Option option){  
		sessionFactory.getCurrentSession().save(option);
	}
	
	// UPDATE ROW
	@Override
	public void update(Lead lead) {
			
		Session session = null;  
		Transaction tx = null;  
		try
		{  
				session = sessionFactory.openSession(); 
				tx = session.beginTransaction();		
				for(Answer answer: getAnswersByLeadID(lead.getLeadID()))
					session.delete(answer);
				if(lead.getAnswer() != null)
					for(Answer answer : lead.getAnswer())
					session.save(answer);
				session.update(lead);
				tx.commit();  				  
		}
		catch (Exception ex) 
		{  
			ex.printStackTrace();  
			tx.rollback();  
		}  
		finally 
		{
			session.close();
		}  
	}
	@Override
	public void update(Campaign project) {
		sessionFactory.getCurrentSession().update(project);
	}
	@Override
	public void update(Employee employee){  
		sessionFactory.getCurrentSession().update(employee);
	}
	
	// DELETE ROW
	@Override
	public void deleteLead(int leadNo) {
		
		Lead lead = sessionFactory.getCurrentSession().byId(Lead.class).load(leadNo);
		sessionFactory.getCurrentSession().delete(lead);
		sessionFactory.getCurrentSession().delete(lead.getProspect());
		sessionFactory.getCurrentSession().delete(lead.getCompany().getAddress());
		sessionFactory.getCurrentSession().delete(lead.getCompany());
		for(Answer answer : getAnswersByLeadID(lead.getLeadID()))
		sessionFactory.getCurrentSession().delete(answer);
	}
	@Override
	public void delete(int employeeID) {
		Employee employee = sessionFactory.getCurrentSession().byId(Employee.class).load(employeeID);
		sessionFactory.getCurrentSession().delete(employee);		
	}
	
	// FETCH ROW BY PRIMARY KEY
	@Override
	public Asset getAssetByID(int assetID) {
		return sessionFactory.getCurrentSession().get(Asset.class,assetID);
	}
	@Override
	public Lead getLeadById(int leadID) {		
		return sessionFactory.getCurrentSession().get(Lead.class,leadID);
	}
	@Override
	public Employee getEmployeeById(int employeeID) {
		return sessionFactory.getCurrentSession().get(Employee.class,employeeID);
	}
	@Override
	public Question getQuestionByID(int questionID) {
		return sessionFactory.getCurrentSession().get(Question.class,questionID);
	}
	@Override
	public Campaign getCampaignByID(int campaignID) {
		return sessionFactory.getCurrentSession().get(Campaign.class,campaignID);
	}


	// GET ALL LEADS BY DATE
	@Override
	public List<Lead> getAllLeadsByDate(int employeeId,String designation,Date fromDate, Date toDate) {
		
		List<Lead> list = getAllLeads();
		List<Lead> newList = new ArrayList<Lead>();;
		Date date;

		if(designation.equals("AGENT"))
		{
			for(Lead lead:list)
			{
				if(lead.getAgent().getEmployeeId() == employeeId)
				{
					date = lead.getDate();
				    if((date.compareTo(fromDate)>0 || date.compareTo(fromDate)==0) && (date.compareTo(toDate)<0 || date.compareTo(toDate)==0) )
				    {
				    	newList.add(lead);
				    }
				}
			}
		}
		else if(designation.equals("TEAM LEADER"))
		{
			for(Lead lead:list)
			{
				if(lead.getTeamLead().getEmployeeId() == employeeId)
				{
					date = lead.getDate();
				    if((date.compareTo(fromDate)>0 || date.compareTo(fromDate)==0) && (date.compareTo(toDate)<0 || date.compareTo(toDate)==0) )
				    {
				    	newList.add(lead);
				    }
				}
			}
		}
		else if(designation.equals("BDM"))
		{
			for(Lead lead:list)
			{
				date = lead.getDate();
				if((date.compareTo(fromDate)>0 || date.compareTo(fromDate)==0) && (date.compareTo(toDate)<0 || date.compareTo(toDate)==0) )
				{
				  	newList.add(lead);
				}
			}
		}
		Collections.reverse(newList);
		return newList;
	}
	
	@Override
	public List<Lead> getAllLeadsByDate(int employeeId,String designation,Date fromDate, Date toDate, int assetID) {
		
		List<Lead> list = getAllLeads();
		List<Lead> newList = new ArrayList<Lead>();;
		Date date;

		for(Lead lead:list)
			{
				date = lead.getDate();
				if((date.compareTo(fromDate)>0 || date.compareTo(fromDate)==0) && (date.compareTo(toDate)<0 || date.compareTo(toDate)==0))
				{
					if(assetID==0)
				  	newList.add(lead);
					else
					if(lead.getAsset().getAssetID() == assetID)
					newList.add(lead);
				}
			}
		Collections.reverse(newList);
		return newList;
	}
	
	@Override
	public List<String> getAllCampaignNames() {
		List<Campaign> list = getAllCampaigns();
		List<String> newList = new ArrayList<String>();;
		for(Campaign campaign:list)
		{
			newList.add(campaign.getCampaignName());
		} 
		return newList;
	}
	@Override
	public List<Integer> getAllTeamLeaders() {
		List<Employee> list = getAllEmployees();
		List<Integer> newList = new ArrayList<Integer>();;
		for(Employee employee:list)
		{
			if(employee.getDesignation().equals("TEAM LEADER"))
			newList.add(employee.getEmployeeId());
		} 
		return newList;
	}	
	
	
	@Override
	public boolean getLeadByEmail(String email) {
		List<Lead> list = getAllLeads();
		for(Lead lead:list)
		{
			if(lead.getProspect().getEmail().equals(email))
			return true;
		}
		return false;
	}
	@Override
	public Map<String, String> getAssets() {
		List<Asset> assets = getAllAssets();
		Map<String,String> map = new TreeMap<String,String>();
		for(Asset asset : assets)
		{
			map.put(asset.getAssetName(),sessionFactory.getCurrentSession().byId(Campaign.class).load(asset.getCampaignID()).getCampaignName());
		}
		return map;
	}
	@Override
	public List<Option> getOptionsByQuestionID(int questionID) {
		List<Option> list = getAllOptions();
		List<Option> selectedOptions= new ArrayList<Option>();
		for(Option option:list)
		{
			if(option.getQuestionID() == questionID )
				selectedOptions.add(option);
		}
		return selectedOptions;
	}
	
	
	@Override
	public List<Asset> getAssetByCampaignID(int campaignID) {
		List<Asset> allAssets = getAllAssets();
		List<Asset> selectedAssets = new ArrayList<Asset>();
		for(Asset asset:allAssets)
		{
			if(asset.getCampaignID() == campaignID )
				selectedAssets.add(asset);
		}
		return selectedAssets;
	}
	@Override
	public List<Question> getQuestionsByAssetID(int assetID) {
		List<Question> list = getAllQuestions();
		List<Question> selectedQuestions= new ArrayList<Question>();
		for(Question question:list)
		{
			if(question.getAssetID() == assetID)
				selectedQuestions.add(question);
		}
		return selectedQuestions;
	}

	@Override
	public List<Answer> getAnswersByLeadID(int leadID) {
		List<Answer> answerList = getAllAnswers();
		List<Answer> selectedanswers = new ArrayList<Answer>();
		for(Answer answer : answerList)
			if(answer.getLeadID().getLeadID()==leadID)
				selectedanswers.add(answer);
		return selectedanswers;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {

		int employeeId = Integer.parseInt(username);
		
		UserBuilder builder = null;		 
		try
		{
			Employee user = getEmployeeById(employeeId);
			if (user != null) 
			{
				builder = org.springframework.security.core.userdetails.User.withUsername(username);
				builder.password(user.getPassword());
				builder.roles(user.getDesignation());
			} 
			else 
			{
				throw new UsernameNotFoundException("User not found.");
			}	
		 }
		 catch(UsernameNotFoundException e)
		 {
			 System.out.println(e);
		 }
		 return builder.build();
	}
}