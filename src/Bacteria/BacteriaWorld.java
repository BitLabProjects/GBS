package Bacteria;

import engine.World;
import engine.WorldObject;
import engine.Robot;
import engine.BehaviorsScheduler;
import engine.Vector;
import utils.Color;

import java.util.Random;

import Robotpainters.BacteriaPainter;

public class BacteriaWorld extends World {

  private int termitesCount = 40;
  private int foodCount = 2;
  private static final BacteriaPainter tp = new BacteriaPainter();

  public BacteriaWorld(int width, int height) {
    super(width, height);

    Color boxColor = new Color(50, 150, 255, 255);

    World world = this;
    Random randGen = new Random();
//        for (int i = 0; i < 10; i++) {
//            Item t = new Box(engine, new Vector(randGen.nextFloat() * engine.getWidth(),
//                                                randGen.nextFloat() * boardHeight),
//                                     10 + randGen.nextFloat() * 100,
//                                     10 + randGen.nextFloat() * 100);
//            t.col = boxColor;
//            engine.addItem(t);
//        }

    world.addBorderBoxes();

    for (int i = 0; i < foodCount; i++) {
      WorldObject item = new WorldObject(world, new Vector(randGen.nextFloat() * (world.getWidth() - 50) + 25,
              randGen.nextFloat() * (world.getHeight() - 50) + 25));
      item.col = Color.GREEN.darker();
      world.addItem(item);
    }

    //Crea i robot dei tre colori
    for (int i = 0; i < termitesCount; i++) {
      Robot f = new Robot(world, new Vector(randGen.nextFloat() * (world.getWidth() - 50) + 25,
              randGen.nextFloat() * (world.getHeight() - 50) + 25), tp);
      f.col = Color.RED;
      BehaviorsScheduler s = new BacteriaScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }
  }

  public void setFoodAttractionStrength(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      BacteriaScheduler s = (BacteriaScheduler) r.getScheduler();
      s.setFoodAttractionStrength(cohesion);
    }
  }

  public void setFoodSightDistance(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      BacteriaScheduler s = (BacteriaScheduler) r.getScheduler();
      s.setFoodSightDistance(cohesion);
    }
  }

  public synchronized void setRobotsCount(int count, Color col) {
    int currentCount = 0;
    if (col == Color.RED) {
      currentCount = termitesCount;
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
      BehaviorsScheduler s = new BacteriaScheduler(f);
      f.setScheduler(s);
      engine.addRobot(f);
    }

    if (col == Color.RED) {
      termitesCount = currentCount;
    }
  }
}
