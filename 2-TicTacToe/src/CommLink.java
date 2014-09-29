import java.io.IOException;
import java.io.OutputStream;

import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;


public class CommLink {

	USBConnection usb;
	OutputStream ostream;
	
	public CommLink() {
		usb = USB.waitForConnection();
		ostream = usb.openOutputStream();
	}
	
	public void close() {
		usb.close();
	}
	
	public void sendBoardState(Board board) {
		String message = "$";
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				message = message + board.getMarking(r, c);
			}
		}
		try {
			ostream.write(message.getBytes());
			ostream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
