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

public class LineFollowerWorld extends World {

	
	/*
	 * 
	 * 
<?xml version="1.0" encoding="UTF-8"?>
<Core Name="LineFollower">
  <State Name="SearchTarget">
    <AlwaysTrigger>
      <SetRandomTargetAction Variable="@myTarget" />
      <ChangeStateToAction State="GoToTarget" />
    </AlwaysTrigger>
  </State>
  <State Name="GoToTarget">
    <AlwaysTrigger>
      <GoToTargetAction Target="@myTarget" />
    </AlwaysTrigger>
    <TargetReached>
      <ChangeStateToAction State="SearchTarget" />
    </TargetReached>
  </State>
</Core>
	 * 
	 * 
	 */
	
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
}
