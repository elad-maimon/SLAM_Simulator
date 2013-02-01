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
	private Point               lastReadObject = null;
	
	public Sensor(SimulatorController simulator, Robot robot, int installHeading, String name) {
		this.simulator      = simulator;
		this.robot          = robot;
		this.installHeading = installHeading;
		this.name           = new String(name);
	}
	
	public double read() {
		Position positionToCheck = new Position(robot.position());
		positionToCheck.setHeading(robot.position().heading() + installHeading);
		
		while (simulator.map.cellStatus(positionToCheck.location()) == Map.CELL_FREE)
			positionToCheck.move(Position.FORWARD);
		
		lastReadObject = new Point(positionToCheck.location());
		
		switch (simulator.map.cellStatus(positionToCheck.location())) {
		case Map.CELL_CAPTURED:
			return robot.position().location().calcDistance(positionToCheck.location());
		case Map.CELL_OUT_OF_BOUND:
			return -1; // Represent infinity
		default:
			Msg.showAsync(SWT.ICON_ERROR, "Error reading sensor", "Found cell with unexpected status");
			return -2; // Represent error
		}
	}
	
	public void paint(GC gc) {
		if (lastReadObject == null)
			return;

		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		gc.drawLine(robot.position().location().x, simulator.map.size - robot.position().location().y,
				    lastReadObject.x, simulator.map.size - lastReadObject.y);
	}
}
