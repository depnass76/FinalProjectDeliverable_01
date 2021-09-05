package project.model;

import project.random.Util;

public abstract class LightController {

	double _time;
	double _timeCounter;

	public enum state {
		GREEN, YELLOW, RED;
	}

	/**
	 * return the current state of light.
	 */
	public abstract state getLightState();

	/**
	 * return the next state of the light.
	 */
	public abstract state nextLightState();

	/**
	 * change the state of light.
	 */
	public abstract LightController changeLightState();

	public boolean run(double duration) {
		_timeCounter -= duration;
		if (_timeCounter <= 0.0) {
			_timeCounter = _time;
			return true;
		} else
			return false;
	}

}

class Green extends LightController {

	Green() {
		_time = Util.nextRandom(MP.greenMinTime, MP.greenMaxTime);
		_timeCounter = _time;
	}

	public state getLightState() {
		return state.GREEN;
	}

	public state nextLightState() {
		return state.YELLOW;
	}

	public LightController changeLightState() {
		return new Yellow();
	}

}

class Yellow extends LightController {

	Yellow() {
		_time = Util.nextRandom(MP.yellowMinTime, MP.yellowMaxTime);
		_timeCounter = _time;
	}

	public state getLightState() {
		return state.YELLOW;
	}

	public state nextLightState() {
		return state.RED;
	}

	public LightController changeLightState() {
		return new Red();
	}

}

class Red extends LightController {

	Red() {
		_time = Double.MAX_VALUE;
	}

	public state getLightState() {
		return state.RED;
	}

	public state nextLightState() {
		return state.GREEN;
	}

	public LightController changeLightState() {
		return new Green();
	}

}
