package renderer.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.IEntity;
import renderer.entity.SphereGravity;
import renderer.entity.builder.*;
import renderer.input.Keyboard;
import renderer.input.Mouse;
import renderer.input.UserInput;
import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.WorldShape;

public class EntityManager {
	
	private List<IEntity> entities;
	private WorldShape[] polygons;
	List<WorldShape> visiblePolys;
	private int FPS;
	
	private SphereGravity[] planets;
	
	private MyVector lightVector = new MyVector(0, 1, -1);
	
	private Mouse mouse;
	private Keyboard keyboard;
	private Camera camera;
	
	public EntityManager() {
		this.entities = new ArrayList<IEntity>();
	}
	
	/**
	 * @param userInput
	 */
	public void init(UserInput userInput, int fps) {
		this.FPS = fps;
		
		this.mouse = userInput.mouse;
		this.keyboard = userInput.keyboard;
		
		this.camera = new ControlCamera(new MyPoint(1000, -200, 300), new MyVector(0, -20, -45), new MyVector(5, 5, 5), new MyVector(600, 600, 600), new MyVector(1000, 600, 600), fps, this);
		PointConverter.camera = this.camera;
		
//		this.entities.add(BasicEntityBuilder.createCube(new MyPoint(0, 150, 0), new MyPoint(0, 150, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), 75));
//		this.entities.add(BasicEntityBuilder.createDiamond(new MyPoint(0, -150, -15), new MyPoint(0, -150, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.CYAN, 100, 6, .8, .65, .5));
//		this.entities.add(BasicEntityBuilder.createSphereType1(new MyPoint(0, 0, 0), new MyPoint(0, 0, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.BLUE, 40, 8));
//		this.entities.add(BasicEntityBuilder.createSphereType1(new MyPoint(500, 300, 0), new MyPoint(500, 300, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.RED, 50, 8));
//		this.entities.add(BasicEntityBuilder.createSphereType1(new MyPoint(-200, 200, 200), new MyPoint(-200, 200, 200), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.GREEN, 35, 8));
		
//		this.entities.add(ComplexEntityBuilder.createRubixCube(new MyPoint(1000, 0, 0), new MyPoint(2000, 0, 0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), 100, 10));
		
		for(int i = 0; i <= 10; i++) {
			this.entities.add(BasicEntityBuilder.createLine(new MyPoint(i*100,0,0), new MyPoint(i*100,1000,0), 20, new MyPoint(0,0,0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.WHITE, true));
			this.entities.add(BasicEntityBuilder.createLine(new MyPoint(0,i*100,0), new MyPoint(1000,i*100,0), 20, new MyPoint(0,0,0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.WHITE, true));
		}
//		
//		this.entities.add(BasicEntityBuilder.createLine(new MyPoint(0,0,0), new MyPoint(1000,0,0), 20, new MyPoint(0,0,0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.WHITE, true));
//		this.entities.add(BasicEntityBuilder.createLine(new MyPoint(0,0,0), new MyPoint(0,1000,0), 20, new MyPoint(0,0,0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.WHITE, true));
		this.entities.add(BasicEntityBuilder.createLine(new MyPoint(0,0,0), new MyPoint(0,0,1000), 20, new MyPoint(0,0,0), new MyVector(1, 1, 1), new MyVector(0, 0, 0), Color.WHITE, true));
		
		
		planets = new SphereGravity[]{
				new SphereGravity(200000, new MyVector(-50, 50, 0), new MyPoint(700, 700, 200), Color.BLUE, false, 0),
				new SphereGravity(200000, new MyVector(50, -50, 0), new MyPoint(300, 300, 200), Color.RED, false, 1),
				new SphereGravity(200000, new MyVector(0, 0, 50), new MyPoint(500, 500, 100), Color.GREEN, false, 2)};
		
		for(SphereGravity p : planets) {
			this.entities.add(p.getSphere());
		}
		
		
		
		
		this.visiblePolys = new ArrayList<WorldShape>();
		
		System.out.println("Manager " + this.entities.size());
		for(IEntity e : entities) {
			visiblePolys.addAll(e.getPolygons());
		}
		
		this.polygons = new WorldShape[visiblePolys.size()];
		this.polygons = visiblePolys.toArray(this.polygons);
		
		this.setLighting();
		this.sortPolygons();
		
		//entities.get(0).setPos(200,100, 500);
	}

	
//	private int initialX, initialY;
	
	private double time = 0;
	
	public void update() {
		
		time += 1.0/FPS;
		
		for(SphereGravity p : planets) {
			p.accelerate(planets);
		}
		for(SphereGravity p : planets) {
			p.updatePos(FPS);
		}
		
		this.camera.update();
	}

	public void render(Graphics g) {
		for(WorldShape p : visiblePolys) {
			p.render(g);
		}
	}
	
	@SuppressWarnings("unused")
	private void rotateEntities(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(IEntity entity : this.entities) {
			entity.rotate(CW, xDegrees, yDegrees, zDegrees);
		}
		
		this.sortPolygons();
		this.setLighting();
	}
	
	public void sortPolygons() {
		this.visiblePolys.clear();
		for(WorldShape p : polygons) {
			if(p.updateVisibility(camera)) this.visiblePolys.add(p);
		}
		this.visiblePolys = WorldShape.sortShapes(this.visiblePolys, this.camera);
	}
	
	public void setLighting() {
		for(WorldShape p : this.visiblePolys) {
			p.updateLightingRatio(this.lightVector);
		}
	}
	
	public Mouse getMouse() {
		return this.mouse;
	}
	
	public Keyboard getKeyboard() {
		return this.keyboard;
	}
}
