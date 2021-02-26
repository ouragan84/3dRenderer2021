package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.Entity;
import renderer.entity.IEntity;
import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.shapes.MyPolygon;
import renderer.shapes.Polyhedron;

public class ComplexEntityBuilder {
	/**
	 * @param position
	 * @param origin
	 * @param scale
	 * @param rotation
	 * @param size
	 * @param cubeSpacing
	 * @return
	 */
	public static IEntity createRubixCube(MyPoint position, MyPoint origin, MyVector scale, MyVector rotation, double size, double cubeSpacing) {
		List<Polyhedron> tetras = new ArrayList<Polyhedron>();
		
		for(int i = -1; i <= 1; i++) {
			double cubeCenterX = i * (size + cubeSpacing) + position.x;
			for(int j = -1; j <= 1; j++) {
				double cubeCenterY = j * (size + cubeSpacing) + position.y;
				for(int k = -1; k <= 1; k++) {
					double cubeCenterZ = k * (size + cubeSpacing) + position.z;
					 
					/*
					 *    2———4
					 *  6———8 |
					 *  | 1—|—3   
					 *  5———7
					 *   
					 */
					
					MyPoint p1 = new MyPoint(cubeCenterX - size/2, cubeCenterY - size/2, cubeCenterZ - size/2);
					MyPoint p2 = new MyPoint(cubeCenterX - size/2, cubeCenterY - size/2, cubeCenterZ + size/2);
					MyPoint p3 = new MyPoint(cubeCenterX - size/2, cubeCenterY + size/2, cubeCenterZ - size/2);
					MyPoint p4 = new MyPoint(cubeCenterX - size/2, cubeCenterY + size/2, cubeCenterZ + size/2);
					MyPoint p5 = new MyPoint(cubeCenterX + size/2, cubeCenterY - size/2, cubeCenterZ - size/2);
					MyPoint p6 = new MyPoint(cubeCenterX + size/2, cubeCenterY - size/2, cubeCenterZ + size/2);
					MyPoint p7 = new MyPoint(cubeCenterX + size/2, cubeCenterY + size/2, cubeCenterZ - size/2);
					MyPoint p8 = new MyPoint(cubeCenterX + size/2, cubeCenterY + size/2, cubeCenterZ + size/2);
					
					MyPolygon polyRed = new MyPolygon(Color.RED, p5, p6, p8, p7);
					MyPolygon polyWhite = new MyPolygon(Color.WHITE, p6, p2, p4, p8);
					MyPolygon polyBlue = new MyPolygon(Color.BLUE, p7, p8, p4, p3);
					MyPolygon polyOrange = new MyPolygon(new Color(255, 128, 0), p1, p3, p4, p2);
					MyPolygon polyYellow = new MyPolygon(new Color(255, 255, 0), p5, p7, p3, p1);
					MyPolygon polyGreen = new MyPolygon(new Color(30, 166, 66), p1, p2, p6, p5);
					
					Polyhedron tetra = new Polyhedron(origin, scale, rotation, polyRed, polyWhite, polyBlue, polyOrange, polyYellow, polyGreen);
					
					tetras.add(tetra);
				}
			}
		}
		
		return new Entity(tetras);
	}
}
