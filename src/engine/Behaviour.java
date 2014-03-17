package engine;

import java.awt.*;

public abstract class Behaviour {

  private Robot robot;
  private boolean isActive;

  public Behaviour(Robot r) {
    robot = r;
    isActive = false;
  }

  public Robot getRobot() {
    return robot;
  }

  public void setIsActive(boolean value) {
    isActive = value;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public void update(Robot r, float dt) {
    if (isActive) {
      updateBehaviour(r, dt);
    }
  }

  public abstract void updateBehaviour(Robot r, float dt);

  public abstract void drawLines(RenderContext g, Robot r);
}
