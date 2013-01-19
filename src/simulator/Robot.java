package simulator;

import java.util.ArrayList;
import common.*;

public class Robot {
	private Map               map;
	private Position          position;
	private ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	
	public Robot(Map map, int x, int y) {
		this.map = map;
		this.position = new Position(x, y, 0);
	}
	
	public Position getPosition() {
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
		
		updateSensors();
	}
	
	private void updateSensors() {
		
	}
}
