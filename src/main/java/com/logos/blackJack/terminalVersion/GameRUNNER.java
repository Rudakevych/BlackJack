package com.logos.blackJack.terminalVersion;

import java.util.Scanner;

public class GameRUNNER {

    /**
     * This metod run terminal version of game Black Jack
     *
     * User has only one chanse.
     * The game will be finished when player has no money
     * @param args
     */
    public static void main(String[] args) {
        Player player;
        Shoe playingShoe;

        String exit = "start";
        while (exit.equalsIgnoreCase("start")) {

            Scanner scanner = new Scanner(System.in);

            System.out.println(">>> Hi! What is your name? <<<");
            String playerName = scanner.next();

//		Boolean greaterThanZero = false;
            int playerMoney = 0;
            while (playerMoney <= 0) {
                System.out.println("> Hi, " + playerName + "! <What is your bet? [example: 80 or 5]");
                try {
                    String playerMoneyUserInput = scanner.next();
                    playerMoney = Integer.parseInt(playerMoneyUserInput);
                } catch (NumberFormatException e) {
                    System.out.println(">>> Value is not a number. Please, enter a numbers. [example: 80 or 50.48]");
                }
            }

            player = new Player(playerName, playerMoney);

            //playingShoe will be the deck the dealer holds
            playingShoe = new Shoe();
            playingShoe.generating52Cards();
            playingShoe.shuffle();

            Shoe playerCards = new Shoe();
            Shoe dealerCards = new Shoe();

            while (playerMoney > 0) {

                int playerBet = 0;
                System.out.println("<You have [$" + playerMoney + "]. How much would you like to bet for now?>");
                while (playerBet == 0) {
                    try {
                        String playerBetUserInput = scanner.next();
                        playerBet = Integer.parseInt(playerBetUserInput);
                        if (playerBet > playerMoney || playerBet <= 0) {
                            System.out.println(">>> Sorry, man. You cannot bet more than you have.");
                            playerBet = 0;
                        }
                    } catch (NumberFormatException x) {
                        System.out.println(">>> Value is not a number. Please, enter a numbers. [example: 80 or 50.48]");
                    }
                }

                boolean endRound = false;

                // give two cards for Player from shoe
                playerCards.giveACardFromShoe(playingShoe);
                playerCards.giveACardFromShoe(playingShoe);

                // give two cards for Dealer from shoe
                dealerCards.giveACardFromShoe(playingShoe);
                dealerCards.giveACardFromShoe(playingShoe);

                while (true) {
                    // player cards
                    System.out.println("> Your cards:" + playerCards.toString());
                    System.out.println("=> Your cards total value: " + playerCards.cardsValue());

                    // dealer card
                    System.out.println("> Dealer cards: " + dealerCards.getCardFromShoe(0).toString() + " and one card is hidden");

                    System.out.println(">>> Would you like to [1]Hit or [2]Stand");
                    System.out.println("Press button [1] to Hit or [2] to Stand");
                    String hitOrStandChoiseUserInput = scanner.next();
                    int hitOrStandChoise = Integer.parseInt(hitOrStandChoiseUserInput);

                    // if player hit
                    if (hitOrStandChoise == 1) {
                        playerCards.giveACardFromShoe(playingShoe);
                        System.out.println("> You give a card from shoe at:" + playerCards.getCardFromShoe(playerCards.shoeSize() - 1).toString());
                        //Bust if they go over 21
                        if (playerCards.cardsValue() > 21) {
                            System.out.println(">>> Bust. Currently valued at: " + playerCards.cardsValue());
                            playerMoney = playerMoney - playerBet;
                            endRound = true;
                            break;
                        }
                    }

                    // if player stand
                    if (hitOrStandChoise == 2) {
                        break;
                    }
                }

                //Reveal Dealer Cards
                System.out.println("> Dealer Cards:" + dealerCards.toString());

                // if dealer has more points than player
                if ((dealerCards.cardsValue() > playerCards.cardsValue()) && endRound == false) {
                    System.out.println(">>> Sorry, but Dealer beats you " + dealerCards.cardsValue() + " to " + playerCards.cardsValue());
                    playerMoney = playerMoney - playerBet;
                    endRound = true;
                }

                //Dealer hits at 16 stands at 17
                while ((dealerCards.cardsValue() < 17) && endRound == false) {
                    dealerCards.giveACardFromShoe(playingShoe);
                    System.out.println("> Dealer draws: " + dealerCards.getCardFromShoe(dealerCards.shoeSize() - 1).toString());
                }

                //Display value of dealer
                System.out.println("> Dealers hand value: " + dealerCards.cardsValue());

                //Determine if dealer busted
                if ((dealerCards.cardsValue() > 21) && endRound == false) {
                    System.out.println("=>>>> Dealer Busts. You win! Congratulations!!! You're the best!");
                    playerMoney = playerMoney + playerBet;
                    endRound = true;
                }

                // determine if push
                if ((dealerCards.cardsValue() == playerCards.cardsValue()) && endRound == false) {
                    System.out.println(">>Push.");
                    endRound = true;
                }

                //Determine if player wins
                if ((playerCards.cardsValue() > dealerCards.cardsValue()) && endRound == false) {
                    System.out.println(">>You win the hand.");
                    playerMoney = playerMoney + playerBet;
                    endRound = true;
                } else if (endRound == false) { //dealer wins
                    System.out.println("Sorry, but Dealer wins.");
                    playerMoney = playerMoney - playerBet;
                }

                //End of hand - put cards back in deck
                playerCards.moveAllToShoe(playingShoe);
                dealerCards.moveAllToShoe(playingShoe);
                System.out.println(" >>> End of Hand <<<  ");
            }

            System.out.println("Do you want to play one more time?");
            System.out.println("Press [start] for continue OR press [exit] for crying");
            exit = scanner.next();

            if(exit.equalsIgnoreCase("exit")) {
                //Close Scanner
                scanner.close();
                //Game is over
                System.out.println("GAME OVER!");
            }
        }
    }
}

