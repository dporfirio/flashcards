import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {

	private static ArrayList<Stack> theStacks;

	public static void main(String[] args) throws IOException {
		theStacks = new ArrayList<Stack>();

		File file = new File("savefile.out");

		if (file.exists()) {
			try {
				FileInputStream filein = new FileInputStream("savefile.out");
				ObjectInputStream objectin = new ObjectInputStream(filein);

				theStacks = (ArrayList<Stack>) objectin.readObject();
				objectin.close();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		chooseAction();
	}

	public static void printCommands(String commandType) {
		if (commandType.equals("main")) {
			System.out.println("commands    -    prints the commands \n"
					+ "quiz        -    take flashcard quiz \n"
					+ "newstack    -    creates a new stack \n"
					+ "viewstacks  -    view all stacks \n"
					+ "editcards   -    edit the cards in a stack \n"
					+ "end         -    end the program" + "\n");
		} else if (commandType.equals("cardeditor")) {
			System.out.println("commands      -    prints these commands \n"
					+ "addcard       -    adds a new card \n"
					+ "removecard    -    removes card at specified index \n"
					+ "displaycards  -    displays all cards in order \n"
					+ "exit          -    back to main \n"
					+ "?cancel       -    cancels the addition of a card \n"
					+ "\n");
		}
	}

	public static void printStacks() {
		for (int i = 0; i < theStacks.size(); i++)
			System.out.println(theStacks.get(i).getSubject());
		System.out.println();
	}

	public static void newStack(String name) {
		theStacks.add(new Stack(name));
	}

	public static void chooseAction() {
		Scanner scanner = new Scanner(System.in);
		boolean end = false;
		while (!end) {
			System.out.print(">  ");
			String startCommand = scanner.next();

			if (startCommand.equals("commands"))
				printCommands("main");
			else if (startCommand.equals("quiz")) {
				printStacks();
				System.out.print("Stack to study: ");
				String stackName = scanner.next();
				System.out.println();

				int stackIndex = -1;
				for (int i = 0; i < theStacks.size(); i++) {
					if (stackName.equals(theStacks.get(i).getSubject()))
						stackIndex = i;
				}
				if (stackIndex > -1) {
					Quiz quiz = new Quiz(theStacks.get(stackIndex));
					quiz.study();
					theStacks.remove(stackIndex);
					theStacks.add(stackIndex, quiz.getUpdatedStack());
					System.out.println();
				} else {
					System.out.println("Stack doesn't exist");
					System.out.println();
				}

			} else if (startCommand.equals("newstack")) {
				System.out.print("Name of stack: ");
				String stackName = scanner.next();
				newStack(stackName);
				System.out.println();
			} else if (startCommand.equals("editcards")) {
				printStacks();
				if (theStacks.size() > 0) {
					System.out.print("Which stack would you like to edit? ");
					String stackToEdit = scanner.next();
					int stackIndex = -1;
					for (int i = 0; i < theStacks.size(); i++) {
						if (stackToEdit.equals(theStacks.get(i).getSubject()))
							stackIndex = i;
					}
					if (stackIndex > -1) {
						System.out.println();
						editStack(stackToEdit);
					} else {
						System.out.println("Stack " + stackToEdit
								+ " does not exist");
						System.out.println();
					}
				} else
					System.out.println("Currently no stacks to edit");
			} else if (startCommand.equals("viewstacks")) {
				if (theStacks.size() > 0)
					printStacks();
				else {
					System.out.println("<<Stack bin is empty>>");
					System.out.println();
				}
			} else if (startCommand.equals("removestack")) {
				System.out.print("Which stack would you like to remove? ");
				String stackToRemove = scanner.next();
				int stackIndex = -1;
				for (int i = 0; i < theStacks.size(); i++) {
					if (stackToRemove.equals(theStacks.get(i).getSubject()))
						stackIndex = i;
				}
				if (stackIndex > -1) {
					System.out.println();
					theStacks.remove(stackIndex);
				} else {
					System.out.println("Stack " + stackToRemove
							+ " does not exist");
					System.out.println();
				}
			} else if (startCommand.equals("end")) {
				end = true;
				try {
					File myFile = new File("savefile.out");
					FileOutputStream fileout = new FileOutputStream(myFile);
					ObjectOutputStream objectout = new ObjectOutputStream(
							fileout);

					objectout.writeObject(theStacks);
					objectout.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
				System.out.println("\nInvalid command");
		}
	}

	private static void editStack(String stackToEdit) {
		System.out.println("Card editor: " + stackToEdit);
		printCommands("cardeditor");
		Scanner scanner = new Scanner(System.in);
		boolean end = false;

		while (!end) {
			System.out.print(">  ");
			String editCommand = scanner.nextLine();

			if (editCommand.equals("addcard")) {
				System.out.print("Front: ");
				String front = scanner.nextLine();

				if (!front.equals("?cancel")) {
					System.out.print("Back: ");
					String back = scanner.nextLine();

					if (!back.equals("?cancel")) {
						int stackIndex = -1;
						for (int i = 0; i < theStacks.size(); i++) {
							if (stackToEdit.equals(theStacks.get(i)
									.getSubject()))
								stackIndex = i;
						}
						theStacks.get(stackIndex).addCard(front, back);
						System.out.println("Card added");
					}
				}
				System.out.println();
			} else if (editCommand.equals("removecard")) {
				int stackIndex = -1;
				for (int i = 0; i < theStacks.size(); i++) {
					if (stackToEdit.equals(theStacks.get(i).getSubject()))
						stackIndex = i;
				}
				Stack temp = theStacks.get(stackIndex);
				temp.displayAllCards();

				System.out.print("Index of card to be removed: ");
				String stringIndex = scanner.nextLine();
				int index = Integer.parseInt(stringIndex);
				theStacks.get(stackIndex).removeCard(index);
				System.out.println();
			} else if (editCommand.equals("displaycards")) {
				int stackIndex = -1;
				for (int i = 0; i < theStacks.size(); i++) {
					if (stackToEdit.equals(theStacks.get(i).getSubject()))
						stackIndex = i;
				}
				Stack temp = theStacks.get(stackIndex);
				temp.displayAllCards();
			} else if (editCommand.equals("exit")) {
				System.out.println();
				System.out.println("Editor closed");
				end = true;
			} else
				System.out.println("\nInvalid command");
		}
	}

}