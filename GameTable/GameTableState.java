package online_uno;

import java.util.HashMap;

public class GameTableState {
	  private String lastMove;
	  private HashMap<String, Integer> playersHandSize; //key: username, value: hand size
	  private String topCard;
	  
	  public GameTableState() {
	    
	  }
	  
	  // public GameTableState SendState() {
	  //   return this;
	  // }
	  
	  public String GetLastMove() {
	    return lastMove;
	  }
	  
	  public HashMap<String, Integer> GetPlayersHandSize() {
	    return playersHandSize; 
	  }
	  
	  public String GetTopCard() {
	    return topCard;
	  }
	  
	  public void SetLastMove(String lastMove) {
	    this.lastMove = lastMove;
	  }
	  
	  public void SetPlayersHandSize(HashMap<String, Integer> playersHandSize) {
	    this.playersHandSize = playersHandSize;
	  }
	  
	  public void SetTopCard(String topCard) {
	    this.topCard = topCard;
	  }
	}
	       