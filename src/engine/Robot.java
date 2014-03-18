package engine;

import Robotpainters.RobotPainter;
import scenarios.ant.Pheromone;
import utils.Color;

import java.util.*;

public class Robot extends MovableObject {

  protected IScheduler scheduler;
  static Random rand = new Random();
  private boolean isOnFault = false;
  private RobotPainter painter;
  private float ang; //TODO: Move in a more appropriate location (The painter?)

  public Robot(World e, Vector p, RobotPainter rp) {
    super(e, p);
    painter = rp;
    ang = (float) (rand.nextFloat() * Math.PI);
  }

  public Robot(World e, Vector p, Color c) {
    super(e, p, c);
  }

  public void setScheduler(IScheduler s) {
    scheduler = s;
  }

  public float getAngle() {
    return ang;
  }

  public void setAngle(float a) {
    ang = a;
  }

  @Override
  public void update(float dt) {
    if (!isOnFault) {
      scheduler.update(dt);
      super.update(dt);
    }
  }

  //TODO Remove the dependency on awt for painting
  @Override
  public void paint(RenderContext g) {
    painter.paintRobot(g, this);
    scheduler.drawInfo(g);
  }

  public IScheduler getScheduler() {
    return scheduler;
  }

  public boolean isOnFault() {
    return isOnFault;
  }

  public void setOnFault(boolean value) {
    isOnFault = value;
  }

  public void dropPheromone(Vector p) {
    mEngine.addItem(new Pheromone(mEngine, p));
  }
}
