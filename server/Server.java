package serverPackage;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Server {
	public static final int rooms = 3;
	public static final int roomsize = 5;
	public static final int maxPlayers = 20;
	//have port be 1024<=port<=49151 since they can be used by any app
	public static final int port = 6789;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	public void main (String[] args) throws IOException
	{
		ExecutorService Playerexecutor = Executors.newFixedThreadPool(maxPlayers);
		ExecutorService Roomexecutor = Executors.newFixedThreadPool(rooms);
		while(true)
		{
			if(playerList.size()>roomsize)
				//assign players to room when there are enough of them
			{
				Player[] players = new Player[roomsize];
				for(int i=0; i<roomsize; i++)
				{
					players[i]=playerList.remove(0);
				}
				Room r = new Room(players, roomsize);
				Roomexecutor.execute(r);
			}
			Socket s = new ServerSocket(port).accept();
			Player p = new Player(s);
			playerList.add(p);
			Playerexecutor.execute(p);
		}
	}
}
