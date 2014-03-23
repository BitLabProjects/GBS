package engine;

import java.util.*;

public class World {

  public static final Color boxColor = new Color(50, 150, 255, 255);
  
  private int mWidth;
  private int mHeight;
  private ArrayList<Robot> robots;
  private ArrayList<WorldObject> items;
  private int deathSteps;
  private boolean isSimulationEnded;

  public World(int width, int height) {
    mWidth = width;
    mHeight = height;
    robots = new ArrayList<Robot>();
    items = new ArrayList<WorldObject>();
    deathSteps = (new Random()).nextInt(80 * 4) + 80 * 3;
    isSimulationEnded = false;
  }
  
  public int getWidth() {
    return mWidth;
  }

  public int getHeight() {
    return mHeight;
  }

  public void addRobot(Robot r) {
    robots.add(r);
  }

  public void addItem(WorldObject i) {
    items.add(i);
  }

  public void clearRobots() {
    robots.clear();
  }

  public void clearItems() {
    items.clear();
  }

  public Collection<Robot> getRobots() {
    return robots;
  }

  public Collection<WorldObject> getItems() {
    return items;
  }

  public synchronized void update(float dt) {
    ArrayList<WorldObject> deadItems = new ArrayList<WorldObject>();
    for (WorldObject o : items) {
      if (o.isDead()) {
        deadItems.add(o);
      } else {
        o.update(dt);
      }
    }
    for (Robot r : robots) {
      if (r.isDead()) {
        deadItems.add(r);
      } else {
        r.update(dt);
      }
    }

    deathSteps--;
    if (deathSteps == 0) {
      Random rand = new Random();

      deathSteps = (rand.nextInt(80 * 5) + 80 * 15);
      if (deathSteps < 5) {
        deathSteps = 5;
      }
      //robots.get(rand.nextInt(robots.size())).setOnFault(true);
    }

    for (WorldObject i : deadItems) {
      remove(i);
    }
  }

  public void paint(RenderContext g) {
    for (Robot r : robots) {
      r.paint(g);
    }
    for (WorldObject o : items) {
      o.paint(g);
    }
  }

  public void remove(WorldObject i) {
    if (!items.remove(i)) {
      robots.remove((Robot) i);
    }
  }

  public void endSimulation() {
    isSimulationEnded = true;
  }

  public boolean getIsSimulationEnded() {
    return isSimulationEnded;
  }
  
  public void addBorderBoxes() {
    //Inserisci i bordi della mappa usando le Box

    WorldObject t;
    t = new Box(this, new Vector(4, getHeight() / 2), 8, getHeight() - 1);
    t.col = boxColor;
    addItem(t);

    t = new Box(this, new Vector(getWidth() - 4, getHeight() / 2), 8, getHeight() - 1);
    t.col = boxColor;
    addItem(t);

    t = new Box(this, new Vector(getWidth() / 2, 4), getWidth() - 1, 8);
    t.col = boxColor;
    addItem(t);

    t = new Box(this, new Vector(getWidth() / 2, getHeight() - 4), getWidth() - 1, 8);
    t.col = boxColor;
    addItem(t);
  }
}
