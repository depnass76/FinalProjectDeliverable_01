package project.model;

import project.random.Util;

/**
 * A car remembers its position from the beginning of its road. Cars have random
 * velocity and random movement pattern: when reaching the end of a road, the
 * dot either resets its position to the beginning of the road, or reverses its
 * direction.
 */
public class Car implements Agent {
	Car() {
	} // Created only by this package

	private Road _road;
	private double _frontPosition = 0;
	private double _carLength = Util.nextRandom(MP.minCarLength, MP.maxCarLength);
	private double _velocity = Util.nextRandom(MP.minVelocity, MP.maxVelocity);
	private double _breakDistance = Util.nextRandom(MP.minBreakDistance, MP.maxBreakDistance);
	private double _stopDistance = Util.nextRandom(MP.minStopDistance, MP.maxStopDistance);

	public double getBackPos() {
		return _frontPosition - _carLength;
	}

	public double getLength() {
		return this._carLength;
	}

	private java.awt.Color _carColor = new java.awt.Color((int) Math.ceil(Math.random() * 200),
			(int) Math.ceil(Math.random() * 200), (int) Math.ceil(Math.random() * 200));

	public java.awt.Color getCarColor() {
		return _carColor;
	}

	public boolean run(double time) {

		double distanceToObstacle = _road.distanceToObstacle(_frontPosition);
		double newVelocity = getVelocity(distanceToObstacle);
		double newPosition = _frontPosition + newVelocity * time;
		return _road.accept(this, newPosition);
	}

	public double getVelocity(double distanceToObstacle) {
		double velocity = (_velocity / (_breakDistance - _stopDistance)) * (distanceToObstacle - _stopDistance);
		velocity = Math.max(0.0, velocity);
		velocity = Math.min(_velocity, velocity);
		return velocity;
	}

	public Road getRoad() {
		return _road;
	}

	public void setRoad(Road road) {
		this._road = road;
	}

	public double getFrontPos() {
		return _frontPosition;
	}

	public void setFrontPos(double position) {
		_frontPosition = position;
	}
}
