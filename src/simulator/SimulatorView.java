package simulator;

import common.*;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class SimulatorView extends Composite {
	private SimulatorController simulator;
	
	private Canvas              canvas;
	private List                listRobots;
	private List                listFiles;
	private Text                robotName;
	private Button              buttonAddArm;
	private Button              buttonSmartRobot;
	private Combo               robotType;
	private MenuItem            startSimulationItem;
	private MenuItem            stopSimulationItem;

	public SimulatorView(Display display, SimulatorController simulator) {
		super(new Shell(display), SWT.NONE);
		this.simulator = simulator;

		this.getShell().setLayout(new GridLayout(2,false));
		this.getShell().setText(Config.MAIN_WIN_TEXT);
		this.getShell().setSize(Config.MAIN_WIN_SIZE_X, Config.MAIN_WIN_SIZE_Y);
	    
	    // Add listener to handle default exit by the X button
		this.getShell().addDisposeListener(new DefaultExit());

	    // Call function to build the menu controls
	    buildMenu();
	    
	    // Set the group of create robot
	    //==============================
	    Group createRobotGroup = new Group(this.getShell(), SWT.SHADOW_IN);
	    createRobotGroup.setText("Create Robot");

	    // makes it to take as much space it can:
	    createRobotGroup.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,true,false));
	    // set the group layout to be 4 cols
	    createRobotGroup.setLayout(new GridLayout(4, false));
	    // Build the group components
	    buildCreateRobotGroup(createRobotGroup);
	    
	    // Set the group of assign programs
	    //=================================
	    Group assignProgramsGroup = new Group(this.getShell(), SWT.SHADOW_IN);
	    assignProgramsGroup.setText("Assign program to robot");

	    // set the group layout to be 3 cols 
	    assignProgramsGroup.setLayout(new GridLayout(3, false));
	    // Build the group components
	    buildAssignProgramGroup(assignProgramsGroup);
	    
	    // Set the big drawing Canvas
	    //===========================
	    canvas = new Canvas(this.getShell(), SWT.BORDER);
	    
	    // set the canvas to take 2 cells
	    canvas.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,true,2,1));
	    canvas.setBackground(this.getShell().getDisplay().getSystemColor(1));
	    canvas.addPaintListener(new DrawEnvironment()); // Listener to redraw the canvas
	    
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
	     * Box (Menu)  
	     * -> Add Box (MenuItem)
	     * =====================================================*/
	    Menu     menuBar, fileMenu, boxMenu;
	    MenuItem fileMenuHeader, boxMenuHeader;

	    // Building the menus objects
	    menuBar  = new Menu(this.getShell(), SWT.BAR);
	    boxMenu  = new Menu(this.getShell(), SWT.DROP_DOWN);
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

	    // Building the box menu objects
	    boxMenuHeader  = new MenuItem(menuBar, SWT.CASCADE);
	    boxMenuHeader.setMenu(boxMenu);
	    boxMenuHeader.setText("&Box");

	    // MenuItem: Add Box
	    MenuItem addBoxItem = new MenuItem(boxMenu, SWT.PUSH);
	    addBoxItem.setText("&Add Box");
	    addBoxItem.addSelectionListener(new SelectionMenuAddBox());
	    
	    this.getShell().setMenuBar(menuBar);
	}
	
	private void buildCreateRobotGroup(Group g) {
	    /* ================= Create robot group parts ==================
	     * the group structure:
	     * +--------------+---------+---------------------+----------+
	     * | Name (Lable) | (Text)  | Add arm (Check)     |   Add    |
	     * +--------------+---------+---------------------| (Button) |
	     * | Type (Lable) | (Combo) | Smart robot (Check) |          |
	     * +--------------+---------+---------------------+----------+
	     * =============================================================*/
		new Label(g, SWT.NONE).setText("Name");
		robotName = new Text(g, SWT.BORDER);
		buttonAddArm = new Button(g, SWT.CHECK);
		buttonAddArm.setText("Add arm");

		// Build the "Add" button
		Button buttonAdd = new Button(g, SWT.PUSH);
		buttonAdd.setText("Add");
		GridData addButtonGrid = new GridData(50, 50);
		addButtonGrid.verticalSpan = 2; // Set the button to span over 2 cells
		buttonAdd.setLayoutData(addButtonGrid);
//		buttonAdd.addSelectionListener(new SelectionAddRobot());

		new Label(g, SWT.NONE).setText("Type");
		
		// Build the robot types combo
		robotType = new Combo(g, SWT.DROP_DOWN | SWT.READ_ONLY);
//		robotType.setItems(Config.ROBOT_TYPES);

		// Build the add arm check button
		buttonSmartRobot = new Button(g, SWT.CHECK);
		buttonSmartRobot.setText("Smart Robot");
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
		new Label(g,SWT.NONE).setText("Robots List");
		new Label(g,SWT.NONE);	// blank
		new Label(g,SWT.NONE).setText("Files List");
		
		// list button list
		listRobots=new List(g,SWT.BORDER | SWT.V_SCROLL);
		listRobots.setLayoutData(new GridData(150,80));
		Button b=new Button(g,SWT.PUSH);
		b.setText("<-");
//		b.addSelectionListener(new SelectionAssign());
		listFiles=new List(g,SWT.BORDER | SWT.V_SCROLL);		
		listFiles.setLayoutData(new GridData(150,80));
		File f=new File(".");
		listFiles.setItems(f.list());
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
	
	public class SelectionMenuAddBox implements SelectionListener {
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
			// Requires only to close the shell, it will invoke the default exit
//			parentShell.dispose();
		}
		
		public void widgetSelected(SelectionEvent e) {
			// Requires only to close the shell, it will invoke the default exit
//			parentShell.dispose();
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
