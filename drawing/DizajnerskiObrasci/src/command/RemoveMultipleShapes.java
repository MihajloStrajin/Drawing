package command;

import java.util.ArrayList;
import java.util.Iterator;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveMultipleShapes implements Command {
	
	private ArrayList<Shape> shapes;
	private DrawingModel model;
	private ArrayList<Shape> deleted = new ArrayList<Shape>();
	
	public RemoveMultipleShapes(ArrayList<Shape> shapes, DrawingModel model) {
		this.shapes = shapes;
		this.model = model;
		deleted.addAll(shapes);
	}

	@Override
	public void execute() {
		Iterator<Shape> it = this.deleted.iterator();
		while(it.hasNext()) {
			Shape shape = it.next();
			model.remove(shape);
		}
		
	}

	@Override
	public void unexecute() {
		Iterator<Shape> it = this.deleted.iterator();
		while(it.hasNext()) {
			Shape shape = it.next();
			model.add(shape);
		}
	}

}
