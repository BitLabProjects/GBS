package engine.robotpainters;

import engine.RenderContext;
import engine.Robot;
import engine.Vector;

public class TadpolePainter extends RobotPainter {

  public TadpolePainter() {
  }

  @Override
  public void paintRobot(RenderContext g, Robot r) {

    g.setColor(r.getColor());


    if (r.isOnFault()) {
      g.setColor(g.getColor().darker().darker());
    } else {
      r.setAngle(r.getAngle() + (float) Math.PI / 15.0f);
    }

    //Disegna il corpo
    g.drawOval((int) r.getPosition().x - 3, (int) r.getPosition().y - 3, 6, 6);

    //Disegna coda        
    Vector v = r.getVelocity().getCopy();
    v.normalize();



    Vector s1 = v.getCopy();
    s1.multiply(-1.0f);
    s1.rotate((float) (Math.sin(r.getAngle()) * Math.PI / 6.0f));

    Vector s2 = v.getCopy();
    s2.multiply(-1.0f);
    s2.rotate((float) (Math.sin(r.getAngle() + Math.PI / 6) * Math.PI / 3.0f));

    float vx = r.getPosition().x - v.x * 3;
    float vy = r.getPosition().y - v.y * 3;

    g.drawLine((int) (vx), (int) (vy), (int) (vx + s1.x * 5), (int) (vy + s1.y * 5));
    vx += s1.x * 5;
    vy += s1.y * 5;

    g.drawLine((int) (vx), (int) (vy), (int) (vx + s2.x * 5), (int) (vy + s2.y * 5));

  }
}
