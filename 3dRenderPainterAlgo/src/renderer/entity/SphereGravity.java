package renderer.entity;

import java.awt.Color;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.point.MyPoint;
import renderer.point.MyVector;

public class SphereGravity {
	
	public double Mass;
	public MyVector velocity;
	public MyPoint position;
	public Color color;
	public int entityIndex;
	
	private IEntity Sphere;
	private boolean isFixed;
	
	private static final double density = .2;
	private static final long gravConstInv = 1000l;//14983518130l;
	
	public SphereGravity(double mass, MyVector initV, MyPoint initP, Color color, boolean isFixed, int index) {
		this.Mass = mass;
		this.position = initP;
		this.color = color;
		this.entityIndex = index;
		this.isFixed = isFixed;
		
		if(isFixed) {
			this.velocity = new MyVector(0, 0, 0);
		}else {
			this.velocity = initV;
		}
		
		this.Sphere = BasicEntityBuilder.createSphereType1(this.position, this.position, new MyVector(1, 1, 1), new MyVector(0, 0, 0), this.color, Math.pow(3*Mass/(4*Math.PI*density), 1.0/3), 8);
	}
	
	public IEntity getSphere() {
		return this.Sphere;
	}
	
	public void accelerate(SphereGravity[] s) {
		if(isFixed) return;
		
		double sumx = 0;
		double sumy = 0;
		double sumz = 0;
		
		for(int i = 0; i < s.length; i++) {
			if(i != this.entityIndex) {
				double dist = MyPoint.dist(this.position, s[i].position);
				double acc = s[i].Mass / dist;
				
				MyVector v = MyVector.abs(new MyVector( s[i].position.x - this.position.x, s[i].position.y - this.position.y, s[i].position.z - this.position.z ));
				
				sumx += v.x*acc;
				sumy += v.y*acc;
				sumz += v.z*acc;
				
			}
		}
		
		this.velocity.x += sumx/gravConstInv;
		this.velocity.y += sumy/gravConstInv;
		this.velocity.z += sumz/gravConstInv;
	}
	
	public void updatePos(int fps) {
		if(isFixed) return;
		
		this.position.x += this.velocity.x/fps;
		this.position.y += this.velocity.y/fps;
		this.position.z += this.velocity.z/fps;
		
		this.Sphere.setPos(position.x, position.y, position.z);
	}

}