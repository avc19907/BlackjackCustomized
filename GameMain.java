package blackjack;

/**
 *  This class contains main function
 *
 */

import java.util.Scanner;
import java.util.InputMismatchException;


public class GameMain {
	
	private Deck newDeck;
	private String playerName;
	private boolean youDone;
	private boolean dealerDone;
	private Players dealer;
	private Players you;
	private Scanner sc = new Scanner(System.in);
	
	
	GameMain(String pName){
		
		this.newDeck = new Deck(4, true);
		boolean gameOver = false;
		this.playerName  = pName;
		
		System.out.println("######################################################################################");
		System.out.println("Hi, "+this.playerName+". Enjoy the game!");
		System.out.println("######################################################################################");
		// Players init
		you = new Players(this.playerName);
		dealer = new Players("Dealer");
		
		
		// Game Starts here --->
		while(!gameOver){
					
			System.out.println("\n"+this.playerName+", Do you want to DEAL or END the game [D or E]??");
			String gameInit = sc.next();
					
			if(gameInit.compareToIgnoreCase("D") == 0){
					
				this.deal();			
			}
			else{
						
				gameOver = true;
			}	
		}
		
		System.out.println("\n"+this.playerName+", game ended !!!");
		
		// To play again
		System.out.println("\n"+this.playerName+", do you want to play again [Y or N]");
		String Y = sc.next();
		if(Y.compareToIgnoreCase("Y") == 0){
			
			new GameMain(this.playerName);
		}
		
		//closing scanner
		sc.close();
		
	}
	
	// Deal the game
	private void deal(){
		
		boolean blackjack = false;
			
		// players start with empty hands
		you.emptyHand();
		dealer.emptyHand();
		
		this.youDone = false;
		this.dealerDone = false;
		
		// Distributing initial cards to players
		you.addCardToPlayersHand(newDeck.dealingNextCard());
		dealer.addCardToPlayersHand(newDeck.dealingNextCard());
		you.addCardToPlayersHand(newDeck.dealingNextCard());
		dealer.addCardToPlayersHand(newDeck.dealingNextCard());
		
		
		// Cards Dealt
		System.out.println("\n########## CARDS DEALT ##########\n");
		dealer.printCardsInHand(false);
		you.printCardsInHand(true);
		
		System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
		
		// checking state on initial card -- if BlackJack
		blackjack = this.checkIfBlackJack();
		
		while(!this.youDone || !this.dealerDone){
		
			if(!this.youDone){
				
				this.yourPlay();
				
			}
			else if(!this.dealerDone){
				
				this.dealersPlay();
			}
			
			System.out.println();
		}
		
		if(!blackjack){
			
			this.decideWinner();		
		}
		
	}
	
	// Natural 21 check on initial cards
	private boolean checkIfBlackJack(){
		
		boolean blackJack = false;
		
		if(you.getPlayersHandTotal() == 21){
			
			 this.youDone = true;
			 this.dealerDone = true;
			 
			 if(you.getPlayersHandTotal() > dealer.getPlayersHandTotal() || dealer.getPlayersHandTotal() > 21){
				 
				 System.out.println("\t\t\t\t#################################");
				 System.out.println("\t\t\t\t#                               #");
				 System.out.println("\t\t\t\t# HURRAY!!...BLACKJACK, YOU WON #");
				 System.out.println("\t\t\t\t#                               #");
				 System.out.println("\t\t\t\t#################################\n");
				 
				 dealer.printCardsInHand(true);
				 
				 System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
				 
				 blackJack = true;
			 }
			 else{
				 
				 System.out.println("\tIt could have been a BlackJack for you...but is equality\n");
				 dealer.printCardsInHand(true);
				 
				 System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal()); 
				 blackJack = false;
			 }
		}
		else if(dealer.getPlayersHandTotal() == 21){
			
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			
			System.out.println("\t\t\t\t#################################");
			System.out.println("\t\t\t\t#                               #");
			System.out.println("\t\t\t\t# DEALER's BLACKJACK, YOU LOST  #");
			System.out.println("\t\t\t\t#                               #");
			System.out.println("\t\t\t\t#################################\n");
			
			this.dealerDone = true;
			blackJack = false;
		}
		
		return blackJack;
	}
	
	// Player's Play Turn
	private void yourPlay(){
		
		String answer;
		/*
		 * flags- Hit, Stand, Double, Split
		 * ---------------------------------
		 */
		
		System.out.print("Hit or Stay? [Enter H or S]");
		
		answer = sc.next();
		System.out.println();
		
		if(answer.compareToIgnoreCase("H") == 0){
			
			this.hit();
		}
		else{
			
			this.stay();
		}
	}
	
	// Player's Hit
	private void hit(){
		
		System.out.println("\tYou Choose to Hit.\n");
		youDone = !you.addCardToPlayersHand(newDeck.dealingNextCard());
		you.printCardsInHand(true);
		System.out.printf("Your Score:%d\t", you.getPlayersHandTotal());
		System.out.println();
		
		if(you.getPlayersHandTotal()>21){
			
			System.out.println("\t\t\t\t##############");
			System.out.println("\t\t\t\t#            #");
			System.out.println("\t\t\t\t# YOU BUSTED #");
			System.out.println("\t\t\t\t#            #");
			System.out.println("\t\t\t\t##############\n");
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			youDone = true;
			dealerDone = true;
		}
		
	}
	
	// Player's Stay
	private void stay(){
		
		System.out.println("\tYou Choose to Stay, Dealer's turn \n");
		youDone = true;
	}
	
	// Dealer's Play Turn
	private void dealersPlay(){
		
		if(dealer.getPlayersHandTotal() < 17){
			
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			System.out.println("\tDealer Hits \n");
			dealerDone = !dealer.addCardToPlayersHand(newDeck.dealingNextCard());
			
			if(dealer.getPlayersHandTotal()>21){
				
				dealer.printCardsInHand(true);
				System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
				System.out.println("\t\t\t\t#################");
				System.out.println("\t\t\t\t#               #");
				System.out.println("\t\t\t\t# DEALER BUSTED #");
				System.out.println("\t\t\t\t#               #");
				System.out.println("\t\t\t\t#################\n");
				dealerDone = true;
			}
		}
		else{
			
			dealer.printCardsInHand(true);
			System.out.printf("Dealer's Score:%d\n\n", dealer.getPlayersHandTotal());
			System.out.println("\tDealer Stays \n");
			dealerDone = true;
		}
	}
	
	// Deciding a Winner
	private void decideWinner(){
		
		int youSum = you.getPlayersHandTotal();
		int dealerSum = dealer.getPlayersHandTotal();
		
		if(youSum>dealerSum && youSum<=21 || dealerSum >21){
			
			System.out.println("\t\t\t\t############");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t# YOU WON  #");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t############\n");
			
		}
		else if(youSum == dealerSum){
			
			System.out.println("\t\t\t\t############");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t#   EQUAL  #");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t############\n");
		}
		else{
			
			System.out.println("\t\t\t\t############");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t# YOU LOST #");
			System.out.println("\t\t\t\t#          #");
			System.out.println("\t\t\t\t############\n");
		}
	}
	

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String playerName;
		
		System.out.println("\n\t\t\t\t##########################################");
		System.out.println("\t\t\t\t#                                        #");
		System.out.println("\t\t\t\t#               BLACKJACK                #");
		System.out.println("\t\t\t\t#                                        #");
		System.out.println("\t\t\t\t##########################################\n");
	
		System.out.println("Enter Your Name:\n");
		playerName = scanner.nextLine();
		
		new GameMain(playerName);
		
		scanner.close();
	}

}
