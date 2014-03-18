package scenarios.flocking;

import Robotpainters.TadpolePainter;
import awt.Board;
import engine.Box;
import engine.World;
import engine.WorldObject;
import engine.Robot;
import engine.BehaviorsScheduler;
import engine.Vector;
import utils.Color;

import java.util.Random;

public class FlockingWorld extends World {

  private int redRobots = 40;
  private int greenRobots = 40;
  private int blueRobots = 40;
  private static final TadpolePainter tp = new TadpolePainter();

  public FlockingWorld(int width, int height) {
    super(width, height);

    Color boxColor = new Color(50, 150, 255, 255);

    World world = this;
    Random randGen = new Random();
    for (int i = 0; i < 10; i++) {
      WorldObject t = new Box(world, new Vector(randGen.nextFloat() * world.getWidth() * 0.8f + world.getWidth() * 0.1f,
                                                randGen.nextFloat() * world.getHeight() * 0.8f + world.getHeight() * 0.1f),
              10 + randGen.nextFloat() * world.getWidth() * 0.2f,
              10 + randGen.nextFloat() * world.getHeight() * 0.2f);
      t.col = boxColor;
      world.addItem(t);
    }

    world.addBorderBoxes();

    //Crea i robot dei tre colori
    for (int i = 0; i < redRobots; i++) {
      Robot f = new Robot(world, new Vector(randGen.nextFloat() * (world.getWidth() - 50) + 25,
              randGen.nextFloat() * (world.getHeight() - 50) + 25), tp);
      f.col = Color.RED;
      BehaviorsScheduler s = new FlockingScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }

    for (int i = 0; i < blueRobots; i++) {
      Robot f = new Robot(world, new Vector(randGen.nextFloat() * (world.getWidth() - 50) + 25, randGen.nextFloat() * (world.getHeight() - 50) + 25), tp);
      f.col = Color.BLUE;
      BehaviorsScheduler s = new FlockingScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }

    for (int i = 0; i < greenRobots; i++) {
      Robot f = new Robot(world, new Vector(randGen.nextFloat() * (world.getWidth() - 50) + 25,
              randGen.nextFloat() * (world.getHeight() - 50) + 25), tp);
      f.col = Color.GREEN;
      BehaviorsScheduler s = new FlockingScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }
  }

  public void setCohesion(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      FlockingScheduler s = (FlockingScheduler) r.getScheduler();
      s.setCohesion(cohesion);
    }
  }

  public void setSightDistance(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      FlockingScheduler s = (FlockingScheduler) r.getScheduler();
      s.setSightDistance(cohesion);
    }
  }

  public synchronized void setRobotsCount(int count, Color col) {
    int currentCount = 0;
    if (col == Color.RED) {
      currentCount = redRobots;
    } else if (col == Color.GREEN) {
      currentCount = greenRobots;
    } else if (col == Color.BLUE) {
      currentCount = blueRobots;
    }

    while (currentCount > count) {
      currentCount--;
      for (Robot r : getRobots()) {
        if (r.col == col && !r.isDead()) {
          r.kill();
          break;
        }
      }
    }

    Random randGen = new Random();

    while (currentCount < count) {
      currentCount++;

      World engine = this;
      Robot f = new Robot(engine, new Vector(randGen.nextFloat() * (engine.getWidth() - 50) + 25,
              randGen.nextFloat() * (engine.getHeight() - 50) + 25), tp);
      f.col = col;
      BehaviorsScheduler s = new FlockingScheduler(f);
      f.setScheduler(s);
      engine.addRobot(f);
    }

    if (col == Color.RED) {
      redRobots = currentCount;
    } else if (col == Color.GREEN) {
      greenRobots = currentCount;
    } else if (col == Color.BLUE) {
      blueRobots = currentCount;
    }
  }
}
