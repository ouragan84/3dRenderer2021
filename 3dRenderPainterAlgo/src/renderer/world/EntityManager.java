package renderer.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import renderer.entity.IEntity;
import renderer.entity.SphereGravity;
import renderer.entity.builder.*;
import renderer.input.Keyboard;
import renderer.input.Mouse;
import renderer.input.UserInput;
import renderer.point.MyVector;
import renderer.shapes.LineSegment;
import renderer.shapes.WorldShape;

public class EntityManager {
	
	private List<IEntity> entities;
	private WorldShape[] polygons;
	List<WorldShape> visiblePolys;
	private int FPS;
	private WorldSpace world;
	private Mouse mouse;
	private Keyboard keyboard;
	
	private MyVector lightVector = new MyVector(0, 1, -1);
	
	public void init(UserInput userInput, int fps) {
		this.FPS = fps;
		this.mouse = userInput.mouse;
		this.keyboard = userInput.keyboard;
		
		this.world = new WorldSpace(this, FPS);
		
		this.entities = world.Init();
		
		this.visiblePolys = new ArrayList<WorldShape>();
		
//		System.out.println("Manager " + this.entities.size());
		for(IEntity e : entities) {
			visiblePolys.addAll(e.getPolygons());
		}
		
		this.polygons = new WorldShape[visiblePolys.size()];
		this.polygons = visiblePolys.toArray(this.polygons);
		
		this.setLighting();
		//this.sortPolygons();
	}
	
	public void update() {
		world.Update();
	}

	public void render(Graphics g) {
		this.sortPolygons();
		for(WorldShape p : visiblePolys) {
			p.render(g);
		}
	}
	
	@SuppressWarnings("unused")
	private void rotateEntities(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(IEntity entity : this.entities) {
			entity.rotate(CW, xDegrees, yDegrees, zDegrees);
		}
		
		//this.sortPolygons();
		this.setLighting();
	}
	
	public void sortPolygons() {
		this.visiblePolys.clear();
		for(WorldShape p : polygons) {
			if(p.updateVisibility(world.getWorldCamera()))
				this.visiblePolys.add(p);
		}
		for(WorldShape[] ps : world.traces) {
			for(WorldShape p : ps) {
				if(p.updateVisibility(world.getWorldCamera()))
					this.visiblePolys.add(p);
			}
		}
		this.visiblePolys = WorldShape.sortShapes(this.visiblePolys, world.getWorldCamera());
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

	public void addEphemereWorldShape(LinkedList<LineSegment[]> traces) {
		
	}
}
