package library.behaviours;

import engine.Behaviour;
import engine.RenderContext;
import engine.WorldObject.VisiblesColorFilter;
import engine.Robot;
import engine.Vector;
import java.awt.*;
import java.util.ArrayList;

public class DirectionBehaviour extends Behaviour {

  private float dOfSight;
  private float strength;

  public DirectionBehaviour(Robot r, float dos) {
    super(r);
    dOfSight = dos;
    strength = 1.0f;
  }

  public void updateBehaviour(Robot r, float dt) {
    //Tutti i robot visibili entro un certo raggio, eccetto r
    ArrayList<Robot> visibles = r.getVisibleRobots(dOfSight, VisiblesColorFilter.MatchColor, null);

    Vector vel = new Vector(0.0f, 0.0f);
    for (Robot o : visibles) {
      vel.add(o.getVelocity());
    }

    if (visibles.size() > 0) {
      vel.multiply(1.0f / visibles.size());
      vel.sub(r.getVelocity());
      vel.multiply(strength);

      r.addForce(vel);
    }
  }

  public void drawLines(RenderContext g, Robot o) {
  }

  public void setStrength(float value) {
    strength = value;
  }

  public void setDistanceOfSight(float value) {
    dOfSight = value;
  }
}
