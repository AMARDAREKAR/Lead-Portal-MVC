package yoan.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import yoan.model.Answer;
import yoan.model.Asset;
import yoan.model.Campaign;
import yoan.model.Employee;
import yoan.model.Lead;
import yoan.model.Option;
import yoan.model.Question;
import yoan.service.YoanService;

@Controller  
public class YoanController {  	
	
	@Autowired
	private YoanService yoanService;
	static protected Employee employee;

	@GetMapping(value="/")
	public String index(Model model, Principal principal) throws ParseException 
	{
		employee = yoanService.getEmployeeById(Integer.parseInt(principal.getName()));
	    designModel(model);
		
		if(employee.getDesignation().equals("AGENT"))
		{
			return "AgentLogin";
		}
		else if(employee.getDesignation().equals("TEAM LEADER"))
		{
			return "TeamLeaderLogin";
		}
		else
		{
			return "BDMLogin";
		}
	}
	
	@GetMapping(value="/agent.addnewlead")
	public String addnewlead(Model m)
	{
		m.addAttribute("teamLeads", yoanService.getAllTeamLeaders());
		Lead lead = new Lead();
		lead.setAgent(employee);
		m.addAttribute("newlead",lead);
		return "addnewlead";
	}
	@PostMapping(value="/agent.uploadlead")  
	public String uploadlead(@Valid @ModelAttribute("newlead") Lead lead,BindingResult result,Model m,RedirectAttributes redirectAttributes) throws ParseException  
	{		
		if(result.hasErrors())
		{
			designModel(m);
			lead.setAgent(employee);
			m.addAttribute("teamLeads", yoanService.getAllTeamLeaders());
			return "addnewlead";
		}
		else
		{
			int i=0;
			List<Question> questions = yoanService.getQuestionsByAssetID(lead.getAsset().getAssetID());
			List<String> answers = lead.getAnswerSet();
			List<Answer> answerObjects = new ArrayList<Answer>();
			Answer answer;
			for(Question question : questions)
			{
				answer = new Answer();
				answer.setAnswer(answers.get(i));
				answer.setQuestionID(yoanService.getQuestionByID(question.getQuestionID()));
				answer.setLeadID(lead);
				answerObjects.add(answer);
				i++;		
			}

			try
			{
				if(yoanService.getLeadByEmail(lead.getProspect().getEmail()))
					lead.setLeadStatus("DUPLICATE");
				else
					lead.setLeadStatus("PENDING");
				
				lead.setAgent(yoanService.getEmployeeById((lead.getAgent().getEmployeeId())));
				lead.setTeamLead(yoanService.getEmployeeById((lead.getTeamLead().getEmployeeId())));
				lead.setCampaign(yoanService.getCampaignByID(lead.getCampaign().getCampaignID()));
				lead.setAsset(yoanService.getAssetByID(lead.getAsset().getAssetID()));
				yoanService.save(lead,answerObjects);
				designModel(redirectAttributes);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}		
			return "redirect:/";
		}		
	}
	@RequestMapping("/agent.updateLeadPage")
	public String updateLeadAgent(@RequestParam("leadID") int leadID,Model m)
	{	
		Lead lead = yoanService.getLeadById(leadID);

		List<Campaign> campaignList = yoanService.getAllCampaigns();		
		Map<Integer,String> campaignMap = new TreeMap<Integer,String>();		
		for(Campaign campaign : campaignList)
			campaignMap.put(campaign.getCampaignID(), campaign.getCampaignName());
		m.addAttribute("campaignNames", campaignMap );

		List<Asset> assetList = yoanService.getAssetsByCampaignID(lead.getCampaign().getCampaignID());		
		Map<Integer,String> assetMap = new TreeMap<Integer,String>();		
		for(Asset asset : assetList)
			assetMap.put(asset.getAssetID(), asset.getAssetName());
		m.addAttribute("assetNames", assetMap );
		
		lead.setAnswer(yoanService.getAnswersByLeadID(leadID));
		
		m.addAttribute("teamLeads", yoanService.getAllTeamLeaders());
		m.addAttribute("updateLead",lead);
		return "updateLeadPageForAgent";
	}
	@RequestMapping("/agent.updateLead")  
	public String updateLeadByAgent(@Valid @ModelAttribute("updateLead") Lead lead,BindingResult result,Model m,RedirectAttributes redirectAttributes) throws ParseException  
	{ 	
		System.out.println("answers"+lead.getAnswer());
		System.out.println("answerset"+lead.getAnswerSet());
		if(lead.getAnswerSet() == null)
		{
			lead.setAnswer(null);
		}
		else
		{
			List<Answer> answerList = new ArrayList<Answer>();
			List<Question> questionSet = yoanService.getQuestionsByAssetID(lead.getAsset().getAssetID());
			int i=0;
			for(String answer:lead.getAnswerSet())
			{
				Answer ans =  new Answer();
				ans.setAnswer(answer);
				ans.setLeadID(lead);
				ans.setQuestionID(questionSet.get(i++));
				answerList.add(ans);
			}
			lead.setAnswer(answerList);
		}
		if(result.hasErrors())
		{
			System.out.print(result);
			List<Campaign> campaignList = yoanService.getAllCampaigns();		
			Map<Integer,String> campaignMap = new TreeMap<Integer,String>();		
			for(Campaign campaign : campaignList)
				campaignMap.put(campaign.getCampaignID(), campaign.getCampaignName());
			m.addAttribute("campaignNames", campaignMap );

			List<Asset> assetList = yoanService.getAssetsByCampaignID(lead.getCampaign().getCampaignID());
			Map<Integer,String> assetMap = new TreeMap<Integer,String>();		
			for(Asset asset : assetList)
				assetMap.put(asset.getAssetID(), asset.getAssetName());
			m.addAttribute("assetNames", assetMap );
			
			lead.setAnswer(lead.getAnswer());
			
			lead.setAgent(employee);
			m.addAttribute("teamLeads", yoanService.getAllTeamLeaders());
			m.addAttribute("updateLead",lead);
			return "updateLeadPageForAgent";
		}
		else
		{
			try
			{
				lead.setAgent(yoanService.getEmployeeById((lead.getAgent().getEmployeeId())));
				lead.setTeamLead(yoanService.getEmployeeById((lead.getTeamLead().getEmployeeId())));
				lead.setCampaign(yoanService.getCampaignByID(lead.getCampaign().getCampaignID()));
				lead.setAsset(yoanService.getAssetByID(lead.getAsset().getAssetID()));
				yoanService.update(lead);
				designModel(redirectAttributes);
			}
			catch(Exception e)
			{
				System.out.println("Catch Block : "+e);
			}
			return "redirect:/";
		}		
	}

	@RequestMapping("/agent.changepassword")
	public String changeagentpassword(Model m)
	{
		return "changeagentpassword";
	}
	@RequestMapping("/agent.updatepassword")  
	public String updateagentpassword(@RequestParam("password1") String pass1,@RequestParam("password2") String pass2,Model m)  
	{  	
		if(pass1.equals(pass2)==false)
		{
			return "changeagentpassword";
		}
		else
		{
			employee.setPassword(new BCryptPasswordEncoder().encode(pass1));
			yoanService.update(employee);
			return "redirect:/logoutAction";
		}		
	}
	@RequestMapping("/agent.leadManagemet")
	public String leadManagement(RedirectAttributes redirectAttributes) throws ParseException
	{
		designModel(redirectAttributes);	
		return "redirect:/";
	}		
	@RequestMapping("/agent.deleteLead")
	public String deleteLeadByAgent(@RequestParam("leadID") int leadID,RedirectAttributes redirectAttributes) throws ParseException
	{
		yoanService.deleteLead(leadID);
		designModel(redirectAttributes);	
		return "redirect:/";
	}
	
	@GetMapping(value = "/bdm.exporttoexcel")
	public void exporttoexcel(HttpServletResponse response,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("fromDate") Date fromDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("toDate") Date toDate, 
			@RequestParam("assetID") int assetID, Model model)
			throws ParseException, IOException {
		String str1, str2;
		str1 = fromDate.toString();
		str2 = toDate.toString();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		str1 = simpleDateFormat.format(fromDate);
		str2 = simpleDateFormat.format(toDate);
		model.addAttribute("fromDate", str1);
		model.addAttribute("toDate", str2);
		List<Lead> list = yoanService.getAllLeadsByDate(employee.getEmployeeId(), employee.getDesignation(), fromDate,toDate,assetID);
		model.addAttribute("leadList", list);

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("Lead Data");

		// Create row object
		XSSFRow row;

		// This data needs to be written (Object[])
		Map<Integer, Object[]> empinfo = new TreeMap<Integer, Object[]>();
		for (Lead lead : list)
			empinfo.put(lead.getLeadID(),
					new Object[] { 
							lead.getDate(), 
							lead.getAgent().getEmployeeId(), 
							lead.getAgent().getFirstName() + " " + lead.getAgent().getLastName(),
							lead.getDepartment(),
							lead.getCampaign().getCampaignName(),
							lead.getAsset().getAssetName(),
							lead.getProspect().getFirstName(), 
							lead.getProspect().getLastName(),
							lead.getProspect().getEmail(),
							lead.getCompany().getCompanyName(), 
							lead.getProspect().getJobTitle(), 
							lead.getProspect().getJobLevel(),
							lead.getProspect().getJobDepartment(),
							lead.getCompany().getAddress().getAddressLine1(),
							lead.getCompany().getAddress().getAddressLine2(),
							lead.getCompany().getAddress().getState(),
							lead.getCompany().getAddress().getCity(),
							lead.getCompany().getAddress().getZipcode(),
							lead.getCompany().getAddress().getCountry(),
							lead.getProspect().getPhoneNumber(),
							lead.getProspect().getDirectNumber(), 
							lead.getCompany().getEmployeeSize(),
							lead.getCompany().getRevenueSize(),
							lead.getCompany().getSicCode(),
							lead.getCompany().getNaicsCode(),
							lead.getCompany().getIndustryType(),							
							lead.getProspect().getJobTitleLink(),
							lead.getCompany().getEmployeeSizeLink(),
							lead.getCompany().getRevenueSizeLink(),
							lead.getCompany().getIndustryTypeLink(),
							lead.getCompany().getSicCodeLink(),
							lead.getCompany().getNaicsCodeLink(),
							lead.getCompany().getDomain(),
							lead.getLeadStatus() });

		// Set 1st Low Values in excel
		int rowid = 0, cellid = 0;
		row = spreadsheet.createRow(rowid++);
		Cell cell = row.createCell(cellid++);
		cell.setCellValue((String) "Date");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Employee Id");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Ra Name");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Department");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Campaign ID");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Asset Title");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "First Name");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Last Name");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Email Address");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Company Name");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Job Title");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Job Level");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Job Department");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Address Line 1");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Address Line 2");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "State");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "City");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Zipcode");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Country");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Phone Number");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Direct Number");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Employee Size");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Revenue Size");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Industry Type");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "SIC Code");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "NAICS Code");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Job Title Link");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Employee Size Link");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Revenue Size Link");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Industry Type Link");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "SIC Code Link");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "NAICS Code Link");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Domain");
		cell = row.createCell(cellid++);
		cell.setCellValue((String) "Lead Status");
		// write custom questions to the sheet
		if(assetID!=0)
		for(Question question : yoanService.getQuestionsByAssetID(assetID))
		{
			cell = row.createCell(cellid++);
			cell.setCellValue((String) question.getQuestion());
		}
		
		// Iterate over data and write to sheet	
		Set<Integer> keyid = empinfo.keySet();
		for (Integer key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = empinfo.get(key);
			cellid = 0;

			for (Object obj : objectArr) {
				cell = row.createCell(cellid++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Date)
					cell.setCellValue((String) obj.toString());
				else if (obj instanceof Long)
					cell.setCellValue((Long) obj);
			}
		}
		// Write answers for custom questions in the sheet
		if(assetID!=0)
		{
			rowid=1;
			for (Integer key : keyid) {
				row = spreadsheet.getRow(rowid++);
				cellid = 34;
				for(Answer answer : yoanService.getAnswersByLeadID(key) )
				{
					cell = row.createCell(cellid++);
					cell.setCellValue((String) answer.getAnswer());
				}
			}
		}
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(
				new File("C:/Users/Amar/Documents/leads.xlsx"));
		workbook.write(out);
		out.close();
		Path file = Paths
				.get("C:/Users/Amar/Documents/","leads.xlsx");
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.addHeader("Content-Disposition", "attachment; filename=" + "leads.xlsx");
		try {
			Files.copy(file, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	@RequestMapping("/bdm.leadManagement")
	public String bdmleads(RedirectAttributes redirectAttributes) throws ParseException
	{
		designModel(redirectAttributes);	
		return "redirect:/";
	}
	@RequestMapping("/bdm.employeeManagement")
	public String addnewuserbybdm(Model m)
	{
		m.addAttribute("newuserbybdm",new Employee());
		m.addAttribute("employeeList",yoanService.getAllEmployees());
		return "employeeManagementPageForBDM";
	}
	@PostMapping("/bdm.uploadnewuser")  
	public String addnewuserbydbm(@Valid @ModelAttribute("newuserbybdm") Employee emp,BindingResult result,Model model,RedirectAttributes redirectAttributes) throws ParseException  
	{
		if( result.hasErrors())
		{
			model.addAttribute("employeeList",yoanService.getAllEmployees());
			return "employeeManagementPageForBDM";
		}
		else
		{
			try
			{
				emp.setPassword(new BCryptPasswordEncoder().encode(emp.getPassword()));
				yoanService.save(emp);
				return "redirect:/bdm.employeeManagement";
			}
			catch(Exception e)
			{
				redirectAttributes.addFlashAttribute("message", "EMPLOYEE ID IS ALREADY PRESENT");
				return "redirect:/bdm.employeeManagement";
			}
		}
	}
	@RequestMapping("/bdm.updateEmployee")
	public String updateUser(@RequestParam("employeeId") String employeeId,Model m)
	{
		Employee employee = yoanService.getEmployeeById(Integer.parseInt(employeeId));
		m.addAttribute("employeeId",employee.getEmployeeId());
		m.addAttribute("firstName",employee.getFirstName());
		m.addAttribute("lastName",employee.getLastName());
		m.addAttribute("designation",employee.getDesignation());
		m.addAttribute("updateEmployeeByBDM",employee);
		return "updateEmployeeByBDM";
	}
	@RequestMapping("/bdm.updateUser")  
	public String updateEmployeeByBDM(@Valid@ModelAttribute("updateEmployeeByBDM") Employee employee,BindingResult result,Model model)
	{ 			
		if( result.hasErrors())
		{
			model.addAttribute("employeeId",employee.getEmployeeId());
			model.addAttribute("firstName",employee.getFirstName());
			model.addAttribute("lastName",employee.getLastName());
			model.addAttribute("designation",employee.getDesignation());
			model.addAttribute("updateEmployeeByBDM",employee);
			return "updateEmployeeByBDM";
		}
		else
		{
			try
			{
				employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
				yoanService.update(employee);
				return "redirect:/bdm.employeeManagement";
			}
			catch(Exception e)
			{
				return "redirect:/bdm.employeeManagement";
			}
		}
	}	
	@RequestMapping("/bdm.campaignManagement")
	public String addnewproject(Model m)
	{
		m.addAttribute("newproject",new Campaign());
		return "addnewproject";
	}
	@RequestMapping("/bdm.uploadproject")  
	public String uploadproject(@ModelAttribute("newproject") Campaign campaign,Model m)  
	{  	
		try {
			yoanService.save(campaign);
			Asset asset = new Asset();
			asset.setAssetName(campaign.getCampaignName());
			asset.setCampaignID(campaign.getCampaignID());
			yoanService.save(asset);
			return "redirect:/bdm.campaignManagement";
		} catch (DuplicateKeyException e) {
			m.addAttribute("message", "CAMPAIGN ALREADY PRESENT");
			return "addnewproject";
		}
	}
	@PostMapping("/bdm.addAsset")  
	public String addAsset(@RequestParam("assetName") String assetName,@RequestParam("campaignID") int campaignID,Model m)  
	{  	
		try {
			Asset asset = new Asset();
			asset.setAssetName(assetName);
			asset.setCampaignID(yoanService.getCampaignByID(campaignID).getCampaignID());
			yoanService.save(asset);
			return "redirect:/bdm.campaignManagement";
		} catch (DuplicateKeyException e) {
			m.addAttribute("message", "ASSET ALREADY PRESENT");
			return "addnewproject";
		}
	}			
	@PostMapping("/bdm.addQuestion")  
	public String addQuestion(@RequestParam("question") String que,@RequestParam("selectAsset") int assetID,Model m)  
	{  	
		try {
			Question question = new Question();
			question.setAssetID(assetID);
			question.setQuestion(que);
			yoanService.save(question);
			return "redirect:/bdm.campaignManagement";
		} catch (DuplicateKeyException e) {
			m.addAttribute("message", "QUESTION ALREADY PRESENT");
			return "addnewproject";
		}
	}			
	@PostMapping("/bdm.addOption")  
	public String addOption(@RequestParam("optionName") String optionName,@RequestParam("getCustomQuestion") int questionID,Model m)  
	{  	
		try {
			Option option = new Option();
			option.setOptionName(optionName);
			option.setQuestionID(questionID);
			yoanService.save(option);
			return "redirect:/bdm.campaignManagement";
		} catch (DuplicateKeyException e) {
			m.addAttribute("message", "OPTION ALREADY PRESENT");
			return "addnewproject";
		}
	}			
	@RequestMapping("/bdm.changepassword")
	public String changebdmpassword(Model m)
	{
		return "changebdmpassword";
	}
	@RequestMapping("/bdm.updatepassword")  
	public String updatebdmpassword(@RequestParam("password1") String pass1,@RequestParam("password2") String pass2,Model m)  
	{  	
		if(pass1.equals(pass2)==false)
		{
			return "changebdmpassword";
		}
		else
		{
			employee.setPassword(new BCryptPasswordEncoder().encode(pass1));
			yoanService.update(employee);
			return "redirect:/logoutAction";
		}		
	}
	
	
	
	@RequestMapping("/tl.changepassword")
	public String changeteamleaderpassword(Model m)
	{
		return "changeteamleaderpassword";
	}
	@RequestMapping("/tl.updatepassword")  
	public String updateteamleaderpassword(@RequestParam("password1") String pass1,@RequestParam("password2") String pass2,Model m)  
	{  	
		if(pass1.equals(pass2)==false)
		{
			return "changeteamleaderpassword";
		}
		else
		{
			employee.setPassword(new BCryptPasswordEncoder().encode(pass1));
			yoanService.update(employee);
			return "redirect:/logoutAction";
		}		
	}
	@RequestMapping("/tl.addnewuser")
	public String addnewuserbytl(Model m)
	{
		m.addAttribute("newuserbytl",new Employee());
		m.addAttribute("employeeList",yoanService.getAllEmployees());
		return "AddNewUserTL";
	}
	@RequestMapping("/tl.AddNewUser")  
	public String addnewuserbytl(@ModelAttribute("newuserbytl") Employee employee,BindingResult result,Model m)  
	{
		if(result.hasErrors())
		{
			return "AddNewUserTL";
		}
		else
		{
			try
			{
				employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
				yoanService.save(employee);
				m.addAttribute("employeeList",yoanService.getAllEmployees());
				return "AddNewUserTL";
			}
			catch(DuplicateKeyException e)
			{
				m.addAttribute("message", "EMPLOYEE ALREADY PRESENT");
				return "AddNewUserTL";
			}
		}			
	} 
	@RequestMapping("/tl.leadManagemet")
	public String leadManagementByTL(RedirectAttributes redirectAttributes) throws ParseException
	{
		designModel(redirectAttributes);	
		return "redirect:/";
	}
	@RequestMapping("/tl.deleteLead")
	public String deleteLead(@RequestParam("leadID") int leadID,RedirectAttributes redirectAttributes) throws ParseException
	{
		yoanService.deleteLead(leadID);
		designModel(redirectAttributes);	
		return "redirect:/";
	}
	@RequestMapping("/tl.deleteEmployee")
	public String deleteEmployeeTL(@RequestParam("employeeId") int employeeId,Model m,RedirectAttributes redirectAttributes) throws ParseException
	{
		designModel(redirectAttributes);
		yoanService.delete(employeeId);
		m.addAttribute("newuserbytl",new Employee());
		m.addAttribute("employeeList",yoanService.getAllEmployees());		
		return "redirect:/tl.addnewuser";
	}	
	
	@RequestMapping("/tl.changeSatus")
	public String changeSatus(@RequestParam("leadID") int leadID,Model m) throws ParseException
	{
		Lead lead = yoanService.getLeadById(leadID);
		if(lead.getLeadStatus().equals("APPROVED"))
			lead.setLeadStatus("REJECTED");
		else
			lead.setLeadStatus("APPROVED");
		
		yoanService.update(lead);
		
		designModel(m);
		return "TeamLeaderLogin";
	}
	@RequestMapping("/tl.updateLeadPage")
	public String updateLeadPageTL(@RequestParam("leadID") int leadID,Model m)
	{	
		Lead lead = yoanService.getLeadById(leadID);

		List<Campaign> campaignList = yoanService.getAllCampaigns();		
		Map<Integer,String> campaignMap = new TreeMap<Integer,String>();		
		for(Campaign campaign : campaignList)
			campaignMap.put(campaign.getCampaignID(), campaign.getCampaignName());
		m.addAttribute("campaignNames", campaignMap );

		List<Asset> assetList = yoanService.getAssetsByCampaignID(lead.getCampaign().getCampaignID());		
		Map<Integer,String> assetMap = new TreeMap<Integer,String>();		
		for(Asset asset : assetList)
			assetMap.put(asset.getAssetID(), asset.getAssetName());
		m.addAttribute("assetNames", assetMap );
		
		lead.setAnswer(yoanService.getAnswersByLeadID(leadID));
		
		m.addAttribute("teamLeads", yoanService.getAllTeamLeaders());
		m.addAttribute("updateLead",lead);
		return "updateLeadByTL";
	}
	
	@RequestMapping("/tl.update")  
	public String updateLeadByTL(@Valid @ModelAttribute("updateLead") Lead lead,BindingResult result,Model m,RedirectAttributes redirectAttributes) throws ParseException  
	{ 	
		if(lead.getAnswerSet() == null)
		{
			lead.setAnswer(null);
		}
		else
		{
			List<Answer> answerList = new ArrayList<Answer>();
			List<Question> questionSet = yoanService.getQuestionsByAssetID(lead.getAsset().getAssetID());
			int i=0;
			for(String answer:lead.getAnswerSet())
			{
				Answer ans =  new Answer();
				ans.setAnswer(answer);
				ans.setLeadID(lead);
				ans.setQuestionID(questionSet.get(i++));
				answerList.add(ans);
			}
			lead.setAnswer(answerList);
		}
		if(result.hasErrors())
		{
			System.out.print(result);
			List<Campaign> campaignList = yoanService.getAllCampaigns();		
			Map<Integer,String> campaignMap = new TreeMap<Integer,String>();		
			for(Campaign campaign : campaignList)
				campaignMap.put(campaign.getCampaignID(), campaign.getCampaignName());
			m.addAttribute("campaignNames", campaignMap );

			List<Asset> assetList = yoanService.getAssetsByCampaignID(lead.getCampaign().getCampaignID());
			Map<Integer,String> assetMap = new TreeMap<Integer,String>();		
			for(Asset asset : assetList)
				assetMap.put(asset.getAssetID(), asset.getAssetName());
			m.addAttribute("assetNames", assetMap );
			
			lead.setAnswer(lead.getAnswer());
			lead.setAgent(lead.getAgent());
			m.addAttribute("teamLeads", yoanService.getAllTeamLeaders());
			m.addAttribute("updateLead",lead);
			return "updateLeadPageForAgent";
		}
		else
		{
			try
			{
				lead.setAgent(yoanService.getEmployeeById((lead.getAgent().getEmployeeId())));
				lead.setTeamLead(yoanService.getEmployeeById((lead.getTeamLead().getEmployeeId())));
				lead.setCampaign(yoanService.getCampaignByID(lead.getCampaign().getCampaignID()));
				lead.setAsset(yoanService.getAssetByID(lead.getAsset().getAssetID()));
				yoanService.update(lead);
				designModel(redirectAttributes);
			}
			catch(Exception e)
			{
				System.out.println("Catch Block : "+e);
			}
			return "redirect:/";
		}		
	}	
	@RequestMapping("/tl.updateEmployee")
	public String updateEmployee(@RequestParam("employeeId") int employeeId,Model m)
	{
		Employee employee = yoanService.getEmployeeById(employeeId);
		m.addAttribute("employeeId",employee.getEmployeeId());
		m.addAttribute("firstName",employee.getFirstName());
		m.addAttribute("lastName",employee.getLastName());
		m.addAttribute("designation",employee.getDesignation());
		m.addAttribute("updateEmployeeByTL",employee);
		return "updateEmployeeByTL";
	}
	@RequestMapping("/tl.updateUser")  
	public String updateEmployeeByTL(@ModelAttribute("updateEmployeeByTL") Employee employee,BindingResult result,Model m)
	{ 	
		if(result.hasErrors())
		{
			return "updateEmployeeByTL";
		}
		else
		{
			try
			{
				employee.setPassword(new BCryptPasswordEncoder().encode(employee.getPassword()));
				yoanService.update(employee);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			m.addAttribute("newuserbytl",new Employee());
			m.addAttribute("employeeList",yoanService.getAllEmployees());
			return "AddNewUserTL";
		}	
	}
		
	public void designModel(Model m) throws ParseException
	{
		String str;			
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		str = simpleDateFormat.format(new Date());
		m.addAttribute("fromDate",str);
		m.addAttribute("toDate",str);			
		Date parsed = simpleDateFormat.parse(str);
		m.addAttribute("leadList",yoanService.getAllLeadsByDate(employee.getEmployeeId(),employee.getDesignation(),parsed,parsed));
	}	
		
	public void designModel(RedirectAttributes redirectAttributes) throws ParseException
	{
		String str;			
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		str = simpleDateFormat.format(new Date());
		redirectAttributes.addFlashAttribute("fromDate",str);
		redirectAttributes.addFlashAttribute("toDate",str);			
		Date parsed = simpleDateFormat.parse(str);
		redirectAttributes.addFlashAttribute("leadList",yoanService.getAllLeadsByDate(employee.getEmployeeId(),employee.getDesignation(),parsed,parsed));
	}

	@RequestMapping("/layout")
	public String layout()
	{
		return "layout";
	}
	@RequestMapping("/login")
	public String login()
	{
		return "login";
	}
	@RequestMapping("/accessdenied")
	public String accessDenied(Model model)
	{
		model.addAttribute("errormessage", "Sorry, you do not have permission to view this page.");
		return "error page";
	}
}