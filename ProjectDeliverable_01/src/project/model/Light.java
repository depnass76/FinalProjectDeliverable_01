package project.model;

import project.model.LightController.state;

/**
 * A light has a boolean state.
 */
public class Light implements Agent, CarAcceptor {

	private LightController _state;
	private CarAcceptor _next;

	// start with green, then it moves to yellow. finally it turned red by state
	// pattern
	Light() {
		_state = new Green();
	}

	public boolean accept(Car d, double frontPosition) {
		if (this.getState() == state.GREEN)
			return _next.accept(d, frontPosition);
		return false;
	}

	public double distanceToObstacle(double position) {
		if (this.getState() == state.GREEN)
			return _next.distanceToObstacle(position);
		return 0.0;
	}

	public CarAcceptor getNext() {
		return _next;
	}

	public void setNext(CarAcceptor _next) {
		this._next = _next;
	}

	public state getState() {
		return _state.getLightState();
	}

	public void setState(state s) {
		while (_state.getLightState() != s) {
			_state = _state.changeLightState();
		}
	}

	public boolean run(double time) {
		if (_state.run(time)) {
			_state = _state.changeLightState();
			if (_state.getLightState() == state.RED)
				return true;
		}
		return false;

	}

}