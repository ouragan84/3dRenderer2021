package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;
import renderer.world.Camera;

public class MyPolygon implements WorldShape{
	
	private static final double AMBIENT_LIGHTNING = .2;
	private static final double LIGHT_SHARPNESS = .95;
	private Color baseColor, lighingColor;
	private MyPoint[] points;
	private boolean visible;
	
	public MyPolygon(Color color, MyPoint... points) {
		this.baseColor = this.lighingColor = color;
		this.points = new MyPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x, p.y, p.z);
		}
		this.visible = false;
	}
	
	public MyPolygon(MyPoint... points) {
		this.baseColor = this.lighingColor = Color.WHITE;
		this.points = new MyPoint[points.length];
		for(int i = 0; i < points.length; i++) {
			MyPoint p = points[i];
			this.points[i] = new MyPoint(p.x, p.y, p.z);
		}
		this.visible = false;
	}
	
	public void render(Graphics g) {
		Polygon poly = new Polygon();
		for(int i = 0; i < this.points.length; i++) {
			Point p = PointConverter.convertPoint(points[i]);
			poly.addPoint(p.x, p.y);
		}
		
		g.setColor(this.lighingColor);
		g.fillPolygon(poly);
	}
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyPoint origin) {
		for(MyPoint p : points) {
			PointConverter.rotateAxisX(p, CW, xDegrees, origin);
			PointConverter.rotateAxisY(p, CW, yDegrees, origin);
			PointConverter.rotateAxisZ(p, CW, zDegrees, origin);
		}
	}
	
	public void scale(boolean up, double xScale, double yScale, double zScale, MyPoint origin) {
		for(MyPoint p : points) {
			PointConverter.scale(p, up, new MyVector(xScale, yScale, zScale), origin);
		}
	}
	
	public double getAverageX() {
		double sum = 0;
		for(MyPoint p : this.points) {
			sum += p.x;
		}
		
		return sum / this.points.length;
	}
	
	public void setColor(Color color) {
		this.baseColor = color;
	}
	
	
	
	public void updateLightingRatio(MyVector lightVector) {
		if(this.points.length < 3) {
			return;
		}
		
		MyVector v1 = new MyVector(this.points[0], this.points[1]);
		MyVector v2 = new MyVector(this.points[1], this.points[2]);
		MyVector normal = MyVector.abs(MyVector.cross(v2, v1));
		MyVector lightNormal = MyVector.abs(lightVector);
		
		double dot = MyVector.dot(normal, lightNormal);
		double sign = dot < 0 ? 1 : -1;
		dot = sign * dot * dot;
		dot = (dot + 1) / 2 * LIGHT_SHARPNESS;
		
		double lightRatio = Math.min(1, Math.max(0, AMBIENT_LIGHTNING+ dot));
		this.updateLightingColor(lightRatio);
	}
	
	private void updateLightingColor(double lightRatio) {
		int red = (int) (this.baseColor.getRed() * lightRatio);
		int blue = (int) (this.baseColor.getBlue() * lightRatio);
		int green = (int) (this.baseColor.getGreen() * lightRatio);
		this.lighingColor = new Color(red, green, blue);
	}
	
	public MyPoint getAveragePoint() {
		double x = 0;
		double y = 0;
		double z = 0;
		for(MyPoint p : this.points) {
			x += p.x;
			y += p.y;
			z += p.z;
		}
		
		x /= this.points.length;
		y /= this.points.length;
		z /= this.points.length;
		
		return new MyPoint(x, y, z);
	}
	
	public void translate(double x, double y, double z) {
		for(MyPoint p : points) {
			p.x += x;
			p.y += y;
			p.z += z;
		}
	}
	
	public void setPos(double x, double y, double z, MyPoint origin) {
		for(MyPoint p : points) {
			p.x = p.x - origin.x + x;
			p.y = p.y - origin.y + y;
			p.z = p.z - origin.z + z;
		}
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public boolean updateVisibility(Camera cam) {
		double dist = cam.forwardDistance(getAveragePoint());
		this.visible = dist >= cam.getNear() && dist <= cam.getFar();
		return this.visible;
	}
}
