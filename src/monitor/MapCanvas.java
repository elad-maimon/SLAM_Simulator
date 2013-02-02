package monitor;

import common.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@SuppressWarnings("serial")
public class MapCanvas extends JPanel {
	private Map map;
	
    public MapCanvas(Map map){
    	this.map = map;
    	randomize();
        int preferredSize = map.size * Config.GRID_SIZE_PIXELS;
        setPreferredSize(new Dimension(preferredSize, preferredSize));
    }
    
    public void randomize() {
        Random r = new Random();
        // Randomize the terrain
        for (int i = 0; i < map.size; i++) {
            for (int j = 0; j < map.size; j++) {
                int randomTerrainIndex = r.nextInt(4);
                map.cells[i][j] = randomTerrainIndex;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.clearRect(0, 0, getWidth(), getHeight());
	
	    // Draw the grid
	    int rectWidth = getWidth() / map.size;
	    int rectHeight = getHeight() / map.size;
	
	    for (int i = 0; i < map.size; i++) {
	        for (int j = 0; j < map.size; j++) {
	            // Upper left corner of this terrain rect
	            int x = i * rectWidth;
	            int y = j * rectHeight;
	            
				switch(map.cells[i][j]) {
				case(Map.CELL_UNKNOWN):
					g.setColor(Color.GRAY);
					break;
				case(Map.CELL_FREE):
					g.setColor(Color.WHITE);
					break;
				case(Map.CELL_CAPTURED):
					g.setColor(Color.BLACK);
				}
	
	            g.fillRect(x, y, rectWidth, rectHeight);
	        }
	    }
    }
}