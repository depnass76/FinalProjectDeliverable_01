package project.model;

public interface CarAcceptor {
	public double distanceToObstacle(double position);

	public boolean accept(Car d, double frontPosition);
}
