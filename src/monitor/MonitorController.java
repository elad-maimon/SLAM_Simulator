package monitor;

import common.*;

public class MonitorController {
	public Map         map; 
	public MonitorView view = new MonitorView();
	
	public MonitorController() {
		view.printMessage("Waiting for connection...");
	}
	
	public void initSimulation(int map_size) {
		this.map = new Map(map_size, Map.CELL_UNKNOWN);
		this.view.initMap(map);
	}
	
	public static void main(String[] args) throws InterruptedException {
		MonitorController controller = new MonitorController();
		Thread.sleep(2000);
		controller.view.printMessage("Connection received!");
		Thread.sleep(2000);
		controller.initSimulation(50);
		Thread.sleep(2000);
		controller.initSimulation(100);
	}
}
