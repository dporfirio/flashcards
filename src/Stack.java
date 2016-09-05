import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Stack implements Serializable {

	private String subject;
	private int size;
	private int nextCardOrder;
	private ArrayList<Card> cardStack;

	public Stack(String subject) {
		this.subject = subject;
		size = 0;
		cardStack = new ArrayList<Card>();
		nextCardOrder = 0;
	}

	public void addCard(String front, String back) {
		cardStack.add(new Card(front, back, nextCardOrder));
		size++;
		nextCardOrder++;
	}

	public void removeCard(int index) {
		cardStack.remove(index);
		size--;
	}

	public void displayAllCards() {
		for (int i = 0; i < size; i++) {
			int number = i + 1;
			System.out.println("Card " + number + ":");
			System.out.println("   " + cardStack.get(i).getFront());
			System.out.println("   " + cardStack.get(i).getBack());
		}
	}

	public String getSubject() {
		return subject;
	}

	public int getSize() {
		return cardStack.size();
	}

	public Card getCard(int index) {
		return cardStack.get(index);
	}

	public void shuffleDeck() {
		Collections.shuffle(cardStack);
	}

	public void reorderDeck() {
		Collections.sort(cardStack);
	}

}