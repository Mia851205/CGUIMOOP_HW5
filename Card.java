class Card {
	private int rank; // 1~13

	public enum Suit {
		Club, Diamond, Heart, Spade
	};

	private Suit suit;

	public Card(Suit s, int values) {
		suit = s;
		rank = values;
	}

	public void printCard() {
		System.out.println(suit + "," + rank);
	}

	public Suit getSuit() {
		return suit;
	}

	public int getRank() {
		return rank;
	}
}