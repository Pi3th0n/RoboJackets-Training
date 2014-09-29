import java.io.OutputStream;

import lejos.geom.Point;
import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;


public class Mapper {

	public static void main(String[] args) {
		
		USBConnection usb = USB.waitForConnection();
		if(usb == null) return;
		
		OutputStream os = usb.openOutputStream();
		
		while(true) {
			int button = Button.waitForAnyPress();
			if(button == Button.ID_ENTER)
				break;
			if(button == Button.ID_ESCAPE)
				return;
		}
		
		Point robotPos = new Point(0,0);
		
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		
		NXTRegulatedMotor rightMotor = new NXTRegulatedMotor(MotorPort.A);
		NXTRegulatedMotor leftMotor = new NXTRegulatedMotor(MotorPort.B);
		rightMotor.setSpeed(20);
		leftMotor.setSpeed(20);
		
		Sound.setVolume(10);
		
		while(Button.ESCAPE.isUp()) {
//			Sound.playNote(Sound.XYLOPHONE, 261, 5);
			
			rightMotor.rotate(20, true);
			leftMotor.rotate(20);
			robotPos.y += 0.9769;
			
			int distance = sensor.getDistance();
			if(distance < 255) {
				String msg = "$" + robotPos.x + "," + (distance + robotPos.y);
				try {
					os.write(msg.getBytes());
					os.flush();
				} catch (Exception e) {
					return;
				}
			}
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				return;
			}
		}
		
		usb.close();
	}

}
