package yoan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import yoan.model.Answer;
import yoan.model.Asset;
import yoan.model.Campaign;
import yoan.model.Employee;
import yoan.model.Lead;
import yoan.model.Option;
import yoan.model.Question;

public interface YoanService extends UserDetailsService {
	
	Employee login(int employeeId);
	
	void save(Employee emp);
	void save(Lead lead,List<Answer> answerObjects);
	void save(Campaign project);
	void save(Asset asset);
	void save(Question question);
	void save(Option option);
	
	void update(Employee emp);
	void update(Lead emp);
	void update(Campaign project);
	
	List<Employee> getAllEmployees();
	List<Campaign> getAllCampaigns();
	List<Lead> getAllLeadsByDate(int employeeId,String designation,Date fromDate, Date toDate);
	List<Lead> getAllLeadsByDate(int employeeId, String designation, Date fromDate, Date toDate, int assetID);
	List<String> getAllCampaignNames();
	List<Integer> getAllTeamLeaders();
	List<Asset> getAllAssets();
	
	void delete(int employeeID);
	void deleteLead(int lead_Number);
	
	Lead getLeadById(int leadNo);
	Employee getEmployeeById(int employeeId);
	Campaign getCampaignByID(int campaignID);
	Asset getAssetByID(Integer integer);
	Question getQuestionByID(int questionID);
	
	Map<String, String> getAssets();
	boolean getLeadByEmail(String email);

	List<Asset> getAssetsByCampaignID(int campaignID);
	List<Question> getQuestionsByAssetID(int assetID);
	List<Option> getOptionsByQuestionID(int questionID);
	List<Answer> getAnswersByLeadID(int leadID);	
}