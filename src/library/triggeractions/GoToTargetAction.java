package library.triggeractions;

import engine.Box;
import engine.Robot;
import engine.TriggerAction;
import engine.Vector;
import engine.World;
import engine.WorldObject;

import java.util.ArrayList;

import scenarios.linefollower.PathWorldObject;
import shortestpath.Coordinates;
import shortestpath.Dijkstra;
import shortestpath.Map;

public class GoToTargetAction extends TriggerAction {

	private final int mScale = 10;
	private ArrayList<Vector> mPath;
	private int mCurrentPathPoint;
	
	public GoToTargetAction() {
		mPath = null;
	}

	@Override
	public void apply(boolean firstApplication) {
		if (mPath == null)
			createPath();
		
		Robot mRobot = mCore.getRobot();
		
		Vector currentPathPoint = mPath.get(mCurrentPathPoint).getCopy();
		Vector posToDistance = currentPathPoint.getCopy();
		posToDistance.sub(mRobot.getPosition());
		if (posToDistance.getLength() > 5) {
			posToDistance.setLength(3);
			mRobot.setVelocity(posToDistance);
		}
		else { 
			mCurrentPathPoint += 1;
		}
	}

	public void createPath() {
		World world = mCore.getRobot().getWorld();
		Map map = new Map(world.getHeight() / mScale, world.getWidth() / mScale);

		for (WorldObject o : world.getItems()) {
			if (o instanceof Box) {
				Box b = (Box) o;
				int rY = (int) ((b.getPosition().y - (b.height / 2)) / mScale);
				int rX = (int) ((b.getPosition().x - (b.width / 2)) / mScale);
				int rW = (int) Math.ceil((b.getPosition().x + b.width / 2 - rX
						* mScale)
						/ mScale);
				int rH = (int) Math.ceil((b.getPosition().y + b.height / 2 - rY
						* mScale)
						/ mScale);
				map.addObstacle(rY, rX, rW, rH);
			}
		}

		Dijkstra d = new Dijkstra(map);
		ArrayList<Coordinates> pathInMap = d.getPath(new Coordinates(0, 0), 
		                                             new Coordinates(map.NumCol - 1, map.NumRow - 1));
		
		mPath = map.getPathInWorld(pathInMap, mScale);
		
		PathWorldObject pwo = new PathWorldObject(world, new Vector(0, 0), map, mPath);

		world.addItem(pwo);
		mCurrentPathPoint = 0;
		
		//Megatrucco
		Robot mRobot = mCore.getRobot();
		mRobot.setPosition(mPath.get(mCurrentPathPoint).getCopy());
	}

	@Override
	public void unApply() {
		mPath = null;
		mCurrentPathPoint = -1;
	}
}
