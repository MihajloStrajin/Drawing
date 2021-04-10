package strategy;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

import mvc.DrawingController;
import mvc.DrawingFrame;

public class Drawing implements Saving{
	
	Serializable data;
	
	public Drawing( Serializable data) {
		this.data = data;
	}

	@Override
	public void save() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save drawing");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int resp = fc.showSaveDialog(null);
		File file = fc.getSelectedFile();
		if (resp == JFileChooser.APPROVE_OPTION) {
			try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(file.toString())))){
				oos.writeObject(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
