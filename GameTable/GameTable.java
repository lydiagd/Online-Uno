import java.util.Stack;

import online_uno.Card;
import online_uno.Deck;

public class GameTable {
	 public DiscardStack discardStk;
	 public DrawStack drawStk;
	 private PlayerVector players; //keep track of all players in the game (PlayerVector class)
	 
	 
	 GameTable(PlayerVector p){ //game table constructor hosting deck, draw pile, and discard pile
		 players = p;
		 
		 Deck d = new Deck();
		 d.shuffle(); //shuffle deck
		 
		 
		//___________ Initialize discard and draw piles for players to user ___________
		 discardStk = new DiscardStack();
		 drawStk = new DrawStack(d);
		 
		 
		 Deal(); //call deal to pass out cards to players
		 
		 
	 }
	 
	 public void Deal(){
		 
		 //iterate through players vector and add to hand
		 //for (Player p : PlayerVector) --> remove top from draw stack and put into player hands
		 
		 
	 }
	 
	 
	 public class DiscardStack extends Deck {
		 Stack discardPile;
		 
		 DiscardStack(){
			 discardPile = new Stack();
		 }
		  
		 public Card top(){
			 
		 }
		 
		 public void discard(Card c) {
			//discard(Card c) -> add to discard stack, called from player's hand
			 
			
		 }
		  
		  
	}


	public class DrawStack extends Deck {
		
		Stack<Card> drawPile;
		
		public DrawStack(Deck d) { //initialize with the remaining cards of a deck.
			
			for(int i = 0; i < d.size(); i++)
			{
				drawPile.add(d.DealOut()); //d.dealout will remove the card from the deck and return it
			}
			
		}
		
		public Card top(){
			 return drawPile.peek();
		 }
		  //top
		public Card draw() {
			
			return drawPile.pop();
		}
		  //draw() -> pop top of draw stack, called from player
	}

}

