package project.model;

import java.util.List;
import java.util.ArrayList;
import project.random.Util;

/**
 * A road holds cars.
 */
public class Road implements CarAcceptor {
	Road() {
	} // Created only by this package

	private CarAcceptor next;
	private double length = Util.nextRandom(MP.roadMinLength, MP.roadMaxLength);
	private List<Car> _cars = new ArrayList<Car>();

	public boolean accept(Car d, double frontPosition) {

		_cars.remove(d);
		if (frontPosition > this.length) {
			return next.accept(d, frontPosition - this.length);
		} else {
			d.setRoad(this);
			d.setFrontPos(frontPosition);
			_cars.add(d);
			return true;
		}
	}

	public double distanceToCarBack(double position) {
		double carBackPosition = Double.POSITIVE_INFINITY;
		for (Car c : _cars) {
			if (c.getBackPos() >= position && carBackPosition > c.getBackPos()) {
				carBackPosition = c.getBackPos();
			}
		}
		return carBackPosition;
	}

	public double distanceToObstacle(double fromPosition) {
		double obstaclePos = this.distanceToCarBack(fromPosition);
		if (obstaclePos == Double.POSITIVE_INFINITY && next != null) {
			fromPosition = fromPosition - this.length;
			obstaclePos = next.distanceToObstacle(fromPosition);
		}
		return obstaclePos - fromPosition;
	}

	public double getLength() {
		return this.length;
	}

	public List<Car> getCars() {
		return _cars;
	}

	public CarAcceptor getNext() {
		return next;
	}

	public void setNext(CarAcceptor next) {
		this.next = next;
	}

}
