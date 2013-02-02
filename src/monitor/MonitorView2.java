package monitor;

import common.*;

import java.awt.Frame;
import java.util.Random;

import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.*;

public class MonitorView2 extends Composite {
	public  Map       map = new Map(100, Map.CELL_UNKNOWN);
	
	private Composite canvasComposite;
	private MapCanvas   canvas;
	private MenuItem  startSimulationItem;
	private MenuItem  stopSimulationItem;
	
	public MonitorView2(Display display) {
		super(new Shell(display), SWT.NONE);

		this.getShell().setText(Config.MONITOR_VIEW_TEXT);
		this.getShell().setLayout(new FillLayout());
		this.setLayout(new GridLayout(1, false));
		
	    buildCanvasComposite();
	    buildMenu();

	    this.getShell().pack();
	}
	
	private void buildMenu() {
	    Menu     menuBar, fileMenu;
	    MenuItem fileMenuHeader;

	    // Building the menus objects
	    menuBar  = new Menu(this.getShell(), SWT.BAR);
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
			}
		});

	    // MenuItem: Stop Simulation
	    stopSimulationItem = new MenuItem(fileMenu, SWT.PUSH);
	    stopSimulationItem.setText("Sto&p Simulation");
	    stopSimulationItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
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

	    this.getShell().setMenuBar(menuBar);
	}
	
	private void buildCanvasComposite() {
	    canvasComposite = new Composite(this, SWT.EMBEDDED | SWT.NONE);
	    canvasComposite.setLayoutData(new GridData(map.size * Config.GRID_SIZE_PIXELS, map.size * Config.GRID_SIZE_PIXELS));
	    Frame frame = SWT_AWT.new_Frame(canvasComposite);
	    canvas = new MapCanvas(map);
	    frame.add(canvas);
	}
	
	public void update() {
		canvas.repaint();
	}
	
	public void open() {
		Display display = this.getDisplay();
		Shell shell = this.getShell();
		
		shell.open();

		for(int z=0; z<20; z++) {
			Random r = new Random();
	        // Randomize the terrain
	        for (int i = 0; i < map.size; i++) {
	            for (int j = 0; j < map.size; j++) {
	                int randomTerrainIndex = r.nextInt(4);
	                map.cells[i][j] = randomTerrainIndex;
	            }
	        }
			update();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
        while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}
