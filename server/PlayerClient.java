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
  //PlayerGUI gui;
  String cardResult;

  
  
  @SuppressWarnings({ "unchecked", "unused" })
public static void main(String[] args){
	  Scanner sc = new Scanner(System.in);
	  ArrayList<String> hand; // color #
	  
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
	            	String usernameResponse = sc.nextLine();
	            	oos.writeObject(usernameResponse);
	            	oos.flush();
	            	System.out.print("Password: ");
	            	String passResponse = sc.nextLine();
	            	oos.writeObject(passResponse);
	            	oos.flush();
	            	
	            	String dbResult = (String) ois.readObject();
	            	if(dbResult.equals("authenticated"))
	            	{
	            		System.out.println("Thank you " + usernameResponse + ", You are authenticated!");
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
            	
            	String username = (String) ois.readObject();
            	System.out.println("Your guest username is " + username);
            }

            Integer num = (Integer) ois.readObject();

            System.out.println("Welcome to Online Uno! There are "+num+" players on the server");
           
            
            //patrick start here
            while (true) {
             //signal game starts
             String s = (String) ois.readObject();
             if(s.equals("start"))
             {
            	 System.out.println("starting the game");
            	 
            	 //gui needs to start up
            	 
                //pass player object
               //create GUI from hand
               //PlayerGUI gui = new PlayerGUI(Card[] hand)
            	 
                 //receive gamestateobj from server
     			GameTableState gs_init = (GameTableState) ois.readObject();
     			
     			PrintGameTableState(gs_init); //instead of printing, pass to gui (patrick)
     			
     			//need to send strings to back end (card value only)
             
     			//wait for a card array/Player object p 
            	 hand = (ArrayList<String>) ois.readObject();
            	 System.out.print("your hand: ");
            	 for(String str : hand)
            	 {
            		 System.out.print(" | " + str + " | ");
            	 }
              
            	 break;
             }
            }
          
          while(true){

            //read in strings from server side

			 String str = (String) ois.readObject();
			
			  if(str.equals("your turn"))
			  {
				  	//signal to gui whose turn it is
				  	
				    System.out.println("Your turn!\n Enter a card: "); //format is "yellow 6"
				    String card = sc.nextLine(); //info from gui (draw, wildcard, yellow 0, etc)
				    //gui.getmove() ; while not "none" (null?)
				    //gui variable that
				    //while(!gui.var)
				    
				    //if(gui.cardChosen.equals("yellow 1"))
				    
				    oos.writeObject(card); //signal end turn
				    oos.flush(); 
				    
				    if(card.equals("wildcard"))
				    {
				    	System.out.print("enter color to change to: ");
				    	String newColor = sc.nextLine();
				    	
				    	oos.writeObject(newColor);
				    	oos.flush();
				    }
				   
				    //update yourself, display hand
				    hand = (ArrayList<String>) ois.readObject();
				    
				    //play your turn based from GUI
				      //while(gui.moveMade == "NONE"){}
				      //cardResult = gui.moveMade; //store in a string
				      
				      //don't worry about error for console version           
			
			  }
			  //else if for not your turn
			  else if(str.equals("end")) {
				  String finalMessage = (String) ois.readObject();
				  System.out.println(finalMessage);
				  break;
			  }

			
			//get game state object 
			GameTableState gs = (GameTableState) ois.readObject();
			PrintGameTableState(gs);
			

			//receive list of strings to display hand
			
			System.out.println();
			System.out.print("Current hand: | ");
			for(String s : hand)
			{
			     System.out.print(s + " | ");
			}
			System.out.println("\n");
            
            //gui.update
            
            //update top card
            //gui.setFaceUp(Card top)
        }  

          
 
        } catch (SocketException se) {
            System.out.println(" Server dropped connection");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        }
    
  }
  
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
