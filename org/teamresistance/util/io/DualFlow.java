package org.teamresistance.util.io;

import org.teamresistance.mathd.Vector2d;

public class DualFlow {
	private OpticalFlowSensor left;
	private OpticalFlowSensor right;
	
	private Vector2d pos;
	private double angle; //orientation of robot
	
	
	public DualFlow() {
		left = new OpticalFlowSensor();
		right = new OpticalFlowSensor();
		
		pos = new Vector2d(0, 0);
		angle = 0;
	}
	
	public void update() {
		
	}
	
	public double getX() {
		return pos.getX();
	}
	
	public double getY() {
		return pos.getY();
	}
}
