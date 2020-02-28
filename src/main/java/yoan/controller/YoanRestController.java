package yoan.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import yoan.model.Asset;
import yoan.model.Campaign;
import yoan.model.Employee;
import yoan.model.Lead;
import yoan.model.Option;
import yoan.model.Question;
import yoan.service.YoanService;

@RestController
public class YoanRestController {
	
	@Autowired
	private YoanService yoanService;
	
	@GetMapping("/getLoginInfo")
	public List<String> getLoginInfo(Principal principal)
	{
		List<String> list = new ArrayList<String>(); 
		Employee employee = yoanService.getEmployeeById(Integer.parseInt(principal.getName()));	
		list.add("Welcome "+employee.getFirstName()+" "+ employee.getLastName());
		return list;
	}
	@GetMapping("/agent.searchLeadsByDate")
	public ArrayList<Lead> searchLeadsByDate(@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("fromDate") Date fromDate,@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("toDate") Date toDate) throws ParseException
	{
		return (ArrayList<Lead>) yoanService.getAllLeadsByDate(YoanController.employee.getEmployeeId(),YoanController.employee.getDesignation(),fromDate,toDate);
	}	
	
	@GetMapping("/bdm.searchLeadsByDate")
	public ArrayList<Lead> searchLeadsByDateByBDM(@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("fromDate") Date fromDate,@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("toDate") Date toDate, @RequestParam("assetID") int assetID) throws ParseException
	{
		return (ArrayList<Lead>) yoanService.getAllLeadsByDate(YoanController.employee.getEmployeeId(),YoanController.employee.getDesignation(),fromDate,toDate,assetID);
	}	
	
	@GetMapping("/tl.searchLeadsByDate")
	public ArrayList<Lead> searchLeadsByDateByTL(@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("fromDate") Date fromDate,@DateTimeFormat(pattern = "yyyy-MM-dd")@RequestParam("toDate") Date toDate) throws ParseException
	{
		return (ArrayList<Lead>) yoanService.getAllLeadsByDate(YoanController.employee.getEmployeeId(),YoanController.employee.getDesignation(),fromDate,toDate);
	}
	
	@GetMapping("/getAssets")
	public Map<String,String> getAssets()
	{
		return yoanService.getAssets();
	}
	@GetMapping("/getAllAssets")
	public List<Asset> getAllAssets()
	{
		return yoanService.getAllAssets();
	}
	@GetMapping("/getCampaigns")
	public List<Campaign> getCampaigns()
	{
		return yoanService.getAllCampaigns();
	}
	@GetMapping("/getAssetNames")
	public List<Asset> getAssetNames(@RequestParam("campaignID") int campaignID)
	{
		return yoanService.getAssetsByCampaignID(campaignID);
	}
	@GetMapping("/getCustomQuestions")
	public List<Question> getCustomQuestions(@RequestParam("assetID") int assetID)
	{
		return yoanService.getQuestionsByAssetID(assetID);
	}
	@GetMapping("/getOptionsForCustomQuestion")
	public List<Option> getOptionsForCustomQuestion(@RequestParam("questionID") int questionID)
	{
		List<Option> list = new ArrayList<Option>(); 	
		list = yoanService.getOptionsByQuestionID(questionID);
		return list;
	}
	@GetMapping("/asset")
	public List<Asset> getAllAsset()
	{
		return yoanService.getAllAssets();
	}
	@GetMapping("/asset/{id}")
	public Asset getAsset(@PathVariable("id") Integer id)
	{
		return yoanService.getAssetByID(id);
	}
	@PostMapping("/asset")
	public Asset postAsset(@RequestBody Asset asset)
	{
		yoanService.save(asset);
		return asset;
	}
}