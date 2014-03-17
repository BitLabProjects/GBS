package Triggers;

import engine.Robot;
import engine.Trigger;
import engine.WorldObject;
import java.util.ArrayList;
import org.simpleframework.xml.*;

public class SightTrigger extends Trigger {
 
  public enum DistanceComparison {
    Within,
    FurtherThan
  }
  
  @Attribute(name="DistanceComparison")
  private DistanceComparison mDistanceComparison;
  @Attribute(name="Distance")
  private float mDistance;
  
  public SightTrigger(@Attribute(name="DistanceComparison") DistanceComparison distanceComparison, @Attribute(name="Distance") float distance) {
    super();
    mDistanceComparison = distanceComparison;
    mDistance = distance;
  }
  
  @Override
  public boolean verifyCondition(float dt, boolean firstVerification) {
    //Use DontMatchColor to detect only enemies
    //TODO: Parametrize the trigger to choose the items to scan for
    ArrayList<Robot> robotsInRange = getRobot().getVisibleRobots(mDistance, WorldObject.VisiblesColorFilter.DontMatchColor, getRobot().getColor());
    //System.out.println("SightTrigger: " + robotsInRange.size() + " robots within range of " + mDistance);
    boolean someRobotsDetected = (robotsInRange.size() > 0);
    switch (mDistanceComparison) {
      case FurtherThan:
        return !someRobotsDetected;
        
      case Within:
        return someRobotsDetected;
        
      default:
        throw new UnsupportedOperationException("DistanceComparison type not handled");
    }
  }
}
