package server;
import java.io.IOException;

import GameTable.Player;

public class loginThread extends Thread {
	
	Server server;
	Player player;
	Boolean loggedIn = false;
	//login gui? Login page?
	
	public loginThread(Server s, Player p)
	{
		this.server = s;
		this.player = p;
		
		this.start();
		
	}
	
	public void run() {
		
		try { //isguest bool in player
			String guestOrUser = (String) player.ois.readObject();
			if(guestOrUser.equals("user"))
			{
				while(!loggedIn)
				{
					String username = (String) player.ois.readObject();
					String password = (String) player.ois.readObject();
					
					//if found, send good message, else send incorrect login **database**
					player.SetName(username);
					loggedIn = true;
					player.oos.writeObject("authenticated");
				}
			}
			else
			{
				//is a guest
				player.SetName("Player"+(int)(Math.random() * (5000) +1));
				player.oos.writeObject(player.GetName());
				player.oos.flush();
			}
			
			//after authenticated, add to server's player list and update num players on server
			server.playerList.add(player);
		    System.out.println("New player added");
		    player.oos.writeObject(server.playerList.size());
		    player.oos.flush();
		    server.curIdx++;
		    System.out.println("current server size: " + server.playerList.size());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
