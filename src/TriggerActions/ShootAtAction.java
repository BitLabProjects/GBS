package TriggerActions;

import commonobjects.Bullet;
import engine.Core;
import engine.World;
import engine.Robot;
import engine.TriggerAction;
import engine.Vector;
import engine.WorldObject;
import java.util.ArrayList;

public class ShootAtAction extends TriggerAction {

  //TODO: Add the possibility to choose which target to shoot at, default is the enemy within range
  public ShootAtAction() {
  }

  @Override
  public void apply(boolean firstApplication) {
    if (!firstApplication)
      return;
    
    Robot mRobot = mCore.getRobot();
    ArrayList<Robot> robotsInRange = mRobot.getVisibleRobots(50, WorldObject.VisiblesColorFilter.DontMatchColor, mRobot.getColor());
    if (robotsInRange.isEmpty())
      return;
    
    Robot target = robotsInRange.get(0);
    World e = mRobot.getWorld();
    e.addItem(createBullet(mRobot, target));
  }
  
  private Bullet createBullet(Robot source, Robot target) {
    Bullet b = new Bullet(source.getWorld(), source.getPosition());
    
    Vector vel = source.getCenterDistance(target);
    vel.setLength(500);
    b.setVelocity(vel);
    
    return b;
  }

  @Override
  public void unApply() {
    
  }
}
