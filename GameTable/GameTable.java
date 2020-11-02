import java.util.Queue;
import java.util.Stack;

public class GameTable {
	 public DiscardStack discardStk;
	 public DrawStack drawStk;
	 private PlayerQueue players; //keep track of all players in the game (PlayerVector class)
	 
	 
	 GameTable(PlayerQueue p){ //game table constructor hosting deck, draw pile, and discard pile
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
//		 for(int i = 0; i < players.Size(); i++)
//		 {
//			 Player p = players.Top();
//			 
//		 }
		 
		 
	 }
	 
	 
	 public class DiscardStack extends Deck {
		 Stack<Card> discardPile;
		 
		 DiscardStack(){
			 discardPile = new Stack();
		 }
		  
		 public Card top(){
			 return discardPile.peek();
		 }
		 
		 public void discard(Card c) {
			//discard(Card c) -> add to discard stack, called from player's hand
			 discardPile.add(c);
			
		 }
		  
		  
	}


	public class DrawStack extends Deck {
		
		Queue<Card> drawPile;
		
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
			
			return drawPile.remove();
		}
		  //draw() -> pop top of draw stack, called from player
	}

}

