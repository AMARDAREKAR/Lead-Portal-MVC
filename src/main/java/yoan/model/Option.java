package yoan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="option_tbl")
public class Option {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int optionID;
	private String optionName;
	private int questionID;
	
	public int getOptionID() {
		return optionID;
	}
	public void setOptionID(int optionID) {
		this.optionID = optionID;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}
}
