package observer;

import java.util.Observable;

import mvc.DrawingController;

public class ObservableButtons extends Observable{
	private int size;
	private DrawingController controller;
	
	public ObservableButtons(DrawingController controller) {
		this.controller = controller;
	}
	
	public void enableButtons() {
		this.size = controller.getSelectedShapes().size();
		setChanged();
		notifyObservers();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
