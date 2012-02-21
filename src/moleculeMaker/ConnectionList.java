package moleculeMaker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class ConnectionList 
{
	private HashMap<String, Element> elements;
	private HashMap<String, Bond> bonds;
	
	private Element selected;
	private Element dragging;
	private Element moving;

	public ConnectionList()
	{
		elements = new HashMap<String, Element>();
		bonds = new HashMap<String, Bond>();
		selected = null;
	}
	
	public void clearBonds()
	{
		selected = null;
		dragging = null;
		moving = null;
		bonds = new HashMap<String, Bond>();
	}
	
	public void clearElements()
	{
		selected = null;
		dragging = null;
		moving = null;
		bonds = new HashMap<String, Bond>();
		elements = new HashMap<String, Element>();
	}
	
	public void addBond(Element e1, Element e2)
	{
		if (e1 == null || e2 == null)
		{
			System.out.println("*** Bonding failed. Null is not allowed!");
			return;
		}
		
		if (e1.getKey().equals(e2.getKey()))
		{
			System.out.println("*** Cannot bond with the same point!");
			return;
		}
		
		
		String bondKey = Bond.getBondKey(e1, e2);
		
		if(!bonds.containsKey(bondKey))
		{
			Bond temp = new Bond(e1, e2);
			System.out.println("Temp bond is: " + temp);
			System.out.println("\tTemp bond's bonder is: " + temp.getBonder());
			System.out.println("\tTemp bond's bondee is: " + temp.getBondee());
			bonds.put(bondKey, temp);
		}
		else
		{
			System.out.println("This bond already exists!");
		}
		
	}
	
	private void removeBonds(Element e)
	{
		// Kudos to: http://joecode.blogspot.com/2004/10/hash-map-iteration.html
		// for the nifty iteration technique, and thanks to:
    	// http://stackoverflow.com/questions/1110404/remove-elements-from-a-hashset-while-iterating
    	// for pointing out, I needed to use i.remove() instead of bond.remove(key);
		
		String tempKey = e.getKey().replace(" ", "");
		
		for (Iterator<String> i = bonds.keySet().iterator(); i.hasNext();)
		{
			String key = i.next();
			
		    if (key.contains(tempKey))
		    {
		    	i.remove(); 
		    }
		}
	}
	
	/**
	 * Add an element to the list
	 * @param e The element to add to the list
	 */
	public void addElement(Element e)
	{
		elements.put(e.getKey(), e);
	}
	
	public void removeElement(Element e)
	{
		if (e == null) { return; }
		
		elements.remove(e.getKey());
	}
	
	public Element[] getCoordinates()
	{
		// Return null if there are no elements in map
		if (elements.keySet().size() == 0) { return null; }
		
		Element[] temp = new Element[elements.keySet().size()];
		
		int i = 0;
		for (Element e : elements.values())
		{
			temp[i] = e;
			i++;
		}
		
		return temp;
	}

	public boolean hasElements()
	{
		return elements.keySet().size() > 0;
	}
	
	/**
	 * Add an element to the list by creating a new element at the specified coordinates
	 * @param x The X coordinate of the element
	 * @param y The Y coordinate of the element
	 */
	public void addElement(int x, int y)
	{
		Element e = new Element(x, y);
		addElement(e);
	}
	
	
	public void setSelected(Element e)
	{
		if (e == null) // If the incoming element doesn't exist...
		{
			if (selected != null) // And there is already a selected element...
			{
				// ... clear that selected element
				// (this will be used when the user wishes to no longer select
				// anything, and clicks an empty area of the grid.
				elements.get(selected.getKey()).setSelected(false);
				selected = null;
			}
			return;
		}
		
		if (elements.get(e.getKey()) == null) // if the element is not in the map
		{
			selected = null;
			return;
		}
		
		if (selected != null) // if there is a previously selected element
		{
			// clear its selected state before setting the new element's selected state:
			elements.get(selected.getKey()).setSelected(false); 
		}
		
		selected = elements.get(e.getKey()); // set selected to the newest Element selected
		elements.get(selected.getKey()).setSelected(true); // set the internal selection flag to true
	}
	
	/**
	 * Clears the selected element and also removes it from map the elements.
	 */
	public void removeSelectedElement()
	{
		if (selected == null) { return; }
		
		removeBonds(selected);
		elements.remove(selected.getKey());
		selected = null;
		
	}
	
	public Element getSelected()
	{
		return selected;
	}
	
	public void setDragging(Element e)
	{
		dragging = e;
	}
	
	public Element getDragging()
	{
		return dragging;
	}
	
	public boolean isSelected(Element e)
	{
		if (e == null)
			return false;
		
		if (selected.getKey().equals(e.getKey()))
			return true;

		return false;
	}
	
	public String toString()
	{
		return "Element List: " + elements.toString();
	}
	
	public Element getElementAt(int i, int j) {
		String key = "" + i + "," + j;
		return getElementAt(key);
	}
	
	public Element getElementAt(String key)
	{
		return elements.get(key);
	}
	
	public Element getMoving() {
		return moving;
	}

	public void setMoving(Element moving) {
		this.moving = moving;
	}
	
	public ArrayList<Bond> getBonds()
	{
		return new ArrayList<Bond>(bonds.values());
	}

	public void setBonds(HashMap<String, Bond> bonds) {
		this.bonds = bonds;
	}

	public HashMap<String, Element> getMap() {
		return elements;
	}

	public HashMap<String, Bond> getBondHash() {
		// TODO Auto-generated method stub
		return bonds;
	}
	
}