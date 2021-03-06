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

public class BasicEntityBuilder {
	
	/**
	 * @param position
	 * @param origin
	 * @param scale
	 * @param rotation
	 * @param size
	 * @return
	 */
	public static IEntity createCube(MyPoint position, MyPoint origin, MyVector scale, MyVector rotation, double size) {
		
		/*
		 *    2———4
		 *  6———8 |
		 *  | 1—|—3   
		 *  5———7
		 *   
		 *      [2]
		 *   [5][1][3][4]
		 *      [6]
		 */
		
		MyPoint p1 = new MyPoint(position.x - size/2, position.y - size/2, position.z - size/2);
		MyPoint p2 = new MyPoint(position.x - size/2, position.y - size/2, position.z + size/2);
		MyPoint p3 = new MyPoint(position.x - size/2, position.y + size/2, position.z - size/2);
		MyPoint p4 = new MyPoint(position.x - size/2, position.y + size/2, position.z + size/2);
		MyPoint p5 = new MyPoint(position.x + size/2, position.y - size/2, position.z - size/2);
		MyPoint p6 = new MyPoint(position.x + size/2, position.y - size/2, position.z + size/2);
		MyPoint p7 = new MyPoint(position.x + size/2, position.y + size/2, position.z - size/2);
		MyPoint p8 = new MyPoint(position.x + size/2, position.y + size/2, position.z + size/2);
		
		MyPolygon poly1 = new MyPolygon(Color.RED, p5, p6, p8, p7);
		MyPolygon poly2 = new MyPolygon(Color.WHITE, p6, p2, p4, p8);
		MyPolygon poly3 = new MyPolygon(Color.BLUE, p7, p8, p4, p3);
		MyPolygon poly4 = new MyPolygon(new Color(255, 128, 0), p1, p3, p4, p2);
		MyPolygon poly5 = new MyPolygon(new Color(255, 255, 0), p5, p7, p3, p1);
		MyPolygon poly6 = new MyPolygon(new Color(30, 166, 66), p1, p2, p6, p5);
		
		Polyhedron tetra = new Polyhedron(origin, scale, rotation, poly1, poly2, poly3, poly4, poly5, poly6);
		List<Polyhedron> tetras = new ArrayList<Polyhedron>();
		tetras.add(tetra);
		
		return new Entity(tetras);
	}
	
	/**
	 * @param position
	 * @param origin
	 * @param scale
	 * @param rotation
	 * @param color
	 * @param size
	 * @param edges
	 * @param inFactor
	 * @param verticalInFactor
	 * @param verticalScale
	 * @return
	 */
	public static IEntity createDiamond(MyPoint position, MyPoint origin, MyVector scale, MyVector rotation, Color color, double size, int edges, double inFactor, double verticalInFactor, double verticalScale) {
		
		List<Polyhedron> tetras = new ArrayList<Polyhedron>();
		
		MyPoint bottom = new MyPoint(position.x, position.y, position.z-size/2*verticalScale);
		MyPoint[] outerPoints = new MyPoint[edges];
		MyPoint[] innerPoints = new MyPoint[edges];
		for(int i = 0; i < edges; i++) {
			
			double theta = 2 * Math.PI / edges * i;
			
			double tempxin = -Math.sin(theta+(Math.PI/edges))*size/2 ;
			double tempyin = Math.cos(theta+(Math.PI/edges))*size/2;
			
			double tempxout = -Math.sin(theta)*size/2 ;
			double tempyout = Math.cos(theta)*size/2;
			
			double tempz =  size/2*verticalScale;
			outerPoints[i] = new MyPoint(tempxout + position.x, tempyout + position.y, tempz*inFactor + position.z);
			innerPoints[i] = new MyPoint(tempxin*inFactor + position.x, tempyin*inFactor + position.y, tempz/verticalInFactor + position.z);
		}
		
		MyPolygon polygons[] = new MyPolygon[edges*3 + 1];
		
		for(int i = 0; i < edges; i++) {
			int next = (i+1 >= edges? 0 : i+1);
			polygons[i] = new MyPolygon(outerPoints[i], outerPoints[next], bottom);
		}
		
		for(int i = 0; i < edges; i++) {
			int next = (i+1) % edges;
			polygons[edges+i*2] = new MyPolygon(outerPoints[i], innerPoints[i], outerPoints[next]);
			polygons[edges+i*2+1] = new MyPolygon(innerPoints[next], outerPoints[next], innerPoints[i]);
		}
		
		MyPoint[] innerInverse = new MyPoint[edges];
		for(int i = 0; i < edges; i++) {
			innerInverse[i] = innerPoints[edges-(i+1)];
		}
		
		polygons[polygons.length-1] = new MyPolygon(innerInverse);
		
		Polyhedron tetra = new Polyhedron(origin, scale, rotation, color, false, polygons);
		tetras.add(tetra);
		
		return new Entity(tetras);
	}
	
	/**
	 * @param position
	 * @param scale
	 * @param rotation
	 * @param color
	 * @param size
	 * @param edges
	 * @param inFactor
	 * @param verticalInFactor
	 * @param verticalScale
	 * @return
	 */
	public static IEntity createDiamond(MyPoint position, MyVector scale, MyVector rotation,  Color color, double size, int edges, double inFactor, double verticalInFactor, double verticalScale) {
		return createDiamond(position, position, scale, rotation, color, size, edges, inFactor, verticalInFactor, verticalScale);
	}
	
	/**
	 * @param position
	 * @param origin
	 * @param scale
	 * @param rotation
	 * @param color
	 * @param radius
	 * @param resolution
	 * @return
	 */
	public static IEntity createSphereType1(MyPoint position, MyPoint origin, MyVector scale, MyVector rotation, Color color, double radius, int resolution) {
		
		List<Polyhedron> tetras = new ArrayList<Polyhedron>();
		List<MyPolygon> polygons = new ArrayList<MyPolygon>();
		
		MyPoint bottom = new MyPoint(position.x, position.y, position.z-radius);
		MyPoint top = new MyPoint(position.x, position.y, position.z+radius);
		
		MyPoint[][] points = new MyPoint[resolution-1][resolution];
		
		for(int i = 1; i < resolution; i++) {
			double theta = Math.PI / resolution *i;
			double z = -Math.cos(theta) * radius;
			double currentRadius = Math.abs(Math.sin(theta)*radius);
			for(int j = 0; j < resolution; j++) {
				double alpha = 2*Math.PI / resolution*j;
				double x = -Math.sin(alpha) * currentRadius;
				double y = Math.cos(alpha) * currentRadius;
				points[i-1][j] = new MyPoint(x + position.x, y + position.y, z + position.z);
			}
		}
		
		for(int i = 1; i <= resolution; i++) {
			for(int j = 0; j < resolution; j++) {
				if(i == 1) {
					polygons.add(new MyPolygon(points[i-1][j], points[i-1][(j+1)%resolution], bottom));
				}
				else if(i == resolution) {
					polygons.add(new MyPolygon(points[i-2][(j+1)%resolution], points[i-2][j], top));
				}
				else {
					polygons.add(new MyPolygon(points[i-1][j], points[i-1][(j+1)%resolution], points[i-2][(j+1)%resolution], points[i-2][j]));
				}
			}
		}
		
		MyPolygon[] polygonArray = new MyPolygon[polygons.size()];
		polygonArray = polygons.toArray(polygonArray);
		
		Polyhedron tetra = new Polyhedron(origin, scale, rotation, color, false, polygonArray);
		tetras.add(tetra);
		
		return new Entity(tetras);
		
	}
}
