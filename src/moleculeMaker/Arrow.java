package moleculeMaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

public class Arrow
{
	/**
	 * This is the midddle point of the arrow, which is generated arbitrarily
	 * using the locations of the reactor and the reactee. In general, the
	 * middle point will always make the curve go upward. More complicated
	 * calculations to allow for downward or straight curves will come later.
	 */
	private Point2D midPoint;
	
	private Point2D startPoint;
	private Point2D endPoint;
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
	private double radius;

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
						new Point(b.getMiddleX(), b.getMiddleY()));
			}
			else if(reactor.getClass() == Bond.class)
			{
				b = (Bond)reactor;
				e = (Element)reactee;
				setMidPoint(new Point(b.getMiddleX(), b.getMiddleY()), 
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
		Arc2D arc = new Arc2D.Double(Arc2D.OPEN);
		arc.setArcByTangent(startPoint, midPoint, endPoint, radius);
		g2d.draw(arc);
	}
	
	/**
	 * Using two points, calculate where the mid-point should go, and then using
	 * those three points, calculate the radius. Using those three points and
	 * the radius, you can use the setArcByTangent method to draw the arc.
	 * @param p1
	 * @param p3
	 */
	private void setMidPoint(Point p1, Point p3)
	{
		startPoint = p1;
		endPoint = p3;
		double x = Math.abs(p3.getX()-p1.getX());
		double y = Math.abs(p3.getY()-p1.getY());
		for(int i = 0; i < 1; i++)
		{
			if(y == 0)
			{
				y -= 0.05*x;
				break;
			}
			else if(y > 0)
			{
				y = 0.25*y;
				break;
			}
			else
			{
				y = p1.getY() - (0.75*y);
			}
		}
		midPoint = new Point2D.Double(p1.getX()+x/2, y);
		double a = Math.sqrt(Math.pow(p3.getX() - p1.getX(),2)+Math.pow(p3.getY()-p1.getY(), 2));
		double b = Math.sqrt(Math.pow(p3.getX() - midPoint.getX(),2)+Math.pow(p3.getY()-midPoint.getY(), 2));
		double c = Math.sqrt(Math.pow(midPoint.getX() - p1.getX(),2)+Math.pow(midPoint.getY()-p1.getY(), 2));
		double s = 0.5 * (a + b + c);
		double K = Math.sqrt(s*(s-a)*(s-b)*(s-c));
		radius = a*b*c/(4*K);
	}
}
