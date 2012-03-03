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
 * Version 0.7
 * 		- Limit grid lines
 * 		- Grid scaling according to window size
 * Version 0.8
 * 		- Massive code restructuring
 *				- Eliminated lots of repeated code
 *				- Elements, bonds, and arrows now inherit from the same
 *				  base classes (MoleculeComponent and MoleculeConnectorComponent) 
 * 		- Added arrows
 * 
 * TODO:
 * 		- Fix up arrow drawing
 * 		- Fix being able to bond to self
 * 		- Allow selection of bonds and arrows
 * 		- Add attributes for bonds and arrows
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
	
	public void displayAttributes(MoleculeComponent e)
	{	
		view.south.removeAll();
		view.south.add(new AttributesModifier(e));
		
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
//		view.gridB.elist.clearElements();
		view.repaint();
	}
	
	public void clearBonds()
	{
		view.gridA.elist.clearBonds();
//		view.gridB.elist.clearBonds();
		view.repaint();
	}
	public void clearArrows()
	{
		view.gridA.elist.clearArrows();
		//view.gridB.elist.clearArrows();
		view.repaint();
	}
	
	public void exportToXML()
	{
		//Coming soon since this is almost done.
	}
}