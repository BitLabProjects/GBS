package library.behaviours;

import engine.Behaviour;
import engine.Color;
import engine.RenderContext;
import engine.WorldObject;
import engine.WorldObject.VisiblesColorFilter;
import engine.WorldObject.VisiblesTypeFilter;
import engine.Robot;
import engine.Vector;
import scenarios.ant.Pheromone;

import java.util.ArrayList;

public class LeveledAttractionBehaviour extends Behaviour {

  private float dOfSight;
  private float strength;
  private VisiblesTypeFilter typeFilter;
  private VisiblesColorFilter colorFilter;
  private Color filterColor;
  private Vector pos;
  private int lv;

  public LeveledAttractionBehaviour(Robot r, float dos, VisiblesTypeFilter typeFilter, VisiblesColorFilter colorFilter, Color filterColor) {
    super(r);
    dOfSight = dos;
    strength = 1.0f;
    this.typeFilter = typeFilter;
    this.colorFilter = colorFilter;
    this.filterColor = filterColor;
  }

  public void updateBehaviour(Robot r, float dt) {

    //Tutti gli item visibili entro un certo raggio, eccetto r
    ArrayList<WorldObject> visibleItems = r.getVisibleItems(dOfSight, colorFilter, filterColor);
    if (visibleItems.size() > 0) {
      lv = -1;

      for (WorldObject i : visibleItems) {
        if (((Pheromone) i).getLevel() > lv) {
          lv = ((Pheromone) i).getLevel();
          pos = i.getPosition();
        }
      }
      pos.sub(r.getPosition());
      pos.multiply(strength);
      r.addForce(pos);
    }

//         else {
//            pos = new Vector(0, 0);
//        }
  }

  public void drawLines(RenderContext g, Robot r) {
//        g.setColor(Color.YELLOW);
//        g.drawLine((int) r.getPosition().x, (int) r.getPosition().y, (int) pos.x, (int) pos.y);
  }

  public void setStrength(float value) {
    strength = value;
  }

  public void setDistanceOfSight(float value) {
    dOfSight = value;
  }
}
