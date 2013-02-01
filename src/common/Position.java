package common;

public class Position {
	public static final int FORWARD  = 1;
	public static final int BACKWARD = -1;
	public static final int LEFT     = -45;
	public static final int RIGHT    = 45;

	private Point location;
	private int   heading;

	public Position(int x, int y, int heading) {
		this.location = new Point(x, y);
		this.heading = heading;
	}

	public Position(Position p) {
		this.location = new Point(p.location());
		this.heading  = p.heading();
	}

	public Point location() {
		return this.location;
	}
	
	public int heading() {
		return this.heading;
	}
	
	public void setLocation(Point p) {
		this.location.clone(p);
	}
	
	public void setHeading(int heading) {
		this.heading = heading % 360;
	}
	
	public void turn(int direction) {
		this.heading = (this.heading + direction + 360) % 360;
	}
	
	public void move(int direction) {
		int x_orientation = (this.heading > 0 && this.heading < 180) ? 1 : -1;
		int y_orientation = (this.heading > 45 && this.heading < 270) ? -1 : 1;

		if (this.heading == 0 || this.heading == 180)
			x_orientation = 0;

		if (this.heading == 90 || this.heading == 270)
			y_orientation = 0;

		location.x += x_orientation * direction;
		location.y += y_orientation * direction;
	}
	
	public String toString() {
		return "(" + this.location.x + "," + this.location.y 
				+ ") heading " + this.heading;
	}
}
