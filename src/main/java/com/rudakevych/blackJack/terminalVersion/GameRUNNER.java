package com.rudakevych.blackJack.terminalVersion;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class GameRUNNER {
    final static Logger logger = Logger.getLogger(GameRUNNER.class);

    /**
     * This metod run terminal version of game Black Jack
     *
     * @param args arruments of main method
     */
    public static void main(String[] args) {
        Shoe playingShoe;
        Player player = new Player("", 0); // create new Player

        // "show must go on" - player can enjoy game many times
        String exit = "start";
        while (exit.equalsIgnoreCase("start")) {

            Scanner scanner = new Scanner(System.in);

            System.out.println(">>> Hi! What is your name? <<<");
            player.setPlayersName(scanner.next());

            while (player.getPlayerCash() <= 0) {
                System.out.println("> Hi, " + player.getPlayersName() + "! How much money are you prepared to earn today? ;)");
                try {
                    String playerMoneyUserInput = scanner.next();
                    player.setPlayerCash(Integer.parseInt(playerMoneyUserInput));
                } catch (NumberFormatException e) {
                    System.out.println("***********************************");
                    System.out.println("INFO: Value is not an integer number. Please, enter an integer number. [min - 1 / max - 2,000,000,000] We don't use coins!");
                    logger.info(e);
                }
            }

            //playingShoe will be the deck the dealer holds
            playingShoe = new Shoe();
            playingShoe.generating52Cards(); // generating shoe
            playingShoe.shuffle(); // randomize shoe

            Shoe playerCards = new Shoe();
            Shoe dealerCards = new Shoe();

            // player bet
            while (player.getPlayerCash() > 0) {
                int playerBet = 0;
                System.out.println("============================================");
                System.out.println("<You have [$" + player.getPlayerCash() + "]. How much would you like to bet for now?>");
                while (playerBet == 0) {
                    try {
                        String playerBetUserInput = scanner.next();
                        playerBet = Integer.parseInt(playerBetUserInput);
                        if (playerBet > player.getPlayerCash() || playerBet <= 0) {
                            System.out.println("INFO: Sorry, man. You cannot bet more than you have. Enter correct number, please!");
                            playerBet = 0;
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("***********************************");
                        System.out.println("INFO: Value is not an integer number. Please, enter an integer number. [example: 80 or 100000] We don't use coins!");
                        logger.info(ex);
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
                    System.out.println("^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^");
                    // player cards
                    System.out.println(">> Your cards: " + playerCards.toString());
                    System.out.println(">> Your cards total value: " + playerCards.cardsValue());

                    System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                    // dealer card
                    System.out.println(">> Dealer cards: ");
                    System.out.println(dealerCards.getCardFromShoe(0).toString());
                    System.out.println("...and one card is hidden");

                    // player's next step - Hit or Stand
                    int hitOrStandChoise = 0;
                    while (hitOrStandChoise != 1 && hitOrStandChoise != 2) {
                        System.out.println("$ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $");
                        System.out.println(">>> Would you like to [1]Hit or [2]Stand");
                        try {
                            String hitOrStandChoiseUserInput = scanner.next();
                            hitOrStandChoise = Integer.parseInt(hitOrStandChoiseUserInput);
                        } catch (NumberFormatException e) {
                            System.out.println("INFO: Please, press button [1] to Hit or [2] to Stand <<<");
                            logger.info(e);
                        }
                    }

                    // if player hit
                    if (hitOrStandChoise == 1) {
                        System.out.println("INFO: Your choice is HIT.");
                        playerCards.giveACardFromShoe(playingShoe);
                        System.out.println("^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^");
                        System.out.println(">> You give a card from shoe at:");
                        System.out.println(playerCards.getCardFromShoe(playerCards.shoeSize() - 1).toString());
                        //Bust if they go over 21
                        if (playerCards.cardsValue() > 21) {
                            System.out.println("INFO: >>> Bust. Currently valued at: " + playerCards.cardsValue());
                            player.setPlayerCash(player.getPlayerCash() - playerBet);
                            endRound = true;
                            break;
                        }
                    }

                    // if player stand
                    if (hitOrStandChoise == 2) {
                        System.out.println("INFO: Your choice is STAND.");
                        break;
                    }
                }

                //Reveal Dealer Cards
                System.out.println(">> Dealer Cards:" + dealerCards.toString());

                // if dealer has more points than player
                if ((dealerCards.cardsValue() > playerCards.cardsValue()) && endRound == false) {
                    System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                    System.out.println("INFO: >>> Sorry, but Dealer beats you " + dealerCards.cardsValue() + " to " + playerCards.cardsValue());
                    player.setPlayerCash(player.getPlayerCash() - playerBet);
                    endRound = true;
                }

                //Dealer hits at 16 stands at 17
                while ((dealerCards.cardsValue() < 17) && endRound == false) {
                    dealerCards.giveACardFromShoe(playingShoe);
                    System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                    System.out.println("INFO: >> Dealer draws:");
                    System.out.println(dealerCards.getCardFromShoe(dealerCards.shoeSize() - 1).toString());
                }

                //Display value of dealer
                System.out.println(">> Dealers hand value: " + dealerCards.cardsValue());

                //Determine if dealer busted
                if ((dealerCards.cardsValue() > 21) && endRound == false) {
                    System.out.println("$ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $");
                    System.out.println("INFO: >>> Dealer Busts. You win! Congratulations!!! You're the best! <<<");
                    player.setPlayerCash((player.getPlayerCash() + playerBet));
                    endRound = true;
                }

                // determine if push - dealer and players has the same cards value
                if ((dealerCards.cardsValue() == playerCards.cardsValue()) && endRound == false) {
                    System.out.println("INFO: >>> Push.");
                    endRound = true;
                }

                //Determine if player wins
                if ((playerCards.cardsValue() > dealerCards.cardsValue()) && endRound == false) {
                    System.out.println("$ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $");
                    System.out.println("INFO: >>> You win the hand.");
                    player.setPlayerCash(player.getPlayerCash() + playerBet);
                    endRound = true;
                } else if (endRound == false) { //dealer wins
                    System.out.println("INFO: Sorry, but Dealer wins.");
                    player.setPlayerCash(player.getPlayerCash() - playerBet);
                }

                //End of hand - put cards back in deck
                playerCards.moveAllToShoe(playingShoe);
                dealerCards.moveAllToShoe(playingShoe);
                System.out.println("INFO: >>> End of Hand <<<  ");
            }

            /* Checking what user want - continue or exit */
            System.out.println("$ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $ $");
            System.out.println("Do you want to play one more time?");
            System.out.println("Enter [start] for continue OR enter [exit] for crying");
            boolean isStartOrExit = false;
            String input = "";
            while (!isStartOrExit) {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    isStartOrExit = true;
                    exit = "exit";
                } else if (input.equalsIgnoreCase("start")) {
                    isStartOrExit = true;
                } else {
                    System.out.println("INFO: Please, enter [start] OR [exit]");
                }
            }

            if (exit.equalsIgnoreCase("exit")) {
                //Game is over
                System.out.println("INFO: GAME OVER!");
                System.out.println(">>> See you soon, " + player.getPlayersName() + "! <<< ");
                //Close Scanner
                scanner.close();
            }
        }
    }
}