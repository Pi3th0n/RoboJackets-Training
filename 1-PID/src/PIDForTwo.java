

import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
/**
 * Runs a PD controller (PID without an Integral part) on the motors plugged
 * into ports A and B on the NXT.
 * @author Matthew Barulic
 * This code was written as part of the Georgia Tech RoboJackets software
 * training program. More information about the RoboJackets and our teams is
 * available at www.robojackets.org.
 */
public class PIDForTwo {

	public static void main(String[] args) {
		
		while(!Button.ENTER.isDown()) { }
		
		PDController controllerRight = new PDController(new NXTMotor(MotorPort.A));
		PDController controllerLeft = new PDController(new NXTMotor(MotorPort.B));
		
		controllerRight.setCoefficients(2, -0.1);
		controllerRight.setDesiredSpeed(10);
		
		controllerLeft.setCoefficients(2, -0.1);
		controllerLeft.setDesiredSpeed(10);
		
		while(!Button.ESCAPE.isDown()) {
			controllerRight.update();
			controllerLeft.update();
		}
		
		controllerLeft.stop();
		controllerRight.stop();
	}

}
