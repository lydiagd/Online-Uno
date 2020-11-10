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
  public static final int roomsize = 1;
  public static final int maxPlayers = 20;
  //have port be 1024<=port<=49151 since they can be used by any app
  public static final int port = 1234; 
//		  6790;
  
  
  private static ArrayList<GameTable> GameTableList = new ArrayList<GameTable>(); //saves game tables
  
	  public static void main (String[] args) throws IOException
	  {
		ArrayList<Player> playerList = new ArrayList<Player>(); //saves num players joined
		int startIdx = 0;
		int curIdx = 0;
		ServerSocket sSocket = new ServerSocket(port);
		System.out.println("Starting Server");
	
	    while(true)
	    {
	      System.out.println("Searching for connection");
	      Socket s = sSocket.accept();
	      Player p = new Player(s); //don't handle username up here
	      System.out.println("New player added");
	      
	      playerList.add(p);
	      curIdx++;
	      p.oos.writeObject(playerList.size()); //tell client how many players are on the server
	      p.oos.flush();
	      System.out.println("current room size: " + playerList.size());
	      
//	      if(playerList.size() >= roomsize){ //make a room
	      if((curIdx-startIdx) >= roomsize){
	    	  
	    	System.out.println("Starting new room");
	        
	        ArrayList<Player> dupPlayerList = new ArrayList<Player>();
	        for(int i = startIdx; i < curIdx; i++)
	        {
//	        	Socket newS = pl.socket;
//	            Player newP = new Player(newS);
	            dupPlayerList.add(playerList.get(i));
	            playerList.get(i).oos.writeObject("start");
	            playerList.get(i).oos.flush();
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
	        
//	        playerList.clear();
	        startIdx = curIdx;
	            
	    }
	  }
	}
}