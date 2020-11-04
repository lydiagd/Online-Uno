
import java.util.*;

public class Player extends Thread {
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String username;
	private Integer score;
	private PlayerQueue playerQueue;
	
	private GameTable table;
	
	public Player(String id) {
		this.username = id;
	}

	private void play() {
		//read in card name
		Card top = table.discardStk.top();
		ChooseMove(HasViableMoves(top.getColor(), top.getNumber()));
		
		if(IsHandEmpty()) {
			//tally points
			ArrayList<Card> discardStk = table.discardStk;
			for(Card c: discardStk) {
				if(c.getNumber() == -1) {
					AddToScore(50);
				} else {
					AddToScore(c.getNumber());
				}
			}
		}
		
	}
	
	private void AddToScore(Integer points) {
		score += points;
	}
	
	private void DisplayHand() {
		//print cards
	}
	
	private boolean HasViableMoves(char color, int number) { //input color and number of card that player must match
		for(Card c: hand) {
			if(c.getColor().charAt(0) == color) {
				return true;
			}
			if(c.getNumber() == number) {
				return true;
			}
		}
		return false;
	}
	
	private boolean IsHandEmpty() {
		return hand.isEmpty();
	}
	
	private void ChooseMove(boolean hasViableMoves) {
		Scanner sc = new Scanner(System.in);
		char move = 0;
		if(hasViableMoves) {
			System.out.println("Enter 'd' to draw a card or 'p' to play a card: ");
			while(move != 'd' && move != 'p') {
				System.out.println("Enter 'd' to draw a card: ");
				move = sc.next().charAt(0);
			}
			if(move == 'd') {
				DrawCard();
			} else {
				PlayCard();
			}
		} else {
			while(move != 'd') {
				System.out.println("Enter 'd' to draw a card: ");
				move = sc.next().charAt(0);
			}
			DrawCard();
		}
	}
	
	private void PlayCard() {
		Card c = null;
		Scanner sc = new Scanner(System.in);
		String cardName;
		char color;
		int number;
		while(true) {
			cardName = sc.next();
			if(cardName.charAt(0) == 'w') { //wildcard
				number = -1;
				color = cardName.charAt(1);
			} else { //normal card
				color = cardName.charAt(0);
				number = cardName.charAt(1) - '0';
			}
			for(Card card: hand) { //obtain card from hand
				if((number == -1 && card.getNumber() == -1)
				|| (number == card.getNumber() && color == card.getColor().charAt(0))) {
					c = card;
				}
			}
			if(c != null) {
				break;
			}
		}

		
		hand.remove(c);
		table.discardStk.discard(c);
	}
	
	private void DrawCard() {
		Card c = table.drawStk.draw();
		hand.add(c);
	}
	
	
	public String GetID() {
		return username;
	}
	
	public ArrayList<Card> GetHand() {
		return hand;
	}
	
	@Override
	public void run() {
		try {
			playerQueue.GetSemaphore().acquire();
			play();
			playerQueue.NextTurn();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			playerQueue.GetSemaphore().release();
		}
	}
	
}
