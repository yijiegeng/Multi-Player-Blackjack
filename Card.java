package blackjack;

/**
 *  Represent playing card
 */
public class Card{
    private final int faceValue;
    private final Suit suit;

    public Card(int val, Suit s){
        faceValue = val;
        suit = s;
    }

    public int value(){
        return faceValue;
    }

    public Suit suit(){
        return suit;
    }
}
