package yoan.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name="answer_tbl")
public class Answer 
{
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int answerID;
	private String answer;
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name ="leadID")	
	private Lead leadID;
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="questionID")
	@Valid
	private Question questionID;
	
	public int getAnswerID() {
		return answerID;
	}
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Question getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Question questionID) {
		this.questionID = questionID;
	}
	public Lead getLeadID() {
		return leadID;
	}
	public void setLeadID(Lead leadID) {
		this.leadID = leadID;
	}
}