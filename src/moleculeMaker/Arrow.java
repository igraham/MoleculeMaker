package moleculeMaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * To heck with arclines!
 * @author Ian Graham
 *
 */
public class Arrow extends MoleculeConnectorComponent
{
	
	private int order;
	
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
		order = 2; //Default value of 2.
	}
	
	public Arrow(MoleculeComponent c1, MoleculeComponent c2, int order)
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
		setOrder(order);
	}
	
	public void draw(Graphics g, int offset, int offset_y)
	{
		// Draw the arrow and the mid-point selection circle
		super.draw(g, offset, offset_y);

		// Now draw the arrow's head
		Arrow.drawBarbs(g, connectee, connector);

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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		if(order >= 2 && order <= 3)//If the specified order is 2 or 3
		{
			this.order = order; //Set the order.
			return;
		}System.out.println("So, someone tried to create the wrong number of arrows.");
		//Don't do anything else if it's not one of these values.
	}
	
	@Override
	protected int setX(int a, int b) {
		// TODO Auto-generated method stub
		return (a + b) / 2;
	}
	
}
