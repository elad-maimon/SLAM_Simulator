package common;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class Config {
	public static final int MAP_SIZE = 100;
    public static final int GRID_SIZE_PIXELS = 3;

	public static final String   MAIN_WIN_TEXT         = "Robot Simulator";
	public static final int      MAIN_WIN_SIZE_X       = 700;
	public static final int      MAIN_WIN_SIZE_Y       = 600;

	public static final String   ADD_MAP_DIALOG_TEXT   = "Add Box";
	public static final int      ADD_MAP_DIALOG_SIZE_X = 190;
	public static final int      ADD_MAP_DIALOG_SIZE_Y = 140;

	public static final String   MONITOR_VIEW_TEXT     = "SLAM Monitor";
	public static final int      MONITOR_VIEW_SIZE_X   = 400;
	public static final int      MONITOR_VIEW_SIZE_Y   = 400;
	
	public static final int    RETVAL_SUCCESS = 0;
	public static final int    RETVAL_FAIL    = -1;

}
