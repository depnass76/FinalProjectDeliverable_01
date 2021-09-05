package project.ui;

import javax.swing.JOptionPane;
//import java.io.IOException;

public final class PopupUI implements UI {
	public void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(null, message, "Input Error!", JOptionPane.ERROR_MESSAGE);
	}

	public void processMenu(UIMenu menu) {
		StringBuilder b = new StringBuilder();
		b.append(menu.getHeading());
		b.append("\n");
		b.append("Enter Your Choice By Number:");
		b.append("\n");

		for (int i = 1; i < menu.size(); i++) {
			b.append("  " + i + ". " + menu.getPrompt(i));
			b.append("\n");
		}

		String response = JOptionPane.showInputDialog(b.toString());
		if (response == null) {
			response = "";
		}
		int selection;
		try {
			selection = Integer.parseInt(response, 10);
			if ((selection < 0) || (selection >= menu.size()))
				selection = 0;
		} catch (NumberFormatException e) {
			selection = 0;
		}

		menu.runAction(selection);
	}

	public String[] processForm(UIForm form) {
		/* <private return="null"> */
		String[] result = new String[form.size()];
		int i = 0;
		while (i < form.size()) {
			String response = JOptionPane.showInputDialog(form.getPrompt(i));
			if (response == null) {
				response = "";
			}
			if (!form.checkInput(i, response)) {
				displayError("You Entered Invalid Input.  Please Try Again!");
			} else {
				result[i] = response;
				++i;
			}
		}
		return result;
		/* </private> */
	}
}
