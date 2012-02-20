import java.awt.Color;
import java.util.HashMap;


public class Element
{
	public enum Type { electrophile, nucleophile }
	
	private int x;
	private int y;
	private String name;
	private int charge;
	private int electrons;
	private boolean selected;
	private boolean dragging;
	private boolean bonding;
	private Type type;
	
	public Element(int x, int y)
	{
		this.x = x;
		this.y = y;
		selected = false;
		dragging = false;
		name = "C";
		electrons = 3;
		charge = 4;
	}
	
	public Color getColor()
	{
		if (bonding)
			return Color.GREEN;
		if (selected)
			return Color.RED;
		if (dragging)
			return Color.LIGHT_GRAY;
		return Color.BLACK;
	}
	
	public boolean isBonding() {
		return bonding;
	}

	public void setBonding(boolean bonding) {
		this.bonding = bonding;
	}
	
	public String getKey()
	{
		return x + "," + y;	
	}
	
	public String toString()
	{
		return "Element at ("+ x + ", " + y + ")";	
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public int getElectrons() {
		return electrons;
	}

	public void setElectrons(int electrons) {
		this.electrons = electrons;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDragging() {
		return dragging;
	}

	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}

