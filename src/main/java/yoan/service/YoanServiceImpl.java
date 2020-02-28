package yoan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yoan.dao.YoanDao;
import yoan.model.Answer;
import yoan.model.Asset;
import yoan.model.Campaign;
import yoan.model.Employee;
import yoan.model.Lead;
import yoan.model.Option;
import yoan.model.Question;

@Service
@Transactional
public class YoanServiceImpl  implements YoanService   {
	
	@Autowired
	private YoanDao yoanDao;
	
	@Override
	public Employee login(int employeeId) {
		return yoanDao.login(employeeId);
	}
	@Override
	public List<Employee> getAllEmployees() {
		return yoanDao.getAllEmployees();
	}
	@Override
	public List<Campaign> getAllCampaigns() {
		return yoanDao.getAllCampaigns();
	}
	@Override
	public List<Asset> getAllAssets() {
		return yoanDao.getAllAssets();
	}
	@Override
	public void save(Employee employee) {
		yoanDao.save(employee);
	}
	@Override
	public void save(Option option) {
		yoanDao.save(option);
	}
	@Override
	public void save(Question question) {
		yoanDao.save(question);
	}
	@Override
	public void save(Lead lead,List<Answer> answerObjects) {
		yoanDao.save(lead,answerObjects);
	}
	@Override
	public void save(Campaign project) {
		yoanDao.save(project);
	}
	@Override
	public void save(Asset asset) {
		yoanDao.save(asset);
	}
	@Override
	public void update(Employee employee) {
		yoanDao.update(employee);
	}
	@Override
	public void update(Lead lead) {
		yoanDao.update(lead);
	}
	@Override
	public void update(Campaign project) {
		yoanDao.update(project);
	}
	@Override
	public List<Lead> getAllLeadsByDate(int employeeId,String designation,Date fromDate, Date toDate) {
		return yoanDao.getAllLeadsByDate(employeeId,designation,fromDate,toDate);
	}
	@Override
	public List<Lead> getAllLeadsByDate(int employeeId,String designation,Date fromDate, Date toDate,int assetID) {
		return yoanDao.getAllLeadsByDate(employeeId,designation,fromDate,toDate,assetID);
	}
	@Override
	public List<String> getAllCampaignNames() {
		return yoanDao.getAllCampaignNames();
	}
	@Override
	public List<Integer> getAllTeamLeaders() {
		return yoanDao.getAllTeamLeaders();
	}
	@Override
	public void deleteLead(int lead_Number) {
		yoanDao.deleteLead(lead_Number);
	}
	@Override
	public Lead getLeadById(int leadNo) {
		return yoanDao.getLeadById(leadNo);
	}
	@Override
	public Employee getEmployeeById(int employeeId) {
		return yoanDao.getEmployeeById(employeeId);
	}
	@Override
	public void delete(int employeeID) {
		yoanDao.delete(employeeID);	
	}
	@Override
	public boolean getLeadByEmail(String email) {
		return yoanDao.getLeadByEmail(email);
	}
	
	@Override
	public Map<String, String> getAssets() {
		return yoanDao.getAssets();
	}
	@Override
	public Campaign getCampaignByID(int campaignID) {
		return yoanDao.getCampaignByID(campaignID);
	}
	@Override
	public Asset getAssetByID(Integer assetID) {
		return yoanDao.getAssetByID(assetID);
	}
	@Override
	public Question getQuestionByID(int questionID) {
		return yoanDao.getQuestionByID(questionID);
	}
	
	@Override
	public List<Asset> getAssetsByCampaignID(int campaignID) {
		return yoanDao.getAssetByCampaignID(campaignID);
	}
	@Override
	public List<Question> getQuestionsByAssetID(int assetID) {
		return yoanDao.getQuestionsByAssetID(assetID);
	}
	@Override
	public List<Option> getOptionsByQuestionID(int questionID) {
		return yoanDao.getOptionsByQuestionID(questionID);
	}
	@Override
	public List<Answer> getAnswersByLeadID(int leadID) {
		return yoanDao.getAnswersByLeadID(leadID);
	}
	@Override
	public UserDetails loadUserByUsername(String username)
	{
		return yoanDao.loadUserByUsername(username);
	}
}