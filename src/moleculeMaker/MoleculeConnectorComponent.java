package moleculeMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MoleculeConnectorComponent extends MoleculeComponent {

	protected MoleculeComponent connector;
	protected MoleculeComponent connectee;
	protected String connectionKey;

	// When slope is -1, middlepoint's y is subtracted from connector's y value, instead of added
	protected int slopeDirection;
	protected Color dragColor = Color.BLACK; // default drag color
	
//	protected abstract int setX(int a, int b);
	
	/**
	 * Takes in two MoleculeComponents and sets whichever is closer to
	 * the coordinates (0,0) as the connector. The farther away MoleculeComponent
	 * is set as the connectee. The direction of the slope, key value, and (X, Y)
	 * coordinates of the midpoint are also set.
	 * 
	 * @param c1
	 * @param c2
	 */
	protected void setConnectionAttributes(MoleculeComponent c1, MoleculeComponent c2)
	{
		slopeDirection = 1;

		// Bonder is the point closer to (0,0); the left-most/top-most ElementImproved
		// The priority of ElementImproveds starts with the closest X value, THEN the Y value

		/*
		 * Cases to consider:
		 * 		1. Bond is vertical |
		 * 		2. Bond is horizontal --
		 * 		3. Bond has positive slope /
		 * 		4. Bond has negative slope \
		 */
		if(c1.getY() == c2.getY()) // If bond is horizontal --
		{
			// ...the lesser X value becomes
			if (c1.getX() < c2.getX()) {
				this.connector = c1;
				this.connectee = c2;
			} else {
				this.connector = c2;
				this.connectee = c1;
			}
		}
		else if(c1.getX() == c2.getX()) // If bond is vertical | 
		{
			// ...the lesser Y value becomes the main ElementImproved.
			if (c1.getY() < c2.getY()) {
				this.connector = c1;
				this.connectee = c2;
			} else {
				this.connector = c2;
				this.connectee = c1;
			}
		} 
		// If bond is not horizontal or vertical, then it is diagonal: \ or /
		// X at this point is either less than or greater than:
		else {
			if(c1.getX() < c2.getX()) {
				this.connector = c1;
				this.connectee = c2;
			}
			else {
				this.connector = c2;
				this.connectee = c1;
			}

			if(this.connector.getY() < this.connectee.getY()) { // bond looks like this: \
				//					yDirection = 1;
			}
			else {
				slopeDirection = -1;
			}

		}

		// Set instance variables after the calculation
		connectionKey = getConnectionKey(c1, c2);
		setX(connector.getX(), connectee.getX());
		setY(connector.getY(), connectee.getY());

	}
	
	public MoleculeComponent getConnector()
	{
		return connector;
	}

	public MoleculeComponent getConnectee()
	{
		return connectee;
	}
	
	public static String getConnectionKey(MoleculeComponent bonder, MoleculeComponent bondee)
	{		
		return bonder.getKey() + ";" + bondee.getKey();
	}
	
//	protected double calculateMiddleX()
//	{
//		return calculateMiddle(connector.getX(), connectee.getX(), MoleculeGrid.GRID_SPACING);
//	}
//	
//	protected double calculateMiddleY()
//	{
//		return calculateMiddle(connector.getY(), connectee.getY(), MoleculeGrid.GRID_SPACING_Y);
//	}
//	
	protected double calculateMiddle(double bonderInt, double bondeeInt, int spacing)
	{
		return Math.abs(bonderInt * spacing + bondeeInt * spacing) / 2;
	}
	
	/**
	 * Should be called every redraw
	 */
	protected void recalculateMiddleXY()
	{
		x = calculateMiddle(connector.getX(), connectee.getX(), MoleculeGrid.GRID_SPACING);;
		y = calculateMiddle(connector.getY(), connectee.getY(), MoleculeGrid.GRID_SPACING_Y);
	}
	
	public void draw(Graphics g)
	{
		// Draw with no offset if there isn't one specified.
		draw(g, 0, 0);
	}
	
	public void draw(Graphics g, int offset, int offset_y)
	{
		//recalculateMiddleXY();

		g.setColor(getColor());
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(getColor());
		g2d.setStroke(new BasicStroke(3));

		// Draw a line
		g2d.drawLine((int)(connector.getX() ),
				(int)(connector.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y),
				(int)(connectee.getX() * MoleculeGrid.GRID_SPACING + offset),
				(int)(connectee.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y));
		
		// Make sure circles don't "ovalize".
		int smallestSize = MoleculeGrid.GRID_SPACING;
		if (MoleculeGrid.GRID_SPACING > MoleculeGrid.GRID_SPACING_Y) {
			smallestSize = MoleculeGrid.GRID_SPACING_Y;
		}

		// Draw the center selector point
		g.drawOval((int)getX(), (int)getY(), smallestSize/2, smallestSize/2);

	}
	
	public boolean contains(double x2, double y2)
	{
		double absBonderX = connector.getX() * MoleculeGrid.GRID_SPACING+MoleculeGrid.GRID_SPACING/4;
		double absBonderY = connector.getY() * MoleculeGrid.GRID_SPACING_Y+MoleculeGrid.GRID_SPACING/4;
		double absBondeeX = connectee.getX() * MoleculeGrid.GRID_SPACING +MoleculeGrid.GRID_SPACING/4;
		double absBondeeY = connectee.getY() * MoleculeGrid.GRID_SPACING_Y+MoleculeGrid.GRID_SPACING/4;

		double midX = (absBonderX + absBondeeX) /2;
		double midY = (absBonderY + absBondeeY) /2;
		double dx = Math.abs(x2-(midX));
		double dy = Math.abs(y2-(midY));
		double distance = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
		double radius = 1.5*MoleculeGrid.GRID_SPACING/4;
		return radius >= distance;
	}
	
	// Remember kids, color needs to be specified by the extending class (ie bond or arrow)
	public static void drawDrag(Graphics g, Color c, MoleculeComponent e, double roundX, double roundY) {
		int spacingX = MoleculeGrid.GRID_SPACING;
		int spacingY = MoleculeGrid.GRID_SPACING_Y;
		int offsetX = MoleculeGrid.OBJECT_OFFSET;
		int offsetY = MoleculeGrid.OBJECT_OFFSET_Y;
		
		g.setColor(c);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));

		g2d.drawLine((int)(e.getX() * spacingX + offsetX), (int)(e.getY() * spacingY + offsetY),
				(int)(roundX * spacingX + offsetX), (int)(roundY * spacingY + offsetY));
	}
	
	
	public String toString()
	{
		return "(" + connector.getX() + ", " + connector.getY() + ") --> ("
				+ connectee.getX() + ", " + connectee.getY() + ")";
	}
	
	// Overrides =========================
	@Override
	protected String getKey() {
		return connectionKey;
	}
	
	public static String getKey(Element e1, Element e2) {
		return e1.getKey() + ";" + e2.getKey();
	}
	
	protected void setX(double xValue) {
		x = xValue;
	}
	
	protected void setY(double yValue) {
		y = yValue;
	}

}
