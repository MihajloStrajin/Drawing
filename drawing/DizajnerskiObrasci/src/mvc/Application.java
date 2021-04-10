package mvc;

import javax.swing.JFrame;

import observer.ObservableButtons;
import observer.ObserverButtons;

public class Application {

	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		
		ObserverButtons observerButtons = new ObserverButtons(frame);
		ObservableButtons observableButtons= controller.getObservableButtons();
		observableButtons.addObserver(observerButtons);
		 
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
