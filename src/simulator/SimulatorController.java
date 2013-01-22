package simulator;

import common.*;

import java.io.*;

import org.eclipse.swt.SWT;

public class SimulatorController {
	public Map   map; 
	public Robot robot;
	
	public SimulatorController(int map_size) {
		this.map   = new Map(map_size);
		this.robot = new Robot(map, map_size / 2, map_size / 2);
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
	
	public int saveMapToFile(String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(map);
			out.close();
			fileOut.close();
			return Config.RETVAL_SUCCESS;
		} catch(IOException e) {
			e.printStackTrace();
			Msg.showAsync(SWT.ICON_ERROR, "Can't save map", "Failed to save map to file");
		}
		
		return Config.RETVAL_FAIL;
	}
	
	public int loadMapFromFile(String filename) {
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map)in.readObject();
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
