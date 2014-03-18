package library.behaviours;

import engine.Behaviour;
import engine.RenderContext;
import engine.WorldObject;
import engine.Robot;
import engine.Vector;
import java.awt.*;

public class FollowObjectBehaviour extends Behaviour {

  private float dOfSight;
  private float strength;
  private WorldObject item;

  public FollowObjectBehaviour(Robot r, float dos, WorldObject item) {
    super(r);
    dOfSight = dos;
    strength = 1.0f;
    this.item = item;
  }

  public void updateBehaviour(Robot r, float dt) {
    if (item != null) {
      Vector pos = item.getPosition();
      pos.sub(r.getPosition());
      pos.multiply(strength);

      r.addForce(pos);
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

  public void setItem(WorldObject item) {
    this.item = item;
  }
}
