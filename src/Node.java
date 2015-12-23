
public class Node {
	private Point coordinate = null;
	private Node next = null;
	
	public Node(Point coordinate) {
		this.coordinate = coordinate;
	}
	public Node(Point coordinate, Node next) {
		this.coordinate = coordinate;
		this.next = next;
	}
	public Point getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Point coordinate) {
		this.coordinate = coordinate;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
}
