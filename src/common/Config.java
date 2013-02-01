package common;

public class Config {
	public static final int MAP_SIZE = 100;

//	//===============
//	// Error messages
//	//===============
//	public static final String FATAL_ERR_MSG  = "Fatal Error or Unhandled exception!\nplease contact your vendor...";
//	public static final String ERR_MSG_CMND   = "Error: command not found.\n"; 
//	public static final String ERR_MSG_TYPE   = "Error: no such robot type.\n"; 
//	public static final String ERR_MSG_NAME   = "Error: no such robot or box with this name.\n";
//	public static final String ERR_MSG_FILE   = "Error: file not found.\n";
//	public static final String ERR_MSG_ADDT   = "Error: no such addition.\n";
//	public static final String ERR_MSG_PICK   = "Error: %s is unable to pickup %s.\n";
//	public static final String ERR_MSG_PUTD   = "Error: %s has no box.\n";
//	public static final String ERR_MSG_ASGN   = "Error: another program is assigned to this robot.\n";
//	public static final String ERR_MSG_EXIST  = "Error: robot or box with that name already exists.\n";
//
//	// Messages add to the GUI
//	public static final String GUI_MSG_SEL_LIST = "Must select robot and file from the lists.";
//
//	//================
//	// Command formats
//	//================
//	public static final String INIT_FORMAT   = "Init type (\\w+) at (-?\\d+) (-?\\d+) named (\\w+)( with (arm|smartKit|all))?";
//	public static final String MOVE_FORMAT   = "Move speed (-?\\d+) heading (-?\\d+)";
//	public static final String SIM_FORMAT    = "Simulate (\\d+) seconds";
//	public static final String GET_FORMAT    = "Get (\\w+) position";
//	public static final String LIST_FORMAT   = "List robots by (distance|name)";
//	public static final String FILE_FORMAT   = "Files";
//	public static final String LOAD_FORMAT   = "Load ([A-Za-z0-9.]+)"; /** or (\\w+\\.?\\w*+)*/
//	public static final String ASGN_FORMAT   = "Assign (\\w+) with ([A-Za-z0-9.]+)";
//	public static final String STOP_FORMAT   = "Stop";
//	public static final String PUTBOX_FORMAT = "PutBox at (-?\\d+) (-?\\d+) named (\\w+)";
//	public static final String PICK_FORMAT   = "Pickup (\\w+)";
//	public static final String DOWN_FORMAT   = "PutBoxDown";
//	public static final String EXIT_FORMAT   = "Exit";
//	
//	//==================
//	// General constants
//	//==================
//	public static final String   CONFIG_FILE       = "config.xml";
//	public static final int      STEP_DURATION     = 1000;
//	public static final int      UNLIMIT_SIMULATE  = -1;
//	public static final String   PROG_MSG_HEADER   = "\t%s on %s: ";
	public static final String   MAIN_WIN_TEXT     = "Robot Simulator";
	public static final int      MAIN_WIN_SIZE_X   = 700;
	public static final int      MAIN_WIN_SIZE_Y   = 600;
//	public static final int      DRAW_OVAL_RADIUS  = 10;
	public static final String[] ROBOT_TYPES       = "RV1 RV2 Aibo".split(" ");

	public static final String   ADD_MAP_DIALOG_TEXT   = "Add Box";
	public static final int      ADD_MAP_DIALOG_SIZE_X = 190;
	public static final int      ADD_MAP_DIALOG_SIZE_Y = 140;

	
	public static final int    RETVAL_SUCCESS = 0;
	public static final int    RETVAL_FAIL    = -1;

}
