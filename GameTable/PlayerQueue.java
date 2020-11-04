

import java.util.*;
import java.util.concurrent.*;

public class PlayerQueue {
	private Queue<Player> q = new LinkedList<>();
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
		return q.peek();
	}
	
	public Semaphore GetSemaphore() {
		return semaphore;
	}
	
	public void NextTurn() {
		Player p = q.remove();
		q.add(p);
	}
}
