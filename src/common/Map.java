package common;

import java.io.Serializable;

import org.eclipse.swt.graphics.GC;

public class Map implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int CELL_UNKNOWN      = 0;
	public static final int CELL_FREE         = 1;
	public static final int CELL_CAPTURED     = 2;
	public static final int CELL_OUT_OF_BOUND = 3;

	public int[][] cells;
	public int     size;

	public Map(int size) {
		this.size = size;
		this.cells = new int[size][size];
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				cells[i][j] = CELL_FREE;
	}
	
	public int cellStatus(Point p) {
		if (p.x >= this.size || p.y >= this.size || p.x < 0 || p.y < 0)
			return CELL_OUT_OF_BOUND;
		else
			return cells[p.x][p.y];
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