package renderer.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.shapes.LineSegment;
import renderer.shapes.MyPolygon;
import renderer.shapes.Polyhedron;
import renderer.shapes.WorldShape;

public class CurveLine implements IEntity {
	
	private static final float decayFactor = 0.95f;
	private List<LineSegment> lines;
	private MyVector scale;
	private MyVector rotation;
	private MyPoint origin;
	private Color color;
	
	public CurveLine(List<LineSegment> lines, MyVector scale, MyVector rotation, MyPoint origin, Color color, boolean isDecaying) {
		this.lines = lines;
		this.scale = scale;
		this.rotation = rotation;
		this.origin = origin;
		this.color = color;
		
		if(isDecaying) {
			this.setDecayingPolygonColor();
		}else {
			this.setPolygonColor();
		}
	}
	
	public MyVector getScale() {
		return scale;
	}

	public void setScale(MyVector scale) {
		this.scale = scale;
	}

	public MyVector getRotation() {
		return rotation;
	}

	public void setRotation(MyVector rotation) {
		this.rotation = rotation;
	}

	public MyPoint getOrigin() {
		return origin;
	}

	public void setOrigin(MyPoint origin) {
		this.origin = origin;
	}
	
	public void translate(double x, double y, double z) {
		for(LineSegment p : this.lines) {
			p.translate(x, y, z);
		}
	}
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(LineSegment p : this.lines) {
			p.rotate(CW, xDegrees, yDegrees, zDegrees, this.origin);
		}
	}
	
	private void setPolygonColor() {
		for(LineSegment poly : this.lines) {
			poly.setColor(this.color);
		}
	}
	
	private void setDecayingPolygonColor() {
		for(LineSegment poly : this.lines) {
			poly.setColor(this.color);
			int r = (int) (this.color.getRed() * decayFactor);
			int g = (int) (this.color.getGreen() * decayFactor);
			int b = (int) (this.color.getBlue() * decayFactor);
			this.color = new Color(r, g, b);
		}
	}
	
	public List<WorldShape> getPolygons() {
//		System.out.println("        Line " + lines.size());
		List<WorldShape> tmp = new ArrayList<WorldShape>();
		for(LineSegment l : lines) {
			tmp.add(l);
		}
		return tmp;
	}

	@Override
	public void setPos(double x, double y, double z) {
		for(LineSegment l : this.lines) {
			l.setPos(x,y,z);
		}
	}

	@Override
	public Object getEntity() {
		// TODO Auto-generated method stub
		return this;
	}
}
