import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AIEvaluation {
    // Evaluate the AI player's hand and return a weighted score for each move
    public static int evaluateHand(Hand hand, Card topCard) {
        int score = 0;

        // Bonus points for low-value cards (Aces and Twos)
        for (Card card : hand.getCards()) {
            if (card.getCardValue() == Card.Value.Ace || card.getCardValue() == Card.Value.Two) {
                score += 5;
            }
        }

        // Bonus points for completing sets or runs
        List<Card> sortedHand = new ArrayList<>(hand.getCards());
        sortedHand.sort(Comparator.comparingInt(card -> card.getCardValue().getRank()));

        int consecutiveCardsCount = 1;
        for (int i = 0; i < sortedHand.size() - 1; i++) {
            if (sortedHand.get(i + 1).getCardValue().getRank() - sortedHand.get(i).getCardValue().getRank() == 1) {
                consecutiveCardsCount++;
            } else {
                consecutiveCardsCount = 1;
            }

            if (consecutiveCardsCount >= 3) {
                score += 10;
            }
        }

        // Bonus points for cards that can complete sets or runs
        for (Card card : hand.getCards()) {
            if (canCompleteSetOrRun(card, hand)) {
                score += 3;
            }
        }

        // Penalty for having too many high-value cards
        int highValueCount = 0;
        for (Card card : hand.getCards()) {
            if (card.getCardValue().getRank() >= 10) {
                highValueCount++;
            }
        }
        score -= highValueCount * 2;

        // Bonus points for drawing from the discard pile if it can complete a set or run
        if (topCard != null && canCompleteSetOrRun(topCard, hand)) {
            score += 5;
        }

        return score;
    }

    // Helper method to check if a card can complete a set or run in the hand
    private static boolean canCompleteSetOrRun(Card card, Hand hand) {
        // Implement your logic here to check if the card can complete a set or run in the hand
        // For example, you can check if the card can be used to complete a run of 3 consecutive cards or a set of 3 cards with the same face value
        // Return true if the card can complete a set or run, false otherwise
        return false;
    }
}
