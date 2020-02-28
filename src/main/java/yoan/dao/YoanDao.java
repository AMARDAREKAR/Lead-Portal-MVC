package yoan.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import yoan.model.Answer;
import yoan.model.Asset;
import yoan.model.Campaign;
import yoan.model.Employee;
import yoan.model.Lead;
import yoan.model.Option;
import yoan.model.Question;

public interface YoanDao{
	
	Employee login(int employeeId);
	
	void save(Employee employee);
	void save(Lead lead,List<Answer> answer);
	void save(Campaign project);
	void save(Asset asset);
	void save(Option option);
	void save(Question question);

	void update(Employee employee);
	void update(Lead lead);
	void update(Campaign project);
	
	List<Employee> getAllEmployees();
	List<Campaign> getAllCampaigns();
	List<Lead> getAllLeadsByDate(int employeeId,String designation,Date fromDate, Date toDate);
	List<Lead> getAllLeadsByDate(int employeeId,String designation,Date fromDate, Date toDate,int assetID);
	
	List<String> getAllCampaignNames();
	List<Asset> getAllAssets();
	List<Integer> getAllTeamLeaders();
	
	void deleteLead(int lead);
	void delete(int employeeID);

	boolean getLeadByEmail(String email);
	Map<String, String> getAssets();

	Employee getEmployeeById(int employeeId);
	Lead getLeadById(int leadNo);
	Campaign getCampaignByID(int campaignID);
	Asset getAssetByID(int assetID);	
	Question getQuestionByID(int questionID);
	
	List<Asset> getAssetByCampaignID(int campaignID);
	List<Question> getQuestionsByAssetID(int assetID);
	List<Option> getOptionsByQuestionID(int questionID);
	List<Answer> getAnswersByLeadID(int leadID);

	UserDetails loadUserByUsername(String username);
}