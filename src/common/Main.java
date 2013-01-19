package common;

import org.eclipse.swt.widgets.Display;

import simulator.*;

public class Main {

	public static void main(String[] args) {
		SimulatorController simulator = new SimulatorController();
		Display display = new Display();

		new SimulatorView(display, simulator).open();
		display.dispose();	
	}
}
