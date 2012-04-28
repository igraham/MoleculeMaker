package moleculeMaker;

public class Element extends MoleculeComponent{
	
	// Element-only attributes
	private String name;
	private int electrons;
	private int charge;
	private int type;
	
	public Element(double x, double y)
	{
		super();
		
		this.x = x;
		this.y = y;
		selected = false;
		dragging = false;
		name = "C";
		electrons = 0;
		charge = 1;
		type = 0;
	}
	
	// ================ Getters and Setters Below ================
	@Override
	public String getKey()
	{
		return x + "," + y;	
	}
	
	public String toString()
	{
		return "Element at ("+ x + ", " + y + ")";	
	}

	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	private String toUTF8Subscript(String str)
	{
		int num = Integer.parseInt(str);
		switch(num)
		{
		case 0:
			return "\u2080";
		case 1:
			return "\u2081";
		case 2:
			return "\u2082";
		case 3:
			return "\u2083";
		case 4:
			return "\u2084";
		case 5:
			return "\u2085";
		case 6:
			return "\u2086";
		case 7:
			return "\u2087";
		case 8:
			return "\u2088";
		case 9:
			return "\u2089";
		}
		return "";
	}
	
	public void setName(String name) {
		
		for(int i = 0; i < name.length();i++)
		{
			if(Character.isDigit(name.charAt(i)))
			{
				name = name.substring(0, i) 
						+ toUTF8Subscript(name.substring(i,i+1))
						+name.substring(i+1, name.length());
			}
		}
		this.name = name;
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	public int getElectrons() {
		return electrons;
	}

	public void setElectrons(int electrons) {
		this.electrons = electrons;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	// ===========================================================
	
}
