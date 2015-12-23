import java.util.Random;


public class Apple {
	private static Random random = new Random();
	private Scene scene = null;
	private Snake snake = null;
	private Point coordinate = null;

	public Apple(Point coordinate, Snake snake, Scene scene) {
		this.coordinate = coordinate;
		this.snake = snake;
		this.scene = scene;
	}
	
	public Point getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}
	
	public void changeLocation() {
		setCoordinate(new Point(random.nextInt(scene.WIDTH), random.nextInt(scene.HEIGHT)));
		if (isOnSnake()) {
			changeLocation();
			System.out.println("on snake");
		}
	}
	
	public boolean isOnSnake() {
		Node node = snake.getHead();
		while (node != null) {
			if (node.getCoordinate().getX() == getCoordinate().getX() && 
					node.getCoordinate().getY() == getCoordinate().getY()) {
				return true;
			}
			node = node.getNext();
		}
		return false;
	}
}
