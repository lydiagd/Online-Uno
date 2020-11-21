package project.GameTable;

public class Reverse_Card extends Card {
	String c;

	Reverse_Card(String col) {
		super(col);
		
	}


	@Override
	public String stringout() {
		return (super.color + " reverse");
	}


	@Override
	public String getColor() {
		return super.color;
	}


	@Override
	public int getNumber() {
		return 10;
	}


	@Override
	protected void setColor(String newColor) {
		super.color = newColor;
	}

}