package monitor;

import java.awt.Font;
import java.awt.TextArea;

import common.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class MonitorView extends JFrame {
	private MapCanvas canvas; 
	private TextArea  console;
	
    public MonitorView(){
    	console = new TextArea();
    	console.setFont(new Font("arial", Font.PLAIN, 20));
    	add(console);

    	setTitle(Config.MONITOR_VIEW_TEXT);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLocation(900, 100);
		setVisible(true);
		pack();
    }
    
    public void initMap(Map map) {
    	console.setVisible(false);
    	canvas = new MapCanvas(map);
    	add(canvas);
    	pack();
    }
    
    public void printMessage(String msg) {
    	console.setText(console.getText() + msg + '\n');
    }
    
    public void updateMap() {
    	canvas.repaint();
    }
}