package simulator;

import java.util.Iterator;

import common.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.*;

public class SimulatorView extends Composite {
	private SimulatorController simulator;
	
	private Canvas              canvas;
	private Text                positionInformation;
	private Text                sensorsInformation;
	private MenuItem            startSimulationItem;
	private MenuItem            stopSimulationItem;
	private MenuItem            loadMapItem;
	private MenuItem            drawMapItem;
	private MenuItem            saveMapItem;
	
	private boolean             drawMapMode = false;
	private boolean             simulationMode = false;

	public SimulatorView(Display display, SimulatorController simulator) {
		super(new Shell(display), SWT.NONE);
		this.simulator = simulator;

		this.getShell().setText(Config.MAIN_WIN_TEXT);
		this.getShell().setSize(Config.MAIN_WIN_SIZE_X, Config.MAIN_WIN_SIZE_Y);
		this.getShell().setLayout(new FillLayout());
		this.setLayout(new GridLayout(1, false));
	    
	    buildInformationComposite();
	    buildCanvasComposite();
	    buildMenu();

	    this.getShell().layout();
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
	    startSimulationItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				simulationMode = true;
				enableMenuItems();
			}
		});

	    // MenuItem: Stop Simulation
	    stopSimulationItem = new MenuItem(fileMenu, SWT.PUSH);
	    stopSimulationItem.setText("Sto&p Simulation");
	    stopSimulationItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				simulationMode = false;
				enableMenuItems();
			}
		});

	    // MenuItem: Exit
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
	    exitItem.setText("E&xit");
	    exitItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				event.display.dispose();
			}
		});

	    // Building the map menu objects
	    mapMenuHeader  = new MenuItem(menuBar, SWT.CASCADE);
	    mapMenuHeader.setMenu(mapMenu);
	    mapMenuHeader.setText("&Map");

	    // MenuItem: Load Map
	    loadMapItem = new MenuItem(mapMenu, SWT.PUSH);
	    loadMapItem.setText("&Load Map");
	    //loadMapItem.addSelectionListener(new SelectionMenuLoadMap());
	    loadMapItem.addListener(SWT.Selection, new LoadMapListener());
	    
	    // MenuItem: Draw Map
	    drawMapItem = new MenuItem(mapMenu, SWT.PUSH);
	    drawMapItem.setText("&Draw Map");
	    drawMapItem.addListener(SWT.Selection, new Listener() {
	    	public void handleEvent(Event event) {
				drawMapMode = true;
				simulator.map = new Map(Config.MAP_SIZE);
				redrawCanvas();
				enableMenuItems();
			}
	    });

	    // MenuItem: Save Map
	    saveMapItem = new MenuItem(mapMenu, SWT.PUSH);
	    saveMapItem.setText("&Save Map");
	    saveMapItem.addListener(SWT.Selection, new SaveMapListener());

	    enableMenuItems();
	    this.getShell().setMenuBar(menuBar);
	}
	
	private void buildInformationComposite() {
	    Composite informationComposite = new Composite(this, SWT.NONE);
	    informationComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	    informationComposite.setLayout(new GridLayout(1, false));

	    Group informationGroup = new Group(informationComposite, SWT.SHADOW_IN);
	    informationGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	    informationGroup.setLayout(new GridLayout(2, false));
	    informationGroup.setText("Robot Information");

		new Label(informationGroup, SWT.NONE).setText("Position:");
		positionInformation = new Text(informationGroup, SWT.NONE);
		positionInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		positionInformation.setEditable(false);

		new Label(informationGroup, SWT.NONE).setText("Sensors read:");
		sensorsInformation = new Text(informationGroup, SWT.NONE);
		sensorsInformation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		sensorsInformation.setEditable(false);
	}

	private void buildCanvasComposite() {
		int borderWidth = 3;
		
	    Composite canvasComposite = new Composite(this, SWT.NONE);
	    canvasComposite.setLayoutData(new GridData(simulator.map.size + 2 * borderWidth, simulator.map.size + 2 * borderWidth));
	    canvasComposite.setBackground(new Color(Display.getCurrent(), new RGB(220, 230, 240)));

	    canvas = new Canvas(canvasComposite, SWT.NONE);
	    canvas.setLocation(borderWidth, borderWidth);
	    canvas.setSize(simulator.map.size, simulator.map.size);
	    canvas.setBackground(Display.getCurrent().getSystemColor(1));

	    MapDrawListener drawListener = new MapDrawListener();
	    
	    canvas.addListener (SWT.MouseDown, drawListener);
	    canvas.addListener (SWT.MouseMove, drawListener);
	    canvas.addListener (SWT.MouseUp, drawListener);
	    canvas.addPaintListener(new DrawEnvironment());

	    canvas.addListener(SWT.KeyDown, new Listener() {
	    	public void handleEvent(Event e) {
				simulator.moveRobot(e.character);
				redrawCanvas();
			}
	    }); 
	}
	
	private void enableMenuItems() {
		startSimulationItem.setEnabled(!simulationMode && !drawMapMode);
		stopSimulationItem.setEnabled(simulationMode && !drawMapMode);
		loadMapItem.setEnabled(!simulationMode);
		drawMapItem.setEnabled(!simulationMode);
		saveMapItem.setEnabled(!simulationMode && drawMapMode);
	}

	private void redrawCanvas() {
		canvas.redraw();
		canvas.update();
	}
	
	public class DrawEnvironment implements PaintListener {
		// the drawing implemented method
		public void paintControl(PaintEvent e) {
			e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			simulator.map.paint(e.gc);

			if (!drawMapMode) {
				e.gc.setLineWidth(2);
				e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
				e.gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));

				simulator.robot.paint(e.gc);
				positionInformation.setText(simulator.robot.position().toString());
			}
		} 
	}
	
	public class MapDrawListener implements Listener {
		private Point startPoint = null;
		
		public void handleEvent (Event e) {
			if (!drawMapMode)
				return;

			switch (e.type) {
			case SWT.MouseDown:
				startPoint = new Point(e.x, e.y);
				break;
			case SWT.MouseUp:
				captureLineInMap(startPoint.x, startPoint.y, e.x, e.y);
				canvas.redraw();
				canvas.update();
				startPoint = null;
				break;
			case SWT.MouseMove:
				if (startPoint == null)
					return;
				
				canvas.redraw();
				canvas.update();
				GC gc = new GC(canvas);
				gc.drawLine(startPoint.x, startPoint.y, e.x, e.y);
			}
		}
		
		private void captureLineInMap(int x1, int y1, int x2, int y2) {
			int x, y, temp;
			
			if (x1 == x2) {
				if (y1 > y2) {
					temp = x1; x1 = x2; x2 = temp; 
					temp = y1; y1 = y2; y2 = temp;
				}
				
				for (y = y1; y <= y2; y++)
					simulator.map.cells[x1][simulator.map.size - y] = Map.CELL_CAPTURED;
			} else {
				if (x1 > x2) {
					temp = x1; x1 = x2; x2 = temp;
					temp = y1; y1 = y2; y2 = temp;
				}
				
				int angel = Math.abs((y1 - y2) / (x1 - x2));
	
				for (x = x1; x <= x2; x++) {
					y = (int)(y1 + ((double)(y2 - y1) / (x2 - x1)) * (x - x1));
					
					for (int i = 0; i <= angel; i++)
						if (y + i >= Math.min(y1, y2) && y + i <= Math.max(y1, y2))
							simulator.map.cells[x + 1][simulator.map.size - y - i] = Map.CELL_CAPTURED;
				}
			}
		}
	}
	
    public class LoadMapListener implements Listener {
		public void handleEvent(Event event) {
			FileDialog fileDlg = new FileDialog(new Shell(), SWT.OPEN);
			fileDlg.setFilterNames(new String[] { "Slam Maps (*.slm)" });
			fileDlg.setFilterExtensions(new String[] { "*.slm" });
			
			String filename = fileDlg.open();
			
			// If user cancel the operation 
			if (filename == null)
				return;
			
			int retval = simulator.loadMapFromFile(filename);
	
			if (retval == Config.RETVAL_SUCCESS) {
				drawMapMode = false;
				enableMenuItems();
				redrawCanvas();
			}
		}
	}

	public class SaveMapListener implements Listener {
		public void handleEvent(Event event) {
			FileDialog fileDlg = new FileDialog(new Shell(), SWT.SAVE);
			fileDlg.setFilterNames(new String[] { "Slam Maps (*.slm)" });
			fileDlg.setFilterExtensions(new String[] { "*.slm" });
			
			String filename = fileDlg.open();
			
			// If user cancel the operation 
			if (filename == null)
				return;

			int retval = simulator.saveMapToFile(filename);

			if (retval == Config.RETVAL_SUCCESS) {
				drawMapMode = false;
				enableMenuItems();
				redrawCanvas();
			}
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
