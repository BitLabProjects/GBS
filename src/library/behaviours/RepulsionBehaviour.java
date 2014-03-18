package library.behaviours;

import engine.Behaviour;
import engine.Color;
import engine.RenderContext;
import engine.WorldObject.VisiblesColorFilter;
import engine.WorldObject.VisiblesTypeFilter;
import engine.Robot;
import engine.Vector;

import java.util.ArrayList;

public class RepulsionBehaviour extends Behaviour {

  private float dOfSight;
  private float strength;
  private VisiblesTypeFilter typeFilter;
  private VisiblesColorFilter colorFilter;
  private Color filterColor;
  private Vector force;

  public RepulsionBehaviour(Robot r, float dos, VisiblesTypeFilter typeFilter, VisiblesColorFilter colorFilter, Color filterColor) {
    super(r);
    this.dOfSight = dos;
    this.strength = 1.0f;
    this.typeFilter = typeFilter;
    this.colorFilter = colorFilter;
    this.filterColor = filterColor;
  }

  public void updateBehaviour(Robot r, float dt) {
    //Distanza visiva, considera gli item/robot a seconda del filtro
    ArrayList<Vector> visibles;
    visibles = r.getReactionsFromVisibles(dOfSight, typeFilter, colorFilter, filterColor);

    force = new Vector(0.0f, 0.0f);
    for (Vector v : visibles) {
      force.add(v);
    }

    force.multiply(strength);

    r.addForce(force);
  }

  public void drawLines(RenderContext g, Robot r) {
  }

  public void setStrength(float value) {
    strength = value;
  }

  public void setDistanceOfSight(float value) {
    dOfSight = value;
  }
}
