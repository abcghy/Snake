import java.awt.Color;
import java.awt.Graphics;


public class Snake {
	private Scene scene;
	
	private Direction direction;
	private Node head = null;
	
	private Node lastNode = null;
	
	public Snake(Scene scene) {
		direction = Direction.UP;
		head = new Node(new Point(scene.WIDTH / 2, scene.HEIGHT / 2));
		this.scene = scene;
		lastNode = new Node(new Point(scene.WIDTH / 2, scene.HEIGHT / 2 + 1));
	}
	
	private void DeleteTail() {
		Node node = head;
		while (node.getNext().getNext() != null) {
			node = node.getNext();
		}
		lastNode = node.getNext();
		node.setNext(null);
	}
	
	public void moveForward() {
		int headX = head.getCoordinate().getX();
		int headY = head.getCoordinate().getY();
		switch (direction) {
		case UP:
			if (headY == 0) {
				scene.stop();
			} else {
				Node node = new Node(new Point(headX, headY - 1), head);
				head = node;
				DeleteTail();
			}
			break;
		case DOWN:
			if (headY == scene.HEIGHT - 1) {
				scene.stop();
			} else {
				Node node = new Node(new Point(headX, headY + 1), head);
				head = node;
				DeleteTail();
			}
			break;
		case LEFT:
			if (headX == 0) {
				scene.stop();
			} else {
				Node node = new Node(new Point(headX - 1, headY), head);
				head = node;
				DeleteTail();
			}
			break;
		case RIGHT:
			if (headX == scene.WIDTH - 1) {
				scene.stop();
			} else {
				Node node = new Node(new Point(headX + 1, headY), head);
				head = node;
				DeleteTail();
			}
			break;
		default:
			break;
		}
		if (isEatItSelf()) {
			scene.stop();
		}
	}
	
	public void changeDirection(Direction direction) {
		switch (direction) {
		case UP:
			if (this.direction != Direction.DOWN) {
				this.direction = direction;
			}
			break;
		case DOWN:
			if (this.direction != Direction.UP) {
				this.direction = direction;
			}
			break;
		case LEFT:
			if (this.direction != Direction.RIGHT) {
				this.direction = direction;
			}
			break;
		case RIGHT:
			if (this.direction != Direction.LEFT) {
				this.direction = direction;
			}
			break;
		default:
			break;
		}
	}
	
	public void eatApple(Apple apple) {
		Node theLast = new Node(new Point(lastNode.getCoordinate().getX(),
				lastNode.getCoordinate().getY()));
		Node p = head;
		while (p.getNext() != null) {
			p = p.getNext();
		}
		
		p.setNext(theLast);
	}
	
	public boolean isEatItSelf() {
		Node node = head.getNext();
		while (node != null) {
			if (node.getCoordinate().getX() == head.getCoordinate().getX() && 
					node.getCoordinate().getY() == head.getCoordinate().getY()) {
				return true;
			}
			node = node.getNext();
		}
		return false;
	}
	
	public void draw(Graphics g) {
		Color oColor = g.getColor();
		Node node = head;
		// 先画蛇头
		g.setColor(new Color(0, 139, 0));
		g.fillRect(node.getCoordinate().getX() * scene.SIZE, node.getCoordinate().getY() * scene.SIZE, scene.SIZE, scene.SIZE);
		
		// 再画蛇身
		node = node.getNext();
		g.setColor(Color.GREEN);
		while (node != null) {
			g.fillRect(node.getCoordinate().getX() * scene.SIZE, node.getCoordinate().getY() * scene.SIZE, scene.SIZE, scene.SIZE);
			node = node.getNext();
		}
		g.setColor(oColor);
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Node getHead() {
		return head;
	}

	public void setHead(Node head) {
		this.head = head;
	}
}
