package library.triggers;

import engine.Trigger;
import org.simpleframework.xml.*;

public class TimeoutTrigger extends Trigger {
  
  @Attribute(name="Seconds")
  private float mTimeoutSeconds;
  private float mTime;

  public TimeoutTrigger(@Attribute(name="Seconds") float timeoutSeconds) {
    super();
    mTimeoutSeconds = timeoutSeconds;
  }
  
  @Override
  public boolean verifyCondition(float dt, boolean firstVerification) {
    mTime += dt;
    
    if (firstVerification)
      return true;
    
    if (mTime >= mTimeoutSeconds) {
      mTime -= mTimeoutSeconds;
      return true;
    }
    return false;
  }
}
