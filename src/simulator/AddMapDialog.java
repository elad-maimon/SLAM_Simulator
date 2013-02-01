package simulator;

import common.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class AddMapDialog {
	private Shell  shell;
	private Text   mapSize;
	private String retVal;
	
	public AddMapDialog(Shell parent) {
		// Set the add box window to be a primary modal and allow it
		// to block input to its parent
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		
		shell.setSize(Config.ADD_MAP_DIALOG_SIZE_X, Config.ADD_MAP_DIALOG_SIZE_Y);
		shell.setText(Config.ADD_MAP_DIALOG_TEXT);
		shell.setLocation(parent.getLocation().x + 50, parent.getLocation().y + 80);
		
		// Call the init function to build the window controls
		buildAddMapWin();
	}
	
	private void buildAddMapWin() {
		shell.setLayout(new GridLayout(2, false));
		
		// Set group for the content controls
		Group contentGroup = new Group(shell, SWT.SHADOW_NONE);
		contentGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		contentGroup.setLayout(new GridLayout(5, false));

		// Build the content group controls
		new Label(contentGroup, SWT.NONE).setText("Map Size");
		mapSize = new Text(contentGroup, SWT.BORDER);
		mapSize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 4, 1));
		mapSize.addListener(SWT.Verify, new VerifyDigits());
		
		// Build the buttons group controls
		Button buttonCancle = new Button(shell, SWT.PUSH);
		buttonCancle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		buttonCancle.setText("&Cancle");
		buttonCancle.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				retVal = null;
				shell.dispose();
			}
		});

		Button buttonOK = new Button(shell, SWT.PUSH);
		buttonOK.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		buttonOK.setText("&OK");
		buttonOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				// Validating all required fields before executing command
				if (!Gen.checkRequired(mapSize, mapSize.getText())) return;
				retVal = mapSize.getText();
				shell.dispose();
			}
		});
		shell.setDefaultButton(buttonOK);	// Set OK button as default button
	}
	
	// This class is listener to verify input in text fields to be only digits.
	//-------------------------------------------------------------------------
	private class VerifyDigits implements Listener {
		public void handleEvent(Event e) {
			// Get the input
			String input = e.text;
			String fullInput = ((Text)e.widget).getText();
			char[] chars = new char[input.length()];
			
			// Getting the input into a characters array
			input.getChars(0, chars.length, chars, 0);
			
			// Need to check all chars to cover cut&paste
			for (int i = 0; i < chars.length; i++) {
				// Check if the input is digits or a minus sign at the start of the input
				if ((chars[i] < '0' || chars[i] > '9') &&
					!(chars[i] == '-' && fullInput.length() == 0)) {
					e.doit = false;
					return;
				}
			}
		}
	}

	public String open() {
		Display display = shell.getDisplay();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		return retVal;
	}
}
