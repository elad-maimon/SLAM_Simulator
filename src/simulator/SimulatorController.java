package simulator;

import common.*;

public class SimulatorController {
	private Map   realMap; 
	private Robot robot;
	
	public SimulatorController() {
		this.realMap = new Map(Config.MAP_SIZE);
		this.robot   = new Robot(realMap, Config.MAP_SIZE / 2, Config.MAP_SIZE / 2);
	}
	
	public void moveRobot(char key) {
		switch (key) {
		case 'w':
			robot.move(Position.FORWARD);
			break;
		case 's':
			robot.move(Position.BACKWARD);
			break;
		case 'a':
			robot.move(Position.LEFT);
			break;
		case 'd':
			robot.move(Position.RIGHT);
		}
	}
	
	public Position getRobotPosition() {
		return robot.getPosition();
	}
}
