package project.model;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;

public class IntersectionTest extends TestCase {
	public IntersectionTest (String name) {
		super(name);
	}
	@Test
	public void testConstructor() {
		Light east_west_light = new Light();
        Light north_south_light = new Light();
        try {
        	new Intersection(null, null);
        	fail();
        }
        catch (IllegalArgumentException e) { }
        
        try {
        	new Intersection(null, north_south_light);
        	fail();
        }
        catch (IllegalArgumentException e) { }
        
        try {
        	new Intersection(east_west_light, null);
        	fail();
        }
        catch (IllegalArgumentException e) { }
           
	}
	@Test
	public void testRun() {

		Light east_west_light = new Light();
        Light north_south_light = new Light();
        Assert.assertTrue(east_west_light.getState() == north_south_light.getState());
	}
}
