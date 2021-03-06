package renderer.point;

import java.awt.Point;

import renderer.Display;
import renderer.world.Camera;

public class PointConverter {
	
	public static Camera camera;
	
	/**
	 * @param point3D
	 * @return
	 */
	public static MyPoint worldToView(MyPoint in) {
		MyPoint out = new MyPoint(in.x, in.y, in.z);
		
		out.x -= camera.getPosition().x;
		out.y -= camera.getPosition().y;
		out.z -= camera.getPosition().z;
		
		out.x *= camera.getScale().x;
		out.y *= camera.getScale().y;
		out.z *= camera.getScale().z;
		
		double temp = out.y;
		double zTheta = 2 * Math.PI / 360 * camera.getRotation().z;
		out.y = temp * Math.cos(zTheta) - out.x * Math.sin(zTheta);
		out.x = out.x * Math.cos(zTheta) + temp * Math.sin(zTheta);
		
		temp = out.x;
		double yTheta = 2 * Math.PI / 360 * camera.getRotation().y;
		out.x = temp * Math.cos(yTheta) - out.z * Math.sin(yTheta);
		out.z = out.z * Math.cos(yTheta) + temp * Math.sin(yTheta);
		
		temp = out.y;
		double xTheta = 2 * Math.PI / 360 * camera.getRotation().x;
		out.y = temp * Math.cos(xTheta) - out.z * Math.sin(xTheta);
		out.z = out.z * Math.cos(xTheta) + temp * Math.sin(xTheta);
		
		return out;
	}
	
	public static Point viewToScreen(MyPoint in) {
//		double dist = Math.sqrt(Math.pow(in.z, 2) + Math.pow(in.y, 2));
//		double theta = Math.atan2(in.z, in.y);
//		
//		dist *= (camera.getDepthOfField() == 0? 1 : Math.abs(camera.getDepthOfField()/(camera.getDepthOfField() - in.x)));
//		
//		Point out = new Point(
//				(int) (Display.WIDTH / 2 + (dist * Math.cos(theta))),
//				(int) (Display.HEIGHT / 2 - (dist * Math.sin(theta))));
//		
//		return out;
		
		double halfPlaneWidth = Math.abs(in.x) * Math.tan((Math.PI/360) * camera.getFovX());
		double halfPlaneHeight = Math.abs(in.x) * Math.tan((Math.PI/360) * camera.getFovY());
		
		Point out = new Point(
				(int) ((Display.WIDTH / 2) * ( 1 + in.y / halfPlaneWidth)),
				(int) ((Display.HEIGHT / 2) * ( 1 - in.z / halfPlaneHeight)));
		
		return out;
	}
	
	public static Point convertPoint(MyPoint point3D) {
		return viewToScreen(worldToView(point3D));
	}
	
	/**
	 * @param p
	 * @param CW
	 * @param degrees
	 * @param origin
	 */
	public static void rotateAxisX(MyPoint p, boolean CW, double degrees, MyPoint origin) {
		double y = p.y - origin.y;
		double z = p.z - origin.z;
		double theta = 2*Math.PI/360*degrees*(CW?-1:1);
		p.y = y * Math.cos(theta) - z * Math.sin(theta) + origin.y;
		p.z = z * Math.cos(theta) + y * Math.sin(theta) + origin.z;
	}
	
	/**
	 * @param p
	 * @param CW
	 * @param degrees
	 * @param origin
	 */
	public static void rotateAxisY(MyPoint p, boolean CW, double degrees, MyPoint origin) {
		double x = p.x - origin.x;
		double z = p.z - origin.z;
		double theta = 2*Math.PI/360*degrees*(CW?-1:1);
		p.x = x * Math.cos(theta) - z * Math.sin(theta) + origin.x;
		p.z = z * Math.cos(theta) + x * Math.sin(theta) + origin.z;
	}
	
	/**
	 * @param p
	 * @param CW
	 * @param degrees
	 * @param origin
	 */
	public static void rotateAxisZ(MyPoint p, boolean CW, double degrees, MyPoint origin) {
		double x = p.x - origin.x;
		double y = p.y - origin.y;
		double theta = 2*Math.PI/360*degrees*(CW?-1:1);
		p.x = x * Math.cos(theta) + y * Math.sin(theta) + origin.x;
		p.y = y * Math.cos(theta) - x * Math.sin(theta) + origin.y;
	}
	
	/**
	 * @param p
	 * @param up
	 * @param factors
	 * @param origin
	 */
	public static void scale(MyPoint p, boolean up, MyVector factors, MyPoint origin) {
		p.x = (p.x - origin.x)*(up? factors.x: 1/factors.x) + origin.x;
		p.y = (p.y - origin.y)*(up? factors.y: 1/factors.y) + origin.y;
		p.z = (p.z - origin.z)*(up? factors.z: 1/factors.z) + origin.z;
	}
}