package project.model;

public class Sink implements CarAcceptor {

	public boolean accept(Car d, double pos) {
		Model._agents.remove(d);
		return true;
	}

	public double distanceToObstacle(double f) {
		return Double.POSITIVE_INFINITY;
	}
}
