package common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

// This class holds methods to performe general calulations, like point converts,
// string functions etc.
// generally used from several deifferent classes.
//===============================================================================
public class Gen {
//	// Method to Convert canvas position (absolute) to 
//	// robot position (relative to middle)
//	public static Position convertCanvasPosToRobot(Position canvasPos, Canvas c) {
//		int maxX = c.getSize().x;
//		int maxY = c.getSize().y;
//		int midX = maxX/2;
//		int midY = maxY/2;
//		int retX = canvasPos.x - midX;
//		int retY = midY - canvasPos.y; // Sub the canvas y position to reverse the direction
//		
//		return (new Position(retX, retY));
//	}
//
//	// Method to Convert robot position (relative to middle) to 
//	// canvas position (absolute)
//	public static Position convertRobotPosToCanvas(Position robotPos, Canvas c) {
//		int maxX = c.getSize().x;
//		int maxY = c.getSize().y;
//		int midX = maxX/2;
//		int midY = maxY/2;
//		int retX = midX + robotPos.x;
//		int retY = midY - robotPos.y; // Sub the robot y position to reverse the direction
//		
//		return (new Position(retX, retY));
//	}

	// Method to check required field
	public static boolean checkRequired(Control item, String value) {
		if (value.isEmpty()) {
			// Focus on the item to let the user know which field required.
			item.setFocus();
			
			MessageBox msgBox = new MessageBox(item.getShell(), SWT.OK | SWT.ICON_INFORMATION);
			msgBox.setMessage("Required field.");
			msgBox.open();
			
			return false;
		}
		else {
			return true;
		}
	}

//	public static Point findPointByRange(Point start, int range, int heading) {
//		Point destination = new Point();
//		double headingRadians = Math.toRadians(heading);
//		destination.x = start.x + range * Math.sin(headingRadians);
//		this.y = this.y + direction * Math.cos(headingRadians);
//	}
}
