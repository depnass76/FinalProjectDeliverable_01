package project.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import project.util.Animator;

/**
 * An example to model for a simple visualization. The model contains two roads.
 * See {@link #SimpleModel(AnimatorBuilder)}.
 */
public class SimpleModel extends Observable {
	public static List<Agent> _agents;
	private Animator _animator;
	private boolean _disposed;

	/**
	 * Creates a model to be visualized using the <code>builder</code>. If the
	 * builder is null, no visualization is performed. Each road has one
	 * {@link Car}.
	 *
	 */
	public SimpleModel(AnimatorBuilder builder) {
		if (builder == null) {
			builder = new NullAnimatorBuilder();
		}
		_agents = new ArrayList<Agent>();
		setup(builder);
		_animator = builder.getAnimator();
		super.addObserver(_animator);
	}

	/**
	 * Run the simulation for <code>duration</code> model seconds.
	 */
	public void run(int duration) {
		if (_disposed)
			throw new IllegalStateException();
		for (double i = 0.0; i < duration; i += MP.timeStep) {
			Agent[] agents_copy = _agents.toArray(new Agent[0]);
			for (Agent a : agents_copy) {
				a.run(MP.timeStep);
			}
			super.setChanged();
			super.notifyObservers();
		}
	}

	/**
	 * Throw away this model.
	 */
	public void dispose() {
		_animator.dispose();
		_disposed = true;
	}

	private void setup(AnimatorBuilder builder) {
		int row = 2;
		int column = 3;
		Light[][] eastToWestLight = new Light[row][column];
		Light[][] northToSouthLight = new Light[column][row];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				eastToWestLight[i][j] = new Light();
			}
		}

		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {
				northToSouthLight[i][j] = new Light();
			}
		}

		Intersection[][] iSection = new Intersection[row][column];
		for (int i = 0; i < row; i++)
			for (int j = 0; j < column; j++) {
				iSection[i][j] = new Intersection(eastToWestLight[i][j], northToSouthLight[j][i]);
				_agents.add(iSection[i][j]);
			}

		List<Road> _roadList = new ArrayList<Road>();
		Road[][] eastToWestRoad = new Road[row][column + 1];
		Road[][] northToSouthRoad = new Road[column][row + 1];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column + 1; j++) {
				eastToWestRoad[i][j] = new Road();
				_roadList.add(eastToWestRoad[i][j]);
			}
		}

		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row + 1; j++) {
				northToSouthRoad[i][j] = new Road();
				_roadList.add(northToSouthRoad[i][j]);
			}
		}

		Generator[] ew = new Generator[row];
		Generator[] ns = new Generator[column];

		boolean eastToWest = true;
		for (int i = 0; i < row; i++) {
			ew[i] = new Generator();
			_agents.add(ew[i]);
			if (eastToWest)
				ew[i].setRoad(eastToWestRoad[i][column]);
			else
				ew[i].setRoad(eastToWestRoad[i][0]);
			for (int j = 0; j < column; j++) {

				if (eastToWest) {
					eastToWestRoad[i][column - j].setNext(eastToWestLight[i][column - j - 1]);
					eastToWestLight[i][column - 1 - j].setNext(eastToWestRoad[i][column - 1 - j]);

					builder.addHorizontalRoad(eastToWestRoad[i][3 - j], i, 3 - j, eastToWest);
					builder.addLight(eastToWestLight[i][j], i, j);

				} else {
					eastToWestRoad[i][j].setNext(eastToWestLight[i][j]);
					eastToWestLight[i][j].setNext(eastToWestRoad[i][j + 1]);

					builder.addHorizontalRoad(eastToWestRoad[i][j], i, j, eastToWest);
					builder.addLight(eastToWestLight[i][j], i, j);
				}

			}
			if (eastToWest) {
				builder.addHorizontalRoad(eastToWestRoad[i][0], i, 0, eastToWest);
				eastToWestRoad[i][0].setNext(new Sink());

			} else {
				builder.addHorizontalRoad(eastToWestRoad[i][column], i, column, eastToWest);
				eastToWestRoad[i][column].setNext(new Sink());
			}

			eastToWest = !eastToWest;
		}

		boolean southToNorth = false;
		for (int i = 0; i < column; i++) {
			ns[i] = new Generator();
			_agents.add(ns[i]);
			if (southToNorth)
				ns[i].setRoad(northToSouthRoad[i][row]);
			else
				ns[i].setRoad(northToSouthRoad[i][0]);

			for (int j = 0; j < row; j++) {

				if (southToNorth) {
					northToSouthRoad[i][row - j].setNext(northToSouthLight[i][row - j - 1]);
					northToSouthLight[i][row - j - 1].setNext(northToSouthRoad[i][row - j - 1]);

					builder.addVerticalRoad(northToSouthRoad[i][row - j], row - j, i, southToNorth);

				} else {
					northToSouthRoad[i][j].setNext(northToSouthLight[i][j]);
					northToSouthLight[i][j].setNext(northToSouthRoad[i][j + 1]);
					builder.addVerticalRoad(northToSouthRoad[i][j], j, i, southToNorth);
				}
			}
			if (southToNorth) {
				builder.addVerticalRoad(northToSouthRoad[i][0], 0, i, southToNorth);
				northToSouthRoad[i][0].setNext(new Sink());
			} else {
				builder.addVerticalRoad(northToSouthRoad[i][row], row, i, southToNorth);
				northToSouthRoad[i][row].setNext(new Sink());
			}
			southToNorth = !southToNorth;
		}

	}
}