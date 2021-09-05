package project.model;

import org.junit.Test;

import junit.framework.TestCase;

public class CarTest extends TestCase {
	public CarTest(String name) {
		super(name);
	}

	@Test
	public void testGetBackPos() {
		Car _car = new Car();
		assertNotNull(_car.getBackPos());
		Road _road = new Road();
		_car.setRoad(_road);
		assertTrue(_car.getBackPos() == _car.getFrontPos() - _car.getLength());
	}

	@Test
	public void testSetFrontPos() {
		Car _car = new Car();
		_car.setFrontPos(5.0);
		assertEquals(_car.getFrontPos(), 5.0);
	}

	@Test
	public void testGetFrontPos() {
		Car _car = new Car();
		_car.setFrontPos(5.0);
		assertNotNull(_car.getFrontPos());
		assertEquals(5.0, _car.getFrontPos());

	}

	@Test
	public void testGetRoad() {
		Road _road = new Road();
		Car _car = new Car();
		_car.setRoad(_road);
		assertNotNull(_car.getRoad());

	}

	@Test
	public void testRun() {
		Car _car = new Car();
		try {
			_car.run(5.0);
			fail();
		} catch (Exception e) {
		}

		Road _road = new Road();
		_car.setRoad(_road);
		_car.run(5.0);
		assertTrue(_car.getFrontPos() >= 0.0 && _car.getFrontPos() <= _road.getLength());
	}

}