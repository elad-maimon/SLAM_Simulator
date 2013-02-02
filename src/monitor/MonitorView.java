package monitor;

import common.*;
import slam.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class MonitorView extends Composite {
	private Slam      slam;
	
	private Composite canvasComposite;
	private Canvas    canvas;
	private MenuItem  startSimulationItem;
	private MenuItem  stopSimulationItem;
	
	public MonitorView(Display display, Slam slam) {
		super(new Shell(display), SWT.NONE);
		this.slam = slam;

		this.getShell().setText(Config.MONITOR_VIEW_TEXT);
		this.getShell().setSize(Config.MONITOR_VIEW_SIZE_X, Config.MONITOR_VIEW_SIZE_Y);
		this.getShell().setLayout(new FillLayout());
		this.setLayout(new GridLayout(1, false));
	    
	    buildCanvasComposite();
	    buildMenu();

	    this.layout();
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
		int borderWidth = 3;
		
	    canvasComposite = new Composite(this, SWT.NONE);
	    canvasComposite.setLayoutData(new GridData(slam.map.size + 2 * borderWidth, slam.map.size + 2 * borderWidth));
	    canvasComposite.setBackground(new Color(Display.getCurrent(), new RGB(220, 230, 240)));

	    canvas = new Canvas(canvasComposite, SWT.NONE);
	    canvas.setLocation(borderWidth, borderWidth);
	    canvas.setSize(slam.map.size, slam.map.size);
	    canvas.setBackground(Display.getCurrent().getSystemColor(1));

	    canvas.addPaintListener(new DrawEnvironment());
	}
	
	private void rebuildCanvasComposite() {
		canvasComposite.dispose();
		buildCanvasComposite();
		this.layout();
	}
	
	private void redrawCanvas() {
		canvas.redraw();
		canvas.update();
	}
	
	private class DrawEnvironment implements PaintListener {
		// the drawing implemented method
		public void paintControl(PaintEvent e) {
			e.gc.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
			slam.map.paint(e.gc);
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
