public class Tester {
	
	public static void main(String [] args) 
	{
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
	}

}
