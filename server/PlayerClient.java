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
public class PlayerClient implements Runnable{
  
  Player p;
  private static Socket s;
  Boolean turn = false;
  //PlayerGUI gui;
  String cardResult;
  ArrayList<String> hand;
  
  
  public static void main(String[] args){
	  Scanner sc = new Scanner(System.in);
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
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            Integer num = (Integer) ois.readObject();

            System.out.println("there are "+num+" players on the server");
            while (true) {
             //signal game starts
             String s = (String) ois.readObject();
             if(s == "start")
             {
                //pass player object
               //create GUI from hand
               //PlayerGUI gui = new PlayerGUI(Card[] hand)
               
             }
             //wait for a card array/Player object p 
               
            //wait for opponents
            //create a gui and pass in cards 
              
             break;
            }
         
          
          ArrayList<String> hand = new ArrayList<String>();
          
          while(true){

            //read in strings from server side

             String str = (String) ois.readObject();

              if(str == "your turn")
              {
                System.out.println("Enter a card: "); //format is "yellow 6"
                String card = sc.nextLine();
                
                oos.writeObject(card); //signal end turn
                oos.flush();               
               //send end turn message
                
                //play your turn based from GUI
                  //while(gui.moveMade == "NONE"){}
                  //cardResult = gui.moveMade; //store in a string
                  
                  //don't worry about error for console version           

              }
//              else //if not a command that's recognized, probably player log
//              {
//                System.out.println(str);
//                //update action log for last player's move
//              }

            //update yourself, display hand
            
            //get game state object 
            GameTableState gs = (GameTableState) ois.readObject();
            
            System.out.println("Last move: " + gs.GetLastMove());
            
            Iterator hmIterator = gs.GetPlayersHandSize().entrySet().iterator(); 
            while(hmIterator.hasNext())//update num of cards of everyone
            {
              Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
              System.out.println(mapElement.getKey() + ": " + mapElement.getValue() + " cards");
            }
            
            System.out.println();
            System.out.println("Top card of deck: " + gs.GetTopCard());
            //receive list of strings to display hand
            hand = (ArrayList<String>) ois.readObject();
            System.out.println();
            System.out.print("Current hand: ");
            for(String s : hand)
            {
                 System.out.print(s + " ");
            }
            
            //gui.update
            
            //update top card
            //gui.setFaceUp(Card top)


        }  

          
          
        }
          catch (SocketException se) {
            System.out.println(" Server dropped connection");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        }
    
  }
  
  public void run() //do we need as a thread?
  {
        
    
  }
}
