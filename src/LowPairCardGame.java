import java.util.*;

public class LowPairCardGame {
    public static int numPlayers;
    public static List<Hand> playerHands = new ArrayList<>();
    public static int currentDealerNum;
    public static int currentPlayer = 0;
    public static Deck deck = new Deck(true);
    public static Deck drawPile = new Deck();
    public static Deck discardPile = new Deck();
    private static int[] playerScores;
    public static boolean gameComplete = false;
    public static boolean handComplete = false;

    public static void main(String[] args) {
        getPlayerNumbers();
        do {
            startGame();
            // Set the current player to the one after the dealer. DO NOT USE AFTER GAME START
            if (currentDealerNum + 1 > numPlayers) {
                currentPlayer = currentDealerNum + 1;
            } else {
                currentPlayer = 0;
            }
            do {
                // START OF HAND
                // Get the current player's hand and print
                Hand currentHand = playerHands.get(currentPlayer);
                if (currentPlayer == 0) {
                    System.out.println("Your current hand is " + currentHand.toString());
                    userTurn(currentHand);
                } else {
                    aiTurn(currentHand);
                }
                // End of hand
                if (currentPlayer < numPlayers) {
                    currentPlayer++;
                } else {
                    currentPlayer = 0;
                }
            } while (!handComplete);
        } while (!gameComplete);
    }

    private static void getPlayerNumbers() {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Enter the number of players (3-6): ");
            if (scanner.hasNextInt()) {
                numPlayers = scanner.nextInt();
                if (numPlayers < 3 || numPlayers > 6) {
                    System.out.println("Invalid number of players. Please enter a number between 3 and 6.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 3 and 6.");
            }
            scanner.nextLine();
        } while (numPlayers < 3 || numPlayers > 6);

        // Initialize the playerScores array with zeros
        playerScores = new int[numPlayers];
        Arrays.fill(playerScores, 0);
    }

    private static void aiTurn(Hand currentHand) {
        System.out.println("The top card in the discard pile is the " + discardPile.getCards().get(discardPile.getNumRemainingCards() - 1));
        // DEBUG TEMP
        System.out.printf("AI %d hand is " + currentHand.toString(), currentPlayer + 1);


    }

    private static void userTurn(Hand currentHand) {
        //Display first card in discard pile
        System.out.println("The top card in the discard pile is the " + discardPile.getCards().get(discardPile.getNumRemainingCards() - 1));

        Scanner scanner = new Scanner(System.in);
        int pileChoice = 0;
        do {
            System.out.println("Type 1 to take from the discard pile, or 2 to take from the draw pile");
            if (scanner.hasNextInt()) {

                pileChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character left by nextInt()
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
                scanner.next(); // Consume the invalid input
            }
        } while (pileChoice != 1 && pileChoice != 2);
        Card newCard;
        if (pileChoice == 1) {
            newCard = discardPile.drawCard();
            currentHand.addCard(newCard);
        } else {
            newCard = drawPile.drawCard();
            currentHand.addCard(newCard);
        }
        System.out.println("You picked up: " + newCard);
        currentHand.sortAscending();
        System.out.println("Your hand is now: " + currentHand);

        // Get the discard selection from the user and discard
        System.out.println("Choose the card you want to discard by typing the number of it's position (1-8): ");
        int cardSelection = -1;
        while (true) {
            System.out.print("Enter the slot number (1-8) of the card you want to discard: ");

            if (scanner.hasNextInt()) {
                cardSelection = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (cardSelection >= 1 && cardSelection <= 8) {
                    break; // Valid selection, exit the loop
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 8.");
                }
            } else {
                scanner.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
            }
        }
        if (cardSelection <= currentHand.getNumCards()) {
            Card discardedCard = currentHand.getCards().get(cardSelection - 1); // Adjust index to 0-based
            currentHand.removeCard(discardedCard);
            discardPile.addCardToDiscardPile(discardedCard); // Add the discarded card to the discard pile
            System.out.println("You discarded the " + discardedCard);
            System.out.println(isHandComplete(currentHand));

        } else {
            System.out.println("Invalid selection. Please choose a valid card slot.");
        }
        System.out.println("Your hand is now " + currentHand);
        System.out.println("END OF TURN");

        System.out.println(currentPlayer);
    }

    private static boolean isHandComplete(Hand currentHand) {

        return false;
    }

    private static void startGame() {
        int currentDealerNum = numPlayers - 1; // Set the current dealer to the num of players so the player (0) always starts

        // Create and shuffle the deck
        deck.shuffle();

        // Create draw pile and discard pile
        drawPile.getCards().addAll(deck.getCards());

        // Deal 7 cards to each player's hand
        for (int i = 0; i < numPlayers; i++) {
            Hand playerHand = new Hand();
            for (int j = 0; j < 7; j++) {
                Card drawnCard = drawPile.drawCard();
                if (drawnCard != null) {
                    playerHand.addCard(drawnCard);
                } else {
                    System.out.println("Not enough cards in the draw pile to deal to all players.");
                    return;
                }
            }
            playerHands.add(playerHand);
        }
        displayAllHands(playerHands, numPlayers);

        // Set the discard pile
        Card drawnCard = drawPile.drawCard();
        if (drawnCard != null) {
            discardPile.getCards().add(drawnCard);
        } else {
            System.out.println("Not enough cards in the draw pile to deal to all players.");
            return;
        }
    }

    private static void displayAllHands(List<Hand> playerHands, int numPlayers) {
        // Sort each player's hand in ascending order with Ace low
        for (Hand playerHand : playerHands) {
            playerHand.sortAscending();
        }

        // Print each player's sorted hand
//        for (int i = 0; i < numPlayers; i++) {
//            Hand playerHand = playerHands.get(i);
//            System.out.println("Player " + (i + 1) + "'s sorted hand: " + playerHand);
//        }
    }


 /*   private static boolean isHandComplete(Hand hand) {
        List<Card> cards = hand.getCards();
        // Sort the cards in ascending order of face value
        Collections.sort(cards, new CardComparator());

        // Create a copy of the original hand to modify during backtracking
        List<Card> handCopy = new ArrayList<>(cards);

        // Helper method to handle backtracking with a list to track used cards
        List<Card> usedCards = new ArrayList<>();
        return findCompleteHand(handCopy, usedCards, null);
    }

    private static boolean findCompleteHand(List<Card> cards, List<Card> usedCards, Card.Value lowPairValue) {
        if (cards.isEmpty()) {
            return true; // Hand is complete
        }

        // Use a HashSet to keep track of tried combinations
        Set<List<Card>> triedCombinations = new HashSet<>();

        // Check if there is a complete run (consecutive face values of the same suit)
        int numCards = cards.size();
        for (int i = 0; i < numCards; i++) {
            List<Card> currentRun = new ArrayList<>();
            currentRun.add(cards.get(i));
            cards.remove(i);

            int j = 0;
            while (j < cards.size()) {
                Card currentCard = cards.get(j);
                if (currentCard.getCardSuit() == currentRun.get(0).getCardSuit() &&
                        currentCard.getCardValue().ordinal() == currentRun.get(currentRun.size() - 1).getCardValue().ordinal() + 1) {
                    currentRun.add(currentCard);
                    cards.remove(j);
                } else {
                    j++;
                }
            }

            if (currentRun.size() >= 3) {
                if (triedCombinations.add(currentRun) && findCompleteHand(cards, usedCards, lowPairValue)) {
                    return true;
                }
            }

            cards.addAll(i, currentRun);
        }

        // Check if there is a complete set (pairs of at least 3 cards of the same face value)
        for (int i = 0; i < cards.size() - 2; i++) {
            List<Card> currentSet = new ArrayList<>();
            currentSet.add(cards.get(i));
            cards.remove(i);

            for (int j = i; j < cards.size(); j++) {
                Card currentCard = cards.get(j);
                if (currentCard.getCardValue() == currentSet.get(0).getCardValue()) {
                    currentSet.add(currentCard);
                }
            }

            if (currentSet.size() >= 3) {
                if (triedCombinations.add(currentSet) && findCompleteHand(cards, usedCards, lowPairValue)) {
                    return true;
                }
            }

            cards.addAll(i, currentSet);
        }

        // Check if there is a low pair as a wild card
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getCardValue() == cards.get(i + 1).getCardValue()) {
                Card.Value pairValue = cards.get(i).getCardValue();
                List<Card> lowPair = new ArrayList<>();
                lowPair.add(cards.get(i));
                lowPair.add(cards.get(i + 1));
                cards.remove(i + 1);
                cards.remove(i);

                // Check if low pair can be used as a wild card to complete a run or set
                if (lowPairValue == null || pairValue == lowPairValue) {
                    if (triedCombinations.add(lowPair) && findCompleteHand(cards, usedCards, pairValue)) {
                        return true;
                    }
                }

                cards.addAll(i, lowPair);
            }
        }

        return false; // No valid runs, sets, or low pair as wild card found
    }



    private static boolean hasLowerValueCards(List<Card> cards, Card.Value value) {
        for (Card c : cards) {
            if (c.getCardValue().getRank() < value.getRank()) {
                return true; // Found a card with a lower face value
            }
        }
        return false; // No card with a lower face value found
    }


    public static class CardComparator implements Comparator<Card> {
        @Override
        public int compare(Card card1, Card card2) {
            // Compare the values of the two cards
            int rankComparison = Integer.compare(card1.getCardValue().getRank(), card2.getCardValue().getRank());

            if (rankComparison != 0) {
                return rankComparison; // If the ranks are different, return the comparison result
            }

            // If the ranks are the same, compare the suits
            return card1.getCardSuit().compareTo(card2.getCardSuit());
        }
    }*/
}