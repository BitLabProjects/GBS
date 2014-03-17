package Robotpainters;

import engine.RenderContext;
import engine.Robot;
import engine.Vector;
//import java.awt.Graphic2D;

public class InsectPainter extends RobotPainter {

  public InsectPainter() {
  }

  @Override
  public void paintRobot(RenderContext g, Robot r) {

    g.setColor(r.getColor());


    if (r.isOnFault()) {
      g.setColor(g.getColor().darker().darker());
    } else {
      r.setAngle(r.getAngle() + (float) Math.PI / 15.0f);
    }

    //Disegna il corpo e corna
    g.push();
    g.translate(r.getPosition().x, r.getPosition().y);
    g.rotate(r.getVelocity().getAngle());

    g.drawOval(- 5, - 2, 10, 4);
    g.drawOval(+5, - 2, 4, 4);
    g.drawLine(+10, -1, +13, -2);
    g.drawLine(+10, +1, +13, +2);

    //Disegna zampe     
    Vector v = new Vector(0, 4);

    Vector s1 = v.getCopy();
    s1.rotate((float) (Math.cos(r.getAngle()) * Math.PI / 6));

    Vector s2 = v.getCopy();
    s2.rotate((float) (Math.sin(r.getAngle()) * Math.PI / 6));

    g.drawLine(3, 2, (int) s1.x + 3, (int) s1.y + 2);
    g.drawLine(-3, 2, (int) s2.x - 3, (int) s2.y + 2);

    s1.multiply(-1);//rotate((float) Math.PI );
    s2.multiply(-1);//rotate((float) Math.PI );

    g.drawLine(3, -2, (int) s1.x + 3, (int) s1.y - 2);
    g.drawLine(-3, -2, (int) s2.x - 3, (int) s2.y - 2);

    g.pop();
  }
}
