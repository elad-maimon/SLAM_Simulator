package simulator;

import common.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class SimulatorView extends Composite {
	private SimulatorController simulator;
	
	private Canvas              canvas;
	private Text                positionInformation;
	private Text                sensorsInformation;
	private MenuItem            startSimulationItem;
	private MenuItem            stopSimulationItem;

	public SimulatorView(Display display, SimulatorController simulator) {
		super(new Shell(display), SWT.NONE);
		this.simulator = simulator;

		this.getShell().setLayout(new GridLayout(1, false));
		this.getShell().setText(Config.MAIN_WIN_TEXT);
		this.getShell().setSize(Config.MAIN_WIN_SIZE_X, Config.MAIN_WIN_SIZE_Y);
	    
	    // Add listener to handle default exit by the X button
		this.getShell().addDisposeListener(new DefaultExit());

	    buildMenu();
	    buildInformationGroup();
	    
	    // Set the big drawing Canvas
	    //===========================
	    canvas = new Canvas(this.getShell(), SWT.BORDER);
	    canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    canvas.setBackground(this.getShell().getDisplay().getSystemColor(1));
	    canvas.addPaintListener(new DrawEnvironment());
	    canvas.addKeyListener(new ReadMoveCommands());
	}

	private void buildMenu() {
	    /* ================== Main menu parts ==================
	     * the menu structure:
	     * File (Menu)
	     * -> Start Simulation (MenuItem)
	     * -> Stop Simulation (MenuItem)
	     * -> Exit (MenuItem)
	     * 
	     * Map (Menu)  
	     * -> Load Map (MenuItem)
	     * -> Draw Map (MenuItem)
	     * -> Save Map (MenuItem)
	     * =====================================================*/
	    Menu     menuBar, fileMenu, mapMenu;
	    MenuItem fileMenuHeader, mapMenuHeader;

	    // Building the menus objects
	    menuBar  = new Menu(this.getShell(), SWT.BAR);
	    mapMenu  = new Menu(this.getShell(), SWT.DROP_DOWN);
	    fileMenu = new Menu(this.getShell(), SWT.DROP_DOWN);

	    // Building the file menu objects
	    fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
	    fileMenuHeader.setMenu(fileMenu);
	    fileMenuHeader.setText("&File");

	    // MenuItem: Start Simulation
	    startSimulationItem = new MenuItem(fileMenu, SWT.PUSH);
	    startSimulationItem.setText("&Start Simulation");
	    startSimulationItem.addSelectionListener(new SelectionMenuStart());

	    // MenuItem: Stop Simulation
	    stopSimulationItem = new MenuItem(fileMenu, SWT.PUSH);
	    stopSimulationItem.setText("Sto&p Simulation");
	    stopSimulationItem.addSelectionListener(new SelectionMenuStop());
	    stopSimulationItem.setEnabled(false);	// At program start no simulation running

	    // MenuItem: Exit
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
	    exitItem.setText("E&xit");
	    exitItem.addSelectionListener(new SelectionMenuExit());

	    // Building the map menu objects
	    mapMenuHeader  = new MenuItem(menuBar, SWT.CASCADE);
	    mapMenuHeader.setMenu(mapMenu);
	    mapMenuHeader.setText("&Map");

	    // MenuItem: Load Map
	    MenuItem loadMapItem = new MenuItem(mapMenu, SWT.PUSH);
	    loadMapItem.setText("&Load Map");
	    //loadMapItem.addSelectionListener(new SelectionMenuLoadMap());
	    loadMapItem.addListener(SWT.Selection, new LoadMapListener());
	    
	    // MenuItem: Draw Map
	    MenuItem drawMapItem = new MenuItem(mapMenu, SWT.PUSH);
	    drawMapItem.setText("&Draw Map");
	    drawMapItem.addSelectionListener(new SelectionMenuDrawMap());

	    // MenuItem: Save Map
	    MenuItem SaveMapItem = new MenuItem(mapMenu, SWT.PUSH);
	    SaveMapItem.setText("&Save Map");
	    SaveMapItem.addSelectionListener(new SelectionMenuSaveMap());

	    this.getShell().setMenuBar(menuBar);
	}
	
	private void buildInformationGroup() {
	    Group informationGroup = new Group(this.getShell(), SWT.SHADOW_IN);
	    informationGroup.setText("Robot Information");
	    
	    // makes it to take as much space it can:
	    informationGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

	    // set the group layout to be 2 cols
	    informationGroup.setLayout(new GridLayout(2, false));

		new Label(informationGroup, SWT.NONE).setText("Position:");
		positionInformation = new Text(informationGroup, SWT.NONE);
		positionInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		positionInformation.setEditable(false);

		new Label(informationGroup, SWT.NONE).setText("Sensors read:");
		sensorsInformation = new Text(informationGroup, SWT.NONE);
		sensorsInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		sensorsInformation.setEditable(false);
	}

	private void buildAssignProgramGroup(Group g){
	    /* ================= Create lists group parts ==================
	     * the group structure:
	     * +-------------+----------+------------+
	     * | Robots list |  Assign  | Files list |
	     * |    (List)   | (Button) |   (List)   |
	     * +-------------+----------+------------+
	     * =============================================================*/
		// headlines:
//		new Label(g,SWT.NONE).setText("Robots List");
//		new Label(g,SWT.NONE);	// blank
//		new Label(g,SWT.NONE).setText("Files List");
//		
//		// list button list
//		listRobots=new List(g,SWT.BORDER | SWT.V_SCROLL);
//		listRobots.setLayoutData(new GridData(150,80));
//		Button b=new Button(g,SWT.PUSH);
//		b.setText("<-");
//		b.addSelectionListener(new SelectionAssign());
//		listFiles=new List(g,SWT.BORDER | SWT.V_SCROLL);		
//		listFiles.setLayoutData(new GridData(150,80));
//		File f=new File(".");
//		listFiles.setItems(f.list());
	}
	
	public class DefaultExit implements DisposeListener {
		public void widgetDisposed(DisposeEvent arg0) {
			// First executing the simulation command
//			ReturnMessage retMsg = cmdExec.exit();
//
//			// Check if command return any errors
//			if (retMsg.hasError())
//				retMsg.showMessage(parentShell);
//
//			// Terminate all threads
//			sim.programs().stopAll();
//			
//			// Terminate the timer thread
//			if (timer != null)
//				timer.cancel();
		}
	}
	
	public class DrawEnvironment implements PaintListener {
		// the drawing implemented method
		public void paintControl(PaintEvent e) {
			int robotCanvasLocationX = simulator.getRobotPosition().location().x;
			int robotCanvasLocationY = Config.MAP_SIZE - simulator.getRobotPosition().location().y;
			int robotCanvasHeading   = simulator.getRobotPosition().heading() - 90;

			// Draw the robot circle 
			e.gc.drawOval(robotCanvasLocationX - 4, robotCanvasLocationY - 4, 8, 8);

			// Calculate and draw the heading indicator
			double headingRadians = Math.toRadians(robotCanvasHeading);
			int headX = (int)(robotCanvasLocationX + Math.round(15 * Math.cos(headingRadians)));
			int headY = (int)(robotCanvasLocationY + Math.round(15 * Math.sin(headingRadians)));
			e.gc.drawLine(robotCanvasLocationX, robotCanvasLocationY, headX, headY);  

			positionInformation.setText(simulator.getRobotPosition().toString());
		} 
	}
	
	public class ReadMoveCommands implements KeyListener {
		public void keyPressed(KeyEvent e) {
			simulator.moveRobot(e.character);
			canvas.redraw();
			canvas.update();
		}

		public void keyReleased(KeyEvent arg0) {
		}
	}
	
    class LoadMapListener implements Listener {
		public void handleEvent(Event event) {
			FileDialog fileDlg = new FileDialog(new Shell(), SWT.OPEN);
			String[] filterExt = new String[1];
			filterExt[0] = "*.slm";
			fileDlg.setFilterExtensions(filterExt);
			
			String filename = fileDlg.open();
			
			// If user choose a file.
			if (filename != null) {
				int retval = simulator.loadMapFromFile(filename);
	
				if (retval != Config.RETVAL_SUCCESS) {
					// TODO:
				}
				else {
					// TODO: Draw the map
				}
			}
		}
	}

    public class SelectionMenuLoadMap implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
//			new WinAddBox(parentShell, cmdExec, canvas);
		}

		public void widgetSelected(SelectionEvent e) {
//			new WinAddBox(parentShell, cmdExec, canvas);
		}
		
	}

	public class SelectionMenuDrawMap implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
//			new WinAddBox(parentShell, cmdExec, canvas);
		}

		public void widgetSelected(SelectionEvent e) {
//			new WinAddBox(parentShell, cmdExec, canvas);
		}
		
	}

	public class SelectionMenuSaveMap implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
//			new WinAddBox(parentShell, cmdExec, canvas);
		}

		public void widgetSelected(SelectionEvent e) {
//			new WinAddBox(parentShell, cmdExec, canvas);
		}
		
	}

	public class SelectionMenuStart implements SelectionListener {
		private void invoke() {
		}

		public void widgetDefaultSelected(SelectionEvent e) {invoke();}
		public void widgetSelected(SelectionEvent e)        {invoke();}
	}
	
	public class SelectionMenuStop implements SelectionListener {
		private void invoke() {
		}
		
		public void widgetDefaultSelected(SelectionEvent e) {invoke();}
		public void widgetSelected(SelectionEvent e)        {invoke();}
	}
	
	public class SelectionMenuExit implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
			e.display.dispose();
		}
		
		public void widgetSelected(SelectionEvent e) {
			e.display.dispose();
		}
	}

	public void open() {
		Display display = this.getDisplay();
		Shell shell = this.getShell();
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}
