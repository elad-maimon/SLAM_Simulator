package common;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import simulator.*;
import slam.Slam;

import monitor.MapCanvas;
import monitor.MonitorController;
import monitor.MonitorView;
import monitor.MonitorView2;

import org.eclipse.swt.widgets.Display;

public class Main {

	public static void main(String[] args) {
		final Display display = new Display();

		final SimulatorController simulator = new SimulatorController(display, Config.MAP_SIZE);
		simulator.robot.addSensor("left sensor", 315);
		simulator.robot.addSensor("right sensor", 45);
		simulator.robot.addSensor("front sensor", 0);
		simulator.robot.addSensor("rear sensor", 180);

		simulator.view.open();

		display.dispose();
	}
}
