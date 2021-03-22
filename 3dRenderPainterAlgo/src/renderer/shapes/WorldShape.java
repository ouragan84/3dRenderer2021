package renderer.shapes;

import java.awt.Graphics;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.world.Camera;

public interface WorldShape {
	
	
	
	public void render(Graphics g);
	
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyPoint origin);
	
	public MyPoint getAveragePoint();
	
	public boolean updateVisibility(Camera cam);
	
	public boolean isVisible();
	
	public static List<WorldShape> sortShapes(List<WorldShape> tmpPolyArray, Camera cam) {
		
		Collections.sort(tmpPolyArray, new Comparator<WorldShape>() {
			public int compare(WorldShape s1, WorldShape s2) {
				MyPoint s1Avg = s1.getAveragePoint();
				MyPoint s2Avg = s2.getAveragePoint();
				MyPoint cameraPoint = cam.getPosition();
				double s1Dist = MyPoint.dist(s1Avg, cameraPoint);
				double s2Dist = MyPoint.dist(s2Avg, cameraPoint);
				double dist = s1Dist - s2Dist;
				
				if(dist == 0)
					return 0;
				
				return dist < 0 ? 1 : -1;
			}
		});
		
		return tmpPolyArray;
	}

	public void updateLightingRatio(MyVector lightVector);
}
