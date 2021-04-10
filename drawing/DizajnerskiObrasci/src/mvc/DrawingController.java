package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBack;
import command.BringToFront;
import command.Command;
import command.RemoveMultipleShapes;
import command.RemoveShapeCmd;
import command.ToBackCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import command.ToFrontCmd;
import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgHexagon;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import hexagon.Hexagon;
import observer.ObservableButtons;
import strategy.Drawing;
import strategy.Log;
import strategy.Saving;

public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	AddShapeCmd addShapeCmd;
	RemoveShapeCmd removeShapeCmd;
	RemoveMultipleShapes removeMultipleShapesCmd;
	UpdatePointCmd updatePointCmd;
	UpdateLineCmd updateLineCmd;
	UpdateCircleCmd updateCircleCmd;
	UpdateRectangleCmd updateRectangleCmd;
	UpdateDonutCmd updateDonutCmd;
	UpdateHexagonCmd updateHexagonCmd;
	BringToBack bringToBackCmd;
	BringToFront bringToFrontCmd;
	ToFrontCmd toFrontCmd;
	ToBackCmd toBackCmd;
	Stack<Command> commands1 = new Stack<Command>();
	Stack<Command> commands2 = new Stack<Command>();
	ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
	Point startPoint;
	Color outerColor = Color.black;
	Color innerColor = Color.white;
	int indexLog = 0;
	Shape selectedShape;
	
	Saving saving;
	String line;
	StringTokenizer tokenizer;
	int indexLoad = 0;

	private ObservableButtons observableButtons = new ObservableButtons(this);

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
	}

	protected void mouseClicked(MouseEvent e) {
		Shape newShape = null;
		if (frame.getTglbtnSelect().isSelected()) {
			Iterator<Shape> it = model.getShapes().iterator();
			selectedShape = null;
			while (it.hasNext()) {
				Shape shape = it.next();
				if (shape.contains(e.getX(), e.getY())) {
					selectedShape = shape;
				}
			}
			
			if (selectedShape == null) {
				Iterator<Shape> it2 = selectedShapes.iterator();
				while (it2.hasNext()) {
					it2.next().setSelected(false);
				}
				frame.dlm.add(indexLog++, "Deselected all");
				this.selectedShapes.clear();
				observableButtons.enableButtons();
			}
			else {
				if (selectedShape.isSelected() == false) {
					selectedShapes.add(selectedShape);
					selectedShape.setSelected(true);
					frame.dlm.add(indexLog++, "Selected " + selectedShape.toString());
					observableButtons.enableButtons();
				} else {
					selectedShapes.remove(selectedShape);
					selectedShape.setSelected(false);
					frame.dlm.add(indexLog++, "Deselected " + selectedShape.toString());
					observableButtons.enableButtons();
				}
			}
		}else

		{
			Iterator<Shape> it2 = selectedShapes.iterator();
			while(it2.hasNext()) {
				it2.next().setSelected(false);
			}
			this.selectedShapes.clear();
			observableButtons.enableButtons();
			if (frame.getTglbtnPoint().isSelected()) {
				newShape = new Point(e.getX(), e.getY(), false, outerColor);
			} else if (frame.getTglbtnLine().isSelected()) {
				if (getStartPoint() == null)
					setStartPoint(new Point(e.getX(), e.getY()));
				else {
					newShape = new Line(getStartPoint(), new Point(e.getX(), e.getY()), false, outerColor);
					setStartPoint(null);
				}

			} else if (frame.getTglbtnRectangle().isSelected()) {
				DlgRectangle dlg = new DlgRectangle();
				dlg.setModal(true);
				dlg.getTxtUpperLeftPointX().setText(Integer.toString(e.getX()));
				dlg.getTxtUpperLeftPointY().setText(Integer.toString(e.getY()));
				dlg.getTxtUpperLeftPointX().setEditable(false);
				dlg.getTxtUpperLeftPointY().setEditable(false);
				dlg.getBtnOutlineColor().setBackground(outerColor);
				dlg.getBtnInnerColor().setBackground(innerColor);
				dlg.setVisible(true);
				if (!dlg.isOk)
					return;
				try {
					newShape = dlg.getRect();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (frame.getTglbtnCircle().isSelected()) {
				DlgCircle dlg = new DlgCircle();
				dlg.setModal(true);
				dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlg.getTxtCenterY().setText(Integer.toString(e.getY()));

				dlg.getTxtCenterX().setEditable(false);
				dlg.getTxtCenterY().setEditable(false);
				dlg.getBtnOutlineColor().setBackground(outerColor);
				dlg.getBtnInnerColor().setBackground(innerColor);
				dlg.setVisible(true);
				if (!dlg.isOk)
					return;
				try {
					newShape = dlg.getCircle();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (frame.getTglbtnDonut().isSelected()) {
				DlgDonut dlg = new DlgDonut();
				dlg.setModal(true);
				dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlg.getTxtCenterY().setText(Integer.toString(e.getY()));
				dlg.getTxtCenterX().setEditable(false);
				dlg.getTxtCenterY().setEditable(false);
				dlg.getBtnOutlineColor().setBackground(outerColor);
				dlg.getBtnInnerColor().setBackground(innerColor);
				dlg.setVisible(true);
				if (!dlg.isOk)
					return;
				try {
					newShape = dlg.getDonut();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (frame.getTglbtnHexagon().isSelected()) {
				DlgHexagon dlg = new DlgHexagon();
				dlg.setModal(true);
				dlg.getTxtCenterX().setText(Integer.toString(e.getX()));
				dlg.getTxtCenterY().setText(Integer.toString(e.getY()));

				dlg.getTxtCenterX().setEditable(false);
				dlg.getTxtCenterY().setEditable(false);
				dlg.getBtnOutlineColor().setBackground(outerColor);
				dlg.getBtnInnerColor().setBackground(innerColor);
				dlg.setVisible(true);
				if (!dlg.isOk)
					return;
				try {
					HexagonAdapter hexagon = new HexagonAdapter(dlg.getHexagon().getHexagon().getX(), dlg.getHexagon().getHexagon().getY(),
							dlg.getHexagon().getHexagon().getR(), dlg.getHexagon().getHexagon().getAreaColor(),
							dlg.getHexagon().getHexagon().getBorderColor());
					newShape = hexagon;
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (newShape != null) {
				addShapeCmd = new AddShapeCmd(newShape, model);
				addShapeCmd.execute();
				commands1.push(addShapeCmd);
				commands2 = new Stack<Command>();
				frame.getUndo().setEnabled(true);
				frame.getRedo().setEnabled(false);
				frame.dlm.add(indexLog++, "Add " + newShape.toString());
			}
		}

		frame.repaint();
	}

	protected void undo() {
		if (!commands1.isEmpty()) {
			Command pop = commands1.pop();
			commands2.push(pop);
			pop.unexecute();
			frame.dlm.add(indexLog++, "Undo");
			Iterator<Shape> it2 = selectedShapes.iterator();
			while (it2.hasNext()) {
				it2.next().setSelected(false);
			}
			this.selectedShapes.clear();
			observableButtons.enableButtons();
			frame.getRedo().setEnabled(true);
			if (commands1.isEmpty()) {
				frame.getUndo().setEnabled(false);
			}
		}

		frame.repaint();
		observableButtons.enableButtons();
	}

	protected void redo() {
		if (!commands2.isEmpty()) {
			Command pop = commands2.pop();
			commands1.push(pop);
			pop.execute();
			frame.dlm.add(indexLog++, "Redo");
			frame.getUndo().setEnabled(true);
			if (commands2.isEmpty()) {
				frame.getRedo().setEnabled(false);
			}
		}
		frame.repaint();
		observableButtons.enableButtons();
	}

	protected void bringToBack() {
		if (selectedShapes.get(0) != null) {
			bringToBackCmd = new BringToBack(selectedShapes.get(0), model);
			bringToBackCmd.execute();
			commands1.push(bringToBackCmd);
			frame.repaint();
			frame.dlm.add(indexLog++, "BringToBack " + selectedShapes.get(0).toString());
		}
	}

	protected void bringToFront() {
		if (selectedShapes.get(0) != null) {
			bringToFrontCmd = new BringToFront(selectedShapes.get(0), model);
			bringToFrontCmd.execute();
			commands1.push(bringToFrontCmd);
			frame.repaint();
			frame.dlm.add(indexLog++, "BringToFront " + selectedShapes.get(0).toString());
		}
	}
	
	protected void toBack() {
		if(selectedShapes.get(0) !=null) {
			toBackCmd = new ToBackCmd(selectedShapes.get(0), model);
			toBackCmd.execute();
			commands1.push(toBackCmd);
			frame.repaint();
			frame.dlm.add(indexLog++, "ToBack " + selectedShapes.get(0).toString());
		}
	}
	
	protected void toFront() {
		if(selectedShapes.get(0) !=null) {
			toFrontCmd = new ToFrontCmd(selectedShapes.get(0), model);
			toFrontCmd.execute();
			commands1.push(toFrontCmd);
			frame.repaint();
			frame.dlm.add(indexLog++, "ToFront " + selectedShapes.get(0).toString());
		}
	}

	protected void delete() {
		if (selectedShapes.size() == 1) {
			Shape selected = selectedShapes.get(0);
			if (selected != null) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Check",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					removeShapeCmd = new RemoveShapeCmd(selected, model);
					removeShapeCmd.execute();
					commands1.push(removeShapeCmd);
					frame.dlm.add(indexLog++, "Deleted " + selected.toString());
					frame.repaint();
					selected.setSelected(false);
					commands2 = new Stack<>();
					frame.getRedo().setEnabled(false);
					selectedShapes.clear();
					observableButtons.enableButtons();
				}
				
			} else {
				JOptionPane.showMessageDialog(null, "Nothing selected");
			}
		} else if (selectedShapes.size() > 1) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Check",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				removeMultipleShapesCmd = new RemoveMultipleShapes(selectedShapes, model);
				removeMultipleShapesCmd.execute();
				Iterator<Shape> it2 = selectedShapes.iterator();
				while (it2.hasNext()) {
					it2.next().setSelected(false);
				}
				commands1.push(removeMultipleShapesCmd);
				commands2 = new Stack<>();
				frame.getRedo().setEnabled(false);
				selectedShapes.clear();
				observableButtons.enableButtons();
				frame.dlm.add(indexLog++, "Deleted selected");
				frame.repaint();

			}
			
		}
	}

	protected void modify() {
		Shape selected = selectedShapes.get(0);
		if (selected != null) {
			if (selected instanceof Point) {
				Point point = (Point) selected;
				DlgPoint dlg = new DlgPoint();
				dlg.getTxtXCoordinate().setText(Integer.toString(point.getX()));
				dlg.getTxtYCoordinate().setText(Integer.toString(point.getY()));
				dlg.getBtnColor().setBackground(point.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk) {
					updatePointCmd = new UpdatePointCmd(point, dlg.getPoint());
					updatePointCmd.execute();
					commands1.push(updatePointCmd);
					frame.dlm.add(indexLog++, "Modified " + dlg.getPoint().toString());

				}
			} else if (selected instanceof Donut) {
				Donut donut = (Donut) selected;
				DlgDonut dlg = new DlgDonut();
				dlg.getTxtCenterX().setText(Integer.toString(donut.getCenter().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(donut.getCenter().getY()));
				dlg.getTxtOuterRadius().setText(Integer.toString(donut.getRadius()));
				dlg.getTxtInnerRadius().setText(Integer.toString(donut.getInnerRadius()));
				dlg.getBtnOutlineColor().setBackground(donut.getColor());
				dlg.getBtnInnerColor().setBackground(donut.getInnerColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk) {
					updateDonutCmd = new UpdateDonutCmd(donut, dlg.getDonut());
					updateDonutCmd.execute();
					commands1.push(updateDonutCmd);
					frame.dlm.add(indexLog++, "Modified " + dlg.getDonut().toString());
				}

			} else if (selected instanceof Line) {
				Line line = (Line) selected;
				DlgLine dlg = new DlgLine();
				dlg.getTxtStartPointX().setText(Integer.toString(line.getStartPoint().getX()));
				dlg.getTxtStartPointY().setText(Integer.toString(line.getStartPoint().getY()));
				dlg.getTxtEndPointX().setText(Integer.toString(line.getEndPoint().getX()));
				dlg.getTxtEndPointY().setText(Integer.toString(line.getEndPoint().getY()));
				dlg.getBtnColor().setBackground(line.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk) {
					updateLineCmd = new UpdateLineCmd(line, dlg.getLine());
					updateLineCmd.execute();
					commands1.push(updateLineCmd);
					frame.dlm.add(indexLog++, "Modified " + dlg.getLine().toString());
				}
			} else if (selected instanceof Circle) {
				Circle circle = (Circle) selected;
				DlgCircle dlg = new DlgCircle();
				dlg.getTxtCenterX().setText(Integer.toString(circle.getCenter().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(circle.getCenter().getY()));
				dlg.getTxtRadius().setText(Integer.toString(circle.getRadius()));
				dlg.getBtnInnerColor().setBackground(circle.getInnerColor());
				dlg.getBtnOutlineColor().setBackground(circle.getColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk) {
					updateCircleCmd = new UpdateCircleCmd(circle, dlg.getCircle());
					updateCircleCmd.execute();
					commands1.push(updateCircleCmd);
					frame.dlm.add(indexLog++, "Modified " + dlg.getCircle().toString());
				}
			} else if (selected instanceof Rectangle) {
				Rectangle rect = (Rectangle) selected;
				DlgRectangle dlg = new DlgRectangle();
				dlg.getTxtUpperLeftPointX().setText(Integer.toString(rect.getUpperLeftPoint().getX()));
				dlg.getTxtUpperLeftPointY().setText(Integer.toString(rect.getUpperLeftPoint().getY()));
				dlg.getTxtWidth().setText(Integer.toString(rect.getWidth()));
				dlg.getTxtHeight().setText(Integer.toString(rect.getHeight()));
				dlg.getBtnOutlineColor().setBackground(rect.getColor());
				dlg.getBtnInnerColor().setBackground(rect.getInnerColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk) {
					updateRectangleCmd = new UpdateRectangleCmd(rect, dlg.getRect());
					updateRectangleCmd.execute();
					commands1.push(updateRectangleCmd);
					frame.dlm.add(indexLog++, "Modified " + dlg.getRect().toString());
				}
			} else if (selected instanceof HexagonAdapter) {
				HexagonAdapter hexagon = (HexagonAdapter) selected;
				DlgHexagon dlg = new DlgHexagon();
				dlg.getTxtCenterX().setText(Integer.toString(hexagon.getHexagon().getX()));
				dlg.getTxtCenterY().setText(Integer.toString(hexagon.getHexagon().getY()));
				dlg.getTxtRadius().setText(Integer.toString(hexagon.getHexagon().getR()));
				dlg.getBtnInnerColor().setBackground(hexagon.getHexagon().getAreaColor());
				dlg.getBtnOutlineColor().setBackground(hexagon.getHexagon().getBorderColor());
				dlg.setModal(true);
				dlg.setVisible(true);
				if (dlg.isOk) {
					updateHexagonCmd = new UpdateHexagonCmd(hexagon,
							new HexagonAdapter(dlg.getHexagon().getHexagon().getX(), dlg.getHexagon().getHexagon().getY(),
									dlg.getHexagon().getHexagon().getR(), dlg.getHexagon().getHexagon().getAreaColor(),
									dlg.getHexagon().getHexagon().getBorderColor()));
					updateHexagonCmd.execute();
					commands1.push(updateHexagonCmd);
					frame.dlm.add(indexLog++, "Modified " + dlg.getHexagon().toString());

				}

			}
			
			commands2 = new Stack<Command>();
			frame.getRedo().setEnabled(false);
			frame.repaint();

		} else {
			JOptionPane.showMessageDialog(null, "Nothing selected");
		}
		observableButtons.enableButtons();
	}

	protected void changeOuterColor() {
		outerColor = JColorChooser.showDialog(null, "Select color", frame.getOuterColorBtn().getBackground());
		if (outerColor != null) {
			frame.getOuterColorBtn().setBackground(outerColor);
			frame.dlm.add(indexLog++, "Changed outer color to " + outerColor.getRGB());
		}
		else {
			outerColor = frame.getOuterColorBtn().getBackground();
		}
		
	}

	protected void changeInnerColor() {
		innerColor = JColorChooser.showDialog(null, "Select color", frame.getInnerColorBtn().getBackground());
		if (innerColor != null) {
			frame.getInnerColorBtn().setBackground(innerColor);
			frame.dlm.add(indexLog++, "Changed inner color to " + innerColor.getRGB());
		}else {
			innerColor = frame.getInnerColorBtn().getBackground();
		}
		
	}

	public void saveLog() {
		saving = new Log(frame.getDlm(), indexLog);
		saving.save();
	}
	
	private void clearDrawingAndLog() {
		model.getShapes().clear();
		frame.dlm.clear();
		indexLog=0;
		indexLoad=0;
		selectedShapes.clear();
		observableButtons.enableButtons();
		frame.repaint();
		commands1.clear();
		commands2.clear();
		frame.getUndo().setEnabled(false);
		frame.getRedo().setEnabled(false);
	}

	public void loadLog() {
		File dir = null;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Load log");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileReader fr = null;
		BufferedReader br = null;

		try {
			int resp = fc.showOpenDialog(null);
			if (resp == JFileChooser.APPROVE_OPTION) {
				clearDrawingAndLog();
				dir = fc.getSelectedFile();
				fr = new FileReader(dir);
				br = new BufferedReader(fr);

				String line;
				while ((line = br.readLine()) != null) {
					frame.dlm.addElement(line);
					indexLog++;
				}
				frame.getLoadBtn().setEnabled(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		commands1 = new Stack<Command>();
		commands2 = new Stack<Command>();
	}
	
	protected void saveDrawing(Serializable data) throws Exception {
		saving = new Drawing(data);
		saving.save();
		
	}
	
	protected void loadDrawing() throws Exception{
		File dir = null;
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Load drawing");
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int resp = fc.showOpenDialog(null);
		if (resp == JFileChooser.APPROVE_OPTION) {
			model.getShapes().clear();
			frame.dlm.clear();
			indexLog=0;
			indexLoad=0;
			frame.repaint();
			dir = fc.getSelectedFile();
			try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(dir.toString())))){
				model.getShapes().addAll((ArrayList<Shape>)ois.readObject());
			}
		}
		commands1 = new Stack<Command>();
		commands2 = new Stack<Command>();
		frame.getLoadBtn().setEnabled(false);
		frame.repaint();
	}

	private void addShape(Shape s) {

		addShapeCmd = new AddShapeCmd(s, model);
		addShapeCmd.execute();
		commands1.push(addShapeCmd);
		commands2 = new Stack<Command>();
		frame.getUndo().setEnabled(true);
		frame.getRedo().setEnabled(false);
	}

	private void selectShape(Shape s) {
		Shape shape = model.getShapes().get(model.getShapes().indexOf(s));
		shape.setSelected(true);
		selectedShapes.add(shape);
		observableButtons.enableButtons();
	}

	private void deselectAll() {
		Iterator<Shape> it2 = selectedShapes.iterator();
		while (it2.hasNext()) {
			it2.next().setSelected(false);
		}
		this.selectedShapes.clear();
		observableButtons.enableButtons();
	}
	private void deselect(Shape s) {
		Shape shape = model.getShapes().get(model.getShapes().indexOf(s));
		if(shape !=null) {
			selectedShapes.remove(shape);
			shape.setSelected(false);
			observableButtons.enableButtons();
		}
	}

	Shape s = null;

	public void loadOneByOne() {
		line = frame.dlm.getElementAt(indexLoad++);
		tokenizer = new StringTokenizer(line, " (),=>");
		ArrayList<String> lineElements = new ArrayList<String>();
		while (tokenizer.hasMoreElements()) {
			lineElements.add(tokenizer.nextToken());
		}
		if (lineElements.contains("Point")) {
			s = new Point(Integer.parseInt(lineElements.get(2)), Integer.parseInt(lineElements.get(3)), false,
					new Color(Integer.parseInt(lineElements.get(4))));
		} else if (lineElements.contains("Line")) {
			s = new Line(new Point(Integer.parseInt(lineElements.get(2)), Integer.parseInt(lineElements.get(3))),
					new Point(Integer.parseInt(lineElements.get(4)), Integer.parseInt(lineElements.get(5))), false,
					new Color(Integer.parseInt(lineElements.get(6))));
		} else if (lineElements.contains("Circle")) {
			s = new Circle(new Point(Integer.parseInt(lineElements.get(3)), Integer.parseInt(lineElements.get(4))),
					Integer.parseInt(lineElements.get(6)), false, new Color(Integer.parseInt(lineElements.get(9))),
					new Color(Integer.parseInt(lineElements.get(12))));
		} else if (lineElements.contains("Rectangle")) {
			s = new Rectangle(new Point(Integer.parseInt(lineElements.get(5)), Integer.parseInt(lineElements.get(6))),
					Integer.parseInt(lineElements.get(8)), Integer.parseInt(lineElements.get(10)), false,
					new Color(Integer.parseInt(lineElements.get(13))),
					new Color(Integer.parseInt(lineElements.get(16))));
		} else if (lineElements.contains("Donut")) {
			s = new Donut(new Point(Integer.parseInt(lineElements.get(3)), Integer.parseInt(lineElements.get(4))),
					Integer.parseInt(lineElements.get(6)), Integer.parseInt(lineElements.get(9)), false,
					new Color(Integer.parseInt(lineElements.get(12))),
					new Color(Integer.parseInt(lineElements.get(15))));
		} else if (lineElements.contains("Hexagon")) {
			s = new HexagonAdapter(Integer.parseInt(lineElements.get(3)), Integer.parseInt(lineElements.get(4)),
					Integer.parseInt(lineElements.get(6)), new Color(Integer.parseInt(lineElements.get(12))),
					new Color(Integer.parseInt(lineElements.get(9))));
		}
		if (line.contains("Add")) {
			addShape(s);
		} else if (line.contains("Selected")) {
			selectShape(s);
		} else if(line.contains("all")) {
			deselectAll();
		}else if (line.contains("Deselected")) {
			deselect(s);
		}
		else if (line.contains("Deleted")) {
			if (selectedShapes.size() == 1) {
				Shape selected = selectedShapes.get(0);
				if (selected != null) {
					removeShapeCmd = new RemoveShapeCmd(selected, model);
					removeShapeCmd.execute();
					commands1.push(removeShapeCmd);
					frame.repaint();

					selected.setSelected(false);
					commands2 = new Stack<>();
					frame.getRedo().setEnabled(false);
					frame.repaint();
					observableButtons.enableButtons();
				} else {
					JOptionPane.showMessageDialog(null, "Nothing selected");
				}
			} else if (selectedShapes.size() > 1) {

				removeMultipleShapesCmd = new RemoveMultipleShapes(selectedShapes, model);
				removeMultipleShapesCmd.execute();
				commands1.push(removeMultipleShapesCmd);
				frame.repaint();
				Iterator<Shape> it = selectedShapes.iterator();
				while (it.hasNext()) {
					it.next().setSelected(false);
				}
				selectedShapes.clear();
				commands2 = new Stack<>();
				frame.getRedo().setEnabled(false);
				frame.repaint();
				observableButtons.enableButtons();
			}
			observableButtons.enableButtons();

		} else if (line.contains("Modified")) {
			
			if (lineElements.contains("Point")) {
				updatePointCmd = new UpdatePointCmd((Point) selectedShapes.get(0), (Point) s);
				updatePointCmd.execute();
				commands1.push(updatePointCmd);
			} else if (lineElements.contains("Line")) {
				updateLineCmd = new UpdateLineCmd((Line) selectedShapes.get(0), (Line) s);
				updateLineCmd.execute();
				commands1.push(updateLineCmd);
			} else if (lineElements.contains("Rectangle")) {
				updateRectangleCmd = new UpdateRectangleCmd((Rectangle) selectedShapes.get(0), (Rectangle) s);
				updateRectangleCmd.execute();
				commands1.push(updateRectangleCmd);
			} else if (lineElements.contains("Circle")) {
				updateCircleCmd = new UpdateCircleCmd((Circle) selectedShapes.get(0), (Circle) s);
				updateCircleCmd.execute();
				commands1.push(updateCircleCmd);
			} else if (lineElements.contains("Donut")) {
				updateDonutCmd = new UpdateDonutCmd((Donut) selectedShapes.get(0), (Donut) s);
				updateDonutCmd.execute();
				commands1.push(updateDonutCmd);
			} else if (lineElements.contains("Hexagon")) {
				updateHexagonCmd = new UpdateHexagonCmd((HexagonAdapter) selectedShapes.get(0), (HexagonAdapter) s);
				updateHexagonCmd.execute();
				commands1.push(updateHexagonCmd);
			}
		} else if (line.contains("Undo")) {
			if (!commands1.isEmpty()) {
				Command pop = commands1.pop();
				commands2.push(pop);
				pop.unexecute();
				Iterator<Shape> it2 = selectedShapes.iterator();
				while (it2.hasNext()) {
					it2.next().setSelected(false);
				}
				this.selectedShapes.clear();
				observableButtons.enableButtons();
				frame.getRedo().setEnabled(true);
				if (commands1.isEmpty()) {
					frame.getUndo().setEnabled(false);
				}
			}

			frame.repaint();
			observableButtons.enableButtons();

		}

		else if (line.contains("Redo")) {
			if (!commands2.isEmpty()) {
				Command pop = commands2.pop();
				commands1.push(pop);
				pop.execute();
				frame.getUndo().setEnabled(true);
				if (commands2.isEmpty()) {
					frame.getRedo().setEnabled(false);
				}
			}
			frame.repaint();
			observableButtons.enableButtons();
		} else if (line.contains("BringToBack")) {
				bringToBackCmd = new BringToBack(selectedShapes.get(0), model);
				bringToBackCmd.execute();
				commands1.push(bringToBackCmd);
			
		} else if (line.contains("BringToFront")) {
				bringToFrontCmd = new BringToFront(selectedShapes.get(0), model);
				bringToFrontCmd.execute();
				commands1.push(bringToFrontCmd);
			
		} else if (line.contains("Changed inner color")) {
			innerColor = new Color(Integer.parseInt(lineElements.get(4)));
			if (innerColor != null)
				frame.getInnerColorBtn().setBackground(innerColor);
		} else if (line.contains("Changed outer color")) {
			outerColor = new Color(Integer.parseInt(lineElements.get(4)));
			if (outerColor != null)
				frame.getOuterColorBtn().setBackground(outerColor);
		} else if(line.contains("ToFront")) {
			if(selectedShapes.get(0) !=null) {
				toFrontCmd = new ToFrontCmd(selectedShapes.get(0), model);
				toFrontCmd.execute();
				commands1.push(toFrontCmd);
			}
		}else if(line.contains("ToBack")) {
			if(selectedShapes.get(0) !=null) {
				toBackCmd = new ToBackCmd(selectedShapes.get(0), model);
				toBackCmd.execute();
				commands1.push(toBackCmd);
			}
		}
		
		if (indexLoad == indexLog) {
			frame.getLoadBtn().setEnabled(false);
		}
		frame.repaint();
	}
	
	

	public ArrayList<Shape> getSelectedShapes() {
		return selectedShapes;
	}

	public void setSelectedShapes(ArrayList<Shape> selectedShapes) {
		this.selectedShapes = selectedShapes;
	}

	public ObservableButtons getObservableButtons() {
		return observableButtons;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public DrawingModel getModel() {
		return model;
	}

	public int getIndexLog() {
		return indexLog;
	}

}
