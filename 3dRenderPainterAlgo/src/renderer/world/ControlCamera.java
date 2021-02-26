package renderer.world;

import renderer.input.ClickType;
import renderer.input.Keyboard;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.point.MyVector;

public class ControlCamera extends Camera {

	private MyVector speed;
	private MyVector sprintSpeed;
	private EntityManager manager;
	private static final double MOUSE_SENSITIVITY_PITCH = .2;
	private static final double MOUSE_SENSITIVITY_YAW = .2;
	
	/**
	 * @param position
	 * @param rotation
	 * @param scale
	 * @param speed <forwar/backward, right/left, up/down>
	 * @param keyboard 
	 */
	public ControlCamera(MyPoint position, MyVector rotation, MyVector scale, MyVector speed, MyVector sprintSpeed, int fps, EntityManager manager) {
		super(position, rotation, scale);
		this.speed = new MyVector(speed.x/fps, speed.y/fps, speed.z/fps);
		this.sprintSpeed = new MyVector(sprintSpeed.x/fps, sprintSpeed.y/fps, sprintSpeed.z/fps);
		this.manager = manager;
	}
	
	private int initialX, initialY;
	
	public void update() {
		Keyboard keyboard = manager.getKeyboard();
		Mouse mouse = manager.getMouse();
		
		int mx = mouse.getX();
		int my = mouse.getY();
		if(mouse.getB() == ClickType.LeftClick) {
			int xDif = mx - initialX;
			int yDif = my - initialY;
			
			this.rotateY(-MOUSE_SENSITIVITY_PITCH*yDif);
			this.rotateZ(-MOUSE_SENSITIVITY_YAW*xDif);
			
			if(xDif != 0 && yDif != 0) {
				manager.sortPolygons();
			}
		}
		
		double xTrans = 0, yTrans = 0, zTrans = 0;
		if(keyboard.getLeft()) {
			yTrans -= 1;
		}
		if(keyboard.getRight()) {
			yTrans += 1;
		}
		if(keyboard.getUp()) {
			zTrans += 1;
		}
		if(keyboard.getDown()) {
			zTrans -= 1;
		}
		if(keyboard.getForward() || mouse.isScrollingUP()) {
			xTrans -= 1;
		}
		if(keyboard.getBackward() || mouse.isScrollingDown()) {
			xTrans += 1;
		}
		if(xTrans != 0 || yTrans != 0 || zTrans != 0) {
			
			double x, y, z;
			
			if(keyboard.getSprint()) {
				x = xTrans*sprintSpeed.x;
				y = yTrans*sprintSpeed.y;
				z = zTrans*sprintSpeed.z;
			}else {
				x = xTrans*speed.x;
				y = yTrans*speed.y;
				z = zTrans*speed.z;
			}
			
			
			double theta = Math.PI/180 * this.rotation.z;
			xTrans = x * Math.cos(theta) - y * Math.sin(theta);
			yTrans = y * Math.cos(theta) + x * Math.sin(theta);
			
			this.translate(xTrans, yTrans, z);
			manager.sortPolygons();
		}
		
		mouse.resetScroll();
		
		initialX = mx;
		initialY = my;
	}

}
