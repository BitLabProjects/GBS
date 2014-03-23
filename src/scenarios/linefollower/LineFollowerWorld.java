package scenarios.linefollower;

import engine.Box;
import engine.Color;
import engine.Core;
import engine.CoreLoader;
import engine.Robot;
import engine.World;
import engine.WorldObject;
import engine.Vector;
import engine.robotpainters.TadpolePainter;

import java.util.Random;

import shortestpath.*;

public class LineFollowerWorld extends World {

	private static final TadpolePainter tp = new TadpolePainter();

	public LineFollowerWorld(int width, int height) {
		super(width, height);

		reCreateWorld();
	}

	public void reCreateWorld() {
		clearItems();
		clearRobots();

		Color boxColor = new Color(50, 150, 255, 255);

		World world = this;
		Random randGen = new Random();
		for (int i = 0; i < 30; i++) {
			WorldObject t = new Box(world, new Vector(randGen.nextFloat()
					* world.getWidth() * 0.8f + world.getWidth() * 0.1f,
					randGen.nextFloat() * world.getHeight() * 0.8f
							+ world.getHeight() * 0.1f), 10
					+ randGen.nextFloat() * world.getWidth() * 0.2f, 10
					+ randGen.nextFloat() * world.getHeight() * 0.2f);
			t.col = boxColor;
			world.addItem(t);
		}

		// world.addBorderBoxes();

		// createPath();

		// Crea i robot dei tre colori
		for (int i = 0; i < 1; i++) {
			Robot r = new Robot(world, new Vector(randGen.nextFloat()
					* (world.getWidth() - 50) + 25, randGen.nextFloat()
					* (world.getHeight() - 50) + 25), tp);
			r.col = Color.RED;
			Core c = CoreLoader.Load(getClass().getResourceAsStream("LineFollowerCore.xml"));
			c.setRobot(r);
			r.setScheduler(c);
			world.addRobot(r);
		}
	}

	// public void createPath() {
	// final int scale = 10;
	// Map map = new Map(getHeight() / scale, getWidth() / scale);
	//
	// for (WorldObject o : getItems()) {
	// if (o instanceof Box) {
	// Box b = (Box) o;
	// int rY = (int) ((b.getPosition().y - (b.height / 2)) / scale);
	// int rX = (int) ((b.getPosition().x - (b.width / 2)) / scale);
	// int rW = (int) Math.ceil((b.getPosition().x + b.width / 2 - rX
	// * scale)
	// / scale);
	// int rH = (int) Math.ceil((b.getPosition().y + b.height / 2 - rY
	// * scale)
	// / scale);
	// map.addObstacle(rY, rX, rW, rH);
	// }
	// }
	//
	// Dijkstra d = new Dijkstra(map);
	// PathWorldObject path = new PathWorldObject(this, new Vector(0, 0), map,
	// d.getPath(new Coordinates(0, 0), new Coordinates(
	// map.NumCol - 1, map.NumRow - 1)));
	//
	// addItem(path);
	// }
}
