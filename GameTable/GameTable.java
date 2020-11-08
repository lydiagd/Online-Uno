package online_uno;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GameTable extends Thread {
	 public DiscardStack discardStk;
	 public DrawStack drawStk;
	 private PlayerQueue players; //keep track of all players in the game (PlayerVector class)
	 private GameTableState gtState;
	 
	 
	 public GameTable(PlayerQueue p) { //game table constructor hosting deck, draw pile, and discard pile
		 players = p;
		 
		 Deck d = new Deck();
		 d.shuffle(); //shuffle deck
		 
		 
		//___________ Initialize discard and draw piles for players to user ___________
		 discardStk = new DiscardStack();
		 drawStk = new DrawStack(d);
		 
		 
		 Deal(); //call deal to pass out cards to players
		 
		 //now place top card on discard pile
		 discardStk.discard(drawStk.draw());
		 while(discardStk.top().getNumber() == -1) //if top is wild, keep drawing
		 {
			 if(drawStk.size() > 0)
			 {
				 discardStk.discard(drawStk.draw());
			 }
			 throw new IllegalArgumentException("Something went wrong in gametable constructor");
		 }
	 }
	 
	 public void Deal() {
		 //iterate through players vector and add to hand
		 int num = players.Size();
		 for(int i = 0; i < num; i++)
		 {
			 Player p = players.Top();
			 for(int c = 0; c < 7; c++)
			 {
				 p.GetHand().add(drawStk.draw());
			 }
			 
			 players.NextTurn();
		 }		 
	 }
	 
	 public PlayerQueue getPlayers() {
		 return players;
	 }
	 
	 
	 public class DiscardStack extends Deck {
		 Stack<Card> discardPile;
		 
		 DiscardStack(){
			 discardPile = new Stack();
		 }
		  
		 public Card top(){
			 if(discardPile.size() > 0)
			 {
				 return discardPile.peek();
			 }
			 else return null;
		 }
		 
		 public void discard(Card c) {
			 discardPile.add(c);
		 }
	}


	public class DrawStack extends Deck {
		
		Queue<Card> drawPile;
		
		public DrawStack(Deck d) { //initialize with the remaining cards of a deck.
			drawPile = new LinkedList<>();
			int deckSize = d.size();
			for(int i = 0; i < deckSize; i++)
			{
				Card c = d.DealOut();
				drawPile.add(c); //d.dealout will remove the card from the deck and return it
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
	
	   @Override
	   public void run() {
		   Deal();
		   try {
			 while(!players.existsEmptyHand()) { //A player has an empty hand
				 Player p = players.Top();
				 String move = p.Play();
				 gtState.SetLastMove(move);
				 gtState.SetTopCard(discardStk.top().getColor() + " " + discardStk.top().getNumber());
				 players.NextTurn();
			 }
			 
		 } catch (InterruptedException e) {
		   e.printStackTrace();
		 } finally {
			 System.out.println("Player x won"); //TODO: specify the actual player who won
		 }
	     
	   }

}

