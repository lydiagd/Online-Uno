/**
 * 
 */
package online_uno;

/**
 * @author Lydia
 *
 */
public abstract class Card {
		String color;
		
		Card(String col)
		{
			color = col;
		}
		
		public abstract void action();
		public abstract String stringout();

}
