package simulator;

import common.*;
import slam.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

public class Robot {
	private SimulatorController simulator;
	private Position            position;
	private ArrayList<Sensor>   sensors = new ArrayList<Sensor>();
	private Slam                slam;
	
	public Robot(SimulatorController simulator, int x, int y) {
		this.simulator = simulator;
		this.position  = new Position(x, y, 0);
		this.slam      = new Slam();
	}
	
	public Position position() {
		return position;
	}
	
	public void addSensor(String name, int installHeading) {
		this.sensors.add(new Sensor(simulator, this, installHeading, name));
	}
	
	public void move(int direction) {
		if (direction == Position.LEFT || direction == Position.RIGHT)
			this.position.turn(direction);

		
		if (direction == Position.FORWARD || direction == Position.BACKWARD) {
			Position newPosition = new Position(this.position);
			
			newPosition.move(direction);
			
			if (simulator.map.cellStatus(newPosition.location()) == Map.CELL_FREE)
				this.position.setLocation(newPosition.location());
		}
		
		sensorsRead();
	}
	
	public void paint(GC gc) {
		gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));

		
		int robotCanvasLocationX = this.position().location().x;
		int robotCanvasLocationY = simulator.map.size - this.position().location().y;
		int robotCanvasHeading   = this.position().heading() - 90;

		// Draw the robot circle 
		gc.fillOval(robotCanvasLocationX - 4, robotCanvasLocationY - 4, 8, 8);

		// Calculate and draw the heading indicator
		double headingRadians = Math.toRadians(robotCanvasHeading);
		int headX = (int)(robotCanvasLocationX + Math.round(10 * Math.cos(headingRadians)));
		int headY = (int)(robotCanvasLocationY + Math.round(10 * Math.sin(headingRadians)));
		gc.setLineWidth(2);
		gc.drawLine(robotCanvasLocationX, robotCanvasLocationY, headX, headY);
		gc.setLineWidth(1);

		Iterator<Sensor> sensorIter = sensors.iterator();
		while (sensorIter.hasNext())
			sensorIter.next().paint(gc);
	}
	
	private void sensorsRead() {
		double       distance;
		Sensor       currSensor;
		StringBuffer sensorsInformation = new StringBuffer();
		
		Iterator<Sensor> sensorIter = sensors.iterator();
		while (sensorIter.hasNext()) {
			currSensor = sensorIter.next();
			distance = currSensor.read();
			sensorsInformation.append(String.format("%s: %.2f, ", currSensor.name, distance));
		}
		
		simulator.view.setSensorsInfoText(sensorsInformation.toString());
	}
}
