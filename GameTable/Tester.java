public class Tester {
	
	public static void main(String [] args) 
	{
		//DECK TEST
		Deck d = new Deck();
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " ");
		}
		System.out.println();
		d.shuffle();
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " | ");
		}
		
		
		//PLAYER TEST
		System.out.println();
		PlayerQueue pq = new PlayerQueue();
		String[] userNms = {"me1", "another1", "3rdGuy", "lastPlayer"};
		for(int i = 0; i < 4; i++)
		{
			Player p = new Player(userNms[i]);
			pq.AddPlayer(p);
		}
		
		System.out.println("top player: "+pq.Top().GetID());
		pq.RemovePlayer(pq.Top());
		
		System.out.println("new top player: "+pq.Top().GetID());
		
		pq.NextTurn();
		System.out.println("next turn player: "+pq.Top().GetID());
		
		for(int i = 0; i < 3; i++)
		{
			pq.NextTurn();
			System.out.println(pq.Top().GetID() + " , then ");
		}
		
		
		//GAMETABLE TEST
	}

}
