package library.triggers;

import engine.Trigger;

public class AlwaysTrigger extends Trigger {

  public AlwaysTrigger() {
    super();
  }

  @Override
  public boolean verifyCondition(float dt, boolean firstVerification) {
    return true;
  }
}
