package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  Represent all cards used in game.
 *  Only one deck of cards is used in this game, which is 52 cards in total.
 */
public class Deck{
    // All cards in game.
    private final List<Card> cards = new ArrayList<>();
    // All cards before dealtIndex is used, others are unused.
    private int dealtIndex = 0;

    public Deck(){
        for(int i = 1; i <= 13; i++){
            for(Suit suit : Suit.values()){
                cards.add(new Card(i, suit));
            }
        }
    }

    // Method1: shuffle the deck
    public void shuffle(){
        for(int i = 0; i < cards.size() - 1; i++){
            int j = ThreadLocalRandom.current().nextInt(i, cards.size());
            Card card1 = cards.get(i);
            Card card2 = cards.get(j);
            cards.set(i, card2);
            cards.set(j, card1);
        }
    }

    // Method2: initial dealt hand cards. (in general rules, number is 2)
    public Card[] dealtHand(int number){
        if(remainingCards() < number) return null;

        Card[] handCards = new Card[number];
        for(int i = 0; i < number; i++){
            handCards[i] = dealtCard();
        }
        return handCards;
    }

    // Method3: dealt other card after initialization.
    public Card dealtCard(){
        return remainingCards() == 0 ? null : cards.get(dealtIndex++);
    }

    // Method4: get the number of remaining cards
    private int remainingCards(){
        return cards.size() - dealtIndex;
    }
}

