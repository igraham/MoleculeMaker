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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


@SuppressWarnings("serial")
public class ElementAttributesModifier extends JPanel implements ActionListener
{
	
	Element element;
	JTextField name;
	JButton save;
	JComboBox electronCount;
	ButtonGroup charge;
	
	// TODO Load an elements attributes from ElementList.getSelected()
	// TODO Save an elements new attributes
	
	public ElementAttributesModifier(Element element)
	{
		if (element != null)
		{
			System.out.println("Passing in " + element + " to ElementAttributesModifier");
			this.element = element;
			
			draw();
			
			save.addActionListener(this);
		}
		else
		{
			JLabel unselected = new JLabel("No element selected.");
			this.add(unselected);
		}
		
	}

	private void draw()
	{
		setLayout(new GridLayout(4,1));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		JLabel nameLabel = new JLabel("Element symbol: ");
		name = new JTextField(element.getName());
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
		switch(element.getCharge())
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
		electronCount.setSelectedIndex(element.getElectrons());
		
		save = new JButton("Save");
		
		electronCountPanel.add(electronCountLabel);
		electronCountPanel.add(electronCount);
		
		add(namePanel);
		add(chargePanel);
		add(electronCountPanel);
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

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		element.setName(name.getText());
		element.setElectrons(electronCount.getSelectedIndex());
		element.setCharge(selectedIndex(charge));
	}
}
