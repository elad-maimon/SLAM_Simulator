package simulator;

import common.*;

import java.io.*;

import org.eclipse.swt.SWT;

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
	
	public void saveMapToFile(String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(realMap);
			out.close();
			fileOut.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public int loadMapFromFile(String filename) {
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			realMap = (Map)in.readObject();
			in.close();
			fileIn.close();

			return Config.RETVAL_SUCCESS;
		} catch(IOException e) {
			e.printStackTrace();
			Msg.showAsync(SWT.ICON_ERROR, "Can't open map", "Failed to read file");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
			Msg.showAsync(SWT.ICON_ERROR, "Can't open map", "File is not map format");
		}
		
		return Config.RETVAL_FAIL;
	}

}
