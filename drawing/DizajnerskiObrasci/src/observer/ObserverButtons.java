package observer;

import java.util.Observable;
import java.util.Observer;

import mvc.DrawingFrame;

public class ObserverButtons implements Observer{

	DrawingFrame frame;

	public ObserverButtons(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void update(Observable o, Object arg) {

		ObservableButtons observable = (ObservableButtons) o;
		int size = observable.getSize();
		if (size == 0) {
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnDelete().setEnabled(false);
			frame.getBringToBackBtn().setEnabled(false);
			frame.getBringToFrontBtn().setEnabled(false);
			frame.getToFrontBtn().setEnabled(false);
			frame.getToBackBtn().setEnabled(false);
		} else if (size == 1) {
			frame.getTglbtnModify().setEnabled(true);
			frame.getTglbtnDelete().setEnabled(true);
			frame.getBringToBackBtn().setEnabled(true);
			frame.getBringToFrontBtn().setEnabled(true);
			frame.getToFrontBtn().setEnabled(true);
			frame.getToBackBtn().setEnabled(true);
		} else {
			frame.getTglbtnModify().setEnabled(false);
			frame.getTglbtnDelete().setEnabled(true);
			frame.getBringToBackBtn().setEnabled(false);
			frame.getBringToFrontBtn().setEnabled(false);
			frame.getToFrontBtn().setEnabled(false);
			frame.getToBackBtn().setEnabled(false);
		}

	}
}
