package project.model;

/**
 * Static class for model parameters.
 */
public class MP {
	private MP() {
	}

	/** Length of cars, in meters */
	public static double carLength = 10;
	/** Length of roads, in meters */
	public static double roadLength = 200;
	/** Maximum car velocity, in meters/second */
	public static double maxVelocity = 24.0;
	/** Minimum car velocity, in meters/second */
	public static double minVelocity = 12.0;
	/** Alternate pattern */
	public static String AlternatePattern = "alternate";
	/** Simple pattern */
	public static String SimplePattern = "simple";

	/** simulation time step in seconds */
	public static double timeStep = 0.5;
	/** simulation run time in seconds */
	public static double runTime = 850.0;
	/** Number of rows */
	public static int rows = 2;
	/** Number of columns */
	public static int columns = 3;
	/** Alternate traffic pattern */
	public static String trafficPattern = AlternatePattern;
	/** Minimum car generated */
	public static double minCarGenerated = 15.0;
	/** Maximum car generated */
	public static double maxCarGenerated = 30.0;
	/** Minimum road length */
	public static double roadMinLength = 200.0;
	/** Maximum road length */
	public static double roadMaxLength = 600.0;
	/** Intersection minimum length */
	public static double intersectionMinLen = 8.0;
	/** Intersection maximum length */
	public static double intersectionMaxLen = 16.0;
	/** Minimum car length */
	public static double minCarLength = 5.0;
	/** Maximum car length */
	public static double maxCarLength = 16.0;
	/** Minimum car stop distance */
	public static double minStopDistance = 0.5;
	/** Maximum car stop distance */
	public static double maxStopDistance = 2.5;
	/** Minimum car break distance */
	public static double minBreakDistance = 9.0;
	/** Maximum car break distance */
	public static double maxBreakDistance = 15.0;
	/** Green light minimum time */
	public static double greenMinTime = 35.0;
	/** Green light maximum time */
	public static double greenMaxTime = 120.0;
	/** Yellow light minimum time */
	public static double yellowMinTime = 3.0;
	/** Yellow light maximum time */
	public static double yellowMaxTime = 6.0;
}
