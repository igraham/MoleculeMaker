package moleculeMaker;
import javax.swing.JOptionPane;

/*
 * Version 0.1
 * 		- Draw graph
 * 		- Place elements
 * 		- Blue highlight effect over elements
 * Version 0.2
 * 		- Move elements (later removed in code rewrite)
 * 		- Remove element by clicking them again (changed in later revision)
 * Version 0.3
 * 		- Element selectability
 * 		- Remove element when selected by pressing Delete key
 * Version 0.4
 * 		- Create bonds (lacks ability to remove bonds)
 * 		- Element attributes GUI blueprint (non-functioning)
 * Version 0.5
 * 		- Remove bonds (by deleting one of the bond's elements)
 * 		- Element attributes GUI implemented (now displays and saves attributes)
 * 		- Added Menu Bar blueprint (File > Export, Elements > Clear Elements, and Bonds > Clear Bonds)
 * Version 0.6
 * 		- Menu Bar now functions
 * 		- Added two molecules, side by side.
 * 
 * TODO:
 * 		- Arrows
 * 		- Limit grid size
 * 		- Electrophile and nucleophile attributes for element attributes
 * 		- XML Export
 * 		- Detailed code comments
 */

public class MMController
{
	
	public MMView view;
	
	public MMController()
	{
		view = new MMView(this); // Show the GUI
	}
	
	public void receivedClick()
	{
		System.out.println("Received Click");
	}
	
	public void displayElementAttributes(Element e)
	{	
		view.south.removeAll();
		view.south.add(new ElementAttributesModifier(e));
		
		view.validate();
		view.repaint();
	}
	
	public void quit()
	{
		System.exit(0);
	}
	
	public void clearElements()
	{
		view.gridA.elist.clearElements();
		view.gridB.elist.clearElements();
		view.repaint();
	}
	
	public void clearBonds()
	{
		view.gridA.elist.clearBonds();
		view.gridB.elist.clearBonds();
		view.repaint();
	}
	
	public void exportToXML()
	{
		JOptionPane.showMessageDialog(null, "Did you think I would do your job for you, Ian!?");
	}
}