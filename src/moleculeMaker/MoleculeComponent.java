package moleculeMaker;

import java.awt.Color;

public abstract class MoleculeComponent {
	
	// Basic structure for elements and bonds!
	protected boolean selected;
	protected boolean dragging;
	protected boolean bonding;
	
	protected double x;
	protected double y;
	
	public MoleculeComponent() {
		selected = false;
		dragging = false;
		bonding = false;
	}
	
	public Color getColor() {
//		if (bonding)
//			return Color.GREEN;
		if (selected)
			return Color.RED;
		if (dragging)
			return Color.LIGHT_GRAY;
		
		return Color.BLACK;
	}
	
	// Abstract Methods (Abstract?! Aww yeah, son!!!)
	protected abstract String getKey();
//	protected abstract double getX(); // X for element is point; for bond it's center point X
//	protected abstract double getY(); // Y for element is point; for bond it's center point Y
	// protected abstract void recalculateOnRedraw();
	
	protected void setX(double xValue) {
		x = xValue;
	}
	
	protected void setY(double yValue) {
		y = yValue;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	
	// ================ Getters and Setters Below ================
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

	public boolean isBonding() {
		return bonding;
	}

	public void setBonding(boolean bonding) {
		this.bonding = bonding;
	}
	// ===========================================================
}
