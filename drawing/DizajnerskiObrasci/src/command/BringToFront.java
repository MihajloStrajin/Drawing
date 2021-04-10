package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFront implements Command{
	
	private Shape shape;
	private DrawingModel model;
	private int pos;
	int last;
	
	public BringToFront(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
		pos = model.getShapes().indexOf(shape);
		last = model.getShapes().size() - 1;
	}

	@Override
	public void execute() {
		for(int i=pos;i<last;i++) {
			Collections.swap(model.getShapes(), i, i+1);
		}
		

	}

	@Override
	public void unexecute() {
		for(int i=last;i>pos;i--) {
			Collections.swap(model.getShapes(), i, i-1);
		}

	}
	
}
