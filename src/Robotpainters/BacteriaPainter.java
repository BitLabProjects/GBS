package Robotpainters;

import engine.RenderContext;
import engine.Robot;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class BacteriaPainter extends RobotPainter {

  public BacteriaPainter() {
  }

  @Override
  public void paintRobot(RenderContext g, Robot r) {

    if (r.isOnFault()) {
      g.setColor(g.getColor().darker().darker());
    }

    g.setColor(r.getColor());

    g.push();
    g.translate(r.getPosition().x, r.getPosition().y);
    g.rotate(r.getVelocity().getAngle());

    g.drawOval(-4, 2, 8, 4);

    g.pop();
  }
}
