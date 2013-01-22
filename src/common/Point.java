package common;

public class Point {
	public int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p) {
		this(p.x, p.y);
	}
	
	public void clone(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public double calcDistance(Point p) {
		return (Math.sqrt(Math.pow(this.x - p.x, 2) +
				  Math.pow(this.y - p.y, 2)));
	}

//	@Override
//	public boolean equals(Object p) {
//		// Check for self comparison.
//		if (this == p) {
//			return true;
//		}
//		
//		// Use "instanceof" instead of "getClass" because it renders
//		// an explicit check of p == null
//		if (!(p instanceof Point))
//			return false;
//
//		// Now safe to cast to Position
//		Point pos = (Point)p;
//		return (this.x == pos.x && this.y == pos.y);
//	}
}
