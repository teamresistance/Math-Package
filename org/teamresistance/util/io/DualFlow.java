package org.teamresistance.util.io;

import org.teamresistance.mathd.Vector2d;

import edu.wpi.first.wpilibj.SPI.Port;

/**
 * Interprets the inputs of two Optical Flow Sensors to give rectangular position coordinants
 * and an orientation angle.
 * @author Mathis
 *
 */
public class DualFlow {
	private OpticalFlowSensor left;
	private OpticalFlowSensor right;
	
	private double x;
	private double y;
	private double angle; //orientation of robot
	
	public DualFlow(int leftPortChannel, int rightPortChannel) {
		this();
		left = new OpticalFlowSensor(leftPortChannel);
		right = new OpticalFlowSensor(rightPortChannel);
	}
	public DualFlow(Port leftPort, Port rightPort) {
		this();
		left = new OpticalFlowSensor(leftPort);
		right = new OpticalFlowSensor(rightPort);
	}
	private DualFlow () {
		x = 0;
		y = 0;
		angle = 0;
	}
	
	public void update() {
		double ldx = left.getX();
		double ldy = left.getY();
		double rdx = right.getX();
		double rdy = right.getY();
		
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	public double getAngle() {
		return angle;
	}
}
