package GameTable;
import java.io.Serializable;
import java.util.HashMap;

public class GameTableState implements Serializable {
	  private String lastMove;
	  private HashMap<String, Integer> playersHandSize; //key: username, value: hand size
	  private String topCard;
	  
	  public GameTableState() {

	  }
	  
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
		  System.out.println("here");
	    this.lastMove = lastMove;
	  }
	  
	  public void SetPlayersHandSize(HashMap<String, Integer> playersHandSize) {
	    this.playersHandSize = playersHandSize;
	  }
	  
	  public void SetTopCard(String topCard) {
	    this.topCard = topCard;
	  }
}
	       