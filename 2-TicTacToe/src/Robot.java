import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;

public class Robot {

	NXTRegulatedMotor drive, turntable, arm;
	LightSensor lightSensor;
	int lightThreshold;
	
	public enum MoveMode {
		Write, Read 
	}
	
	public Robot() {
		drive = new NXTRegulatedMotor(MotorPort.A);
		drive.setSpeed(60);
		turntable = new NXTRegulatedMotor(MotorPort.C);
		turntable.setSpeed(500);
		arm = new NXTRegulatedMotor(MotorPort.B);
		arm.setSpeed(45);
		lightSensor = new LightSensor(SensorPort.S1);
		lightThreshold = 400;
	}
	
	public void moveArmUp() {
		arm.rotateTo(0);
	}
	
	public void moveArmToSense() {
		arm.rotateTo(70);
	}
	
	public void moveArmToWrite() {
		arm.rotateTo(90);
	}
	
	public void moveToCell(int row, int col, MoveMode mode) {
		moveArmToSense();
		turntable.rotateTo(angleForColumn(col));
		drive.rotateTo(driveAngleForCell(row, col, mode));
		if(mode == MoveMode.Write)
			moveArmToWrite();
		if(mode == MoveMode.Read)
			moveArmToSense();
	}
	
	public void moveToHome() {
		moveArmUp();
		drive.rotateTo(driveAngleForCell(2,1,MoveMode.Write));
		turntable.rotateTo(angleForColumn(1));
	}
	
	private int angleForColumn(int col) {
		switch(col) {
		case 0:
			return 720;
		case 1:
			return 0;
		case 2:
			return -720;
		default:
			return 0;
		}
	}
	
	private int driveAngleForCell(int row, int col, MoveMode mode) {
		if(row == 2) {
			if(mode == MoveMode.Write)
				return 0;
			if(mode == MoveMode.Read)
				return 20;
		}
		if(row == 1) {
			if(mode == MoveMode.Write)
				return 35;
			if(mode == MoveMode.Read)
				return 50;
		}
		if(row == 0) {
			if(mode == MoveMode.Write)
				return 60;
			if(mode == MoveMode.Read)
				return 80;
		}
		return 0;
	}
	
	public boolean isLightDark() {
		try { Thread.sleep(100); } catch (Exception e) { }
		return lightSensor.getNormalizedLightValue() < lightThreshold;
	}
	
}
