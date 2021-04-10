package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBack implements Command {
	
	private Shape shape;
	private DrawingModel model;
	private int pos;
	
	public BringToBack(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
		pos = model.getShapes().indexOf(shape);
	}

	@Override
	public void execute() {
		for(int i=pos;i>0;i--) {
			Collections.swap(model.getShapes(), i, i-1);
		}
		

	}

	@Override
	public void unexecute() {
		for(int i =0;i<pos;i++) {
			Collections.swap(model.getShapes(), i, i+1);
		}

	}

}
