package org.teamresistance.util.io.odometry;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * Interprets the inputs of two OpticalFlowSensors to give planar rectangular position coordinants
 * and an orientation angle.
 * 
 * Assumes sensors are fixed "parallel and in the same orientation as" the robot,
 * and that sensors are equidistant from the robot's center of rotation.
 * 
 * The sensor designated "right sensor" will not always necessarily be to the right
 * of the other sensor in every reference frame. The "right sensor"'s defining characteristic is that
 * the change in y-flow reading it gives will match the sign of the change in angle that would occur.
 * 
 * Perhaps "sign" and "anti-sign" or some other nomenclature that stresses this characteristic
 * and not a relative direction, would be better.
 * @author Mathis
 *
 */
public class Odometer {
	private OpticalFlowSensor left;
	private OpticalFlowSensor right;
	
	private double x;
	private double y;
	private double theta; //orientation of robot, in radians
	
	private double diameter = -1; //TODO distance between sensors, in feet
	private double tolerance = -1; //TODO how equal do readings have to be before I can pretend they should be equal?
	
	/**
	 * Constructs a new Odometer that takes input from the flow sensors connected to the specified port channels
	 * @param leftPortChannel the channel for the left flow sensor
	 * @param rightPortChannel the channel for the right flow sensor
	 */
	public Odometer(int leftPortChannel, int rightPortChannel) {
		this(Port.valueOf("kOnboardCS" + leftPortChannel), 
				Port.valueOf("kOnboardCS" + rightPortChannel));
	}
	
	/**
	 * Constructs a new Odometer that takes input from the flow sensors connected to the specified ports
	 * @param leftPort the port for the left flow sensor
	 * @param rightPort the port for the right flow sensor
	 */
	public Odometer(Port leftPort, Port rightPort) {
		left = new OpticalFlowSensor(leftPort);
		right = new OpticalFlowSensor(rightPort);
		x = 0;
		y = 0;
		theta = 0;
	}
	
	/**
	 * Updates this Odometer, polling the sensors for their input.
	 */
	public void update() {
		double ldx = left.getX();
		double ldy = left.getY();
		double rdx = right.getX();
		double rdy = right.getY();

		//Average. the two x values *should* be equal, since only y readings get tangential stuff
		double avgdx = (ldx + rdx) / 2; //x-translation of sensors, relative to sensor
		
		//Average. This is the arc length of the movement of the midpoint.
		double s = (ldy + rdy) / 2; //arc length
		
		//Subtract to cancel the parts of Y that represent equal readings for translation.
		//The resulting two subtracted equal and opposite y values underneath represent rotation.
		//Divide by two, since those two y rotation values were equal (One was opposite, but they were subtracted...)
		//Divide by the radius.
		double dtheta = (rdy - ldy) / diameter; //rotation of sensors, in radians, absolute.

		//Absolute translation vector
		double dx;
		double dy;
		
		//sin and cos of current angle, computed beforehand as a slight optimization.
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		
		//if dtheta should be zero; if translated only
		if (-tolerance <= dtheta && dtheta <= tolerance) {
			//The translation, average flow readings.
			//Let the relative translation vector be (avgdx, r).
			//Rotate the relative translation vector by current angle, theta
			dx = avgdx * cos - s * sin;
			dy = avgdx * sin + s * cos;
		} else { 
			//The distance between the point of rotation (the rotation of dtheta) and the midpoint.
			double r = s / dtheta;
			
			dx = r * (Math.cos(theta + dtheta) - cos);
			dy = r * (Math.sin(theta + dtheta) - sin);
			
			//TODO is this even possible? To have x-translation WHILE you rotate circularly?
			//dx += avgdx;
		}
		
		x += dx;
		y += dy;
		theta += dtheta;
	}
	
	/**
	 * Returns the absolute x-displacement from the origin.
	 * @return the distance, in feet.
	 */
	public double getX() {
		return x;
	}
	/**
	 * Returns the absolute y-displacement from the origin.
	 * @return the distance, in feet.
	 */
	public double getY() {
		return y;
	}
	/**
	 * Returns the angle between the absolute x-axis and the joining segment of the two sensors.
	 * @return the angle, in radians.
	 */
	public double getAngle() {
		return theta;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void setAngle(double angle) {
		this.theta = angle;
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + theta + ")";
	}
	
}
