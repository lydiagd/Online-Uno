package serverPackage;

public class Room implements Runnable{
	private int roomSize;
	private Player[] players;
	public Room(Player[] players, int roomsize)
	{
		this.roomSize = roomsize;
		players = new Player[roomSize];
		for(int i=0; i<this.players.length; i++)
		{
			this.players[i] = players[i];
		}
	}
	public void run()
	{
		/*Collaborate with game table functions and layout group for this section
		 *
		 * */
	}
}
