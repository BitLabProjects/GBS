package engine;

//import java.awt.Graphic2D;
import java.util.LinkedList;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.*;

@Root
public class Core implements IScheduler {

  @Attribute(name="Name")
  private final String mName;
  @ElementList(entry="State", inline=true)
  private LinkedList<State> mStates;
  
  private Robot mRobot;
  private State mState;
  private String mNextStateName;

  public Core(@Attribute(name="Name") String name) {
    this(name, null);
  }

  public Core(String name, Robot robot) {
    mName = name;
    mRobot = robot;
    mStates = new LinkedList<State>();
    mNextStateName = null;
    mState = null;
  }
  
  @Commit
  public void commit() {
    mState = mStates.peekFirst();
    for(State state: mStates) {
      state.setCore(this);
    }
  }

  public String getName() {
    return mName;
  }
  
  public Robot getRobot() {
    return mRobot;
  }

  public void setRobot(Robot robot) {
    mRobot = robot;
  }

  private State getStateByName(String name) {
    for (State state : mStates) {
      if (state.getName().equals(name)) {
        return state;
      }
    }
    return null;
  }

  public State getState() {
    return mState;
  }

  public void setNextState(String name) {
    mNextStateName = name;
  }

  public void addState(State s) {
    mStates.add(s);
    s.setCore(this);
    if (mStates.size() == 1) {
      mState = s;
    }
  }

  public void update(float dt) {
    mNextStateName = null;

    if (mState != null) {
      mState.update(dt);
    }

    if (mNextStateName != null) {
      State newState = getStateByName(mNextStateName);
      
      if (newState == null) {
        System.out.println("Core.update: no state found with name " + mNextStateName);
      }
      else {
        System.out.println("Core.update: state changed to " + mNextStateName);
        mState.exit();
        mState = newState;
      }
      mNextStateName = null;
    }
  }

  public void drawInfo(RenderContext g) {
  }
}
