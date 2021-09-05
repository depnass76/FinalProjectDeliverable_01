package project.model;

import project.random.Util;

public final class Intersection implements Agent {

	private Light eastWestLight;
	private Light northSouthLight;
	private Light currentLight;
	private boolean isEastToWest;
	private double intersectionLength;

	public Intersection(Light eastwest, Light northsouth) {

		if (eastwest == null || northsouth == null) {
			throw new IllegalArgumentException();
		}

		intersectionLength = Util.nextRandom(MP.intersectionMinLen, MP.intersectionMaxLen);
		eastWestLight = eastwest;
		northSouthLight = northsouth;
		northSouthLight.setState(LightController.state.RED);

		currentLight = eastWestLight;
		isEastToWest = true;
	}

	public double getLength() {
		return intersectionLength;
	}

	public boolean run(double time) {

		if (currentLight.run(time)) {
			if (isEastToWest) {
				currentLight = northSouthLight;
			} else
				currentLight = eastWestLight;
			isEastToWest = !isEastToWest;
			currentLight.setState(LightController.state.GREEN);
		}
		return true;
	}

}
