package moleculeMaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Arrow extends MoleculeConnectorComponent
{
	/**
	 * The element connection of this arrow class. It can be either the starting or ending point.
	 */
	private MoleculeComponent e;
	/**
	 * The bond connection of this arrow class. It can be either the starting or the ending point;
	 */
	private MoleculeComponent b;
	/**
	 * The radius of the arcline to be generated. This is found using two measured points and one
	 * arbitrarily created one.
	 */
	
	public Arrow(MoleculeComponent c1, MoleculeComponent c2)
	{
		super();

		System.out.println("Arrow being created using: " + c1 + " and " + c2);

		if (c1 == null || c2 == null) {
			System.out.println("Arrower or Arrowee is null");
			return;
		}

		dragColor = Color.BLUE; // set the drag color for arrow lines
		setConnectionAttributes(c1, c2);
		recalculateMiddleXY();
	}
	
	public void draw(Graphics g, int offset, int offset_y)
	{
		// Draw the arrow and the mid-point selection circle
		super.draw(g, offset, offset_y);

		// Now draw the arrow's head
		Arrow.drawBarbs(g, connector, connectee);

	}
	
	public Color getColor() {
		if (bonding)
			return Color.BLUE; // Never called, I don't think.
		if (selected)
			return Color.GREEN;
		if (dragging)
			return Color.LIGHT_GRAY;
		
		return Color.BLUE;
	}
	
//	@Override
//	protected String getKey() {
//		return e.getKey()+";"+b.getKey();
//	}
	
	public static void drawBarbs(Graphics g2, MoleculeComponent tip, MoleculeComponent tail)
	{
		double barbLength = 10;
		double angle = Math.toRadians(35);
		double dy = tip.y - tail.y;
		double dx = tip.x - tail.x;
//		System.out.println("Change in Y: "+dy);
//		System.out.println("Change in X: "+dx);
		double theta = Math.atan2(dy, dx);
//		System.out.println("Angle of Theta: "+theta);
		double x, y, rho = theta + angle;
		
		for(int i = 0; i < 2; i++)
		{
//			System.out.println("Value of angle Rho: "+Math.toDegrees(rho));
			x = tip.x - barbLength * Math.cos(rho);
			y = tip.y - barbLength * Math.sin(rho);
			((Graphics2D) g2).draw(new Line2D.Double(tip.x, tip.y, x, y));
			rho = theta - angle;
		}
	}
}
