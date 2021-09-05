package project.main;

import project.model.AnimatorBuilder;
import project.model.MP;
import project.model.Model;
import project.model.swing.SwingAnimatorBuilder;
import project.random.Util;
import project.ui.UI;
import project.ui.UIError;
import project.ui.UIFormBuilder;
import project.ui.UIFormTest;
import project.ui.UIMenu;
import project.ui.UIMenuAction;
import project.ui.UIMenuBuilder;

class Control {
	private static final int CHANGESIMULATIONPARAMETER = 3;
	private static final int START = 2;
	private static final int EXIT = 1;
	private static final int EXITED = 0;
	private static final int NUMSTATES = 10;

	private int _state;
	private UIMenu[] _menus;

	private UIFormTest __testNumber;
	private UIFormTest _testString;
	private UIFormTest _testDouble;
	private UI _ui;

	Control(UI ui) {
		_ui = ui;
		_menus = new UIMenu[NUMSTATES];
		_state = START;
		addSTART(START);
		addEXIT(EXIT);
		addParameters(CHANGESIMULATIONPARAMETER);

		_testString = new UIFormTest() {
			public boolean run(String input) {
				if (input == null)
					return false;
				return MP.AlternatePattern.equalsIgnoreCase(input.trim())
						|| MP.SimplePattern.equalsIgnoreCase(input.trim());
			}
		};

		_testDouble = new UIFormTest() {
			public boolean run(String input) {
				try {
					double i = Double.parseDouble(input);
					if (Util.isLess(i, 0.0)) {
						return false;
					} else {
						return true;
					}
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};

		__testNumber = new UIFormTest() {
			public boolean run(String input) {
				try {
					Integer.parseInt(input);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};
	}

	void run() {
		try {
			while (_state != EXITED) {
				_ui.processMenu(_menus[_state]);
			}
		} catch (UIError e) {
			_ui.displayError("UI is closed");
		}
	}

	private void addSTART(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("Oops Error!!");
			}
		});
		m.add("Run Traffic Simulation", new UIMenuAction() {
			public void run() {
				AnimatorBuilder b = new SwingAnimatorBuilder();
				Model m = new Model(b, MP.rows, MP.columns);
				m.run(MP.runTime);
				m.dispose();
			}
		});

		m.add("Change Traffic Simulation Parameters", new UIMenuAction() {
			public void run() {
				_state = CHANGESIMULATIONPARAMETER;
			}
		});

		m.add("Exit Traffic Simulation", new UIMenuAction() {
			public void run() {
				_state = EXIT;
			}
		});

		_menus[stateNum] = m.toUIMenu("Project Traffic Simulation");
	}

	private void addParameters(int stateNum) {
		UIMenuBuilder value = new UIMenuBuilder();
		value.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("Oops Error!!");
			}
		});
		value.add("Show Current Values", new UIMenuAction() {
			public void run() {
				StringBuffer buffer = new StringBuffer();
				buffer.append("Simulation time step (seconds)   [").append(MP.timeStep).append("]\n");
				buffer.append("Simulation run time (seconds)    [").append(MP.runTime).append("]\n");
				buffer.append("Grid size (number of roads)      [row=").append(MP.rows).append(",column=")
						.append(MP.columns).append("]\n");

				buffer.append("Traffic pattern                   [").append(MP.trafficPattern).append("]\n");
				buffer.append("Car Generated  (seconds/car)      [min=").append(MP.minCarGenerated).append(",max=")
						.append(MP.maxCarGenerated).append("]\n");
				buffer.append("Road length (meters)              [min=").append(MP.roadMinLength).append(",max=")
						.append(MP.roadMaxLength).append("]\n");
				buffer.append("Intersection length (meters)       [min=").append(MP.intersectionMinLen).append(",max=")
						.append(MP.intersectionMaxLen).append("]\n");
				buffer.append("Car length (meters)                 [min=").append(MP.minCarLength).append(",max=")
						.append(MP.maxCarLength).append("]\n");
				buffer.append("Car maximum velocity (meters/second) [min=").append(MP.minVelocity).append(",max=")
						.append(MP.maxVelocity).append("]\n");
				buffer.append("Car stop distance (meters)           [min=").append(MP.minStopDistance).append(",max=")
						.append(MP.maxStopDistance).append("]\n");
				buffer.append("Car brake distance (meters)          [min=").append(MP.minBreakDistance).append(",max=")
						.append(MP.maxBreakDistance).append("]\n");
				buffer.append("Green light time (seconds)           [min=").append(MP.greenMinTime).append(",max=")
						.append(MP.greenMaxTime).append("]\n");
				buffer.append("Yellow light time (seconds)          [min=").append(MP.yellowMinTime).append(",max=")
						.append(MP.yellowMaxTime).append("]\n");
				_ui.displayMessage(buffer.toString());
			}
		});
		value.add(" Traffic Simulation Time Step", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();

				f.add("Traffic Simulation Time Step: " + "[" + Double.toString(MP.timeStep) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Simulation time step"));
				MP.timeStep = Double.parseDouble(result[0]);
			}
		});
		value.add("Traffic Simulation Run Time", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Traffic Simulation Run Time: " + "[" + Double.toString(MP.runTime) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Simulation run time"));
				MP.runTime = Double.parseDouble(result[0]);
			}
		});
		value.add("Traffic Simulation Grid Size", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Rows: " + "[" + Integer.toString(MP.rows) + "]", __testNumber);
				f.add("Columns: " + "[" + Integer.toString(MP.columns) + "]", __testNumber);
				String[] result = _ui.processForm(f.toUIForm("Grid Size"));
				MP.rows = Integer.parseInt(result[0]);
				MP.columns = Integer.parseInt(result[1]);
			}
		});
		value.add("Traffic Simulation Pattern", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Traffic Simulation Pattern: (simple or alternate) ", _testString);
				String[] result = _ui.processForm(f.toUIForm("Traffic pattern"));
				MP.trafficPattern = result[0];
			}
		});
		value.add("Traffic Simulation Generated Car ", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Car Generated: " + "[" + Double.toString(MP.minCarGenerated) + "]", _testDouble);
				f.add("Maximum Car Generated: " + "[" + Double.toString(MP.maxCarGenerated) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Car generated"));
				MP.minCarGenerated = Double.parseDouble(result[0]);
				MP.maxCarGenerated = Double.parseDouble(result[1]);
			}
		});
		value.add("Traffic Simulation Road Length", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Min " + "[" + Double.toString(MP.roadMinLength) + "]", _testDouble);
				f.add("Max " + "[" + Double.toString(MP.roadMaxLength) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Road length"));
				MP.roadMinLength = Double.parseDouble(result[0]);
				MP.roadMaxLength = Double.parseDouble(result[1]);
			}
		});
		value.add("Traffic Simulation Intersection Length", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Intersection Length  " + "[" + Double.toString(MP.intersectionMinLen) + "]",
						_testDouble);
				f.add("Maximum Intersection Length " + "[" + Double.toString(MP.intersectionMaxLen) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Intersection length"));
				MP.intersectionMinLen = Double.parseDouble(result[0]);
				MP.intersectionMaxLen = Double.parseDouble(result[1]);

			}
		});
		value.add("Traffic Simulation Car Length", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Car Length " + "[" + Double.toString(MP.minCarLength) + "]", _testDouble);
				f.add("Maximum Car Length " + "[" + Double.toString(MP.maxCarLength) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Car length"));
				MP.minCarLength = Double.parseDouble(result[0]);
				MP.maxCarLength = Double.parseDouble(result[1]);
			}
		});
		value.add("Traffic Simulation Car Maximum Velocity", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Car Maximum Velocity " + "[" + Double.toString(MP.minVelocity) + "]", _testDouble);
				f.add("Maximum Car Maximum Velocity " + "[" + Double.toString(MP.maxVelocity) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Car maximum velocity"));
				MP.minVelocity = Double.parseDouble(result[0]);
				MP.maxVelocity = Double.parseDouble(result[1]);
			}
		});
		value.add("Traffic Simulation Car Stopping Distance", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Car Stopping Distance " + "[" + Double.toString(MP.minStopDistance) + "]", _testDouble);
				f.add("Maximum Car Stopping Distance " + "[" + Double.toString(MP.maxStopDistance) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Car stop distance"));
				MP.minStopDistance = Double.parseDouble(result[0]);
				MP.maxStopDistance = Double.parseDouble(result[1]);
			}
		});
		value.add("Traffic Simulation Car Braking Distance", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Car Braking Distance " + "[" + Double.toString(MP.minBreakDistance) + "]", _testDouble);
				f.add("Maximum Car Braking Distance " + "[" + Double.toString(MP.maxBreakDistance) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Car brake distance"));
				MP.minBreakDistance = Double.parseDouble(result[0]);
				MP.maxBreakDistance = Double.parseDouble(result[1]);
			}
		});
		value.add("Traffic Simulation Green Light Time", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Green Light Time " + "[" + Double.toString(MP.greenMinTime) + "]", _testDouble);
				f.add("Maximum Green Light Time " + "[" + Double.toString(MP.greenMaxTime) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Traffic light green time"));
				MP.greenMinTime = Double.parseDouble(result[0]);
				MP.greenMaxTime = Double.parseDouble(result[1]);

			}
		});
		value.add("Traffic Simulation Yellow Light Time", new UIMenuAction() {
			public void run() {
				UIFormBuilder f = new UIFormBuilder();
				f.add("Minimum Yellow Light Time " + "[" + Double.toString(MP.yellowMinTime) + "]", _testDouble);
				f.add("Maximum Yellow Light Time " + "[" + Double.toString(MP.yellowMaxTime) + "]", _testDouble);
				String[] result = _ui.processForm(f.toUIForm("Traffic light yellow time"));
				MP.yellowMinTime = Double.parseDouble(result[0]);
				MP.yellowMaxTime = Double.parseDouble(result[1]);

			}
		});

		value.add("Reset Traffic Simulation and Return To The Main Menu", new UIMenuAction() {
			public void run() {

				_state = START;
			}
		});

		_menus[stateNum] = value.toUIMenu("Change Traffic Simulation Parameters");

	}

	private void addEXIT(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
			}
		});
		m.add("Yes", new UIMenuAction() {
			public void run() {
				_state = EXITED;
			}
		});
		m.add("No", new UIMenuAction() {
			public void run() {
				_state = START;
			}
		});

		_menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
	}
}
