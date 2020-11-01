package online_uno;

public class Deck {
	
	Card[] cards;
	
	public Deck() { //fill deck with 25 red, 25 blue, 25 green, 25 yellow cards (normal), 8 wild
		
		cards = new Card[108];
		int idx = 0;
		String[] colors = {"red","blue","green","yellow"};
		
		for(String c : colors)
		{
			for(int i = 0; i < 25; i++) //can adjust this number lower to accommodate more cards
			{
				cards[idx] = new Normal_Card(c, i%9); //create a normal card
				idx++;
			}
		}
		
		for(int i = 0; i < 8; i++) //add wildcards to the deck
		{
			cards[idx] = new Wild_Card("Wildcard");
			idx++;
		}
		
	}
	
	public void shuffle() { //ref: https://medium.com/@blakeeh723/how-to-build-a-card-game-with-object-oriented-programming-c43cd2cadb3a
		for(int i = 0; i < 500; i++) //do for at least 500 iterations to have more mixed cards
		{
			int loc1 = (int) Math.floor((Math.random() * this.cards.length));
	        int loc2 = (int) Math.floor((Math.random() * this.cards.length));
	        Card tmp = this.cards[loc1];
	           this.cards[loc1] = this.cards[loc2];
	           this.cards[loc2] = tmp; //swap two cards and their locs in the array
		}
	}

}
