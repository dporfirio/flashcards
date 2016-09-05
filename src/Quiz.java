import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz {

	private Stack quizStack;
	private Grader grader;
	private int diffCap;
	private boolean exit;
	private Practice practice;

	public Quiz(Stack quizStack) {
		this.quizStack = quizStack;
		grader = new Grader(4);
		diffCap = 1;
		exit = false;
	}

	public void study() {
		Scanner scanner = new Scanner(System.in);
		exit = false;
		int index = 0;

		System.out.print("Diffcap: ");
		int initialDiffCap = Integer.parseInt(scanner.nextLine());
		diffCap = initialDiffCap;
		System.out.print("Shuffle ('y' for yes): ");
		String shuffle = scanner.nextLine();
		if (shuffle.equals("y"))
			quizStack.shuffleDeck();
		else
			quizStack.reorderDeck();

		while (!exit) {
			if (index == quizStack.getSize())
				index = 0;

			if (quizStack.getCard(index).getDifficulty() >= diffCap) {

				System.out.print("LEV "
						+ quizStack.getCard(index).getDifficulty() + ": ");
				System.out.println(quizStack.getCard(index).getFront());
				String userAnswer = scanner.nextLine();
				if (userAnswer.equals("?exit"))
					exit = true;
				else if (userAnswer.equals("?practice")) {
					practice = new Practice(quizStack.getCard(index));
					practice.practice();
				} else {
					grader = new Grader(4);
					String grade = grader.calculateCorrectness(userAnswer,
							quizStack.getCard(index).getBack(), quizStack
									.getCard(index).getOtherBacks());
					System.out.println(quizStack.getCard(index).getBack());
					System.out.println(grade);
					System.out.print(">> ");
					String furtherAnswer = scanner.nextLine();

					while (!furtherAnswer.equals("")) {
						furtherAnswer(furtherAnswer, index, userAnswer);
						System.out.print(">> ");
						furtherAnswer = scanner.nextLine();
					}

					System.out.println();
					index++;

					if (index == quizStack.getSize()) {
						endQuiz();
						// System.out.println("Quiz ended.");
						// System.out.println("Enter 'y' to update cards and take the quiz again.");
						// System.out.println("Enter 'n' to take the quiz again without any updates.");
						// System.out.println("Enter any other key to end the quiz session.");
						// System.out.print("> ");
						// String quizOver = scanner.nextLine();
						// if (quizOver.equals("y")) {
						// System.out.print("Diffcap: ");
						// int newDiffCap =
						// Integer.parseInt(scanner.nextLine());
						// diffCap = newDiffCap;
						// System.out.print("Shuffle ('y' for yes): ");
						// String newShuffle = scanner.nextLine();
						// if (newShuffle.equals("y"))
						// quizStack.shuffleDeck();
						// else
						// quizStack.reorderDeck();
						// }
						// else if (quizOver.equals("n"))
						// System.out.println("Taking quiz again, no updates");
						// else {
						// System.out.println("Quiz exited");
						// exit = true;
						// }
						// System.out.println();
					}
				}
			} else {
				System.out.println();
				index++;

				if (index == quizStack.getSize()) {
					endQuiz();
				}
			}
		}
	}

	private void furtherAnswer(String furtherAnswer, int index,
			String userAnswer) {
		if (furtherAnswer.equals("commands")) {
			printCommands();
		} else if (furtherAnswer.length() > 7
				&& furtherAnswer.substring(0, 6).equals("diffto")) {
			changeDiff(furtherAnswer, index);
		} else if (furtherAnswer.equals("validanswer")) {
			System.out.println("Setting as valid answer");
			quizStack.getCard(index).setOtherBack(userAnswer);
		} else if (furtherAnswer.equals("newvalidanswer")) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Back: ");
			String newBack = scanner.nextLine();
			quizStack.getCard(index).setOtherBack(newBack);
		} else if (furtherAnswer.equals("printanswers")) {
			System.out.println(quizStack.getCard(index).getBack());
			for (int i = 0; i < quizStack.getCard(index).getOtherBacks().size(); i++) {
				System.out.println(quizStack.getCard(index).getOtherBacks()
						.get(i));
			}
		}
	}

	private void changeDiff(String intConvert, int index) {
		int changeTo = 0;
		try {
			changeTo = Integer.parseInt(intConvert.substring(
					intConvert.length() - 1, intConvert.length()));
		} catch (RuntimeException e) {
			System.out.println("Unable to parse int");
		}
		if (changeTo > 0 && changeTo < 4) {
			quizStack.getCard(index).setDifficulty(changeTo);
			System.out.println("Difficulty changed to " + changeTo);
		} else
			System.out.println("Not a valid difficulty");
	}

	public Stack getUpdatedStack() {
		return quizStack;
	}

	private void printCommands() {
		System.out.println("commands       -    prints the commands \n"
				+ "printanswers   -    print all the acceptable answers \n"
				+ "diffto         -    change difficulty of card \n"
				+ "validanswer    -    set submitted answer as valid \n"
				+ "newvalidanswer -    create a new valid answer \n"
				+ "exit           -    end the quiz \n"
				+ "?exit          -    exit during an answer" + "\n");
	}

	private void endQuiz() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Quiz ended.");
		System.out
				.println("Enter 'y' to update cards and take the quiz again.");
		System.out
				.println("Enter 'n' to take the quiz again without any updates.");
		System.out.println("Enter any other key to end the quiz session.");
		System.out.print("> ");
		String quizOver = scanner.nextLine();
		if (quizOver.equals("y")) {
			System.out.print("Diffcap: ");
			int newDiffCap = Integer.parseInt(scanner.nextLine());
			diffCap = newDiffCap;
			System.out.print("Shuffle ('y' for yes): ");
			String newShuffle = scanner.nextLine();
			if (newShuffle.equals("y"))
				quizStack.shuffleDeck();
			else
				quizStack.reorderDeck();
		} else if (quizOver.equals("n"))
			System.out.println("Taking quiz again, no updates");
		else {
			System.out.println("Quiz exited");
			exit = true;
		}
		System.out.println();
	}

}