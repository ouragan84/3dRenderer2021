package renderer.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean left, right, up, down, forward, backward, sprint;
	
	public boolean getDown() {
		return this.down;
	}
	
	public boolean getUp() {
		return this.up;
	}
	
	public boolean getRight() {
		return this.right;
	}
	
	public boolean getLeft() {
		return this.left;
	}
	
	public boolean getForward() {
		return this.forward;
	}
	
	public boolean getBackward() {
		return this.backward;
	}
	
	public boolean getSprint() {
		return this.sprint;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A: this.left = true; break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D: this.right = true; break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W: this.forward = true; break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S: this.backward = true; break;
			case KeyEvent.VK_SPACE: this.up = true; break;
			case KeyEvent.VK_CONTROL: this.down = true; break;
			case KeyEvent.VK_SHIFT: this.sprint = true; break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A: this.left = false; break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D: this.right = false; break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W: this.forward = false; break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S: this.backward = false; break;
			case KeyEvent.VK_SPACE: this.up = false; break;
			case KeyEvent.VK_CONTROL: this.down = false; break;
			case KeyEvent.VK_SHIFT: this.sprint = false; break;
		}
	}

}
