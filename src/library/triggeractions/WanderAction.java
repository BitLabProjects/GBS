package library.triggeractions;

import engine.Robot;
import engine.TriggerAction;
import engine.Vector;

public class WanderAction extends TriggerAction {
  
  public WanderAction() {
  }

  @Override
  public void apply(boolean firstApplication) {
    Vector v = new Vector();
    v.setRandom(50);
    
    Robot mRobot = mCore.getRobot();
    mRobot.setVelocity(v);
  }

  @Override
  public void unApply() {
    
  }
}
