import java.util.Scanner;

public class Practice {

	private Card practiceCard;
	private boolean end;
	private Grader grader;

	public Practice(Card practiceCard) {
		this.practiceCard = practiceCard;
		end = false;
	}

	public void practice() {
		while (!end) {
			System.out
					.println("*****************************************************");
			System.out
					.println("*****************************************************");
			Scanner scanner = new Scanner(System.in);
			System.out.println("FRONT: " + practiceCard.getFront());
			String answer = scanner.nextLine();

			if (answer.equals("?exit")) {
				end = true;
			}

			else {
				grader = new Grader(4);
				System.out.println(practiceCard.getBack());
				String grade = grader.calculateCorrectness(answer,
						practiceCard.getBack(), practiceCard.getOtherBacks());
				System.out.println(grade);
				System.out.print(">> ");
				String furtherAnswer = scanner.nextLine();
				if (furtherAnswer.equals("exit")) {
					end = true;
				}
			}
			System.out
					.println("*****************************************************");
			System.out
					.println("*****************************************************");
		}
	}
}
