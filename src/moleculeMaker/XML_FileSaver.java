package moleculeMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class XML_FileSaver implements ActionListener{
	
	private JFileChooser saver = new JFileChooser(new File(new File(".").getAbsolutePath()));
	private File destination;
	private int returnVal;
	
	public XML_FileSaver()
	{
		saver.addActionListener(this);
		returnVal = saver.showSaveDialog(null);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			System.out.println(saver.getSelectedFile().getAbsolutePath());
			destination = new File(saver.getSelectedFile().getAbsolutePath());
		}
	}
	
	public String getDestination()
	{
		String path = destination.getAbsolutePath();
		if(path.contains("."))
		{
			if(path.length() == 0 || path.substring(0, path.indexOf(".")).length() == 0)
			{
				return "Example.xml";
			}
			else
			{
				return path.substring(0, path.indexOf(".")) + ".xml";
			}
		}
		else
		{
			return path + ".xml";
		}
	}

}
