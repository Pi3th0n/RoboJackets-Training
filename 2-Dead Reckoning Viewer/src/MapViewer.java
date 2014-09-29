import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import lejos.geom.Point;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;


public class MapViewer extends JFrame {

	NXTComm nxtComm;
	InputStream is;
	JMap map;
	Timer timer;
	
	public MapViewer() {
		map = new JMap();
		this.add(map);
		
		this.setSize(400, 400);
		
		try{
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			NXTInfo[] devices = nxtComm.search(null);
			if(devices.length == 0) {
				JOptionPane.showMessageDialog(this, "No NXTs found.");
				System.err.println("No NXTs found.");
				return;
			}
			if(!nxtComm.open(nxtComm.search(null)[0])) {
				JOptionPane.showMessageDialog(this, "Could not open communication port.");
				System.err.println("Could not open communication port.");
				return;
			}
			is = nxtComm.getInputStream();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(timer.isRunning())
					timer.stop();
				try {
					nxtComm.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				byte[] input = new byte[100];
				if(is.read(input) > 0) {
					String message = new String(input);
					String[] tokens = message.split("$");
					for(String token : tokens) {
						token = token.substring(1);
						String[] coordinates = token.split(",");
						map.AddPoint(new Point(Float.parseFloat(coordinates[0]), Float.parseFloat(coordinates[1])));
						map.repaint();
					}
				}
				} catch(Exception exc) {
					if(exc instanceof IOException && exc.getMessage().equals("Error in read")) {
						System.err.println("Error in USB communication. Timer stopped.");
						timer.stop();
					} else {
						exc.printStackTrace();
					}
				}
			}
		});
		timer.setRepeats(true);
		timer.start();
	}
	
	public static void main(String[] args) {
		MapViewer viewer = new MapViewer();
		viewer.setVisible(true);
		while(viewer.isVisible()) {}
		viewer.dispose();
	}

}
