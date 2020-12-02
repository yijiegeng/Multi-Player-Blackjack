package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand{
    // All hand cards of the player.
    private final List<Card> cards = new ArrayList<>();

    // Method1: add different number of cards into hand cards.
    public void addCards(Card[] list){
        Collections.addAll(cards, list);
    }

    // Method2: get the score of current hand cards.
    public int score(){
        List<Integer> scores = possibleScores();
        int maxUnder = Integer.MIN_VALUE;
        int minOver = Integer.MAX_VALUE;
        for(int score : scores){
            if(score > 21) minOver = Math.min(minOver, score);
            else maxUnder = Math.max(maxUnder, score);
        }
        return maxUnder == Integer.MIN_VALUE ? minOver : maxUnder;
    }

    // helper function: calculate the possible score of current hand cards.
    private List<Integer> possibleScores(){
        List<Integer> scores = new ArrayList<>();
        dfsSearch(scores, 0, 0);
        return scores;
    }

    // helper function: Using DFS to get the all possible score.
    private void dfsSearch(List<Integer> ans, int curr, int level){
        if(level == cards.size()){
            ans.add(curr);
            return;
        }
        Card card = cards.get(level);
        int[] toAdd = getScores(card);
        for(int val : toAdd){
            dfsSearch(ans, curr + val, level + 1);
        }
    }

    // helper function: get the score of each card.
    private int[] getScores(Card card){
        if(card.value() > 1) return new int[]{Math.min(card.value(), 10)};
        else return new int[]{1, 11};
    }

    // Method3: Determine whether the hand score is busted.
    public boolean busted(){
        return score() > 21;
    }

    // Method3: Determine whether the hand score is BlackJack.
    public boolean isBlackJack(){
        if(cards.size() != 2) return false;
        Card first = cards.get(0);
        Card second = cards.get(1);
        return (isAce(first) && isTen(second))
                || (isAce(second) && isTen(first));
    }

    // helper function: Determine whether the card is Ace.
    private boolean isAce(Card card){
        return card.value() == 1;
    }

    // helper function: Determine whether the card's score is 10.
    private boolean isTen(Card card){
        return card.value() >= 10 && card.value() <= 13;
    }

    // helper function: print hand cards' score.
    public void print(boolean isDealer){
        if(isDealer) System.out.print(cards.get(0).value() + " ");
        else{
            for(int i = 0; i < cards.size(); i++)
                System.out.print(cards.get(i).value() + " ");
        }
    }
}

