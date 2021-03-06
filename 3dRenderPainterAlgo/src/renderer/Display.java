package renderer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import renderer.display.ZBuffer;
import renderer.input.UserInput;
import renderer.world.EntityManager;
import renderer.world.Camera;

public class Display extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private static String title = "3D Renderer";
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	private static final int UPDATES_PER_SECONDS = 60;
	private static boolean running = false;
	
	private Camera MainCamera;
	
	private EntityManager entityManager;
	
	private UserInput userInput;
	
	
	public Display() {
		this.frame = new JFrame();
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		this.setPreferredSize(size);
		
		this.userInput = new UserInput();
		
		this.entityManager = new EntityManager();
		
		this.addMouseListener(this.userInput.mouse);
		this.addMouseMotionListener(this.userInput.mouse);
		this.addMouseWheelListener(this.userInput.mouse);
		this.addKeyListener(this.userInput.keyboard);
	}
	
	public static void main(String[] args) {
		Display display = new Display();
		display.frame.setTitle(title);
		display.frame.add(display);
		display.frame.pack();
		display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.frame.setLocationRelativeTo(null);
		display.frame.setResizable(false);
		display.frame.setVisible(true);
		
		display.start();
	}
	
	public synchronized void start() {
		running = true;
		this.thread = new Thread(this, "Display");
		this.thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			this.thread.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / UPDATES_PER_SECONDS;
		double delta = 0;
		int frames = 0;
		
		entityManager.init(userInput, UPDATES_PER_SECONDS, WIDTH, HEIGHT);
		this.MainCamera = entityManager.getCamera();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
				update();
				delta--;
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				this.frame.setTitle(title + " | " + frames + " fps");
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		MainCamera.getZBuffer().drawBuffer(g);
		
//		MainCamera.getZBuffer().clearBuffer();
//		int[] x = {200, 400, 200};
//		int[] y = {200, 200, 400};
//		MainCamera.getZBuffer().pushFilledTriangle(new Polygon(x, y, 3), (float) 50.0, Color.RED);
		
		g.dispose();
		bs.show();
	}
	
	private void update() {
		this.entityManager.update();
	}
}
