package common;

import org.eclipse.swt.widgets.*;

public class Msg {
	public static void showSync(final int    style
							   ,final String title
							   ,final String msg) {
		Shell shell       = new Shell();
		MessageBox msgBox = new MessageBox(shell, style);
		
		// Set the message box parameters and open it
		msgBox.setText(title);
		msgBox.setMessage(msg);
		msgBox.open();
		
		shell.dispose();
	}
	
	public static void showAsync(final int    style
								,final String title
								,final String msg)
	{
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Msg.showSync(style, title, msg);
			}
		});
	}
}
