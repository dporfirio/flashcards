import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable, Comparable<Card> {

	private String front;
	private String back;
	private ArrayList<String> otherBacks;
	private int order;
	private int difficulty;

	public Card(String front, String back, int order) {
		this.front = front;
		this.back = back;
		this.order = order;
		otherBacks = new ArrayList<String>();
		difficulty = 3;
	}

	public String getFront() {
		return front;
	}

	public String getBack() {
		return back;
	}

	public int getCardOrder() {
		return order;
	}

	public void setCardOrder(int order) {
		this.order = order;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public void setOtherBack(String otherBack) {
		otherBacks.add(otherBack);
	}

	public ArrayList<String> getOtherBacks() {
		return otherBacks;
	}

	public int compareTo(Card toCompare) {
		if (getCardOrder() < toCompare.getCardOrder())
			return -1;
		else if (getCardOrder() > toCompare.getCardOrder())
			return 1;
		else
			return 0;
	}
}