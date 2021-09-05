package project.main;

import project.model.SimpleModel;
import project.model.swing.SwingAnimatorBuilder;

/**
 * A static class to demonstrate the visualization aspect of simulation.
 */
public class SimpleMain {
	private SimpleMain() {
	}

	public static void main(String[] args) {
		SimpleModel m = new SimpleModel(new SwingAnimatorBuilder());
		m.run(500);
		m.dispose();
		System.exit(0);
	}
}
