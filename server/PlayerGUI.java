package server;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
//
//
//TODO:  setFaceUp is for the playerCLient not me, print out the winner
//Only play viable moves
//
//
//
//
//
//
//
//

import GameTable.*;

public class PlayerGUI{
	private String moveMade = "None";
	//handles the dimensions of the pop-up
	private JFrame frame;
	private JPanel panel;
	
	private JLabel tracker;
	
	//labeling of the players and the number of cards in their hands
	private JLabel player1, player2, player3, player4;

	private JLabel cards1, cards2, cards3, cards4;
	private JLabel pages;
	
	//the facedown  and faceup piles
	private JLabel blank;
	private JButton back;
	
	private JLabel wildcard;
	private JComboBox colors;
	private JButton select;
	
	//Button to play a specific card
	private JButton play;
	//button to see the other cards in your hand
	private JButton nextPage;
	//Drop down box for player to pick which card to play
	private JComboBox hand;
	//TODO: Wildcard drop down box
	
	private int page = 0;
	private String[] names;
	private String[] usernames;
	private int[] hands;
	private String myName;
	
	//images of the cards in your hand
	private JLabel hand0, hand1, hand2, hand3, hand4, hand5, hand6;

	
	//in the future, GUI should also take in a Player class so that it can tell the back-end whenever a move is made
	//Constructor of the GUI,
	public PlayerGUI(List<String> handIn, String username, String firstCard) throws IOException {
		//create the framework for the application
		
		this.myName = username;
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
		
		pages = new JLabel("");
		
		play = new JButton("Play");
		nextPage = new JButton("Next Page");
		
		
		wildcard = new JLabel("What color?");
		String[] unoColors = {"Yellow", "Red", "Green", "Blue"};
		colors = new JComboBox(unoColors);
		select = new JButton("Select");

		tracker = new JLabel("");
		tracker.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tracker.setBounds(10, 0, 450, 40);
		panel.add(tracker);
		
		//sets up the image of where the face up cards are going to be placed
		BufferedImage blankIm = ImageIO.read(new File("CardImages/blank.PNG"));
		Image resizeBlank = blankIm.getScaledInstance(100, -1, 0);
		blank = new JLabel(new ImageIcon(resizeBlank));
		blank.setBounds(355, 140, 110, 200); panel.add(blank);
		setFaceUp(firstCard);
		
		//sets up the image of where the face down deck is going to be placed
		BufferedImage backIm = ImageIO.read(new File("CardImages/back.PNG"));
		Image resizeBack = backIm.getScaledInstance(100, -1, 0);
		back = new JButton(new ImageIcon(resizeBack));
		back.setBounds(225, 165, 100, 150); panel.add(back);
		
		
		
		//display the cards in your hand
		Image resizeHand = blankIm.getScaledInstance(60, -1, 0);
		hand0 = new JLabel(new ImageIcon(resizeHand));hand0.setBounds(15, 400, 60, 120);panel.add(hand0);
		hand1 = new JLabel(new ImageIcon(resizeHand));hand1.setBounds(95, 400, 60, 120);panel.add(hand1);
		hand2 = new JLabel(new ImageIcon(resizeHand));hand2.setBounds(175, 400, 60, 120);panel.add(hand2);
		hand3 = new JLabel(new ImageIcon(resizeHand));hand3.setBounds(255, 400, 60, 120);panel.add(hand3);
		hand4 = new JLabel(new ImageIcon(resizeHand));hand4.setBounds(335, 400, 60, 120);panel.add(hand4);
		hand5 = new JLabel(new ImageIcon(resizeHand));hand5.setBounds(415, 400, 60, 120);panel.add(hand5);
		hand6 = new JLabel(new ImageIcon(resizeHand));hand6.setBounds(495, 400, 60, 120);panel.add(hand6);
		
		setHand(handIn);
		
		//create drop down menu based on the array of cards provided

		hand = new JComboBox(names);
		
		//TODO: implement function that determines if the card selected is a valid play
		//when the player clicks the Play button, the card selected by the drop down is played
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//only play a card if their hand isn't empty
					
				if(names.length > 0) {
					//if they are able to play their last card, they win
					if(names.length==1)
						System.out.println("YOU WIN!");
						String answer = (String)hand.getSelectedItem();
						if(answer.compareTo("Wildcard") == 0) {
						select.setVisible(true);
						colors.setVisible(true);
						wildcard.setVisible(true);
					}
					else{
					//put the selected card in the face up pile and remove from your hand
						moveMade = answer;
						//setFaceUp(answer);
						//removeCard(answer);
					}
				}
				
			}
		});
		
		select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//only play a card if their hand isn't empty
				String color = (String) colors.getSelectedItem();
				String answer = color + " -1";
				//setFaceUp(answer);
				//removeCard(answer);
				moveMade = answer;
				select.setVisible(false);
				colors.setVisible(false);
				wildcard.setVisible(false);
			}
		});
		
		
		
		//if you click the Next Page button, change the cards shown in your hand
		nextPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePage();
			}
		});
		
		//TODO: call another function that tells the Server that this player needs another card to add to their hand
		//when you click the face down deck, you should draw a card. Hasn't been created yet so it says Play Ball because I was watching a Dodgers game
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Play ball");
				moveMade = "draw";
			}
		});
		
		//TODO: create function that updates the other players' num of cards
		//Text displaying each player in the room and the number of cards each has
		
		hands = new int[3];
		for(int i = 0; i<3; i++)
			hands[i] = 7;
		
		player1 = new JLabel("You: " + username); cards1 = new JLabel("Cards: " + names.length);
		player2 = new JLabel("2");cards2 = new JLabel("Cards: " + hands[0]);
		player3 = new JLabel("3");cards3 = new JLabel("Cards: " + hands[1]);
		player4 = new JLabel("4");cards4 = new JLabel("Cards: " + hands[2]);
		
		//display Player 1's text
		player1.setBounds(120, 380, 280, 40);
		cards1.setBounds(320, 380, 100, 40);
		pages.setBounds(420, 380, 100, 40);
		player1.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards1.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player1); panel.add(cards1); panel.add(pages);
		
		//display Player 2's text
		BufferedImage p2 = ImageIO.read(new File("CardImages/p2.PNG"));
		Image resizep2 = p2.getScaledInstance(100, -1, 0);
		player2.setIcon(new ImageIcon(resizep2));
		player2.setIconTextGap(-184 + (8*(16-player2.getText().length()/2)));
		player2.setBounds(594-(8*player2.getText().length()/2), 100, 280, 200);
		cards2.setBounds(560, 230, 100, 40);
		player2.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards2.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player2); panel.add(cards2);
		
		//display Player 3's text
		BufferedImage p3 = ImageIO.read(new File("CardImages/p3.PNG"));
		Image resizep3 = p3.getScaledInstance(100, -1, 0);
		player3.setIcon(new ImageIcon(resizep3));
		player3.setIconTextGap(-184 + (8*(16-player3.getText().length()/2)));
		player3.setBounds(319-(8*player3.getText().length()/2), -20, 280, 200);
		cards3.setBounds(310, 70, 100, 130);
		player3.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards3.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player3); panel.add(cards3);
		
		//display Player 4's text
		BufferedImage p4 = ImageIO.read(new File("CardImages/p4.PNG"));
		Image resizep4 = p4.getScaledInstance(100, -1, 0);
		player4.setIcon(new ImageIcon(resizep4));
		player4.setIconTextGap(-184 + (8*(16-player4.getText().length()/2)));
		player4.setBounds(84-(8*player4.getText().length()/2), 100, 280, 200);
		cards4.setBounds(50, 230, 100, 40);
		player4.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards4.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player4); panel.add(cards4);
		 
		//add each button to the application
		play.setBounds(580, 420, 100, 40); panel.add(play);
		hand.setBounds(570,470,120,40); panel.add(hand);
		nextPage.setBounds(580, 370, 100, 40); panel.add(nextPage);
		select.setBounds(290, 340, 100, 40); panel.add(select);
		colors.setBounds(160, 340, 120, 40); panel.add(colors);
		wildcard.setBounds(70, 340, 100, 40); panel.add(wildcard);
		
		select.setVisible(false); colors.setVisible(false); wildcard.setVisible(false);

		//display the application
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("UNO!!");
		frame.pack();
		frame.setVisible(true);
		Insets insets = frame.getInsets();
		frame.setSize(700 + insets.left + insets.right,
		              525 + insets.top + insets.bottom);
	}
	
	//The main only creates a sample hand with the GUI, this can be altered for when we implement the Player class
//	public static void main(String[] args) throws InterruptedException {
//		try {
//			ArrayList<String> cards = new ArrayList<String>();
//			cards.add(("Wildcard"));
//			cards.add(("yellow 4"));
//			cards.add(("red 5"));
//			cards.add("blue 4");
//			cards.add("green 4");
//			cards.add("red 1");
//			cards.add("yellow 9");
//			
//			String[] others = {"Player2FillerTex", "Player3", "Player4"}; 
//			PlayerGUI GUI = new PlayerGUI(cards, "I");
//			GUI.setUsername(others);
//			
//			//check if You play the card
//			GUI.playMove("I played yellow -1");
//			GUI.playMove("I played red 1");
//			GUI.playMove("I played draw");
////			GUI.playMove("Player4", "draw");
//			GUI.yourTurn();
////			while (true) {
////				String response = GUI.getMove();
////				while(response.compareTo("None") == 0) {
////					response = GUI.getMove();
////					//timer reaches 90 sec, break;
////					System.out.println(response);
////					}
////				if(response.compareTo("Draw") == 0) {
////					//GUI.drawCard(newCard);
//////					for(every player)
//////						GUI.playMove(user, null, 0);
////					//GUI.drawCard("Wildcard");
////					System.out.println("B" + response);
//////					GUI.moveMade = "None";
////				}
////				else {
////					System.out.println("A" + response);
////					GUI.setFaceUp(response);
////					GUI.removeCard(response);
////					
//////					Thread.sleep(1000);
//////					GUI.moveMade = "None";
////				}
////			}
//			
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	//function that changes which card is displayed in the face up pile
	//TODO: Card not String
	private void setFaceUp(String temp) {
		BufferedImage image;
		try {
			String[] card = temp.split("\s");
			if(card[1].equals("-1")) {
				card[1] = card[0];
				card[0] = "wildcard";
			}
			image = ImageIO.read(new File("CardImages/" + card[0].toLowerCase() + card[1] + ".PNG"));
			Image resize = image.getScaledInstance(110, -1, 0);
			blank.setIcon(new ImageIcon(resize));
		}
		catch(IOException ex) {}
	}
	
	//prints the cards on the bottom to show the player's hand
	private void displayHand() {
		int smallest = 7*page;
		int largest = 7*(page+1);
		for(int i = smallest; i < largest; i++) {
			JLabel curr = getHandCard(i%7);
			BufferedImage image;
			if(i<names.length) {
				try {
					String newCard = names[i];
					String[] card = newCard.split("\s");
					if(card.length == 1) {
						image = ImageIO.read(new File("CardImages/" + card[0] + ".PNG"));
					}
					else {
						image = ImageIO.read(new File("CardImages/" + card[0].toLowerCase() + card[1] + ".PNG"));
					}
					Image resize = image.getScaledInstance(60, -1, 0);
					curr.setIcon(new ImageIcon(resize));
					
				}
				catch(IOException ex) {}
			}
			else {
				try {
					image = ImageIO.read(new File("CardImages/blank.PNG"));
					Image resizeBlank = image.getScaledInstance(60, -1, 0);
					curr.setIcon(new ImageIcon(resizeBlank));
				}
				catch(IOException ex) {}
			}
		}
		pages.setText("Page " + (page+1) + " of " + ((names.length-1)/7 + 1));
	}
	
	//remove a card from the drop down menu and from your playerHand array
	private void removeCard(String temp) {
		
		hand.removeAllItems();
		String[] parse = temp.split("\s");
		if(parse[1].equals("-1"))
			temp = "Wildcard";
		String[] newNames = new String[names.length - 1];
		boolean removed = false;
		int j = 0;
		for(int i = 0; i< newNames.length; i++) {
			if(temp.compareTo(names[i]) == 0) {
				if(!removed) {
					removed = true;
					j++;
				}
			}
			newNames[i] = names[j++];
			hand.addItem(newNames[i]);
		}
		if (!removed) {
			hand.addItem(names[names.length-1]);
			return;
		}
		this.names = newNames;
		displayHand();
		cards1.setText("Cards: " + names.length);
	}
	
	//updates your hand variable
	public void setHand(List<String> cards) {
		this.names = new String[cards.size()];
		for(int i = 0; i <cards.size(); i++)
			names[i] = cards.get(i);
		
		page = 0;
		pages.setText("Page " + (page+1) + " of " + ((names.length-1)/7 + 1));
		displayHand();
	}
	
	//change the page displaying cards
	private void changePage() {
		int maxPages = (names.length-1)/7;
		if(page == maxPages)
			page = 0;
		else
			page++;
		pages.setText("Page " + (page+1) + " of " + ((names.length-1)/7 + 1));
		displayHand();
	}
	
	public void setUsername(String[] u) {
		this.usernames = u;
		player2.setText(usernames[0]);
		player2.setIconTextGap(-184 + (8*(16-player2.getText().length()/2)));
		player2.setBounds(554-(2*player2.getText().length()), 100, 280, 200);

		player3.setText(usernames[1]);
		player3.setIconTextGap(-184 + (8*(16-player3.getText().length()/2)));
		player3.setBounds(299-(2*((player3.getText().length()+1))), -20, 280, 200);
		
		player4.setText(usernames[2]);
		player4.setIconTextGap(-184 + (8*(16-player4.getText().length()/2)));
		player4.setBounds(44-(2*player4.getText().length()), 100, 280, 200);
	}
	
	public void playMove(String moveMade) {
		String[] parsing = moveMade.split("\s");
		String user = parsing[0];
		String played;
		if(parsing.length == 3) {
			played = "draw";
		}
		else {
			played = parsing[2] + " " + parsing[3];
		}
		String response = "";
		String chat = "";
		String user2 = user + " has ";
		if(user.equals(myName)) {
			user2 = "You have ";
			if(!played.equals("draw"))
				removeCard(played);
		}
		if(played.equals("draw")) {
			chat = user2 + "drawn a card.";
			for(int i = 0; i < 3; i++) {
				if(user.compareTo(usernames[i]) == 0) {
					hands[i] = hands[i] + 1;
				}
			}
		}
		else {
			response = played;
			String[] wildcard = response.split("\s");
			if(wildcard[1].equals("-1"))
				response = "Wildcard " + wildcard[0];
			chat = user2 + "played " + response + ".";
			setFaceUp(played);
			for(int i = 0; i < 3; i++) {
				if(user.compareTo(usernames[i]) == 0) {
					hands[i] = hands[i] - 1;
					if(hands[i] == 0) {
						System.out.println("You lose.");
					}
				}
			}
		}
		updateNums();
		tracker.setText(chat);
	}
	public void yourTurn() {
		tracker.setText(tracker.getText() + " Your turn.");
	}
	
	public void drawCard(String newCard) {
		String[] newHand = new String[names.length + 1];
		newHand[0] = newCard;
		for(int i = 1; i< newHand.length; i++)
			newHand[i] = names[i-1];
		names = newHand;
		cards1.setText("Cards: " + names.length);
		displayHand();
	}
	
	//helper function to determine which card needs to be updated
	private JLabel getHandCard(int num) {
		switch (num) {
			case 0:
				return hand0;
			case 1:
				return hand1;
			case 2:
				return hand2;
			case 3:
				return hand3;
			case 4:
				return hand4;
			case 5:
				return hand5;
			case 6:
				return hand6;
			default:
				return hand0;
		}
	}

	public String getMove() {
		while(moveMade.equals("None")) {}
		return moveMade;
	}
	public void setMove() {
		moveMade = "None";
	}
	
	private void updateNums() {
		cards2.setText("Cards: " + hands[0]);
		cards3.setText("Cards: " + hands[1]);
		cards4.setText("Cards: " + hands[2]);
	}
}