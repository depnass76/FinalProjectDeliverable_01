package project.model;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class RoadTest extends TestCase {
	public RoadTest(String name) {
		super(name);
	}
    @Test
	public void testAccept() {
		Car _car1 = new Car();
		Road _road = new Road();
		Assert.assertTrue(_road.accept(_car1, 0.0));

		Car _car2 = new Car();
		Assert.assertTrue(_road.accept(_car2, 20.0));
		Assert.assertTrue(_road.getCars().size() == 2);
	}
    @Test
	public void testDistanceToObstacle() {
		Car _car1 = new Car();
		Road _road = new Road();
		_car1.setRoad(_road);
		Assert.assertTrue(_road.distanceToObstacle(_car1.getBackPos()) == Double.POSITIVE_INFINITY);

		_car1.run(3.0);

		Car _car2 = new Car();
		_car2.setRoad(_road);
		Assert.assertTrue(_road.distanceToObstacle(_car2.getBackPos()) < Double.POSITIVE_INFINITY);
	}
    @Test
	public void testDistanceToCarBack() {
		Car _car1 = new Car();
		Road _road = new Road();
		_car1.setRoad(_road);
		Assert.assertTrue(_road.distanceToCarBack(_car1.getFrontPos()) == Double.POSITIVE_INFINITY);

		_car1.run(3.0);

		Car _car2 = new Car();
		_car2.setRoad(_road);
		Assert.assertTrue(_road.distanceToCarBack(_car2.getBackPos()) < Double.POSITIVE_INFINITY);
	}

}
