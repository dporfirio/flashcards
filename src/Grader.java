import java.text.DecimalFormat;
import java.util.ArrayList;

public class Grader {

	private int difficulty;
	private DecimalFormat format;
	private ArrayList<String> correctWords;
	private ArrayList<String> alternateWords;
	private ArrayList<String> givenWords;

	public Grader(int difficulty) {
		this.difficulty = difficulty;
		format = new DecimalFormat("#%");
		correctWords = new ArrayList<String>();
		alternateWords = new ArrayList<String>();
		givenWords = new ArrayList<String>();

	}

	public String calculateCorrectness(String givenAnswer,
			String correctAnswer, ArrayList<String> otherBacks) {
		double correctness = calculateGivenCorrectness(givenAnswer,
				correctAnswer);
		double otherCorrectness = calculateOtherCorrectness(givenAnswer,
				otherBacks);

		if (correctness > otherCorrectness)
			return format.format(correctness);

		return format.format(otherCorrectness);
	}

	private double calculateGivenCorrectness(String givenAnswer,
			String correctAnswer) {
		int nextToSpace = 0;

		for (int i = 0; i < correctAnswer.length(); i++) {
			if (correctAnswer.charAt(i) == ' ') {
				correctWords
						.add(correctAnswer.substring(nextToSpace, i).trim());
				nextToSpace = i + 1;
			} else if (i == correctAnswer.length() - 1)
				correctWords.add(correctAnswer.substring(nextToSpace, i + 1)
						.trim());
		}

		givenAnswerToArray(givenAnswer);

		double correctCount = correctWords.size();
		double givenCorrect = 0;
		boolean alreadyFound = false;

		for (int i = 0; i < correctWords.size(); i++) {
			alreadyFound = false;
			for (int j = 0; j < givenWords.size(); j++) {
				if (correctWords.get(i).equals(givenWords.get(j))
						&& !alreadyFound) {
					givenCorrect++;
					givenWords.remove(j);
					givenWords.add("--removed--");
					alreadyFound = true;
				}
			}
		}

		double percent;

		if (correctWords.size() >= givenWords.size()) {
			percent = givenCorrect / correctCount;
		} else
			percent = givenCorrect / givenWords.size();

		return percent;
	}

	private double calculateOtherCorrectness(String givenAnswer,
			ArrayList<String> otherBacks) {

		double highestPercent = 0.0;
		double latestPercent = 0.0;

		for (int i = 0; i < otherBacks.size(); i++) {
			alternateWords = new ArrayList<String>();
			int nextToSpace = 0;

			for (int i1 = 0; i1 < otherBacks.get(i).length(); i1++) {
				if (otherBacks.get(i).charAt(i1) == ' ') {
					alternateWords.add(otherBacks.get(i)
							.substring(nextToSpace, i1).trim());
					nextToSpace = i1 + 1;
				} else if (i1 == otherBacks.get(i).length() - 1)
					alternateWords.add(otherBacks.get(i)
							.substring(nextToSpace, i1 + 1).trim());
			}

			givenWords = new ArrayList<String>();
			givenAnswerToArray(givenAnswer);

			double correctCount = alternateWords.size();
			double givenCorrect = 0;
			boolean alreadyFound = false;

			for (int i1 = 0; i1 < alternateWords.size(); i1++) {
				alreadyFound = false;
				for (int j = 0; j < givenWords.size(); j++) {
					if (alternateWords.get(i1).equals(givenWords.get(j))
							&& !alreadyFound) {
						givenCorrect++;
						givenWords.remove(j);
						givenWords.add("--removed--");
						alreadyFound = true;
					}
				}
			}

			if (alternateWords.size() >= givenWords.size()) {
				latestPercent = givenCorrect / correctCount;
			} else
				latestPercent = givenCorrect / givenWords.size();

			if (latestPercent > highestPercent)
				highestPercent = latestPercent;
		}

		return highestPercent;
	}

	private void givenAnswerToArray(String givenAnswer) {
		int nextToSpace = 0;

		for (int i = 0; i < givenAnswer.length(); i++) {
			if (givenAnswer.charAt(i) == ' ') {
				givenWords.add(givenAnswer.substring(nextToSpace, i));
				nextToSpace = i + 1;
			} else if (i == givenAnswer.length() - 1)
				givenWords.add(givenAnswer.substring(nextToSpace, i + 1));
		}
	}

}
