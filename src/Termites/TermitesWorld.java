package Termites;

import engine.BehaviorsScheduler;
import engine.Robot;
import engine.Vector;
import engine.World;
import engine.WorldObject;
import utils.Color;

import java.util.Random;

import Robotpainters.InsectPainter;

public class TermitesWorld extends World {
  
  private int termitesCount = 40;
  private int foodCount = 250;
  private static final InsectPainter tp = new InsectPainter();
  
  public TermitesWorld(int width, int height) {
    super(width, height);
    
    Random randGen = new Random();
//        for (int i = 0; i < 10; i++) {
//            Item t = new Box(engine, new Vector(randGen.nextFloat() * engine.getWidth(),
//                                                randGen.nextFloat() * boardHeight),
//                                     10 + randGen.nextFloat() * 100,
//                                     10 + randGen.nextFloat() * 100);
//            t.col = boxColor;
//            engine.addItem(t);
//        }

    addBorderBoxes();

    for (int i = 0; i < foodCount; i++) {
      WorldObject item = new WorldObject(this, new Vector(randGen.nextFloat() * (this.getWidth() - 50) + 25,
              randGen.nextFloat() * (getHeight() - 50) + 25));
      item.col = Color.GREEN.darker();
      addItem(item);
    }

    //Crea i robot dei tre colori
    for (int i = 0; i < termitesCount; i++) {
      Robot f = new Robot(this, new Vector(randGen.nextFloat() * (this.getWidth() - 50) + 25,
              randGen.nextFloat() * (getHeight() - 50) + 25), tp);
      f.col = Color.RED;
      BehaviorsScheduler s = new Termites.TermitesScheduler(f);
      f.setScheduler(s);
      addRobot(f);
    }
  }
  
  public void setFoodAttractionStrength(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      TermitesScheduler s = (TermitesScheduler) r.getScheduler();
      s.setFoodAttractionStrength(cohesion);
    }
  }

  public void setFoodSightDistance(float cohesion, Color col) {
    for (Robot r : getRobots()) {
      if (r.col != col) {
        continue;
      }
      TermitesScheduler s = (TermitesScheduler) r.getScheduler();
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

      World world = this;
      Robot f = new Robot(world, new Vector(randGen.nextFloat() * (world.getWidth() - 50) + 25,
              randGen.nextFloat() * (world.getHeight() - 50) + 25), tp);
      f.col = col;
      BehaviorsScheduler s = new TermitesScheduler(f);
      f.setScheduler(s);
      world.addRobot(f);
    }

    if (col == Color.RED) {
      termitesCount = currentCount;
    }
  }
}
