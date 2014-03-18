package scenarios.capturetheflag;

import engine.Box;
import engine.Color;
import engine.World;
import engine.WorldObject;
import engine.Robot;
import engine.BehaviorsScheduler;
import engine.Vector;
import engine.awt.Board;
import engine.robotpainters.TadpolePainter;

import java.util.Random;

public class CTFWorld extends World {

  private int redRobots = 80;
  private int blueRobots = 40;
  private static final TadpolePainter tp = new TadpolePainter();

  public CTFWorld(int width, int height) {
    super(width, height);

    Color boxColor = new Color(50, 150, 255, 255);

    World world = this;
    Random randGen = new Random();
    for (int i = 0; i < 20; i++) {
      WorldObject t = new Box(world, new Vector(randGen.nextFloat() * world.getWidth(),
              randGen.nextFloat() * world.getWidth()),
              10 + randGen.nextFloat() * 100,
              10 + randGen.nextFloat() * 100);
      t.col = boxColor;
      world.addItem(t);
    }

    world.addBorderBoxes();

    WorldObject home = new WorldObject(world, new Vector(100, 100));
    home.col = Color.GRAY;
    world.addItem(home);

    DefenderScheduler.redCount = redRobots;

    //Crea i robot dei tre colori
    for (int i = 0; i < redRobots / 2; i++) {
      Robot f = new Robot(world, new Vector(125 + randGen.nextInt(50), world.getHeight() - 125 + randGen.nextInt(50)), tp);
      f.col = Color.RED;
      BehaviorsScheduler s = new AttackerScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }

    for (int i = 0; i < redRobots / 2; i++) {
      Robot f = new Robot(world, new Vector(world.getWidth() - 125 + randGen.nextInt(50), 125 + randGen.nextInt(50)), tp);
      f.col = Color.RED;
      BehaviorsScheduler s = new AttackerScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }

    for (int i = 0; i < blueRobots; i++) {
      Robot f = new Robot(world, new Vector(100 - 25 + randGen.nextInt(50), 100 - 25 + randGen.nextInt(50)), tp);
      f.col = Color.BLUE;
      BehaviorsScheduler s = new DefenderScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }
  }

  public void setCohesion(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      AttackerScheduler s = (AttackerScheduler) r.getScheduler();
      s.setCohesion(cohesion);
    }
  }

  public void setSightDistance(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      AttackerScheduler s = (AttackerScheduler) r.getScheduler();
      s.setSightDistance(cohesion);
    }
  }

  public void setEnemiesSightDistance(float value) {
    for (Robot r : getRobots()) {
      if (r.col != Color.BLUE) {
        continue;
      }
      DefenderScheduler s = (DefenderScheduler) r.getScheduler();
      s.setEnemiesSightDistance(value);
    }
  }

  public void setGoalSightDistance(float value) {
    for (Robot r : getRobots()) {
      if (r.col != Color.RED) {
        continue;
      }
      AttackerScheduler s = (AttackerScheduler) r.getScheduler();
      s.setGoalSightDistance(value);
    }
  }

  public synchronized void setRobotsCount(int count, Color col) {
    int currentCount = 0;
    if (col == Color.RED) {
      currentCount = redRobots;
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
      BehaviorsScheduler s = null;
      if (col == Color.RED) {
        s = new AttackerScheduler(f);
      } else if (col == Color.BLUE) {
        s = new DefenderScheduler(f);
      }
      f.setScheduler(s);
      engine.addRobot(f);
    }

    if (col == Color.RED) {
      redRobots = currentCount;
    } else if (col == Color.BLUE) {
      blueRobots = currentCount;
    }
  }
}
