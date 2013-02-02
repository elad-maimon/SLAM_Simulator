package slam;

import monitor.MonitorController;
import monitor.MonitorView2;
import common.*;

public class Slam {
	private MonitorController monitor;
	public  Map               map;
	public  Position          estimatedRobotPos;
	
	public Slam(int size, MonitorController monitor) {
		this.monitor = monitor;
		map = new Map(size + 10, Map.CELL_UNKNOWN);
		estimatedRobotPos = new Position(size / 2 + 5, size / 2 + 5, 0);
	}
	
	public void iterate(int odometry, int[] sensorsHeading, double[] sensorsReadings) {
		estimatedRobotPos.move(odometry);
		
		for(int i = 0; i < sensorsHeading.length; i++) {
			if (sensorsReadings[i] == -1)
				continue;
			
			double headingRadians = Math.toRadians(sensorsHeading[i]);
			int x = (int)Math.round(estimatedRobotPos.x() + sensorsReadings[i] * Math.sin(headingRadians));
			int y = (int)Math.round(estimatedRobotPos.y() + sensorsReadings[i] * Math.cos(headingRadians));
			
			map.cells[x][y] = Map.CELL_CAPTURED;
//			System.out.println("Sensor heading " + sensorsHeading[i] + " found obstacle at (" + x + "," + y + ")");
		}
		
		monitor.map = this.map;
		monitor.view.updateMap();
	}
}
