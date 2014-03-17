package engine;

import utils.Color;
import java.util.*;

public class WorldObject {

  public Color col;
  protected Vector position = new Vector();
  protected World mEngine;
  protected boolean dead;
  protected float auraRadius = 3.0f;

  public WorldObject(World e, Vector p) {
    col = Color.BLACK;
    position = p;
    mEngine = e;
    dead = false;
  }

  public WorldObject(World e, Vector p, Color c) {
    this(e, p);
    col = c;
  }

  public boolean isDead() {
    return dead;
  }

  public World getWorld() {
    return mEngine;
  }

  public Vector getPosition() {
    return position.getCopy();
  }

  public void setPosition(Vector p) {
    position = p;
  }

  public Color getColor() {
    return col;
  }

  public void kill() {
    dead = true;
  }

  public Vector getCenterDistance(WorldObject o) {
    Vector diff = o.getPosition();
    diff.sub(getPosition());

    return diff;
  }

  public Vector getNormalReaction(Robot o, float dOfSight) {
    Vector diff = getPosition();
    diff.sub(o.getPosition());

    float len = diff.getLength();
    if (len < 0.01) {
      len = 0.01f;
    }
    if (len - auraRadius - o.auraRadius > dOfSight) {
      diff.x = 0.0f;
      diff.y = 0.0f;
    } else {
      diff.multiply(1.0f - (auraRadius + o.auraRadius) / len);
      if (len < auraRadius + o.auraRadius) {
        diff.multiply(-1.0f);
      }
    }
    return diff;
  }

  public enum VisiblesColorFilter {
    All,
    MatchColor,
    DontMatchColor
  }

  public enum VisiblesTypeFilter {

    All,
    Robots,
    Items
  }

  public ArrayList<Vector> getReactionsFromVisibles(float dOfSight, VisiblesTypeFilter typeFilter, VisiblesColorFilter colorFilter, Color filterColor) {

    ArrayList<Vector> distances = new ArrayList<Vector>();

    if (filterColor == null) {
      filterColor = col;
    }

    if (typeFilter == VisiblesTypeFilter.All || typeFilter == VisiblesTypeFilter.Robots) {
      for (Robot r : mEngine.getRobots()) {
        if (r.equals(this) || r.dead) {
          continue;
        }

        if (colorFilter == VisiblesColorFilter.MatchColor) {
          if (!r.col.equals(filterColor)) {
            continue;
          }
        } else if (colorFilter == VisiblesColorFilter.DontMatchColor) {
          if (r.col.equals(filterColor)) {
            continue;
          }
        }

        Vector v = r.getNormalReaction((Robot) this, dOfSight);
        float len = v.getLength();

        if (len > 0.01) {
          len = (dOfSight * dOfSight - len * len) / (len * len);

          v.multiply(-1 * len);
        }
        distances.add(v);
      }
    }

    if (typeFilter == VisiblesTypeFilter.All || typeFilter == VisiblesTypeFilter.Items) {
      for (WorldObject i : mEngine.getItems()) {

        if (i.equals(this) || i.dead) {
          continue;
        }

        if (colorFilter == VisiblesColorFilter.MatchColor) {
          if (!i.col.equals(filterColor)) {
            continue;
          }
        } else if (colorFilter == VisiblesColorFilter.DontMatchColor) {
          if (i.col.equals(filterColor)) {
            continue;
          }
        }

        Vector v = i.getNormalReaction((Robot) this, dOfSight);
        float len = v.getLength();

        if (len > 0.01) {
          len = (dOfSight * dOfSight - len * len) / (len * len);

          v.multiply(-1 * len);
        }
        distances.add(v);

      }
    }
    return distances;
  }

  public ArrayList<Robot> getVisibleRobots(float dOfSight, VisiblesColorFilter colorFilter, Color filterColor) {

    ArrayList<Robot> robots = new ArrayList<Robot>();

    if (filterColor == null) {
      filterColor = col;
    }

    for (Robot r : mEngine.getRobots()) {
      if (r.equals(this) || r.dead) {
        continue;
      }

      if (colorFilter == VisiblesColorFilter.MatchColor) {
        if (!r.col.equals(filterColor)) {
          continue;
        }
      } else if (colorFilter == VisiblesColorFilter.DontMatchColor) {
        if (r.col.equals(filterColor)) {
          continue;
        }
      }

      Vector v = r.getNormalReaction((Robot) this, dOfSight);
      float len = v.getLength();

      if (len > 0.01) {
        robots.add(r);
      }
    }

    return robots;
  }

  public ArrayList<WorldObject> getVisibleItems(float dOfSight, VisiblesColorFilter colorFilter, Color filterColor) {

    ArrayList<WorldObject> items = new ArrayList<WorldObject>();

    if (filterColor == null) {
      filterColor = col;
    }

    for (WorldObject i : mEngine.getItems()) {
      if (i.equals(this) || i.dead) {
        continue;
      }

      if (colorFilter == VisiblesColorFilter.MatchColor) {
        if (!i.col.equals(filterColor)) {
          continue;
        }
      } else if (colorFilter == VisiblesColorFilter.DontMatchColor) {
        if (i.col.equals(filterColor)) {
          continue;
        }
      }

      Vector v = i.getNormalReaction((Robot) this, dOfSight);
      float len = v.getLength();

      if (len > 0.01) {
        items.add(i);
      }
    }

    return items;
  }

  public ArrayList<WorldObject> getVisibles(float dOfSight, VisiblesTypeFilter typeFilter, VisiblesColorFilter colorFilter, Color filterColor) {

    ArrayList<WorldObject> result = new ArrayList<WorldObject>();
    if (typeFilter == VisiblesTypeFilter.All || typeFilter == VisiblesTypeFilter.Robots) {
      result.addAll(getVisibleRobots(dOfSight, colorFilter, filterColor));
    }

    if (typeFilter == VisiblesTypeFilter.All || typeFilter == VisiblesTypeFilter.Items) {
      result.addAll(getVisibleItems(dOfSight, colorFilter, filterColor));
    }

    return result;
  }

  public void update(float dt) {
  }

  public void paint(RenderContext g) {
    g.setColor(col);
    g.fillOval((int) position.x - 2, (int) position.y - 2, 4, 4);                                 //Disegna il robot
  }
}
