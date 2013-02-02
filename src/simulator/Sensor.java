package simulator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import common.*;

public class Sensor {
	private SimulatorController simulator;
	private Robot               robot;
	private int                 installHeading;
	public  String              name;
	private Position            obstacleAhead = null;
	
	public Sensor(SimulatorController simulator, Robot robot, int installHeading, String name) {
		this.simulator      = simulator;
		this.robot          = robot;
		this.installHeading = installHeading;
		this.name           = new String(name);
	}
	
	public int installHeading() {
		return this.installHeading;
	}
	
	public double read() {
		obstacleAhead = new Position(robot.position());
		obstacleAhead.setHeading(robot.position().heading() + installHeading);
		
		return simulator.map.distanceToObstacleAhead(obstacleAhead);
	}
	
	public void paint(GC gc) {
		if (obstacleAhead == null)
			return;

		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		gc.drawLine(robot.position().x(), simulator.map.size - robot.position().y(),
				    obstacleAhead.x(), simulator.map.size - obstacleAhead.y());
	}
}
