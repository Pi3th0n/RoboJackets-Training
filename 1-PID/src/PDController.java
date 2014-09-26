

import lejos.nxt.NXTMotor;
/**
 * A reusable PD Controller. We've packaged the logic of the controller into an
 * object so we can easily control many motors at the same time.
 * @author Matthew Barulic
 * This code was written as part of the Georgia Tech RoboJackets software
 * training program. More information about the RoboJackets and our teams is
 * available at www.robojackets.org.
 */
public class PDController {
	private NXTMotor motor;
	private int setSpeed = 0;
	private long lastTime;
	private int lastError = 0;
	private int output = 0;
	
	private double P = 0;
	private double D = 0;
	
	public PDController(NXTMotor motor) {
		this.motor = motor;
	}
	
	/**
	 * Sets the desired output speed of the motor and resets the PD math.
	 * @param degreesPerSecond The desired output speed in degrees per second.
	 */
	public void setDesiredSpeed(int degreesPerSecond) {
		setSpeed = degreesPerSecond;
		lastTime = System.currentTimeMillis();
		motor.resetTachoCount();
	}
	
	/**
	 * Stops the motors and sets desired output speed to zero.
	 */
	public void stop() {
		setDesiredSpeed(0);
		motor.stop();
	}
	
	/**
	 * Sets the PD equation coefficients to be used in this instance of the
	 * controller.
	 * @param p The coefficient for the Proportional component
	 * @param d The coefficient for the Derivative component
	 */
	public void setCoefficients(double p, double d) {
		P = p;
		D = d;
	}
	
	/**
	 * Iterates the PD controller. The user should call this method
	 * in a loop to allow the controller to adjust motor power properly.
	 */
	public void update() {
		long dTime = System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		int degrees = motor.getTachoCount();
		motor.resetTachoCount();
		int measuredSpeed = (int) (degrees / ((double)dTime / 1000.0));
		int error = setSpeed - measuredSpeed;
		int dError = error - lastError;
		lastError = error;
		output += P * error + D * dError;
		output = Math.min(100, Math.max(0, output));
		motor.setPower(output);
	}
}
