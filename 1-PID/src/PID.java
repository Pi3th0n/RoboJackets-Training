

import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;

/**
 * Runs a PD controller (PID without an Integral part) on the motor plugged
 * into Port A on the NXT. Note that with this PD controller running on the
 * motor, it takes noticeably more force to slow and stop the motor. The PD
 * controller is actively adjusting the motor power in an attempt to maintain
 * output speed!
 * @author Matthew Barulic
 * This code was written as part of the Georgia Tech RoboJackets software
 * training program. More information about the RoboJackets and our teams is
 * available at www.robojackets.org.
 */
public class PID {

	public static void main(String[] args) {
		// Initialize Variables
		NXTMotor motor = new NXTMotor(MotorPort.A);
		int setSpeed = 10;
		long lastTime = System.currentTimeMillis();
		int lastError = 0;
		int output = 0;
		
		double P =  2.0;
		double D = -0.1;
		
		// Wait for orange button press before moving
		while(!Button.ENTER.isDown()) { }
		
		// Run PD controller in loop until small grey button is pressed
		while(!Button.ESCAPE.isDown()) {
			
			
			// Calculate the speed the wheels are currently moving
			long dTime = System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			
			int degrees = motor.getTachoCount();
			motor.resetTachoCount();
			
			int measuredSpeed = (int) (degrees / ((double)dTime / 1000.0));
			
			// Calculate the error and estimate derivative of error
			int error = setSpeed - measuredSpeed;
			int dError = error - lastError;
			lastError = error;
			
			// The PID equation, without the I
			output += P * error + D * dError;
			
			// Ensure that output stays between 0 and 100
			output = Math.min(100, Math.max(0, output));
			
			motor.setPower(output);
		}
		
		motor.stop();
	}

}
