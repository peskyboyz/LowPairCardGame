import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Hand {
    private List<Card> cards;

    // Constructor to create an empty hand
    public Hand() {
        cards = new ArrayList<>();
    }

    // Method to add a card to the hand
    public void addCard(Card card) {
        cards.add(card);
    }

    // Method to remove a card from the hand
    public void removeCard(Card card) {
        cards.remove(card);
    }

    // Method to check if the hand contains a specific card
    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    // Method to get the list of cards in the hand
    public List<Card> getCards() {
        return cards;
    }

    // Method to check if the hand is empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Method to get the number of cards in the hand
    public int getNumCards() {
        return cards.size();
    }

    public void sortAscending() {
        cards.sort(Comparator.comparingInt(card -> card.getCardValue().getRank()));
    }
    // Method to convert the Hand object to a string representation
    @Override
    public String toString() {
        StringBuilder handStr = new StringBuilder();
        for (Card card : cards) {
            handStr.append(card.toString()).append(", ");
        }
        if (handStr.length() > 2) {
            handStr.delete(handStr.length() - 2, handStr.length());
        }
        return "[" + handStr + "]";
    }
}
