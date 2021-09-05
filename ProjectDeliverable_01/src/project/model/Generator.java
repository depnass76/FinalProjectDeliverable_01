package project.model;

import java.util.ArrayList;
import java.util.List;

import project.random.Util;

final class Generator implements Agent {
	private double _genrationTime;
	private Road _road;
	private double _timeCounter;
	static List<Car> _list = new ArrayList<Car>();

	public Generator() {
		_genrationTime = Util.nextRandom(MP.minCarGenerated, MP.maxCarGenerated);
	}

	public Road getRoad() {
		return _road;
	}

	public void setRoad(Road road) {
		this._road = road;
	}

	public boolean run(double time) {
		_timeCounter += time;

		if (_timeCounter >= _genrationTime) {
			_timeCounter = 0;

			Car car = new Car();
			car.setRoad(_road);

			Model._agents.add(car);

		}
		return true;
	}
}
