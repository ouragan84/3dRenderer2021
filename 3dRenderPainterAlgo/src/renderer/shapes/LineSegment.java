package renderer.shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;
import renderer.world.Camera;

public class LineSegment implements WorldShape {
	
	public MyPoint StartPoint;
	public MyPoint EndPoint;
	public Color color;
	private boolean visible;
	
	public LineSegment(MyPoint StartPoint, MyPoint EndPoint) {
		this.EndPoint = EndPoint;
		this.StartPoint = StartPoint;
		this.visible = false;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyPoint origin) {
		PointConverter.rotateAxisX(StartPoint, CW, xDegrees, origin);
		PointConverter.rotateAxisY(StartPoint, CW, yDegrees, origin);
		PointConverter.rotateAxisZ(StartPoint, CW, zDegrees, origin);
		PointConverter.rotateAxisX(EndPoint, CW, xDegrees, origin);
		PointConverter.rotateAxisY(EndPoint, CW, yDegrees, origin);
		PointConverter.rotateAxisZ(EndPoint, CW, zDegrees, origin);
	}
	
	public void render(Graphics g) {
		
		g.setColor(color);
		
		Point s = PointConverter.convertPoint(StartPoint);
		Point f = PointConverter.convertPoint(EndPoint);
		
		g.drawLine(s.x, s.y, f.x, f.y);
	}

	@Override
	public MyPoint getAveragePoint() {
		double x = (StartPoint.x + EndPoint.x)/ 2;
		double y = (StartPoint.y + EndPoint.y)/ 2;
		double z = (StartPoint.z + EndPoint.z)/ 2;
		
		return new MyPoint(x, y, z);
	}

	@Override
	public boolean updateVisibility(Camera cam) {
		double dist = cam.forwardDistance(getAveragePoint());
		this.visible = dist >= cam.getNear() && dist <= cam.getFar();
		return this.visible;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return this.visible;
	}

	@Override
	public void updateLightingRatio(MyVector lightVector) {
		
	}

	public void translate(double x, double y, double z) {
		StartPoint.x += x;
		StartPoint.y += y;
		StartPoint.z += z;
		EndPoint.x += x;
		EndPoint.y += y;
		EndPoint.z += z;
	}
	
	public void setPos(double x, double y, double z) {
		EndPoint.x -= (StartPoint.x-x);
		EndPoint.y -= (StartPoint.y-y);
		EndPoint.z -= (StartPoint.z-z);
		StartPoint.x = x;
		StartPoint.y = y;
		StartPoint.z = z;
		
	}
}
