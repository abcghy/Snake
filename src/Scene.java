import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;


public class Scene extends Frame{
	
	static boolean GAMEOVER = false;
	static boolean PAUSE = true;
	
	PaintThread paintThread = new PaintThread();
	
	private Snake snake = new Snake(this);
	private Apple apple = new Apple(new Point(2, 2), snake, this);	
	
	static final int WIDTH = 35;
	static final int HEIGHT = 35;
	static final int SIZE = 20;
	
	Image offImage = null;
	
	public void launchFrame() {
		setTitle("Ì°³ÔÉß");
		setBounds(200, 200, SIZE * WIDTH, SIZE * HEIGHT);
//		setUndecorated(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setUndecorated(true);
		setVisible(true);
		setResizable(false);
		
		// Ìí¼Ó¼üÅÌ¼àÌý
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_UP:
					if (PAUSE == false) {
						snake.changeDirection(Direction.UP);
					}
					break;
				case KeyEvent.VK_DOWN:
					if (PAUSE == false) {
						snake.changeDirection(Direction.DOWN);
					}
					break;
				case KeyEvent.VK_LEFT:
					if (PAUSE == false) {
						snake.changeDirection(Direction.LEFT);	
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (PAUSE == false) {
						snake.changeDirection(Direction.RIGHT);	
					}
					break;
				case KeyEvent.VK_SPACE:
					if (PAUSE == true) {
						PAUSE = false;
					} else {
						PAUSE = true;
					}
					break;
				default:
					break;
				}
			}
		});
		new Thread(paintThread).start();
	}

	@Override
	public void paint(Graphics g) {
		Color oColor = g.getColor();
		
		// paint the background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH * SIZE, HEIGHT * SIZE);
		
		// paint the layout
		g.setColor(Color.BLACK);
		for (int i = 1; i < WIDTH; i++) {
			g.drawLine(SIZE * i, 0, SIZE * i, SIZE * HEIGHT);
		}
		for (int i = 1; i < HEIGHT; i++) {
			g.drawLine(0, SIZE * i, SIZE * WIDTH, SIZE * i);
		}
		
		// paint the snake
		snake.draw(g);
		
		// paint the apple
		paintApple(g, apple.getCoordinate());
		
		if (PAUSE == true) {
			Image img = new ImageIcon("pic/pause.png").getImage();
			Image spaceImg = new ImageIcon("pic/space.png").getImage();
			g.drawImage(img, (WIDTH * SIZE - img.getWidth(null)) / 2, (HEIGHT * SIZE - img.getHeight(null)) / 2, null);
			g.drawImage(spaceImg, 0, 0, null);
		}
		
		g.setColor(oColor);
	}
	
	@Override
	public void update(Graphics g) {
		if (offImage == null) {
			offImage = createImage(WIDTH * SIZE, HEIGHT * SIZE);
		}
		Graphics offG = offImage.getGraphics();
		paint(offG);
		g.drawImage(offImage, 0, 0, null);
	}

	public void paintApple(Graphics g, Point point) {
		Color oColor = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(SIZE * point.getX(), SIZE * point.getY(), SIZE, SIZE);
		g.setColor(oColor);
	}
	
	public void stop() {
		GAMEOVER = true;
	}
	
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while (!GAMEOVER) {
				repaint();
				if (PAUSE == true) {
					continue;
				}
				snake.moveForward();
				if (snake.getHead().getCoordinate().getX() == apple.getCoordinate().getX()
						&& snake.getHead().getCoordinate().getY() == apple.getCoordinate().getY()) {
					snake.eatApple(apple);
					apple.changeLocation();
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
