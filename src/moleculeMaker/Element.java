package moleculeMaker;

public class Element extends MoleculeComponent {
	
	// Element-only attributes
	private String name;
	private int electrons;
	private int charge;
	
	public Element(int x, int y)
	{
		super();
		
		this.x = x;
		this.y = y;
		selected = false;
		dragging = false;
		name = "C";
		electrons = 3;
		charge = 4;
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
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
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
	
	// ===========================================================
	
}
