package moleculeMaker;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AttributesModifier extends JPanel
{
	
	MoleculeComponent component;
	JTextField name;
	JButton save;
	JComboBox electronCount;
	JComboBox bondOrderBox;
	JComboBox arrowOrderBox;
	ButtonGroup charge;
	
	// TODO Load an elements attributes from ElementList.getSelected()
	// TODO Save an elements new attributes
	
	public AttributesModifier(MoleculeComponent e)
	{
		if (e != null)
		{
			if (e.getClass() == Element.class) {
				System.out.println("Passing in " + e + " to ElementAttributesModifier");
				this.component = (Element) e;
				
				drawElementAttributes();
			}
			else if (e.getClass() == Bond.class) {
				System.out.println("Passing in " + e + " to ElementAttributesModifier");
				this.component = (Bond) e;
				
				drawBondAttributes();
			}
			else if (e.getClass() == Arrow.class) {
				System.out.println("Passing in " + e + " to ElementAttributesModifier");
				this.component = (Arrow) e;
				
				drawArrowAttributes();
			}
			
		}
		else
		{
			JLabel unselected = new JLabel("No element selected.");
			this.add(unselected);
		}
		
	}

	private void drawElementAttributes()
	{
		setLayout(new GridLayout(4,1));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		JLabel nameLabel = new JLabel("Element symbol: ");
		name = new JTextField(((Element) component).getName());
		name.setPreferredSize(new Dimension(100, 30));
		namePanel.add(nameLabel);
		namePanel.add(name);
		
		JPanel chargePanel = new JPanel();
		chargePanel.setLayout(new FlowLayout());
		JLabel chargeLabel = new JLabel("Charge: ");
		charge = new ButtonGroup();
		JRadioButton pos = new JRadioButton("Positive+");
		JRadioButton neu = new JRadioButton("Neutral");
		JRadioButton neg = new JRadioButton("Negative-");
		charge.add(pos);
		charge.add(neu);
		charge.add(neg);
		chargePanel.add(chargeLabel);
		chargePanel.add(pos);
		chargePanel.add(neu);
		chargePanel.add(neg);
		
		// Yuck, a switch? Really Derek!?!
		switch(((Element) component).getCharge())
		{
		case 0:
			pos.setSelected(true);
			break;
		case 1:
			neu.setSelected(true);
			break;
		case 2:
			neg.setSelected(true);
			break;
		}
		
		JPanel electronCountPanel = new JPanel();
		electronCountPanel.setLayout(new FlowLayout());
		JLabel electronCountLabel = new JLabel("No. of electrons: ");
		
		electronCount = new JComboBox();
		electronCount.addItem("0");
		electronCount.addItem("1");
		electronCount.addItem("2");
		electronCount.addItem("3");
		electronCount.addItem("4");
		electronCount.addItem("5");
		electronCount.addItem("6");
//		System.out.println("The electron count is " + element.getElectrons());
		electronCount.setSelectedIndex(((Element) component).getElectrons());
		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((Element) component).setName(name.getText());
				((Element) component).setElectrons(electronCount.getSelectedIndex());
				((Element) component).setCharge(selectedIndex(charge));
			}
		});
		
		electronCountPanel.add(electronCountLabel);
		electronCountPanel.add(electronCount);
		
		add(namePanel);
		add(chargePanel);
		add(electronCountPanel);
		add(save);
	}
	
	private void drawBondAttributes()
	{
		setLayout(new GridLayout(2,1));
		JPanel bondOrderPanel = new JPanel();
		JLabel bondOrder = new JLabel("Bond Order: ");
		bondOrderBox = new JComboBox();
		bondOrderBox.addItem("1");
		bondOrderBox.addItem("2");
		bondOrderBox.addItem("3");
		bondOrderBox.setSelectedIndex(((Bond)component).getBondOrder());
		
		bondOrderPanel.add(bondOrder);
		bondOrderPanel.add(bondOrderBox);
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((Bond) component).setBondOrder(bondOrderBox.getSelectedIndex());
			}
		});
		add(bondOrderPanel);
		add(save);
	}
	
	private void drawArrowAttributes()
	{
		setLayout(new GridLayout(2,1));
		JPanel arrowOrderPanel = new JPanel();
		JLabel arrowOrder = new JLabel("Arrow Order: ");
		arrowOrderBox = new JComboBox();
		arrowOrderBox.addItem("2");
		arrowOrderBox.addItem("3");
		arrowOrderBox.setSelectedIndex(((Arrow)component).getOrder()-1);
		
		arrowOrderPanel.add(arrowOrder);
		arrowOrderPanel.add(arrowOrderBox);
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((Arrow) component).setOrder(arrowOrderBox.getSelectedIndex()+1);
			}
		});
		add(arrowOrderPanel);
		add(save);
	}
	
	//From http://tutiez.com/how-to-determine-which-radio-button-is-selected-in-a-button-group-in-java.html
	private int selectedIndex(ButtonGroup group)
	{
		int i=0;
		for (Enumeration eRadio=group.getElements(); eRadio.hasMoreElements(); ) {
			//Iterating over the Radio Buttons
			JRadioButton radioButton = (JRadioButton)eRadio.nextElement();
			
			if (radioButton.getModel() == group.getSelection()) // Comparing radioButtons model with groups selection
			{
				return i;
			}
			i++;
		}
		return -1;
	}
}
