package engine;

import java.util.LinkedList;

import library.triggeractions.ChangeStateToAction;
import library.triggeractions.ShootAtAction;
import library.triggeractions.WalkTowardsAction;
import library.triggeractions.WanderAction;

import org.simpleframework.xml.*;

@Root
public abstract class Trigger {
  @ElementListUnion({
      @ElementList(entry="ChangeStateToAction", type=ChangeStateToAction.class, inline=true),
      @ElementList(entry="ShootAtAction", type=ShootAtAction.class, inline=true),
      @ElementList(entry="WalkTowardsAction", type=WalkTowardsAction.class, inline=true),
      @ElementList(entry="WanderAction", type=WanderAction.class, inline=true)
   })
  private LinkedList<TriggerAction> mActions;
  private Core mCore;
  private boolean mIsApplied;
  
  public Trigger() {
    mActions = new LinkedList<TriggerAction>();
    mIsApplied = false;
  }
  
  public void setCore(Core core) {
    mCore = core;
    for(TriggerAction action: mActions) {
      action.setCore(mCore);
    }
  }
  
  public void addAction(TriggerAction action) {
    mActions.add(action);
    action.setCore(mCore);
  }
  
  protected Robot getRobot() {
    return mCore.getRobot();
  }
  
  public abstract boolean verifyCondition(float dt, boolean firstVerification);

  public void update(float dt, boolean isFirstUpdateAfterStateEnter) {
    if (verifyCondition(dt, isFirstUpdateAfterStateEnter)) {
      //System.out.println("Trigger.update: apply of " + this.getClass().getName());
      applyAll(!mIsApplied);
      mIsApplied = true;
    }
    else {
      unApplyIfApplied();
    }
  }
  
  public void unApplyIfApplied() {
    if (mIsApplied) {
      //System.out.println("Trigger.update: unapply of " + this.getClass().getName());
      unApplyAll();
      mIsApplied = false;
    }
  }
  
  private void applyAll(boolean firstApplication) {
    for(TriggerAction action : mActions) {
      action.apply(firstApplication);
    }
  }
  
  private void unApplyAll() {
    for(TriggerAction action : mActions) {
      action.unApply();
    }
  }
}
