public class Card {
    // Enum to represent the suits of the cards
    public enum Suit {
        Hearts, Clubs, Diamonds, Spades
    }

    // Enum to represent the values/ranks of the cards
    public enum Value {
        Ace(1),
        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(11),
        Queen(12),
        King(13);

        private final int rank;

        Value(int rank) {
            this.rank = rank;
        }

        public int getRank() {
            return rank;
        }
    }

    // Properties of the Card class
    private Suit cardSuit;
    private Value cardValue;
    private boolean isLowPair;

    // Constructor to create a new Card instance
    public Card(Suit suit, Value value, boolean isLowPair) {
        cardSuit = suit;
        cardValue = value;
        this.isLowPair = isLowPair;
    }

    // Getters for Card properties
    public Suit getCardSuit() {
        return cardSuit;
    }

    public Value getCardValue() {
        return cardValue;
    }

    public boolean isLowPair() {
        return isLowPair;
    }

    // Method to check if two cards have the same suit
    public boolean hasSameSuit(Card otherCard) {
        return cardSuit == otherCard.getCardSuit();
    }

    // Method to check if two cards have the same value/rank
    public boolean hasSameValue(Card otherCard) {
        return cardValue == otherCard.getCardValue();
    }
    @Override
    public String toString() {
        String suitStr = switch (cardSuit) {
            case Hearts -> "Hearts";
            case Clubs -> "Clubs";
            case Diamonds -> "Diamonds";
            case Spades -> "Spades";
        };

        String valueStr = switch (cardValue) {
            case Ace -> "Ace";
            case Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten -> String.valueOf(cardValue.getRank());
            case Jack -> "Jack";
            case Queen -> "Queen";
            case King -> "King";
        };

        return valueStr + " of " + suitStr;
    }
}
