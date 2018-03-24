package com.rudakevych.blackJack.terminalVersion;

import java.util.ArrayList;
import java.util.Random;

public class Shoe {

    private ArrayList<Card> cards;

    public Shoe() {
        this.cards = new ArrayList<>();
    }

    public Card getCardFromShoe(int i) {
        return this.cards.get(i);
    }

    /**
     * Simple toString method
     *
     * @return list of Card
     */
    public String toString() {
        String cardList = "";
        int i = 0;
        for (Card card : this.cards) {
            cardList = cardList + "\n" + card.toString();
            i++;
        }
        return cardList;
    }

    /**
     * Generating 52 cards (Shoe)
     */
    public void generating52Cards() {
        for (Suit suiteOfCards : Suit.values()) {
            for (Value valueOfCards : Value.values()) {
                this.cards.add(new Card(suiteOfCards, valueOfCards));
            }
        }
    }

    /**
     * Creating a random shoe (card/value)
     */
    public void shuffle() {
        ArrayList<Card> shuffledCards = new ArrayList<>();
        Random random = new Random();

        int randomCardIndex = 0;

        for (int i = 0; i < cards.size(); i++) {
            randomCardIndex = random.nextInt((this.cards.size() - 1 - 0) + 1) + 0;
            shuffledCards.add(this.cards.get(randomCardIndex));
            this.cards.remove(randomCardIndex);
        }
        this.cards = shuffledCards;
    }

    public void removeCardFromTheShoe(int i) {
        this.cards.remove(i);
    }

    public void addCardToShoe(Card addedCard) {
        this.cards.add(addedCard);
    }

    public void giveACardFromShoe(Shoe ourShoe) {
        // add card to this Shoe (what we using now)
        this.cards.add(ourShoe.getCardFromShoe(0));
        // remove card from old Shoe
        ourShoe.removeCardFromTheShoe(0);
    }

    public void moveAllToShoe(Shoe cardList) {
        int cardListSize = this.cards.size();
        for (int i = 0; i < cardListSize; i++) {
            cardList.addCardToShoe(this.getCardFromShoe(i));
        }
        for (int i = 0; i < cardListSize; i++) {
            this.removeCardFromTheShoe(0);
        }
    }

    public int shoeSize() {
        return this.cards.size();
    }

    /**
     * What value this shoe has
     *
     * @return
     */
    public int cardsValue() {
        int cardValue = 0;
        int acesNumbers = 0;
        //For every card in the deck
        for (Card aCard : this.cards) {
            switch (aCard.getValue()) {
                case TWO:
                    cardValue = cardValue + 2;
                    break;
                case THREE:
                    cardValue = cardValue + 3;
                    break;
                case FOUR:
                    cardValue = cardValue + 4;
                    break;
                case FIVE:
                    cardValue = cardValue + 5;
                    break;
                case SIX:
                    cardValue = cardValue + 6;
                    break;
                case SEVEN:
                    cardValue = cardValue + 7;
                    break;
                case EIGHT:
                    cardValue = cardValue + 8;
                    break;
                case NINE:
                    cardValue = cardValue + 9;
                    break;
                case TEN:
                    cardValue = cardValue + 10;
                    break;
                case JACK:
                    cardValue = cardValue + 10;
                    break;
                case QUEEN:
                    cardValue = cardValue + 10;
                    break;
                case KING:
                    cardValue = cardValue + 10;
                    break;
                case ACE:
                    acesNumbers = acesNumbers + 1;
                    break;
            }
        }

        /**
         * Set aces value (1 or 11)
         */
        for (int i = 0; i < acesNumbers; i++) {
            if (cardValue > 10) // if total card value more than 10 - ace will have value 1
                cardValue = cardValue + 1;
            else // ace will have value 11
                cardValue = cardValue + 11;
        }
        return cardValue;
    }
}
