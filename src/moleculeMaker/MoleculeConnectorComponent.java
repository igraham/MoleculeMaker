package moleculeMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class MoleculeConnectorComponent extends MoleculeComponent{

	protected MoleculeComponent connector;
	protected MoleculeComponent connectee;
	protected String connectionKey;

	protected Color dragColor = Color.BLACK; // default drag color
	
	/**
	 * Takes in two MoleculeComponents and sets whichever is closer to
	 * the coordinates (0,0) as the connector. The farther away MoleculeComponent
	 * is set as the connectee. The direction of the slope is also set.
	 * 
	 * @param c1
	 * @param c2
	 */
	protected void setConnectionAttributes(MoleculeComponent c1, MoleculeComponent c2)
	{
		this.connector = c1;
		this.connectee = c2;

		connectionKey = getConnectionKey(c1, c2);
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
		if(bonder.getClass() == Element.class && bondee.getClass() == Element.class)
		{
			return bonder.getKey() + ";" + bondee.getKey();
		}else
		{
			return bonder.getKey() + ":" + bondee.getKey();
		}
	}
	
	protected double calculateMiddleX()
	{
		return calculateMiddle(connector.getX(), connectee.getX(), MoleculeGrid.GRID_SPACING);
	}
	
	protected double calculateMiddleY()
	{
		return calculateMiddle(connector.getY(), connectee.getY(), MoleculeGrid.GRID_SPACING_Y);
	}
	
	protected double calculateMiddle(double bonderInt, double bondeeInt, int spacing)
	{
		System.out.println(Math.abs(bonderInt * spacing + bondeeInt * spacing) / 2);
		return Math.abs(bonderInt + bondeeInt) / 2;
	}
	
	/**
	 * Should be called every redraw
	 */
	protected void recalculateMiddleXY()
	{
		x = calculateMiddleX();
		y = calculateMiddleY();
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
		g2d.draw(new Line2D.Double((connector.getX() * MoleculeGrid.GRID_SPACING + offset),
					(connector.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y),
					(connectee.getX() * MoleculeGrid.GRID_SPACING + offset),
					(connectee.getY() * MoleculeGrid.GRID_SPACING_Y + offset_y)));
		
		// Make sure circles don't "ovalize".
		int smallestSize = MoleculeGrid.GRID_SPACING;
		if (MoleculeGrid.GRID_SPACING > MoleculeGrid.GRID_SPACING_Y) {
			smallestSize = MoleculeGrid.GRID_SPACING_Y;
		}
		
		// Draw the center selector point
		g.drawOval((int)(getX()* MoleculeGrid.GRID_SPACING), (int)(getY() * MoleculeGrid.GRID_SPACING_Y), smallestSize/2, smallestSize/2);

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

		g2d.draw(new Line2D.Double((e.getX() * spacingX + offsetX), (e.getY() * spacingY + offsetY),
				(roundX * spacingX + offsetX), (roundY * spacingY + offsetY)));
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

	@Override
	protected double getX() {
		return x;
	}

	@Override
	protected double getY() {
		return y;
	}
	
	public static String getKey(MoleculeComponent e1, MoleculeComponent e2) {
		System.out.println(e1.getKey() + ";"+e2.getKey());
		return e1.getKey() + ";" + e2.getKey();
	}

	public boolean equals(MoleculeConnectorComponent e)
	{
		if (e.getClass() == Bond.class) {
			String[] key = e.getKey().split(";");
			if(key[0].equals(key[1])){return true;}
			String[] otherKey = getKey().split(";");
			if(e.getKey().equals(getKey()) 
					|| key[0].equals(otherKey[1]) && key[1].equals(otherKey[0]))
			{
				return true;
			}
			return false;
		}
		else if (e.getClass() == Arrow.class) {
			String[] key = e.getKey().split(":");
			String[] otherKey = getKey().split(":");
			if(e.getKey().equals(getKey())
					|| key[0].equals(otherKey[1]) && key[1].equals(otherKey[0]))
			{
				return true;
			}
			return false;
		}
		return false;
	}

}
