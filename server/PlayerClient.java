package serverPackage;
import java.util.concurrent.*;
import java.net.*;

public class PlayerClient implements Runnable{
  
  Player p;
  private Socket socket;
  Boolean turn = false;
  PlayerGUI gui;
  String cardResult;
  
  
  public static void main(String[] args){
       while (true) {
              String ans = null;
              try {
                  System.out.print("Enter the server hostname: ");
                  String hostname = in.nextLine();
                  System.out.print("Enter the server port number: ");
                  ans = in.nextLine();
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
          
          
          
          while(true){

            //read in strings from server side

             String str = (String) ois.readObject();

              if(str == "your turn")
              {
                //play your turn based from GUI
                  //while(gui.moveMade == "NONE"){}
                  //cardResult = gui.moveMade; //store in a string


                //send end turn message

              }
              else //if not a command that's recognized, probably player log
              {
                System.out.println(str);
              }

            //update yourself

            //update action log for last player's move

            //update num of cards of everyone
            //gui.update
            
            //update top card
            //gui.setFaceUp(Card top)


        }  

          
          
        }
          catch (SocketException se) {
            System.out.println(TimeFormatter.getTimeString() + " Server dropped connection");
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
