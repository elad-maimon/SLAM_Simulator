package common;

public class Map {
	public static final int CELL_UNKNOWN      = 0;
	public static final int CELL_FREE         = 1;
	public static final int CELL_CAPTURED     = 2;
	public static final int CELL_OUT_OF_BOUND = 3;

	private int[][] cells;
	private int     size;

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
}
