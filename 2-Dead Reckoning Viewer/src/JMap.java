import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import lejos.geom.Point;


public class JMap extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private List<Point> map;
	
	private float minx = -1;
	private float maxx = 1;
	private float miny = 0;
	private float maxy = 0;
	
	public JMap() {
		map = new LinkedList<Point>();
	}
	
	public void paintComponent(Graphics g){
		
		float rangex = maxx - minx;
		float rangey = maxy - miny;
		
		if(rangex == 0 || rangey == 0) 
			return;
		
		g.setColor(Color.BLACK);
		
		for(Point p : map) {
			g.fillOval((int)(getWidth()*((p.x - minx)/rangex)), (int)(getHeight()*((p.y - miny)/rangey)), 5, 5);
		}
		
		g.setColor(Color.RED);
		g.drawLine(0, (int)(getHeight()*((-miny)/rangey)), getWidth(), (int)(getHeight()*((-miny)/rangey)));

		g.setColor(Color.GREEN);
		g.drawLine((int)(getWidth()*((-minx)/rangex)),0 , (int)(getWidth()*((-minx)/rangex)), getHeight());
    }
	
	public void AddPoint(Point p) {
		map.add(p);
		minx = Math.min(minx, p.x);
		maxx = Math.max(maxx, p.x);
		miny = Math.min(miny, p.y);
		maxy = Math.max(maxy, p.y);
	}
	
	public void Clear() {
		map.clear();
	}
	
}
