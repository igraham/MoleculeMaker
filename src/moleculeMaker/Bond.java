package moleculeMaker;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bond //extends JButton //implements ActionListener
{	
	// This is the most important comment of all.
	private Element bonder; //BONDER
	private Element bondee; //BONDEE
	private String bondKey; //BONDKEYS
	private int middleX;
	private int middleY;
	private int yDirection; // 1 = bondee's Y is greater than bonder's Y; -1 = the opposite

	private boolean selected;
	private boolean dragging;
	private boolean bonding;

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
	
	public Bond(Element bonder, Element bondee)
	{
		System.out.println("Bond being created using: " + bonder + " and " + bondee);
		
		if (bonder == null) {
			System.out.println("Bonder is null");
			return;
		}
		if (bondee == null) {
			System.out.println("Bondee is null");
			return;
		}

		selected = false;
		dragging = false;
		bonding = false;
		
		setBonderBondeeAndBondCenter(bonder, bondee);
		
		// Set first time... it will update in draw()
		middleX = Math.abs(bonder.getX() * MoleculeGrid.GRID_SPACING + bondee.getX() * MoleculeGrid.GRID_SPACING) / 2;
		middleY = Math.abs(bonder.getY() * MoleculeGrid.GRID_SPACING_Y + bondee.getY() * MoleculeGrid.GRID_SPACING_Y) / 2;
		
//		System.out.println("middleX: " + middleX);
//		System.out.println("middleY: " + middleY );
	}
	
	public int getMiddleX() {
		return middleX;
	}
	
	public int getMiddleY() {
		return middleY;
	}

	private void setBonderBondeeAndBondCenter(Element bonder, Element bondee)
	{
		yDirection = 1;
		
		// Bonder is the point closer to (0,0); the left-most/top-most element
		// The priority of elements starts with the closest X value, THEN the Y value
		
		/*
		 * Cases to consider:
		 * 		1. Bond is vertical |
		 * 		2. Bond is horizontal --
		 * 		3. Bond has positive slope /
		 * 		4. Bond has negative slope \
		 */
		if(bonder.getY() == bondee.getY()) // If bond is horizontal --
		{
			// ...the lesser X value becomes
			if (bonder.getX() < bondee.getX()) {
				this.bonder = bonder;
				this.bondee = bondee;
			} else {
				this.bonder = bondee;
				this.bondee = bonder;
			}
		}
		else if(bonder.getX() == bondee.getX()) // If bond is vertical | 
		{
			// ...the lesser Y value becomes the main element.
			if (bonder.getY() < bondee.getY()) {
				this.bonder = bonder;
				this.bondee = bondee;
			} else {
				this.bonder = bondee;
				this.bondee = bonder;
			}
		} 
		// If bond is not horizontal or vertical, then it is diagonal: \ or /
		// X at this point is either less than or greater than:
		else {
			if(bonder.getX() < bondee.getX()) {
				this.bonder = bonder;
				this.bondee = bondee;
			}
			else {
				this.bonder = bondee;
				this.bondee = bonder;
			}
			
			if(this.bonder.getY() < this.bondee.getY()) { // bond looks like this: \
//				yDirection = 1;
			}
			else {
				yDirection = -1;
			}
			
		}
		
		bondKey = getBondKey(bonder, bondee);
		
		
//		if (yDirection == -1)
//			middleY *= -1;
	}
	
	public static String getBondKey(Element bonder, Element bondee)
	{		
		return bonder.getKey() + ";" + bondee.getKey();
	}
	
	public void draw(Graphics g)
	{
		// Draw with no offset if there isn't one specified.
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offset, int offset_y)
	{
		middleX = Math.abs(bonder.getX() * MoleculeGrid.GRID_SPACING + bondee.getX() * MoleculeGrid.GRID_SPACING) / 2;
		middleY = Math.abs(bonder.getY() * MoleculeGrid.GRID_SPACING_Y + bondee.getY() * MoleculeGrid.GRID_SPACING_Y) / 2;
		

		g.setColor(getColor());
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		
		g2d.drawLine(bonder.getX() * MoleculeGrid.GRID_SPACING + offset,
				bonder.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y,
				bondee.getX() * MoleculeGrid.GRID_SPACING + offset,
				bondee.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y);
		
		int absBonderX = bonder.getX() * MoleculeGrid.GRID_SPACING + offset;
		int absBonderY = bonder.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y;
		int absBondeeX = bondee.getX() * MoleculeGrid.GRID_SPACING + offset;
		int absBondeeY = bondee.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y;
		
//		System.out.println("Absolute coordinates are: (" + absBonderX + "," + absBonderY + ")");
//		System.out.println("Absolute coordinates are: (" + absBondeeX + "," + absBondeeY + ")");
//		
//		System.out.println("X Midpoint is: " + middleX);
//		System.out.println("Y midpoint is: " + middleY);
		
//		g.drawOval((bonder.getX() + middleX) * MoleculeGrid.GRID_SPACING,
//				(bonder.getY() + middleY) * MoleculeGrid.GRID_SPACING_Y,
//				MoleculeGrid.GRID_SPACING/2,
//				MoleculeGrid.GRID_SPACING_Y/2);

		g.drawOval(middleX, middleY, MoleculeGrid.GRID_SPACING/2,
				MoleculeGrid.GRID_SPACING_Y/2);
		
	}
	
	public boolean contains(double x2, double y2)
	{

		int absBonderX = bonder.getX() * MoleculeGrid.GRID_SPACING+MoleculeGrid.GRID_SPACING/4;
		int absBonderY = bonder.getY() * MoleculeGrid.GRID_SPACING_Y+MoleculeGrid.GRID_SPACING/4;
		int absBondeeX = bondee.getX() * MoleculeGrid.GRID_SPACING +MoleculeGrid.GRID_SPACING/4;
		int absBondeeY = bondee.getY() * MoleculeGrid.GRID_SPACING_Y+MoleculeGrid.GRID_SPACING/4;
		int midX = (absBonderX + absBondeeX) /2;
		int midY = (absBonderY + absBondeeY) /2;
		double dx = Math.abs(x2-(midX));
		double dy = Math.abs(y2-(midY));
		double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
		double radius = 1.8*MoleculeGrid.GRID_SPACING/4;
		return radius >= distance;
	 }
	
	public Element getBonder()
	{
		return bonder;
	}
	
	public Element getBondee()
	{
		return bondee;
	}
	
	public String toString()
	{
		return "(" + bonder.getX() + ", " + bonder.getY() + ") --> ("
				+ bondee.getX() + ", " + bondee.getY() + ")";
	}
	
	public String getBondKey()
	{
		return bondKey;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
}
