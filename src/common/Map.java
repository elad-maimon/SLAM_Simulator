package common;

import java.awt.Point;
import java.io.Serializable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;

public class Map implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CELL_UNKNOWN      = 0;
	public static final int CELL_FREE         = 1;
	public static final int CELL_CAPTURED     = 2;
	public static final int CELL_OUT_OF_BOUND = 3;

	public int[][] cells;
	public int     size;

	public Map(int size, int cells_init) {
		this.size = size;
		this.cells = new int[size][size];
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				cells[i][j] = cells_init;
	}

	public int cellStatus(Point p) {
		return cellStatus(p.x, p.y);
	}
	
	public int cellStatus(int x, int y) {
		if (x >= this.size || y >= this.size || x < 0 || y < 0)
			return CELL_OUT_OF_BOUND;
		else
			return cells[x][y];
	}

	public double distanceToObstacleAhead(Position pos) {
		Point originalPoint = pos.toPoint();
		
		while (cellStatus(pos.toPoint()) == CELL_FREE || cellStatus(pos.toPoint()) == CELL_UNKNOWN)
			pos.move(Position.FORWARD);
		
		switch (cellStatus(pos.toPoint())) {
		case Map.CELL_CAPTURED:
			return originalPoint.distance(pos.toPoint());
		case Map.CELL_OUT_OF_BOUND:
			return -1; // Represent infinity
		default:
			Msg.showAsync(SWT.ICON_ERROR, "Error reading sensor", "Found cell with unexpected status");
			return -2; // Represent error
		}
	}
	
	public void paint(GC gc) {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (cells[i][j] == CELL_CAPTURED)
					gc.drawPoint(i, size - j);
	}
	
	public String toString() {
		StringBuffer mapStr = new StringBuffer(Integer.toString(this.size) + "\n");

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				mapStr.append(Integer.toString(cells[i][j]));
			}
			mapStr.append("\n");
		}
		
		return mapStr.toString();
	}
}