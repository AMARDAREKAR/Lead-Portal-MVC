package yoan.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name="question_tbl")
public class Question {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int questionID;
	private String question;
	private int assetID;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn(name ="questionID")
	@Valid
	private List<Option> optionID;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="questionID")
	@Valid
	private Answer answerID;

	public int getAssetID() {
		return assetID;
	}
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	public int getQuestionID() {
		return questionID;
	}
	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}