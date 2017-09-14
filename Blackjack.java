package blackjack;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Blackjack {
	public static void main(String[] args) throws IOException {
		BufferedReader mReader;
		int money;
		int bet;
		boolean userWins;
		
		System.out.printf("Welcome to Blackjack %n%n");
		money = 100;
		while(true) {
			System.out.printf("You have %s dollars %n", money);
			do {
				mReader = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("How much do you want to bet? (Enter 0 to end)");
				bet = Integer.parseInt(mReader.readLine());
				if (bet < 0 || bet > money)
                   System.out.printf("Your answer must be between 0 and %s%n", money);
			} while(bet < 0 || bet > money);
			if(bet == 0) {
				System.out.println("Thanks for playing!");
				break;
			}
			userWins = playBlackjack();
			if(userWins) {
				money += bet;
			} else {
				money -= bet;
			}
			if(money == 0) {
				System.out.println("Oops! You are out of money.");
				break;
			}
		}
	}
	
	static boolean playBlackjack() throws IOException {
		Deck deck;
		BlackjackHand dealerHand;
		BlackjackHand playerHand;
		BufferedReader mReader = new BufferedReader(new InputStreamReader(System.in));
		
		deck = new Deck();
		dealerHand = new BlackjackHand();
		playerHand = new BlackjackHand();
		deck.shuffle();
		dealerHand.addCard(deck.dealCard());
		dealerHand.addCard(deck.dealCard());
		playerHand.addCard(deck.dealCard());
		playerHand.addCard(deck.dealCard());
		
		if(dealerHand.getBlackjackValue() == 21) {
			System.out.printf("Dealer has the %s and the %s %n%n", dealerHand.getCard(0), dealerHand.getCard(1));
			System.out.printf("Player has the %s and the %s %n%n", playerHand.getCard(0), playerHand.getCard(1));
			System.out.println("Dealer has Blackjack. Dealer wins.");
			return false;
		}
		
		if(playerHand.getBlackjackValue() == 21) {
			System.out.printf("Player has the %s and the %s %n%n", playerHand.getCard(0), playerHand.getCard(1));
			System.out.printf("Dealer has the %s and the %s %n%n", dealerHand.getCard(0), dealerHand.getCard(1));
			System.out.println("Player has Blackjack. Player wins.");
			return true;
		}
		
		while(true) {
			System.out.println("Your cards are: ");
			for(int i=0; i<playerHand.getCardCount(); i++) {
				System.out.printf(" %s  ", playerHand.getCard(i));
			}
			System.out.printf("Your total is: %s%n", playerHand.getBlackjackValue());
			System.out.printf("Dealer is showing the %s%n", dealerHand.getCard(0));
			System.out.println("Would you like to Hit (H) or Stand (S)?");
			char userAction;
			do {
				char[] userInput = mReader.readLine().toCharArray();
				userAction = userInput[0];
				if(userAction != 'h' && userAction != 's') {
					System.out.println("Please respond with H or S");
				}
			} while(userAction != 'h' && userAction != 's');
			if(userAction == 's') {
				break;
			} else {
				Card newCard = deck.dealCard();
				playerHand.addCard(newCard);
				System.out.println("User hits");
				System.out.printf("You drew the %s%n", newCard);
				System.out.printf("Your new total is %s%n: ", playerHand.getBlackjackValue());
				if(playerHand.getBlackjackValue() > 21) {
					System.out.println("You are over 21. You lose.");
					System.out.printf("Dealer's other card was the %s%n", dealerHand.getCard(1));
					return false;
				}
			}
		}
		System.out.println("Player stands");
		System.out.println("Dealer's cards are: ");
		System.out.printf(" %s%n ", dealerHand.getCard(0));
		System.out.printf(" %s%n ", dealerHand.getCard(1));
		while(dealerHand.getBlackjackValue() <= 16) {
			Card newCard = deck.dealCard();
			System.out.printf("Dealer hits and gets the %s%n: ", newCard);
			dealerHand.addCard(newCard);
			if(dealerHand.getBlackjackValue() > 21) {
				System.out.println("Dealer went over 21. You Win");
				return true;
			}
		}
		if(playerHand.getBlackjackValue() == dealerHand.getBlackjackValue()) {
			System.out.println("Dealer wins on a tie. You lose.");
			return false;
		} else if (dealerHand.getBlackjackValue() > playerHand.getBlackjackValue()) {
			System.out.printf("Dealer wins, %s points to %s%n", dealerHand.getBlackjackValue(), playerHand.getBlackjackValue());
			return false;
		} else {
			System.out.printf("You win! %s points to %s%n", playerHand.getBlackjackValue(), dealerHand.getBlackjackValue());
			return true;
		}
	}
}
