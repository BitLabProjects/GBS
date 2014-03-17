package TriggerActions;

import engine.Core;
import engine.Robot;
import engine.TriggerAction;
import engine.Vector;
import engine.WorldObject;
import java.util.ArrayList;

public class WalkTowardsAction extends TriggerAction {

  //TODO: Add the possibility to choose which target to follow, default is the enemy within range
  public WalkTowardsAction() {
  }

  @Override
  public void apply(boolean firstApplication) {
    Robot mRobot = mCore.getRobot();
    ArrayList<Robot> robotsInRange = mRobot.getVisibleRobots(200, WorldObject.VisiblesColorFilter.DontMatchColor, mRobot.getColor());
    if (robotsInRange.isEmpty())
      return;
    
    Robot target = robotsInRange.get(0);
    Vector vel = mRobot.getCenterDistance(target);
    vel.setLength(55);
    mRobot.setVelocity(vel);
  }

  @Override
  public void unApply() {
    Robot mRobot = mCore.getRobot();
    mRobot.setVelocity(new Vector());
  }
}
