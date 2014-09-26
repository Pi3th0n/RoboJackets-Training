
import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;

/**
 * Runs the motor plugged into port A on the NXT at 50% power. Note that there
 * is no controller paying attention to how much resistance the motor is
 * experiencing, so it is very easy to slow down or stop the wheel.
 * @author Matthew Barulic
 * This code was written as part of the Georgia Tech RoboJackets software
 * training program. More information about the RoboJackets and our teams is
 * available at www.robojackets.org.
 */
public class NoPID {

	public static void main(String[] args) {
		NXTMotor right = new NXTMotor(MotorPort.A);
		NXTMotor left  = new NXTMotor(MotorPort.B);
		
		// Wait for the enter button to be pressed.
		while(!Button.ENTER.isDown()) { }
		
		// Set the motors to 50% power
		right.setPower(50);
		left.setPower(50);
		
		// Wait for the escape button to be pressed.
		while(!Button.ESCAPE.isDown()) { }
		
		right.stop();
		left.stop();
	}

}
