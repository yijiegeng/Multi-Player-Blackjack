package blackjack;

import java.util.*;

/**
 *  Simulate the Blackjack game flow.
 */
public class Game{
    // All cards in game.
    private Deck deck;
    // Hand cards of each player and dealer.
    private Hand[] hands;
    private final int HIT_UNTIL = 17;
    public Game(int numPlayers){
        hands = new Hand[numPlayers + 1];
        for(int i = 0; i < hands.length; i++){
            hands[i] = new Hand();
        }
    }

    // Method1: generate a deck and shuffle it.
    void initializeDeck(){
        deck = new Deck();
        deck.shuffle();
    }

    // Method2: dealt two cards for each player and dealer.
    boolean initialDealt(){
        for(Hand hand : hands){
            Card[] cards = deck.dealtHand(2);
            if(cards == null) return false;
            hand.addCards(cards);
        }
        return true;
    }

    // Method3: simulate one round of the game.
    // this method will be called when Human player hasn't determined hand cards.
    boolean playOneRound(){
        for(int i = 1; i < hands.length; i++){
            Hand hand = hands[i];
            if(i == 1 || hand.score() < HIT_UNTIL){
                Card card = deck.dealtCard();
                if(card == null) return false;
                hand.addCards(new Card[]{card});
            }
        }
        return true;
    }

    // Method4: simulate rest rounds of the game.
    // this method will be called when Human player's hands card is determined.
    boolean playAllHands(){
        for(int i = 0; i < hands.length; i++){
            if(i == 1) continue;
            Hand hand = hands[i];
            while(hand.score() < HIT_UNTIL){
                Card card = deck.dealtCard();
                if(card == null) return false;
                hand.addCards(new Card[]{card});
            }
        }
        return true;
    }

    // Method5: get the id of all players who got blackjack
    Set<Integer> getBlackJacks(){
        Set<Integer> winners = new HashSet<>();
        for(int i = 1; i < hands.length; i++){
            if(hands[i].isBlackJack()) winners.add(i);
        }
        return winners;
    }

    // Method6: get all winners.
    List<Integer> getWinners(){
        List<Integer> winners = new ArrayList<>();
        for(int i = 1; i < hands.length; i++){
            if(!hands[i].busted()){
                if(hands[0].busted()) winners.add(i);
                else{
                    int playerVal = hands[i].score();
                    int dealerVal = hands[0].score();
                    if(playerVal > dealerVal) winners.add(i);
                }
            }

        }
        return winners;
    }

    // helper function: print hand cards and their scores.
    void printHandsAndScore(boolean hidden){
        if(hidden){
            System.out.print("dealer(?): ");
            hands[0].print(true);
            System.out.println("*");
        }
        for(int i = 0; i < hands.length; i++){
            if(hidden && i == 0) continue;
            if(i == 0) System.out.print("dealer");
            else if(i == 1) System.out.print("you");
            else System.out.print("player" + i);
            System.out.print(" (" + hands[i].score() + "): ");
            hands[i].print(false);
            System.out.println();
        }
    }

    // Method6: simulate the entire game process.
    public void simulate(){
        // initial stage.
        initializeDeck();
        boolean success = initialDealt();
        if(!success){
            System.out.println("Error. Out of cards");
            return;
        }
        System.out.println("-- Initial --");
        printHandsAndScore(true);   // one of the dealer's hand card will be hidden.

        // Now each player get two hand cards, we can check the blackjacks.
        Set<Integer> blackjacks = getBlackJacks();
        if(blackjacks.size() > 0){
            System.out.print("Blackjack at: ");
            for(int i : blackjacks) System.out.print((i == 1 ? "you" : "player" + i) + " ");
            System.out.println();
        }

        // If human player's hand card is NOT determined,
        // the game will be played round by round.
        if(!blackjacks.contains(1)){
            int yourScore = hands[1].score();
            while(yourScore < 21){
                System.out.println("Hit?(y/n)");
                Scanner sc = new Scanner(System.in);
                char choice = sc.nextLine().charAt(0);
                if(choice == 'n') break;
                success = playOneRound();
                if(!success){
                    System.out.println("Error. Out of cards");
                    return;
                }
                yourScore = hands[1].score();
                System.out.println("-- another round --");
                printHandsAndScore(true);
            }
        }

        // If human player's hand card is determined,
        // The rest of the process will be completed directly.
        success = playAllHands();
        if(!success){
            System.out.println("Error. Out of cards");
            return;
        }
        System.out.println("\n-- Completed Game --");
        printHandsAndScore(false);  // dealer's hand card will be exposed.

        // Get the winners.
        List<Integer> winners = getWinners();
        if(winners.size() > 0){
            System.out.print("Winners: ");
            for(int i : winners) System.out.print((i == 1 ? "you" : "player"  + i) + " ");
            System.out.println();
        }
        else if(blackjacks.size() > 0) System.out.println("Push! No one wins");
        else System.out.println("Dealer win!");
    }

    // A simple test case.
    public static void main(String[] args) {
        Game g = new Game(6);
        g.simulate();
    }
}
