package slam;

import common.*;

public class Slam {
	private Map map;
	
	public Slam() {
		map = new Map(Config.MAP_SIZE * 2, Map.CELL_UNKNOWN);
	}
}
