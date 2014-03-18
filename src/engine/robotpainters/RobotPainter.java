package engine.robotpainters;

import engine.RenderContext;
import engine.Robot;
import java.awt.*;

public abstract class RobotPainter {

  public abstract void paintRobot(RenderContext g, Robot r);
}
