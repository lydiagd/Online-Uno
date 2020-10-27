package online_uno;

public class Tester {
	
	public static void main(String [] args) 
	{
		Deck d = new Deck();
		System.out.println("Card deck contents:");
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " ");
		}
		System.out.println();
		
		System.out.println("Shuffled card deck:");
		d.shuffle();
		for(Card c : d.cards)
		{
			System.out.print(c.stringout() + " | ");
		}
	}

}
