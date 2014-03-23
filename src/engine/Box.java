package engine;

//import java.awt.Graphic2D;

public class Box extends WorldObject {

  public final float width, height;

  public Box(World e, Vector p, float w, float h) {
    super(e, p);
    width = w;
    height = h;
  }

  public void paint(RenderContext g) {
    g.setColor(col);
    g.fillRect((int) (position.x - width / 2), (int) (position.y - height / 2), (int) (width), (int) (height));
    g.setColor(col.darker());
    g.drawRect((int) (position.x - width / 2), (int) (position.y - height / 2), (int) (width), (int) (height));
  }

  @Override
  public Vector getNormalReaction(Robot robot, float dOfSight) {
    float dx = getPosition().x - robot.getPosition().x;
    float compx = 1.0f;
    if (dx < 0) {
      compx = -1.0f;
    }
    dx -= Math.signum(dx) * (width / 2 + robot.auraRadius);
    compx *= dx;

    float dy = getPosition().y - robot.getPosition().y;
    float compy = 1.0f;
    if (dy < 0) {
      compy = -1.0f;
    }
    dy -= Math.signum(dy) * (height / 2 + robot.auraRadius);
    compy *= dy;

    Vector v;

    //Se non compenetrain x
    if (compx > 0) {
      //Se compenetra più in x che in y
      if (compx > compy) {
        v = new Vector(dx, 0.0f);
      } else {
        v = new Vector(0.0f, dy);
      }
      //Se compenetra in x
    } else {
      //Se non compenetra in y
      if (compy > 0) {
        v = new Vector(0.0f, dy);
        //Oggetto compenetrato
      } else {
        //Restituisci la compenetrazione minore
        if (compx > compy) {
          return new Vector(-dx, 0.0f);
        } else {
          return new Vector(0.0f, -dy);
        }
      }
    }

    //Valuta se sei distante per reagire
    if (Math.abs(v.x + v.y) > dOfSight) {
      v = new Vector(0.0f, 0.0f);
    }

    return v;
  }
}
