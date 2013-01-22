package simulator;

import org.eclipse.swt.SWT;

import common.*;

public class Sensor {
	private Robot  robot;
	private Map    map;
	private int    installHeading;
	private String name;
	private Point  lastReadObject = null;
	
	public Sensor(Robot robot, Map map, int installHeading, String name) {
		this.robot          = robot;
		this.map            = map;
		this.installHeading = installHeading;
		this.name           = new String(name);
	}
	
	public double read() {
		Position positionToCheck = new Position(robot.position());
		positionToCheck.setHeading(robot.position().heading() + installHeading);
		
		while (map.cellStatus(positionToCheck.location()) == Map.CELL_FREE)
			positionToCheck.move(Position.FORWARD);
		
		lastReadObject = new Point(positionToCheck.location());
		
		switch (map.cellStatus(positionToCheck.location())) {
		case Map.CELL_CAPTURED:
			return robot.position().location().calcDistance(positionToCheck.location());
		case Map.CELL_OUT_OF_BOUND:
			return -1; // Represent infinity
		default:
			Msg.showAsync(SWT.ICON_ERROR, "Error reading sensor", "Found cell with unexpected status");
			return -2; // Represent error
		}
	}
	
	public void paint() {
		if (lastReadObject == null)
			return;
		
		System.out.println(name);
	}
}
