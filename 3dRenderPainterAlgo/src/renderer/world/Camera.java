package renderer.world;

import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;

public class Camera {
	
	protected MyPoint position;
	protected MyVector rotation;
	protected MyVector scale;
	//protected double depthOfField = 1400;
	
	protected double FovX = 90.0;
	protected double FovY = 90.0;
	
	protected static final double far = 100000;
	protected static final double near = 10;

	protected static final double MAX_PITCH = 90;
	protected static final double MIN_PITCH = 270;
	
	/**
	 * @param position
	 * @param rotation
	 * @param scale
	 */
	public Camera(MyPoint position, MyVector rotation, MyVector scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	/**
	 * @param scale
	 * @param up
	 */
	public void scale(MyVector scale, boolean up) {
		this.scale.x *= (up ? scale.x : 1/scale.x);
		this.scale.y *= (up ? scale.y : 1/scale.y);
		this.scale.z *= (up ? scale.z : 1/scale.z);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(double x, double y, double z) {
		this.position.x += x;
		this.position.y += y;
		this.position.z += z;
	}
	
	public MyVector getScale() {
		return this.scale;
	}
	
	public MyVector getRotation() {
		return this.rotation;
	}
	
	public void rotateX(double degrees) {
		this.rotation.x = degrees + ((this.rotation.x + 360)%360);
	}
	
	public void rotateY(double degrees) {
		this.rotation.y = degrees + ((this.rotation.y + 360)%360);
		if(this.rotation.y > MAX_PITCH && this.rotation.y < MIN_PITCH) {
			if(this.rotation.y - MAX_PITCH > MIN_PITCH - this.rotation.y) {
				this.rotation.y = MIN_PITCH;
			}else {
				this.rotation.y = MAX_PITCH;
			}
		}
	}
	
	public void rotateZ(double degrees) {
		this.rotation.z = degrees + ((this.rotation.z + 360)%360);
	}
	
	public double getFovX() {
		return this.FovX;
	}
	
	public double getFovY() {
		return this.FovY;
	}

	public MyPoint getPosition() {
		return this.position;
	}

	public double getFar() {
		return far;
	}
	
	public double getNear() {
		return near;
	}
	
	public double forwardDistance(MyPoint p) {
		return this.position.x - PointConverter.worldToView(p).x ;
	}
	
	public void update() {
	}
}
