package moleculeMaker;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bond extends MoleculeComponent {

	// This is the most important comment of all.
	private Element bonder; //BONDER
	private Element bondee; //BONDEE
	private String bondKey; //BONDKEYS

	private int yDirection; // 1 = bondee's Y is greater than bonder's Y; -1 = the opposite

	public Bond(Element bonder, Element bondee)
	{
		super();

		System.out.println("Bond being created using: " + bonder + " and " + bondee);

		if (bonder == null || bondee == null) {
			System.out.println("Bonder or bondee is null");
			return;
		}

		setBonderBondeeAndBondCenter(bonder, bondee);

		// Set first time... it will update in draw()
//		x = Math.abs(bonder.getX() * MoleculeGridImproved.GRID_SPACING + bondee.getX() * MoleculeGridImproved.GRID_SPACING) / 2;
//		y = Math.abs(bonder.getY() * MoleculeGridImproved.GRID_SPACING_Y + bondee.getY() * MoleculeGridImproved.GRID_SPACING_Y) / 2;
		
		recalculateMiddleXY();
	}

	private void setBonderBondeeAndBondCenter(Element bonder, Element bondee)
	{
		yDirection = 1;

		// Bonder is the point closer to (0,0); the left-most/top-most ElementImproved
		// The priority of ElementImproveds starts with the closest X value, THEN the Y value

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
			// ...the lesser Y value becomes the main ElementImproved.
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
				//					yDirection = 1;
			}
			else {
				yDirection = -1;
			}

		}

		bondKey = getBondKey(bonder, bondee);


		//			if (yDirection == -1)
		//				middleY *= -1;
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
	
	/**
	 * Should be called every redraw
	 */
	private void recalculateMiddleXY()
	{
		x = calculateMiddleX();
		y = calculateMiddleY();
	}
	
	private int calculateMiddleX()
	{
		return calculateMiddle(bonder.getX(), bondee.getX(), MoleculeGrid.GRID_SPACING);
	}
	
	private int calculateMiddleY()
	{
		return calculateMiddle(bonder.getY(), bondee.getY(), MoleculeGrid.GRID_SPACING_Y);
	}
	
	private int calculateMiddle(int bonderInt, int bondeeInt, int spacing)
	{
		return Math.abs(bonderInt * spacing + bondeeInt * spacing) / 2;
	}

	public void draw(Graphics g, int offset, int offset_y)
	{
		//recalculateMiddleXY();


		g.setColor(getColor());
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));

		g2d.drawLine(bonder.getX() * MoleculeGrid.GRID_SPACING + offset,
				bonder.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y,
				bondee.getX() * MoleculeGrid.GRID_SPACING + offset,
				bondee.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y);
		
//		System.out.println("Absolute coordinates are: (" + absBonderX + "," + absBonderY + ")");
//		System.out.println("Absolute coordinates are: (" + absBondeeX + "," + absBondeeY + ")");
//		
//		System.out.println("X Midpoint is: " + getX());
//		System.out.println("Y midpoint is: " + getY());
//		System.out.println("bonder x,y is: "+ bonder.getX() + ", " + bonder.getX());
//		System.out.println("bondee x,y is: " + bondee.getX() + ", " + bondee.getY());
//		System.out.println("grid_spacing_x: " + MoleculeGridImproved.GRID_SPACING);
//		System.out.println("grid_spacing_y: " + MoleculeGridImproved.GRID_SPACING_Y);
		
		// Make sure circles don't "ovalize".
		int smallestSize = MoleculeGrid.GRID_SPACING;
		if (MoleculeGrid.GRID_SPACING > MoleculeGrid.GRID_SPACING_Y) {
			smallestSize = MoleculeGrid.GRID_SPACING_Y;
		}

		g.drawOval(getX(), getY(), smallestSize/2, smallestSize/2);

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
		double radius = 1.5*MoleculeGrid.GRID_SPACING/4;
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



	// ====================================
	// ====================================

	@Override
	public String getKey() {
		return bondKey;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return calculateMiddleX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return calculateMiddleY();
	}

	public static String getKey(Element e1, Element e2) {
		// TODO Auto-generated method stub
		return e1.getKey() + ";" + e2.getKey();
	}

}
