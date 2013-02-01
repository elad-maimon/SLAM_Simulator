package common;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;

// This class holds methods to performe general calulations, like point converts,
// string functions etc.
// generally used from several deifferent classes.
//===============================================================================
public class Gen {
	// Function to read a complete line from the screen buffer.
	//---------------------------------------------------------
//	public static StringBuffer readLine() {
//		StringBuffer line = new StringBuffer();
//		char currChar = 0;
//		
//		try {
//			// Read the first character
//			currChar = (char)System.in.read();
//
//			// Loop to read all characters from the screen until
//			// line separators.
//			while (currChar != '\r' && currChar != '\n') {
//				line.append(currChar);
//				currChar = (char)System.in.read();
//			}
//
//			// Skiping the line seperator in order that the next line read
//			// will be ready.
//			System.in.skip(1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return (line);
//	}
//
//	// Method to get the first word (until blank)from a string
//	public static String firstWord(String pStr) {
//		int wordSeparatePos = pStr.indexOf(" ");
//		return pStr.substring(0, wordSeparatePos < 0 ?
//								 pStr.length() : wordSeparatePos);
//	}
//
//	// Method to handle unexpected errors. exiting the program.
//	public static void handleError(boolean exit) {
//		System.out.println(Const.FATAL_ERR_MSG);
//		
//		if (exit) {
//			System.exit(0);
//		}
//	}
//	
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
//	
//	// This method get an anchor position and return new position that exists in
//	// the top left corner (225 degrees) of the surrounded rectangle with distance radius
//	public static Position getTopLeftCorner(Position start, int distance) {
//		// Calculating the top left corner degrees in radians (225 in degrees)
//		double radianHeading = Math.PI / 4 * 5;
//		Position corner = new Position(0,0);
//		
//		// Calculating the new position in the required distance
//		corner.x = (int)(start.x + Math.round(distance * Math.cos(radianHeading)));
//		corner.y = (int)(start.y + Math.round(distance * Math.sin(radianHeading)));
//		
//		return (corner);
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
	
//	// Method to convert any number to degree in range 0 to 359
//	public static int convToDegree(int heading) {
//		int retDegree = (heading % 360);
//		if (retDegree < 0)
//			retDegree += 360;
//		
//		return (retDegree);
//	}
}
