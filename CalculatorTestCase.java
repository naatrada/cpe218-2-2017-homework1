
public class CalculatorTestCase {
	String question;
	String answer;
	
	public CalculatorTestCase(String question, String answer) {
		this.setQuestion(question);
		this.setAnswer(answer);
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
