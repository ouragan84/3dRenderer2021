package renderer.shapes;

import java.awt.Color;

import renderer.point.MyPoint;
import renderer.point.MyVector;

public class Polyhedron {
	
	private MyPolygon[] polygons;
	private MyVector scale;
	private MyVector rotation;
	private Color color;
	private MyPoint origin;
	private static final double decayFactor = 0.95;
	
	public Polyhedron(MyPoint origin, MyVector scale, MyVector rotation, Color color, boolean decayColor, MyPolygon... polygons) {
		this.color = color;
		this.origin = origin;
		this.rotation = rotation;
		this.polygons = polygons;
		if(decayColor) {
			this.setDecayingPolygonColor();
		}else {
			this.setPolygonColor();
		}
	}

	public Polyhedron(MyPoint origin, MyVector scale, MyVector rotation, MyPolygon... polygons) {
		this.color = Color.WHITE;
		this.origin = origin;
		this.rotation = rotation;
		this.polygons = polygons;
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
	//please change to translateOffset and add real translate method
	public void translate(double x, double y, double z) {
		for(MyPolygon p : this.polygons) {
			p.translate(x, y, z);
		}
		origin.x += x;
		origin.y += y;
		origin.z += z;
	}
	
	public void setPos(double x, double y, double z) {
		for(MyPolygon p : this.polygons) {
			p.setPos(x, y, z, origin);
		}
		origin = new MyPoint(x,y,z);
	}
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(MyPolygon p : this.polygons) {
			p.rotate(CW, xDegrees, yDegrees, zDegrees, this.origin);
		}
	}
	
	private void setPolygonColor() {
		for(MyPolygon poly : this.polygons) {
			poly.setColor(this.color);
		}
	}
	
	private void setDecayingPolygonColor() {
		for(MyPolygon poly : this.polygons) {
			poly.setColor(this.color);
			int r = (int) (this.color.getRed() * decayFactor);
			int g = (int) (this.color.getGreen() * decayFactor);
			int b = (int) (this.color.getBlue() * decayFactor);
			this.color = new Color(r, g, b);
		}
	}
	
	public MyPolygon[] getPolygons() {
//		System.out.println("        Tetra " + polygons.length);
		return polygons;
	}
	
}
