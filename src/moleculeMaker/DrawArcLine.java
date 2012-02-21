package moleculeMaker;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawArcLine {

	
	public DrawArcLine()
	{
		JFrame frame = new JFrame();
		frame.setSize(240,240);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Point2D p1 = new Point2D.Double(40.0,100.0);
		Point2D p2 = new Point2D.Double(190.0, 100.0);
		frame.add(new ArcLine(p1, p2));
		Point2D p3 = new Point2D.Double(0, 0);
		Point2D p4 = new Point2D.Double(100, 50);
		//frame.add(new ArcLine(p3, p4));
		Point2D p5 = new Point2D.Double(200, 100);
		Point2D p6 = new Point2D.Double(300, 50);
		//frame.add(new ArcLine(p5, p6));
		frame.setVisible(true);
	}
	
	class ArcLine extends JPanel
	{
		private Point2D p1;
		private Point2D p3;
		
		public ArcLine(Point2D p1, Point2D p3)
		{
			this.p1 = p1;
			this.p3 = p3;
		}
		
		public void paint(Graphics g)
		{
			super.paint(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
								RenderingHints.VALUE_ANTIALIAS_ON);
			Arc2D arc = new Arc2D.Double(Arc2D.OPEN);
			double x = Math.abs(p3.getX()-p1.getX());
			System.out.println(p1);
			System.out.println(p3);
			double y = Math.abs(p3.getY()-p1.getY());
			System.out.println(x);
			System.out.println(y);
			
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
			
			Point2D p2 = new Point2D.Double(p1.getX()+x/2, y);
			System.out.println(p2);
			double a = Math.sqrt(Math.pow(p3.getX() - p1.getX(),2)+Math.pow(p3.getY()-p1.getY(), 2));
			double b = Math.sqrt(Math.pow(p3.getX() - p2.getX(),2)+Math.pow(p3.getY()-p2.getY(), 2));
			double c = Math.sqrt(Math.pow(p2.getX() - p1.getX(),2)+Math.pow(p2.getY()-p1.getY(), 2));
			double s = 0.5 * (a + b + c);
			double K = Math.sqrt(s*(s-a)*(s-b)*(s-c));
			double R = a*b*c/(4*K);
			System.out.println("Radius: "+R);
			arc.setArcByTangent(p1, p2, p3, R);
			g2.draw(arc);
		}
	}
	
	public static void main(String[] args)
	{
		new DrawArcLine();
	}
	
}
