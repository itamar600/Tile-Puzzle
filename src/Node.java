
public class Node {
	enum Color{
		GREEN,
		RED,
		BlACK
	}
	private int num;
	private Color color;
	public Node(int num, Color color) {
		this.num=num;
		this.color=color;
	}
	public int getNum() {
		return num;
	}
	
	public Color getColor() {
		return color;
	}
}
