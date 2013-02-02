package monitor;

import org.eclipse.swt.widgets.Display;

import common.*;

public class MonitorController {
	public Map         map; 
	public MonitorView view;
	
	public MonitorController(Display display, int map_size) {
		this.map  = new Map(map_size, Map.CELL_FREE);
		this.view = new MonitorView(map);
	}
}
