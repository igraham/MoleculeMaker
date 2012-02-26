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
<<<<<<< HEAD
=======

	public Arrow(Object reactor, Object reactee)
	{
		super();
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
>>>>>>> 98a5899f2f1d4eb0edcdf2afc3d59f67cbfb8bb4
	
	public Arrow(MoleculeComponent c1, MoleculeComponent c2)
	{
		super();

		System.out.println("Bond being created using: " + c1 + " and " + c2);

		if (c1 == null || c2 == null) {
			System.out.println("Bonder or bondee is null");
			return;
		}

		dragColor = Color.BLUE; // set the drag color for arrow lines
		setConnectionAttributes(c1, c2);
		recalculateMiddleXY();
	}
	
	public void draw(Graphics g, int offset, int offset_y)
	{
<<<<<<< HEAD
		// Draw the arrow and the mid-point selection circle
		super.draw(g, offset, offset_y);

		// Now draw the arrow's head
		Arrow.drawBarbs(g, connector, connectee);
=======
		startPoint = p1;
		endPoint = p3;
		int x = (startPoint.x + endPoint.x)/2;
		int y = (startPoint.y  + endPoint.y)/2;
		midPoint = new Point(x,y);
	}
	
	public static String getArrowKey(Object reactor, Object reactee)
	{
		Element e = null;
		Bond b = null;
		if(reactor != null && reactee != null)
		{
			if(reactor.getClass() == Element.class)
			{
				e = (Element)reactor;
				b = (Bond)reactee;
			}
			else if(reactor.getClass() == Bond.class)
			{
				b = (Bond)reactor;
				e = (Element)reactee;
			}
			return e.getKey() + ";" + b.getKey();
		}
		else
		{
			return null;
		}
<<<<<<< HEAD
=======
	}
	
	public void getDirection(Object reactor, Object reactee, Element e, Bond b)
	{
		if(e.getX() == startPoint.getX() && e.getY() == startPoint.getY())
		{
			reactor = e;
		}
		else if(e.getX() == endPoint.getX() && e.getY() == endPoint.getY())
		{
			reactee = e;
		}
		else
		{
			System.out.println("Element is neither end nor start point. Wha?");
			return;
		}
		if(b.getX() == startPoint.getX() && b.getY() == startPoint.getY())
		{
			reactor = b;
		}
		else if(b.getX() == startPoint.getX() && b.getY() == startPoint.getY())
		{
			reactee = b;
		}
		else
		{
			System.out.println("Bond is neither end nor start point. WHAT!?");
		}
	}
	
	public String toString()
	{
		Object reactor = null;
		Object reactee = null;
		getDirection(reactor, reactee, e, b);
		if(reactor.getClass() == Element.class)
		{
			return "(" + ((Element) reactor).getX() + ", " + ((Element) reactor).getY() + ") --> ("
					+ ((Bond) reactor).getX() + ", " + ((Bond) reactor).getY() + ")";
		}
		else if(reactor.getClass() == Bond.class)
		{
			return "(" + ((Bond) reactor).getX() + ", " + ((Bond) reactor).getY() + ") --> ("
					+ ((Element) reactor).getX() + ", " + ((Element) reactor).getY() + ")";
		}
		return "";
	}
	
	@Override
	protected String getKey() {
		return e.getKey()+";"+b.getKey();
>>>>>>> 98a5899f2f1d4eb0edcdf2afc3d59f67cbfb8bb4
	}
	
	public void getDirection(Object reactor, Object reactee, Element e, Bond b)
	{
		if(e.getX() == startPoint.getX() && e.getY() == startPoint.getY())
		{
			reactor = e;
		}
		else if(e.getX() == endPoint.getX() && e.getY() == endPoint.getY())
		{
			reactee = e;
		}
		else
		{
			System.out.println("Element is neither end nor start point. Wha?");
			return;
		}
		if(b.getX() == startPoint.getX() && b.getY() == startPoint.getY())
		{
			reactor = b;
		}
		else if(b.getX() == startPoint.getX() && b.getY() == startPoint.getY())
		{
			reactee = b;
		}
		else
		{
			System.out.println("Bond is neither end nor start point. WHAT!?");
		}
	}
	
	public String toString()
	{
		Object reactor = null;
		Object reactee = null;
		getDirection(reactor, reactee, e, b);
		if(reactor.getClass() == Element.class)
		{
			return "(" + ((Element) reactor).getX() + ", " + ((Element) reactor).getY() + ") --> ("
					+ ((Bond) reactor).getX() + ", " + ((Bond) reactor).getY() + ")";
		}
		else if(reactor.getClass() == Bond.class)
		{
			return "(" + ((Bond) reactor).getX() + ", " + ((Bond) reactor).getY() + ") --> ("
					+ ((Element) reactor).getX() + ", " + ((Element) reactor).getY() + ")";
		}
		return "";
	}
	
	@Override
	protected String getKey() {
		return e.getKey()+";"+b.getKey();
>>>>>>> 98a5899f2f1d4eb0edcdf2afc3d59f67cbfb8bb4
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
}
