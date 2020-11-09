package GameTable;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
		 
		 
		// Deal(); //call deal to pass out cards to players
		 
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
		   Player winningPlayer = null; //place to store info for winning player
		   System.out.println("Game room successfully started");
		   Deal(); //ask for usernames?
		   
		   List<Player> allP = players.q; //force set username?
		   for(int i = 0; i < allP.size(); i++)
		   {
			   allP.get(i).setName("player "+ i);
			   //SET GAME TABLE
			   allP.get(i).setGameTable(this);
		   }
		   
		   try {
			 while(true) { //A player has an empty hand
				 Player p = players.Top();
				 if(p.IsHandEmpty())
				 {
					 //end game sequence - write null to player clients???
					 winningPlayer = p;
					 break;
				 }
				 String move = p.Play();
				 
				 //UPDATE OTHER PLAYERS THAT ITS NOT THEIR TURN
				   allP = players.q;
				   for(int i = 0; i < allP.size(); i++)
				   {
					   if(!(allP.get(i).GetName() == p.GetName()))
					   {
						   allP.get(i).updateClient("not your turn");
					   }
				   }
				 
				 gtState.SetLastMove(move);
				 gtState.SetTopCard(discardStk.top().getColor() + " " + discardStk.top().getNumber());
				 //send to all clients
				 for(int i = 0; i < allP.size(); i++)
				 {
					 try {
						allP.get(i).oos.writeObject(gtState);
					} catch (IOException e) {
						e.printStackTrace();
					}
				 }
				 
				 players.NextTurn(); //swap to next turn
			 }
			 
		 } 
//			 catch (InterruptedException e) {
//		   e.printStackTrace();
//		 } 
		finally {
			 System.out.println("Player" + winningPlayer.GetName() + " won the game."); //TODO: specify the actual player who won
			 //don't just pring on server, actually send message to player threads before ending them?
		 }
	     
	   }

}

