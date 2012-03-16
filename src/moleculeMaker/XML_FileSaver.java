package moleculeMaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class XML_FileSaver implements ActionListener{
	
	private JFileChooser saver = new JFileChooser(new File(new File(".").getAbsolutePath()));
	private File destination;
	private int returnVal;
	MMView storage;
	
	public XML_FileSaver(MMView view)
	{
		saver.addActionListener(this);
		returnVal = saver.showSaveDialog(null);
		storage = view;
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
		return destination.getAbsolutePath();
	}

}
