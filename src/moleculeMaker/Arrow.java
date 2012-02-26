package moleculeMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class Arrow extends MoleculeComponent
{
	/**
	 * This is the middle point of the arrow, which is generated arbitrarily
	 * using the locations of the reactor and the reactee. In general, the
	 * middle point will always make the curve go upward. More complicated
	 * calculations to allow for downward or straight curves will come later.
	 */
	private Point midPoint;
	
	private Point startPoint;
	private Point endPoint;
	/**
	 * The element connection of this arrow class. It can be either the starting or ending point.
	 */
	private Element e;
	/**
	 * The bond connection of this arrow class. It can be either the starting or the ending point;
	 */
	private Bond b;
	/**
	 * The radius of the arcline to be generated. This is found using two measured points and one
	 * arbitrarily created one.
	 */

	public Arrow(Object reactor, Object reactee)
	{
		System.out.println("Arrow being created between "+reactor+" and "+reactee+".");
		e = null;
		b = null;
		if(!(reactor == null) && !(reactee == null))
		{
			if(reactor.getClass() == Element.class)
			{
				e = (Element)reactor;
				b = (Bond)reactee;
				setMidPoint(new Point(e.getX(), e.getY()), 
						new Point(b.getX(), b.getY()));
			}
			else if(reactor.getClass() == Bond.class)
			{
				b = (Bond)reactor;
				e = (Element)reactee;
				setMidPoint(new Point(b.getX(), b.getY()), 
						new Point(e.getX(), e.getY()));
			}
		}
		else
		{
			System.out.println("One of the connections is null.");
			return;
		}
	}
	
	public void draw(Graphics g, int offset, int offset_y)
	{
		g.setColor(Color.BLACK);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		
		int smallestSize = MoleculeGrid.GRID_SPACING;
		if (MoleculeGrid.GRID_SPACING > MoleculeGrid.GRID_SPACING_Y) {
			smallestSize = MoleculeGrid.GRID_SPACING_Y;
		}

		g.drawOval((int)midPoint.getX(), (int)midPoint.getY(), smallestSize/2, smallestSize/2);
	}
	
	private void setMidPoint(Point p1, Point p3)
	{
		startPoint = p1;
		endPoint = p3;
		int x = (startPoint.x + endPoint.x)/2;
		int y = (startPoint.y  + endPoint.y)/2;
		midPoint = new Point(x,y);
	}
	/**
	 * Arrow hashmap not implemented yet.
	 */
	@Override
	protected String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getX() {
		return midPoint.x;
	}

	@Override
	protected int getY() {
		return midPoint.y;
	}
}
