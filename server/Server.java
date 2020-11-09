package server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import GameTable.GameTable;
import GameTable.Player;
import GameTable.PlayerQueue;

public class Server {
  public static final int rooms = 3;
  public static final int roomsize = 4;
  public static final int maxPlayers = 20;
  //have port be 1024<=port<=49151 since they can be used by any app
  public static final int port = 6789;
  private ArrayList<Player> playerList = new ArrayList<Player>(); //saves num players joined
  
  private ArrayList<GameTable> GameTableList = new ArrayList<GameTable>(); //saves game tables
	  public void main (String[] args) throws IOException
	  {
	
	    while(true)
	    {
	      
	      Socket s = new ServerSocket(port).accept();
	      Player p = new Player(s); //don't handle username up here
	      System.out.println("New player added");
	      
	      
	      if(playerList.size() >= roomsize){
	    	  
	    	System.out.println("Starting new room");
	        
	        ArrayList<Player> dupPlayerList = new ArrayList<Player>();
	        for(Player pl : playerList)
	        {
	            Player newP = new Player(pl.socket);
	            dupPlayerList.add(newP);
	        }
	        
	        //looped through
	        //recreated each variable pass in socket
	        //playerList.clear()
	        
	        PlayerQueue pq = new PlayerQueue(dupPlayerList); //!!need deep copy? How to transfer players?
	        
	        GameTable gt = new GameTable(pq);
	        GameTableList.add(gt);
	        //gt.SetPlayerQueue(pq);
	        
	        //maybe?
	        gt.start();
	        
	        playerList.clear();
	            
	    }
	  }
	}
}