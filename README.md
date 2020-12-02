# Multi-Player Blackjack

## Description:

This is a gambling game [Blackjack](https://en.wikipedia.org/wiki/Blackjack). It support a programmatic dealer and 6 players (1 human player with 5 programmatically controlled ones) using only text-based user-interface.

## Assumptions:

There are 4 assumptions of my design: 

* Each player in turn competes against the dealer and players do not compete against each other.
* Only one deck of cards is used in each game, which is 52 cards in total.
* Dealer and other programmatically controlled player always follow a strategy: they must hit (take another card from the dealer) until the cards total up to a hit parameter, which is 17 points in my case. When reaching up to hit parameter points or higher, they must stay.
* **No** “Split”, “Double” or “Surrender” options in this game. Player including dealer only choose “Hit” or “Stay”.

 

## Class and data structure:

According to the basic rule of blackjack, I designed four java classes and one java enum to implement the game. Four classes are:

* **Card**: used to represent ordinary playing cards which has face value and suit. Number 1 to 13 represent all of the face values. (special cases: 1 represents A, 11 represents J, 12 represents Q, 13 represents K)

* **Deck**: used to represent the status and actions of 52 cards used in the game. 

   Fields: “list of cards” (represent all cards) and “dealt index” (used to separate used and unused cards).

   Methods: “shuffle”, “dealt hand”, “dealt card” and “get the number of remaining cards”.

* **Hand**: used to represent the status and actions of the player’ hand cards. 

   * Fields: “list of hand cards” (represent hand cards of this player).

   * Methods: “add cards”, “score”, “is busted” and “is blackjack”.

* **Game**: used to simulate the Blackjack game flow. 

   * Fields: “deck”, “array of hands”, “hit parameter for programmatically controlled player”.

   * Methods: “initialize deck”, “initial dealt”, “play one round”, “play all hands”, “get blackjacks”, “get      winner” and “simulate (run the game)”.

(I explained the function and details of all methods in the code comments.)

One enum represent the suit of each card (club, diamond, heart, spade). This is optional and it’s used for scalability.

 

## Simulation of game process:

At the beginning, players are each dealt two cards, face up (exposed). The dealer is also dealt two cards, one up (exposed) and one down (hidden). If there are enough remaining cards in the deck, and the human player’s hand is not busted, the player can choose “hit” or “stay” by typing “y” or “n” in the console. The hands and scores of all players in each round will be displayed in the console. The game will end when the human player’s hand is busted, or the player choose “stay”.

If the dealer's hand is busted, then all players with no busted hand win. Otherwise, all players with higher score than the dealer’s win. 

If the player's two hands are "A" and a card with a face value of 10, it is called "blackjack". If the dealer and the player are both “blackjacks”, it is called "push", no one wins.