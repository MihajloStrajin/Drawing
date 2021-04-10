package mvc;

import java.util.ArrayList;
import java.util.Stack;

import geometry.Point;
import geometry.Shape;

public class DrawingModel {
	ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	public void add(Shape shape) {
		shapes.add(shape);
	}
	public void remove(Shape shape) {
		shapes.remove(shape);
	}
	
	

}
