package common;

import java.awt.Point;

public class Position {
	public static final int FORWARD  = 1;
	public static final int BACKWARD = -1;
	public static final int LEFT     = -5;
	public static final int RIGHT    = 5;

	private double x;
	private double y;
	private int    heading;

	public Position(int x, int y, int heading) {
		this.x = x;
		this.y = y;
		setHeading(heading);
	}

	public Position(Position p) {
		clone(p);
	}

	public void clone(Position p) {
		this.x = p.x;
		this.y = p.y;
		setHeading(p.heading);
	}
	
	public int x() {
		return (int)Math.round(this.x);
	}
	
	public int y() {
		return (int)Math.round(this.y);
	}

	public int heading() {
		return this.heading;
	}
	
	public void setHeading(int heading) {
		this.heading = heading % 360;
	}
	
	public void turn(int direction) {
		this.heading = (this.heading + direction + 360) % 360;
	}
	
	public void move(int direction) {
		double headingRadians = Math.toRadians(this.heading);
		this.x = this.x + direction * Math.sin(headingRadians);
		this.y = this.y + direction * Math.cos(headingRadians);
	}
	
//	public double calcDistance(Position p) {
//		return (Math.sqrt(Math.pow(x() - p.x(), 2) + Math.pow(y() - p.y(), 2)));
//	}

	public Point toPoint() {
		return new Point(x(), y());
	}
	
	public String toString() {
		return "(" + this.x() + "," + this.y() + ") heading " + this.heading;
	}
}
