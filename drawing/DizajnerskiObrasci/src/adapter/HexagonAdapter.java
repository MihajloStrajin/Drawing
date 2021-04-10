package adapter;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.plaf.ColorUIResource;

import geometry.Circle;
import geometry.Point;
import geometry.Shape;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape {
	Hexagon hexagon = new Hexagon(-1,-1,-1);
	
	public HexagonAdapter(int x, int y, int r, Color innerColor, Color outerColor) {
		hexagon.setX(x);
		hexagon.setY(y);
		hexagon.setR(r);
		hexagon.setAreaColor(innerColor);
		hexagon.setBorderColor(outerColor);
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.hexagon.setX(hexagon.getX() + byX);
		this.hexagon.setY(hexagon.getY() + byY);
		
	}

	@Override
	public int compareTo(Object arg0) {
		if(arg0 instanceof Hexagon) {
			return (int) (this.hexagon.getR() - ((Hexagon) arg0).getR());
		}
		return 0;
	}

	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
		
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}
	
	public Hexagon getHexagon() {
		return this.hexagon;
	}
	
	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	public boolean isSelected() {
		return hexagon.isSelected();
	}

	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}
	
	public String toString() {
		return "Hexagon " + "Center=(" + hexagon.getX() + "," + hexagon.getY() + ") " + "radius=" + hexagon.getR() + " Outer color " + hexagon.getBorderColor().getRGB() + " Inner color " + hexagon.getAreaColor().getRGB();
	}
	
	public HexagonAdapter clone() {
		HexagonAdapter ha = new HexagonAdapter(-1,-1,-1,Color.black, Color.black);
		
		ha.getHexagon().setX(this.getHexagon().getX());
		ha.getHexagon().setY(this.getHexagon().getY());
		ha.getHexagon().setR(this.getHexagon().getR());
		ha.getHexagon().setBorderColor(this.getHexagon().getBorderColor());
		ha.getHexagon().setAreaColor(this.getHexagon().getAreaColor());
		
		return ha;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			HexagonAdapter pros = (HexagonAdapter) obj;
			if (this.getHexagon().getX() == pros.getHexagon().getX() && this.getHexagon().getY() == pros.getHexagon().getY() && this.getHexagon().getY() == pros.getHexagon().getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
