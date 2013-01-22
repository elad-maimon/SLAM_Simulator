package simulator;

import common.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.swt.graphics.GC;

public class Robot {
	private Map               map; // TODO: replace with simulator
	private Position          position;
	private ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	
	public Robot(Map map, int x, int y) {
		this.map = map;
		this.position = new Position(x, y, 0);
	}
	
	public Position position() {
		return position;
	}
	
	public void addSensor(Sensor sensor) {
		this.sensors.add(sensor);
	}
	
	public void move(int direction) {
		if (direction == Position.LEFT || direction == Position.RIGHT)
			this.position.turn(direction);

		
		if (direction == Position.FORWARD || direction == Position.BACKWARD) {
			Position newPosition = new Position(this.position);
			
			newPosition.move(direction);
			
			if (map.cellStatus(newPosition.location()) == Map.CELL_FREE)
				this.position.setLocation(newPosition.location());
		}
		
		sensorsRead();
	}
	
	public void paint(GC gc) {
		int robotCanvasLocationX = this.position().location().x;
		int robotCanvasLocationY = map.size - this.position().location().y;
		int robotCanvasHeading   = this.position().heading() - 90;

		// Draw the robot circle 
		gc.fillOval(robotCanvasLocationX - 4, robotCanvasLocationY - 4, 8, 8);

		// Calculate and draw the heading indicator
		double headingRadians = Math.toRadians(robotCanvasHeading);
		int headX = (int)(robotCanvasLocationX + Math.round(10 * Math.cos(headingRadians)));
		int headY = (int)(robotCanvasLocationY + Math.round(10 * Math.sin(headingRadians)));
		gc.drawLine(robotCanvasLocationX, robotCanvasLocationY, headX, headY);
	}
	
	private void sensorsRead() {
		Iterator<Sensor> sensorIter = sensors.iterator();
		while (sensorIter.hasNext())
			sensorIter.next().read();
	}
}
