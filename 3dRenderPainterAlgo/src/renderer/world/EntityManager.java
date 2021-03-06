package renderer.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.IEntity;
import renderer.entity.builder.*;
import renderer.input.Keyboard;
import renderer.input.Mouse;
import renderer.input.UserInput;
import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;

public class EntityManager {
	
	private List<IEntity> entities;
	private MyPolygon[] polygons;
	List<MyPolygon> visiblePolys;
	
	private MyVector lightVector = new MyVector(0, 1, -1);
	
	private Mouse mouse;
	private Keyboard keyboard;
	private Camera camera;
	
	public EntityManager() {
		this.entities = new ArrayList<IEntity>();
	}
	
	public void init(UserInput userInput, int fps, int width, int height) {
		this.mouse = userInput.mouse;
		this.keyboard = userInput.keyboard;
		
		this.camera = new ControlCamera(new MyPoint(500, 0, 0), new MyVector(0, 0, 0), new MyVector(5, 5, 5), new MyVector(600, 600, 600), new MyVector(1000, 600, 600), fps, this, width, height);
		PointConverter.camera = this.camera;
		
		this.entities.add(BasicEntityBuilder.createCube(new MyPoint(0, 150, 0), new MyPoint(0, 150, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), 75));
		this.entities.add(BasicEntityBuilder.createDiamond(new MyPoint(0, -150, -15), new MyPoint(0, -150, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.CYAN, 100, 6, .8, .65, .5));
		this.entities.add(BasicEntityBuilder.createSphereType1(new MyPoint(0, 0, 0), new MyPoint(0, 0, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.RED, 50, 8));
		
		this.entities.add(ComplexEntityBuilder.createRubixCube(new MyPoint(1000, 0, 0), new MyPoint(2000, 0, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), 100, 10));
		
		this.visiblePolys = new ArrayList<MyPolygon>();
		
		System.out.println("Manager " + this.entities.size());
		for(IEntity e : entities) {
			visiblePolys.addAll(e.getPolygons());
		}
		
		this.polygons = new MyPolygon[visiblePolys.size()];
		this.polygons = visiblePolys.toArray(this.polygons);
		
		MyPoint test = new MyPoint(-500, 200, -300);
		System.out.println("(-500, 200, -300) goes to " + PointConverter.viewToScreen(test));
		
		this.setLighting();
		this.camera.updateBuffer();
//		this.sortPolygons();
	}
	
	public void update() {
		this.camera.update();
	}
	
	public void updatePolys(Camera cam) {
		for(MyPolygon p : visiblePolys) {
			p.updateBuffer(cam);
		}
	}
	
	public void setLighting() {
		for(MyPolygon p : this.visiblePolys) {
			p.updateLightingRatio(this.lightVector);
		}
	}
	
	public Mouse getMouse() {
		return this.mouse;
	}
	
	public Keyboard getKeyboard() {
		return this.keyboard;
	}

	public Camera getCamera() {
		return this.camera;
	}
}
