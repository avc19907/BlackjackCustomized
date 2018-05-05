package blackjack;

import java.util.Random ;
/**
 * Deck of cards-- forming a dynamic deck
 * 1 Pack = 52 cards;
 * Deck = one or more packs;
 *
 */
public class Deck {
	
	private Cards[] cardsInDeck;
	private int numOfCardsInDeck;
	private int onePack = 52;
	
	/**
	 * constructor for default shuffled deck consisting of 1 pack i.e 52 cards
	 */
	
	public Deck(){
		
		this(1, true);
	}
	
	/**
	 * 
	 * Deck constructor to define number of cards in a Deck && whether it should be shuffled or not
	 * 
	 * @param numPacks
	 * @param shuffle
	 */
	public Deck(int numPacks, boolean shuffle){
		
		this.numOfCardsInDeck = numPacks*this.onePack;
		this.cardsInDeck = new Cards[this.numOfCardsInDeck];
		
		int c = 0;
		
		// for each deck
		for(int d=0;d<numPacks;d++){
			
			// for each suit
			for(int s=0; s<4;s++){
				
				// for each number
				for(int n=1;n<=13;n++){
					
					this.cardsInDeck[c] = new Cards(Suits.values()[s], n);
					c++;
				}
			}
		}
		
		//shuffle
		if(shuffle){
			
			this.shuffleDeck();
		}
	}
	
	public void shuffleDeck(){
		
		Random rng = new Random();
		
		Cards temp;
		
		// swapping
		
		int j;
		for(int i=0; i<this.numOfCardsInDeck;i++){
			
			j = rng.nextInt(this.numOfCardsInDeck);
			
			temp = this.cardsInDeck[i];
			this.cardsInDeck[i] = this.cardsInDeck[j];
			this.cardsInDeck[j] = temp;	
		}
	}
	
	
	// dealing a card from deck
	public Cards dealingNextCard(){
		
		Cards topCard = this.cardsInDeck[0];
		
		for(int c =1; c<this.numOfCardsInDeck;c++){
			
			this.cardsInDeck[c-1] = this.cardsInDeck[c];
		}
		
		this.cardsInDeck[this.numOfCardsInDeck - 1] = null;
		this.numOfCardsInDeck--;
		 
		return topCard;
	}
	
	public void printDeckCards(int num){
		
		for(int c=0; c<num; c++){
			
			System.out.printf("% 3d/%d %s\n", c+1, this.numOfCardsInDeck, this.cardsInDeck[c].toString());
		}
		
		System.out.printf("\t\t[%d other]\n", this.numOfCardsInDeck - num);
	}
}
