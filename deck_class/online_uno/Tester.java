package online_uno;

public class Tester {
	
	public static void main(String [] args) 
	{
		Deck d = new Deck();
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " ");
		}
		
		d.shuffle();
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " | ");
		}
	}

}
