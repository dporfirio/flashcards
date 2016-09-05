import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GraderTest {

	String correctAnswer = "This is the correct answer.";
	String givenAnswer = "This is what was given.";
	ArrayList<String> otherAnswers = new ArrayList<String>();

	Grader grader = new Grader(4);

	@Test
	public void testGraderMainBack() {
		assertEquals("40%", grader.calculateCorrectness(givenAnswer,
				correctAnswer, new ArrayList<String>()));
	}

	@Test
	public void testGraderAlternateBacks() {
		otherAnswers.add("This is what wrong answer");
		assertEquals("60%", grader.calculateCorrectness(givenAnswer,
				correctAnswer, otherAnswers));
	}

}
