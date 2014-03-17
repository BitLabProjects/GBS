package Ant;

import engine.Box;
import engine.World;
import engine.WorldObject;
import engine.Robot;
import engine.BehaviorsScheduler;
import engine.Vector;
import Robotpainters.InsectPainter;
import utils.Color;
import java.util.Random;

public class AntWorld extends World {

  private int antCount = 40;
  private int foodCount = 600;
  private static final InsectPainter ip = new InsectPainter();

  public AntWorld(int width, int height) {
    super(width, height);

    Color boxColor = new Color(50, 150, 255, 255);

    World world = this;
    Random randGen = new Random();

    WorldObject t = new Box(world, new Vector(350, 200), 500, 200);
    t.col = boxColor;
    world.addItem(t);

    t = new Box(world, new Vector(450, 500), 500, 150);
    t.col = boxColor;
    world.addItem(t);



    world.addBorderBoxes();

    WorldObject item = new WorldObject(world, new Vector(50, 50));
    item.col = Color.GRAY.darker();
    world.addItem(item);

    for (int i = 0; i < foodCount; i++) {
      item = new WorldObject(world, new Vector(world.getWidth() - 25 - randGen.nextInt(75), world.getHeight() - 25 - randGen.nextInt(75)));
      item.col = Color.GREEN.darker();
      world.addItem(item);
    }

    for (int i = 0; i < antCount; i++) {
      Robot r = new Robot(world, new Vector(randGen.nextInt(25) + 25, randGen.nextInt(25) + 25), ip);
      r.col = Color.RED;
      BehaviorsScheduler s = new AntScheduler(r);
      r.setScheduler(s);
      world.addRobot(r);
    }
  }

  public void setFoodAttractionStrength(float strength) {
    for (Robot r : getRobots()) {
      AntScheduler s = (AntScheduler) r.getScheduler();
      s.setFoodAttractionStrength(strength);
    }
  }

  public void setPheromoneAttractionStrength(float strength) {
    for (Robot r : getRobots()) {
      AntScheduler s = (AntScheduler) r.getScheduler();
      s.setPheromoneAttractionStrength(strength);
    }
  }

  public void setHomeAttractionStrength(float strength) {
    for (Robot r : getRobots()) {
      AntScheduler s = (AntScheduler) r.getScheduler();
      s.setHomeAttractionStrength(strength);
    }
  }

  public void setFoodSightDistance(float distance) {
    for (Robot r : getRobots()) {
      AntScheduler s = (AntScheduler) r.getScheduler();
      s.setFoodSightDistance(distance);
    }
  }

  public void setPheromoneSightDistance(float distance) {
    for (Robot r : getRobots()) {
      AntScheduler s = (AntScheduler) r.getScheduler();
      s.setPheromoneSightDistance(distance);
    }
  }

  public void setHomeSightDistance(float distance) {
    for (Robot r : getRobots()) {
      AntScheduler s = (AntScheduler) r.getScheduler();
      s.setHomeSightDistance(distance);
    }
  }

  public synchronized void setRobotsCount(int count, Color col) {
    int currentCount = 0;
    if (col == Color.RED) {
      currentCount = antCount;
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
              randGen.nextFloat() * (engine.getHeight() - 50) + 25), ip);
      f.col = col;
      BehaviorsScheduler s = new AntScheduler(f);
      f.setScheduler(s);
      engine.addRobot(f);
    }

    if (col == Color.RED) {
      antCount = currentCount;
    }
  }
}
