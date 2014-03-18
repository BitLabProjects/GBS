package library.behaviours;

import engine.Behaviour;
import engine.Color;
import engine.RenderContext;
import engine.WorldObject;
import engine.WorldObject.VisiblesColorFilter;
import engine.WorldObject.VisiblesTypeFilter;
import engine.Robot;
import engine.Vector;

import java.util.ArrayList;

public class AttractionBehaviour extends Behaviour {

  private float dOfSight;
  private float strength;
  private VisiblesTypeFilter typeFilter;
  private VisiblesColorFilter colorFilter;
  private Color filterColor;
  private Vector pos;

  public AttractionBehaviour(Robot r, float dos, VisiblesTypeFilter typeFilter, VisiblesColorFilter colorFilter, Color filterColor) {
    super(r);
    dOfSight = dos;
    strength = 1.0f;
    this.typeFilter = typeFilter;
    this.colorFilter = colorFilter;
    this.filterColor = filterColor;
  }

  public void updateBehaviour(Robot r, float dt) {
    //Tutti i robot visibili entro un certo raggio, eccetto r
    ArrayList<WorldObject> visibles = r.getVisibles(dOfSight, typeFilter, colorFilter, filterColor);

    pos = new Vector(0.0f, 0.0f);
    for (WorldObject i : visibles) {
      pos.add(i.getPosition());
    }

    if (visibles.size() > 0) {
      pos.multiply(1.0f / visibles.size());
      pos.sub(r.getPosition());
      pos.multiply(strength);

      r.addForce(pos);
    }
  }

  public void drawLines(RenderContext g, Robot r) {
//        g.setColor(Color.GREEN);
//        g.drawLine((int) r.getPosition().x, (int) r.getPosition().y, (int) pos.x, (int) pos.y);
  }

  public void setStrength(float value) {
    strength = value;
  }

  public void setDistanceOfSight(float value) {
    dOfSight = value;
  }
}
