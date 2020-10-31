package ClientPackage;
import java.io.IOException;
import java.net.*;

public class Client {
/*goals for this client class:
 * connect to server
 * once connected to server, allow user to take appropriate actions
 * appropriate actions include: querying for stats if registered, playing the game if in a game room
 */
	private static final int port = 6789;
	public void main(String[] args) throws UnknownHostException, IOException
	{
		//for now, client will only connect to an uno server being hosted on the same machine
		String server = "localhost";
		Socket s = new Socket(server, port);
		/*will need to collaborate with GUI team for this section
		from this point to the end of the main we can send output to server
		but what form that output takes is dependent on what users can do in GUI
		*/
		boolean exit = false;
		while(!exit)
			//ask for user input until user wishes to exit
		{
			
		}
		
		
		s.close();
	}
}
