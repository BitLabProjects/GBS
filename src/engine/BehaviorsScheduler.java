package engine;

//import java.awt.Graphic2D;
import java.util.ArrayList;

public abstract class BehaviorsScheduler implements IScheduler {

  private Robot robot;

  public BehaviorsScheduler(Robot r) {
    robot = r;
  }

  public void update(float dt) {
    updateScheduler(dt);

    ArrayList<Behaviour> activeBehaviours = getActiveBehaviours();
    for (Behaviour bh : activeBehaviours) {
      bh.update(robot, dt);
    }
  }
  
  public void drawInfo(RenderContext g) {
    ArrayList<Behaviour> ab = getActiveBehaviours();
    for (Behaviour b : ab) {
      b.drawLines(g, robot);
    }
  }

  public abstract ArrayList<Behaviour> getActiveBehaviours();

  public abstract void updateScheduler(float dt);

  public Robot getRobot() {
    return robot;
  }
}
