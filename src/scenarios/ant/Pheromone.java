package scenarios.ant;

import engine.Color;
import engine.RenderContext;
import engine.World;
import engine.WorldObject;
import engine.Vector;

public class Pheromone extends WorldObject {

  private int lev = 5000;

  public Pheromone(World e, Vector p) {
    super(e, p);
    col = new Color(255, 255, 0, 100);
  }

  public int getLevel() {

    return lev;
  }

  @Override
  public void update(float dt) {
    if (--lev <= 0) {
      kill();
    }
  }

  @Override
  public void paint(RenderContext g) {

    g.setColor(col);

    int size = 4;

    if (lev > 50) {
      size = 6;
    }

    if (lev > 100) {
      size = 8;
    }
    if (lev > 200) {
      size = 10;
    }

    g.fillOval((int) position.x - size / 2, (int) position.y - size / 2, size, size);
  }
}
