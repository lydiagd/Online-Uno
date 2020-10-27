package online-uno;

//Author: Patrick Jarvis

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
public class PlayerGUI{
	//handles the dimensions of the pop-up
	private JFrame frame;
	private JPanel panel;
	
	//labeling of the players and the number of cards in their hands
	private JLabel player1;
	private JLabel player2;
	private JLabel player3;
	private JLabel player4;

	private JLabel cards1;
	private JLabel cards2;
	private JLabel cards3;
	private JLabel cards4;
	private JLabel pages;
	
	//the facedown  and faceup piles
	private JLabel blank;
	private JButton back;
	
	//Button to play a specific card
	private JButton play;
	//button to see the other cards in your hand
	private JButton nextPage;
	//Drop down box for player to pick which card to play
	private JComboBox hand;
	
	private int page = 0;
	private String[] playerHand;
	private boolean canPlay = false;
	
	//images of the cards in your hand
	private JLabel hand0;
	private JLabel hand1;
	private JLabel hand2;
	private JLabel hand3;
	private JLabel hand4;
	private JLabel hand5;
	private JLabel hand6;
	
	//Constructor of the GUI,
	public PlayerGUI(String[] handIn) throws IOException {
		//create the framework for the application
		this.playerHand = handIn;
		frame = new JFrame();
		panel = new JPanel();
		panel.setLayout(null);
		
		pages = new JLabel("Page " + (page+1) + " of " + (playerHand.length/7 + 1));
		
		play = new JButton("Play");
		nextPage = new JButton("Next Page");

		//sets up the image of where the face up cards are going to be placed
		BufferedImage blankIm = ImageIO.read(new File("CardImages/blank.PNG"));
		Image resizeBlank = blankIm.getScaledInstance(100, -1, 0);
		blank = new JLabel(new ImageIcon(resizeBlank));
		blank.setBounds(355, 140, 100, 200);
		panel.add(blank);
		
		//sets up the image of where the face down deck is going to be placed
		BufferedImage backIm = ImageIO.read(new File("CardImages/back.PNG"));
		Image resizeBack = backIm.getScaledInstance(100, -1, 0);
		back = new JButton(new ImageIcon(resizeBack));
		back.setBounds(225, 165, 100, 150);
		panel.add(back);
		
		
		
		//display the cards in your hand
		Image resizeHand = blankIm.getScaledInstance(60, -1, 0);
		hand0 = new JLabel(new ImageIcon(resizeHand));
		hand1 = new JLabel(new ImageIcon(resizeHand));
		hand2 = new JLabel(new ImageIcon(resizeHand));
		hand3 = new JLabel(new ImageIcon(resizeHand));
		hand4 = new JLabel(new ImageIcon(resizeHand));
		hand5 = new JLabel(new ImageIcon(resizeHand));
		hand6 = new JLabel(new ImageIcon(resizeHand));
		hand0.setBounds(15, 400, 60, 120);
		hand1.setBounds(95, 400, 60, 120);
		hand2.setBounds(175, 400, 60, 120);
		hand3.setBounds(255, 400, 60, 120);
		hand4.setBounds(335, 400, 60, 120);
		hand5.setBounds(415, 400, 60, 120);
		hand6.setBounds(495, 400, 60, 120);
		panel.add(hand0);
		panel.add(hand1);
		panel.add(hand2);
		panel.add(hand3);
		panel.add(hand4);
		panel.add(hand5);
		panel.add(hand6);
		
		
		
		//print the beginning hand
		displayHand();
		
		//create drop down menu based on the array of cards provided
		hand = new JComboBox(playerHand);
		
		//TODO: implement function that determines if the card selected is a valid play
		//when the player clicks the Play button, the card selected by the drop down is played
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//only play a card if their hand isn't empty
				if(canPlay) {
					if(playerHand.length > 0) {
						//if they are able to play their last card, they win
						if(playerHand.length==1)
							System.out.println("YOU WIN!");
						String answer = (String) hand.getSelectedItem();
						//put the selected card in the face up pile and remove from your hand
						setFaceUp(answer);
						removeCard(answer);
					}
				}
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
				if(canPlay)
					System.out.println("Play ball");
				
			}
		});
		
		//TODO: create function that updates the other players' num of cards
		//Text displaying each player in the room and the number of cards each has
		player1 = new JLabel("You: Guest01823930412");
		cards1 = new JLabel("Cards: " + playerHand.length);
		player2 = new JLabel("Guest01823930412");
		cards2 = new JLabel("Cards: " + playerHand.length);
		player3 = new JLabel("Guest01823930413");
		cards3 = new JLabel("Cards: " + playerHand.length);
		player4 = new JLabel("Guest01823930414");
		cards4 = new JLabel("Cards: " + playerHand.length);
		
		//display Player 1's text
		player1.setBounds(120, 380, 280, 40);
		cards1.setBounds(320, 380, 100, 40);
		pages.setBounds(420, 380, 100, 40);
		player1.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards1.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player1);
		panel.add(cards1);
		panel.add(pages);
		
		//display Player 2's text
		BufferedImage p2 = ImageIO.read(new File("CardImages/p2.PNG"));
		Image resizep2 = p2.getScaledInstance(100, -1, 0);
		player2.setIcon(new ImageIcon(resizep2));
		player2.setIconTextGap(-120);
		player2.setBounds(530, 100, 280, 200);
		cards2.setBounds(560, 230, 100, 40);
		player2.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards2.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player2);
		panel.add(cards2);
		
		//display Player 3's text
		BufferedImage p3 = ImageIO.read(new File("CardImages/p3.PNG"));
		Image resizep3 = p3.getScaledInstance(100, -1, 0);
		cards3.setIcon(new ImageIcon(resizep3));
		cards3.setIconTextGap(-87);
		player3.setBounds(255, 0, 280, 40);
		cards3.setBounds(280, 0, 100, 130);
		player3.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards3.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player3);
		panel.add(cards3);
		
		//display Player 4's text
		BufferedImage p4 = ImageIO.read(new File("CardImages/p4.PNG"));
		Image resizep4 = p4.getScaledInstance(100, -1, 0);
		player4.setIcon(new ImageIcon(resizep4));
		player4.setIconTextGap(-120);
		player4.setBounds(20, 100, 280, 200);
		cards4.setBounds(50, 230, 100, 40);
		player4.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		cards4.setFont(new Font(player1.getFont().getName(), Font.PLAIN, 16));
		panel.add(player4);
		panel.add(cards4);
		 
		//add each button to the application
		play.setBounds(580, 420, 100, 40);
		panel.add(play);
		
		hand.setBounds(570,470,120,40);
		panel.add(hand);
		
		nextPage.setBounds(580, 370, 100, 40);
		panel.add(nextPage);

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
	public static void main(String[] args) {
		try {
			ArrayList<String> cards = new ArrayList<String>();
			cards.add("Yellow 4");
			cards.add("Green 4");
			cards.add("Green 9");
			cards.add("Blue 7");
			cards.add("Yellow 2");
			cards.add("Red 4");
			cards.add("Red 9");
			cards.add("Blue 1");
			cards.add("Blue 0");
			cards.add("Blue 7");
			cards.add("Yellow 2");
			cards.add("Red 4");
			cards.add("Yellow 7");
			cards.add("Blue 4");
			cards.add("Green 2");
			
			String[] cardsH = new String[cards.size()];
			int x = 0;
			for(String i : cards) {
				cardsH[x++] = i;
			}
			PlayerGUI GUI = new PlayerGUI(cardsH);
			GUI.changeTurns(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//function that changes which card is displayed in the face up pile
	public void setFaceUp(String newCard) {
		BufferedImage image;
		try {
			String[] card = newCard.split("\s");
			if(card.length != 2)
				throw new IOException();
			image = ImageIO.read(new File("CardImages/" + card[0].toLowerCase() + card[1] + ".PNG"));
			Image resize = image.getScaledInstance(100, -1, 0);
			blank.setIcon(new ImageIcon(resize));
		}
		catch(IOException ex) {}
	}
	
	//prints the cards on the bottom to show the player's hand
	public void displayHand() {
		int smallest = 7*page;
		int largest = 7*(page+1);
		for(int i = smallest; i < largest; i++) {
			JLabel curr = getHandCard(i%7);
			BufferedImage image;
			if(i<playerHand.length) {
				try {
					String newCard = playerHand[i];
					String[] card = newCard.split("\s");
					image = ImageIO.read(new File("CardImages/" + card[0].toLowerCase() + card[1] + ".PNG"));
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
	}
	
	//remove a card from the drop down menu and from your playerHand array
	public void removeCard(String select) {
		hand.removeAllItems();
		
		String[] newHand = new String[playerHand.length-1];
		boolean removed = false;
		int j = 0;
		for(int i = 0; i< newHand.length; i++) {
			if(select.compareTo(playerHand[j])==0) {
				if(!removed) {
					removed = true;
					j++;
				}
			}
			newHand[i] = playerHand[j++];
			hand.addItem(newHand[i]);
		}
		setHand(newHand);
		cards1.setText("Cards: " + playerHand.length);
	}
	
	//updates your hand variable
	public void setHand(String[] cards) {
		this.playerHand = cards;
		
		page = 0;
		pages.setText("Page " + (page+1) + " of " + ((playerHand.length-1)/7 + 1));
		displayHand();
	}
	
	//change the page displaying cards
	public void changePage() {
		int maxPages = (playerHand.length-1)/7;
		if(page == maxPages)
			page = 0;
		else
			page++;
		pages.setText("Page " + (page+1) + " of " + ((playerHand.length-1)/7 + 1));
		displayHand();
	}
	
	//helper function to determine which card needs to be updated
	public JLabel getHandCard(int num) {
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
	
	public void changeTurns(boolean playing) {
		this.canPlay = playing;
	}
}



//Server gives the GUI an ArrayList that has the cards in my hand
