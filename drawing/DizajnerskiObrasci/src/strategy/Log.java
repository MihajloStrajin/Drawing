package strategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingFrame;

public class Log implements Saving{
	
	DefaultListModel<String> dlm;
	int indexLog;
	
	public Log(DefaultListModel<String> dlm, int indexLog) {
		this.dlm = dlm;
		this.indexLog = indexLog;
	}

	@Override
	public void save() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save log");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int resp = fc.showSaveDialog(null);
		if (resp == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			FileWriter fw = null;
			try {
				fw = new FileWriter(file + ".txt");
				for (int i = 0; i < indexLog; i++) {
					fw.write(dlm.getElementAt(i) + "\r\n");
				}
				fw.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException ex) {
					}
				}
			}
		}
		
	}

}
