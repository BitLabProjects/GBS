package CommonObjects;

import engine.World;
import engine.Vector;
import engine.MovableObject;
import engine.RenderContext;
import utils.Color;
//import java.awt.Graphic2D;

public class Bullet extends MovableObject {
  public Bullet(World e, Vector p) {
    super(e, p, Color.RED);
  }
  
  @Override
  public void paint(RenderContext g) {
    g.setColor(col);
    Vector v = getVelocity();
    v.normalize();
    v.multiply(-5);
    v.add(position);
    g.drawLine((int) position.x, (int) position.y, (int) v.x, (int) v.y);
  }
}
