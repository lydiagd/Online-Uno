package serverPackage;
import java.util.concurrent.*;
import java.net.*;

public class Player implements Runnable{
	private Socket socket;
	public Player(Socket s)
	{
		socket = s;
	}
	public void run()
	{
		//collaborate with backend player class group for this section
	}
}
