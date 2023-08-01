import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    // Constructor to create an empty deck
    public Deck() {
        cards = new ArrayList<>();
    }

    // Constructor to create a standard deck of playing cards
    public Deck(boolean createStandardDeck) {
        cards = new ArrayList<>();

        if (createStandardDeck) {
            // Add all 52 standard playing cards to the deck
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Value value : Card.Value.values()) {
                    cards.add(new Card(suit, value, false));
                }
            }
        }
    }
    // Method to shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Method to draw a card from the deck
    public Card drawCard() {
        if (!isEmpty()) {
            return cards.remove(0);
        } else {
            return null;
        }
    }

    // Method to check if the deck is empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // Method to get the number of cards remaining in the deck
    public int getNumRemainingCards() {
        return cards.size();
    }
    // Method to get the list of cards in the deck (draw pile)
    public List<Card> getCards() {
        return cards;
    }
    // Method to add a card to the deck (discard pile)
    public void addCardToDiscardPile(Card card) {
        cards.add(card);
    }
    // Method to merge the discard pile back into the draw deck and shuffle
    public void mergeDiscardPile(Deck discardPile) {
        cards.addAll(discardPile.getCards());
        discardPile.getCards().clear();
        shuffle();
    }
}
