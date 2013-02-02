package monitor;

import common.*;

import javax.swing.*;

public class MonitorView {
	private MapCanvas canvas; 
	
    public MonitorView(Map map){
    	MapCanvas canvas = new MapCanvas(map);
        
        JFrame frame = new JFrame(Config.MONITOR_VIEW_TEXT);
        frame.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
    
    public void updateMap() {
    	canvas.repaint();
    }
}