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
		order = 1; //Default value of 2.
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
		double barbLength = 13;
		double angle = Math.toRadians(35);
		double dy = tip.y - tail.y;
		double dx = tip.x - tail.x;
		double theta = Math.atan2(dy, dx);
		double x, y, rho = theta + angle;
		int spacingX = MoleculeGrid.GRID_SPACING;
		int spacingY = MoleculeGrid.GRID_SPACING_Y;
		int offsetX = MoleculeGrid.OBJECT_OFFSET;
		int offsetY = MoleculeGrid.OBJECT_OFFSET_Y;
		double realTipX, realTipY;
		realTipX = tip.x*spacingX+offsetX;
		realTipY = tip.y*spacingY+offsetY;
		
		for(int i = 0; i < 2; i++)
		{
			x = realTipX - barbLength * Math.cos(rho);
			y = realTipY - barbLength * Math.sin(rho);
			((Graphics2D) g2).draw(new Line2D.Double(realTipX, realTipY, x, y));
			rho = theta - angle;
		}
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
}
