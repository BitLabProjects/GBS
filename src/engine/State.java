package engine;

import triggers.AlwaysTrigger;
import triggers.SightTrigger;
import triggers.TimeoutTrigger;
import java.util.LinkedList;
import org.simpleframework.xml.*;

@Root
public class State {
  
  @Attribute(name="Name")
  private final String mName;
  
  @ElementListUnion({
      @ElementList(entry="AlwaysTrigger", type=AlwaysTrigger.class, inline=true),
      @ElementList(entry="SightTrigger", type=SightTrigger.class, inline=true),
      @ElementList(entry="TimeoutTrigger", type=TimeoutTrigger.class, inline=true)
   })
  private LinkedList<Trigger> mTriggers;
  
  private Core mCore;
  private boolean mFirstUpdateAfterEnterDone;
  
  public State(@Attribute(name="Name") String name) {
    mName = name;
    mTriggers = new LinkedList<Trigger>();
    mFirstUpdateAfterEnterDone = false;
  }
  
  public Core getCore() {
    return mCore;
  }
  
  public void setCore(Core core) {
    mCore = core;
    for(Trigger trigger: mTriggers) {
      trigger.setCore(mCore);
    }
  }

  public String getName() {
    return mName;
  }
  
  public void addTrigger(Trigger t) {
    mTriggers.add(t);
    t.setCore(mCore);
  }

  void update(float dt) {
    for(Trigger t: mTriggers) {
      t.update(dt, !mFirstUpdateAfterEnterDone);
    }
    mFirstUpdateAfterEnterDone = true;
  }
  
  void exit() {
    for(Trigger t: mTriggers) {
      t.unApplyIfApplied();
    }
    mFirstUpdateAfterEnterDone = false;
  }
}
