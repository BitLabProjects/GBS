package TriggerActions;

import engine.Core;
import engine.TriggerAction;
import org.simpleframework.xml.*;

public class ChangeStateToAction extends TriggerAction {
  
  @Attribute(name="State")
  private String mStateToSet;
  
  public ChangeStateToAction(@Attribute(name="State") String stateToSet) {
    mStateToSet = stateToSet;
  }

  @Override
  public void apply(boolean firstApplication) {
    mCore.setNextState(mStateToSet);
  }

  @Override
  public void unApply() {
    
  }
}
