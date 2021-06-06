package renderer.point;

public class MyPoint {
	
	public double x, y, z;
	
	public MyPoint() {
	}
	
	public MyPoint(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static double dist(MyPoint p1, MyPoint p2) {
		// TODO Auto-generated method stub
		return Math.sqrt((p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y) + (p2.z-p1.z)*(p2.z-p1.z));
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

	public MyPoint add(MyVector v2) {
		return new MyPoint(this.x + v2.x, this.y + v2.y, this.z + v2.z);
	}

	public static MyPoint[] initArray(MyPoint[] myPoints) {
		for(int i = 0; i < myPoints.length; i++) {
			myPoints[i] = new MyPoint();
		}
		return myPoints;
	}
}
