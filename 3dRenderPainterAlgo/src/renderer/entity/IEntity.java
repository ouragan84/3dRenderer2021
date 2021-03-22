package renderer.entity;

import java.util.List;

import renderer.shapes.WorldShape;

public interface IEntity {
	
	void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees);
	
	public List<WorldShape> getPolygons();

	//please change to translateOffset and add real translate method
	void translate(double x, double y, double z);
	
	void setPos(double x, double y, double z);
	
	Object getEntity();
}
