package renderer.point;

public class MyVector {
	public double x, y, z;
	
	public MyVector() {
		this.x = this.y = this.z = 0;
	}
	
	public MyVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public MyVector(MyPoint p1, MyPoint p2) {
		this.x = p2.x - p1.x;
		this.y = p2.y - p1.y;
		this.z = p2.z - p1.z;
	}
	
	public static double dot(MyVector v1, MyVector v2) {
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}
	
	public static MyVector cross(MyVector v1, MyVector v2) {
		return new MyVector(v1.y*v2.z - v1.z*v2.y, v1.z*v2.x - v1.x*v2.z, v1.x*v2.y - v1.y*v2.x);
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}

	public static MyVector makeUnitVector(MyVector v) {
		double mag = v.magnitude();
		return new MyVector(v.x/mag, v.y/mag, v.z/mag);
	}
	
	public void rotateX(double degrees, boolean CW) {
		double radius = Math.sqrt(this.y*this.y + this.z*this.z);
		double theta = Math.atan2(this.z, this.y); 
		theta += 2*Math.PI/360*degrees*(CW?-1:1);
		this.y = radius * Math.cos(theta);
		this.z = radius * Math.sin(theta);
	}
	
	public void rotateY(double degrees, boolean CW) {
		double radius = Math.sqrt(this.x*this.x + this.z*this.z);
		double theta = Math.atan2(this.z, this.x); 
		theta += 2*Math.PI/360*degrees*(CW?-1:1);
		this.x = radius * Math.cos(theta);
		this.z = radius * Math.sin(theta);
	}
	
	public void rotateZ(double degrees, boolean CW) {
		double radius = Math.sqrt(this.x*this.x + this.y*this.y);
		double theta = Math.atan2(this.x, this.y); 
		theta += 2*Math.PI/360*degrees*(CW?-1:1);
		this.y = radius * Math.cos(theta);
		this.x = radius * Math.sin(theta);
	}

	public MyVector scale(double s) {
		return new MyVector(this.x * s, this.y * s, this.z * s);
	}

	public MyVector add(MyVector v2) {
		return new MyVector(this.x + v2.x, this.y + v2.y, this.z + v2.z);
	}

	public static MyVector[] initArray(MyVector[] myVectors) {
		for(int i = 0; i < myVectors.length; i++) {
			myVectors[i] = new MyVector();
		}
		return myVectors;
	}
	
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}
}
