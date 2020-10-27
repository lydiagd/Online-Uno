package online_uno;

public class Normal_Card extends Card{
	
		int number;

	Normal_Card(String col, int num) {
		super(col);
		number = num;
	}

	@Override
	public void action() {
		//normal cards don't have an action
	}

	@Override
	public String stringout() {
		String s = "";
		s = s.concat(super.color + " " + Integer.toString(number));
		
		return s;
	}

}
