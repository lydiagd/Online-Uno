package GameTable;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GameTable extends Thread {
	 public DiscardStack discardStk;
	 public DrawStack drawStk;
	 private PlayerQueue players; //keep track of all players in the game (PlayerVector class)
	 private GameTableState gtState = new GameTableState();
	 
	 
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
			 else {
				 throw new IllegalArgumentException("Something went wrong in gametable constructor");
			 }
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
	 
	 public void ReShuffle() {
		 Deck d = new Deck();
		 d.shuffle();
		 drawStk = new DrawStack(d);
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
			   Player curP = allP.get(i);
			   //artificial username
			   curP.SetName("player "+ i);
			   
			   //send their hand to the client before starting game
			   ArrayList<String> handInfo = new ArrayList<String>();
				for(Card c : curP.GetHand())
				{
					handInfo.add(c.stringout());
				}
				
				try {
					curP.oos.writeObject(handInfo); //write successfully
					curP.oos.flush(); //all cards are making it through
				} catch (IOException e) {
					e.printStackTrace();
				}
			   
			   
			   //SET GAME TABLE
			   curP.setGameTable(this);
		   }
		   
		   //send gamestate obj before starting game
		   
		   try {
			 while(true) { //A player has an empty hand
				 Player p = players.Top();
				 if(p.IsHandEmpty())
				 {
					 //end game sequence - write null to player clients???
					 winningPlayer = p;
					 break;
				 }
				 
				 //UPDATE OTHER PLAYERS THAT ITS NOT THEIR TURN
				   allP = players.q;
				   for(int i = 0; i < allP.size(); i++)
				   {
					   String name1 = allP.get(i).GetName();
					   String name2 = p.GetName();
					   if(!(name1.equals(name2)))
					   {
						   allP.get(i).updateClient("not your turn");
					   }
				   }
				 
				 String move = p.Play(); //returns string update
				 
				 gtState.SetLastMove(move);
				 gtState.SetTopCard(discardStk.top().getColor() + " " + discardStk.top().getNumber());
				 //send to all clients
				 for(int i = 0; i < allP.size(); i++)
				 {
					 try {
						allP.get(i).oos.writeObject(gtState);
						allP.get(i).oos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				 }
				 
				 players.NextTurn(); //swap to next turn
			 }
			 
		 } 
//		catch (SocketException e) {
//		   e.printStackTrace();
//		 } 
		finally {
			 System.out.println("Player" + winningPlayer.GetName() + " won the game."); 
			 //don't just print on server, actually send message to player threads before ending them?
		 }
	     
	   }

}

