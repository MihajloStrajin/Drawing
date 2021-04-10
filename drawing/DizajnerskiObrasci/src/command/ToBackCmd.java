package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command {
	
	private Shape shape;
	private DrawingModel model;
	
	public ToBackCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		if(model.getShapes().indexOf(shape) > 0) {
			Collections.swap(model.getShapes(), model.getShapes().indexOf(shape), model.getShapes().indexOf(shape) - 1);
		}

	}

	@Override
	public void unexecute() {
		
		if(model.getShapes().indexOf(shape) < model.getShapes().size()-1) {
			Collections.swap(model.getShapes(), model.getShapes().indexOf(shape), model.getShapes().indexOf(shape) + 1);
		}

	}

}
