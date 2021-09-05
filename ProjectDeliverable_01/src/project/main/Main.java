package project.main;

import project.main.Control;
import project.ui.UI;

/**
 * A static class to demonstrate the visualization aspect of simulation.
 */
public class Main {
	private Main() {
	}

	public static void main(String[] args) {
		UI ui;
		if (Math.random() <= 0.5) {
			ui = new project.ui.TextUI();
		} else {
			ui = new project.ui.PopupUI();
		}
		Control control = new Control(ui);
		control.run();

		System.exit(0);
	}
}
