package scenarios.linefollower;

import java.util.List;

import shortestpath.Coordinates;
import shortestpath.Map;
import engine.Color;
import engine.RenderContext;
import engine.Vector;
import engine.World;
import engine.WorldObject;

public class PathWorldObject extends WorldObject {
	private List<Vector> mPath;
	private Map mMap;
	
	public PathWorldObject(World e, Vector p, Map map, List<Vector> path) {
		super(e, p);
		mPath = path;
		mMap = map;
	}

	@Override
	public void paint(RenderContext g) {
		final float scale = 10.0f;
	
		g.setColor(new Color(220, 220, 220, 100));
		for(int i=0; i<mMap.NumCol; i++) {
			g.drawLine(i * scale, 0, i * scale, mMap.NumRow * scale);	
		}
		for(int i=0; i<mMap.NumRow; i++) {
			g.drawLine(0, i * scale, mMap.NumCol * scale, i * scale);	
		}
		
		g.setColor(new Color(220, 0, 0, 100));
		

		for(int i=0; i<mMap.NumCol; i++) {
			for(int j=0; j<mMap.NumRow; j++) {
				if(mMap.get(i, j))
					g.drawOval((int)(i * scale),(int) (j * scale), (int)(scale/2), (int)(scale/2));
			}		
		}
		
		g.setColor(getColor());
		for(int i = 0; i < mPath.size() - 1; i++) {
			g.drawLine(mPath.get(i).x, mPath.get(i).y, 
					   mPath.get(i + 1).x, mPath.get(i + 1).y);
		}
	}
}
