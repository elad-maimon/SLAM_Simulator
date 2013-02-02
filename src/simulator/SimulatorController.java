package simulator;

import common.*;
import slam.*;

import java.io.*;

import monitor.MonitorController;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class SimulatorController {
	public Map               map; 
	public Robot             robot;
	public Slam              slam;
	public SimulatorView     view;
	
	public SimulatorController(Display display, int map_size) {
		this.map     = new Map(map_size, Map.CELL_FREE);
		this.robot   = new Robot(this, map_size / 2, map_size / 2);
		this.view    = new SimulatorView(display, this);
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
			robot.position().clone(new Position(map.size / 2, map.size / 2, 0));
			return Config.RETVAL_SUCCESS;
		} catch(IOException e) {
			e.printStackTrace();
			Msg.showAsync(SWT.ICON_ERROR, "Can't save map", "Failed to save map to file");
			return Config.RETVAL_FAIL;
		}
	}
	
	public int loadMapFromFile(String filename) {
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			map = (Map)in.readObject();
			in.close();
			fileIn.close();
			robot.position().clone(new Position(map.size / 2, map.size / 2, 0));
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
