

import java.util.*;
import java.util.concurrent.*;

public class PlayerQueue {
	public List<Player> q = new ArrayList<>();
	private Semaphore semaphore;
	
	//4 players
	
	public PlayerQueue() {
		semaphore = new Semaphore(1); //mutually exclusive
	}
	
	public void AddPlayer(Player p) {
		q.add(p);
	}
	
	public void RemovePlayer(Player p) {
		q.remove(p);
	}
	
	public boolean IsEmpty() {
		return q.isEmpty();
	}
	
	public int Size() {
		return q.size();
	}
	
	public Player Top() {
		return q.get(0);
	}
	
	public Semaphore GetSemaphore() {
		return semaphore;
	}
	
	public void NextTurn() {
		Player p = q.remove(0);
		q.add(p);
	}
}
