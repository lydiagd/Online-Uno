package online_uno;

public class Wild_Card extends Card {

	Wild_Card(String col) {
		super(col);
		
	}

	@Override
	public void action() {
		//player can set new color
		
	}

	@Override
	public String stringout() {
		return (super.color);
	}

}
