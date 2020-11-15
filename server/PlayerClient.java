package server;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.*;

import GameTable.GameTableState;
import GameTable.Player;
//import PlayerGUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

//PLAYER CLIENT CLASS
public class PlayerClient{
  
  Player p;
  private static Socket s;
  Boolean turn = false;
  private PlayerGUI gui;
  String cardResult;

  
  
  @SuppressWarnings({ "unchecked", "unused" })
  
  public static void main(String[] args)
  {
	  PlayerClient pc = new PlayerClient();
  }
  
  
public PlayerClient() {
	  Scanner sc = new Scanner(System.in);
	  ArrayList<String> hand; // color #
	  String username = null;
       while (true) {
              String ans = null;
              try {
                  System.out.print("Enter the server hostname: ");
                  String hostname = sc.nextLine();
                  System.out.print("Enter the server port number: ");
                  ans = sc.nextLine();
                  int port = Integer.parseInt(ans);
                  s = new Socket(hostname, port);
                  System.out.println();
                  break;
              } catch (NumberFormatException nfe) {
                  System.out.println("The given input " + ans + " is not a number.\n");
              } catch (UnknownHostException uhe) {
                  System.out.println("The given host is unknown.\n");
              } catch (IOException ioe) {
                  ioe.printStackTrace();
              }
          }

    
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            
            //enter username
            System.out.println("Are you a guest or a user? ");
            String response = sc.nextLine();
            
            if(response.equals("user"))
            {
            	oos.writeObject("user");
            	oos.flush();
            	while(true)
            	{
	       
	            	System.out.print("Username: ");
	            	username = sc.nextLine();
	            	oos.writeObject(username);
	            	oos.flush();
	            	System.out.print("Password: ");
	            	String passResponse = sc.nextLine();
	            	oos.writeObject(passResponse);
	            	oos.flush();
	            	
	            	String dbResult = (String) ois.readObject();
	            	if(dbResult.equals("authenticated"))
	            	{
	            		System.out.println("Thank you " + username + ", You are authenticated!");
	            		break;
	            	}
	            	else
	            	{
	            		System.out.println("Sorry, your username or password did not work");
	            	}
            	}
            }
            else {
            	oos.writeObject("guest");
            	oos.flush();
            	
            	username = (String) ois.readObject();
            	System.out.println("Your guest username is " + username);
            }

            Integer num = (Integer) ois.readObject();

            System.out.println("Welcome to Online Uno! There are "+num+" players on the server");
            while (true) {
             //signal game starts
             String s = (String) ois.readObject();
             if(s.equals("start"))
             {
            	 System.out.println("starting the game");
                //pass player object
            	 
                 //receive gamestateobj from server
     			GameTableState gs_init = (GameTableState) ois.readObject();
//T     			PrintGameTableState(gs_init);
             
             //wait for a card array/Player object p 
            	 hand = (ArrayList<String>) ois.readObject();
//T            	 System.out.print("your hand: ");
//            	 for(String str : hand)
//            	 {
//            		 System.out.print(" | " + str + " | ");
//            	 }
            	 
            	 gui = new PlayerGUI(hand, username, gs_init.GetTopCard());
            	 gui.setUsername((String[])gs_init.GetUsernames().toArray());
            	 break;
            	 
             }
            }
                   
            
          while(true){

            //read in strings from server side

			 String str = (String) ois.readObject();
			
			  if(str.equals("your turn"))
			  {
				  
				    gui.setMove();	
				    String card = gui.getMove();
				    gui.yourTurn();
				    
				    while(card.equals("None")){ card = gui.getMove();}
				    oos.writeObject(card); //signal end turn
				    oos.flush(); 
				  
				    //if you write a Card played, we don't need to read anything but just update the GUI
				    //if you write a "draw", grab a card from the GameTable Deck and give it to the GUI
				    
				    
//Let's keep the array
				    //update yourself, display hand
				    hand = (ArrayList<String>) ois.readObject();
				    //gui.setHand(hand);
				    gui.setMove();			
			  }
			  else if(str.equals("end")) {
				  String finalMessage = (String) ois.readObject();
				  System.out.println(finalMessage);
			  }
			  else if(str == null) //END PLAYER RUN
			  {
				  break;
			  }
//              else //if not a command that's recognized, probably player log
//              {
//                System.out.println(str);
//                //update action log for last player's move
//              }
			
			//get game state object 
			GameTableState gs = (GameTableState) ois.readObject();
//T			PrintGameTableState(gs);

			//need the user who played it and the card played or if they drew
			//gui.playMove(gs.GetLastMove());
			//Player2 has played Blue -1
			

			//receive list of strings to display hand
			
//T			System.out.println();
//			System.out.print("Current hand: | ");
//			for(String s : hand)
//			{
//			     System.out.print(s + " | ");
//			}
//			System.out.println("\n");
            
            
        }  

        while(true) { } //keep PlayerClient running so gui doesn't terminate
 
        } catch (SocketException se) {
            System.out.println(" Server dropped connection");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        }
    
  }
  
  //TODO: delete this after testing
  //prints the last player move, the hand sizes of each player, and the top card on the discard stack
  public static void PrintGameTableState(GameTableState gs) {
		System.out.println("Last move: " + gs.GetLastMove());
		
		Iterator hmIterator = gs.GetPlayersHandSize().entrySet().iterator(); 
		while(hmIterator.hasNext())//update num of cards of everyone
		{
		  Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
		  System.out.println(mapElement.getKey() + ": " + mapElement.getValue() + " cards");
		}
		
		System.out.println();
		System.out.println("Top card of deck: " + gs.GetTopCard());
  }
}
