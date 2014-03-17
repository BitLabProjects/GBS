package Behaviours;

import engine.Behaviour;
import engine.RenderContext;
import engine.WorldObject;
import engine.Robot;
import engine.Vector;
import java.awt.*;

public class CarryItemBehaviour extends Behaviour {

  private WorldObject item;
  private Vector offset;

  public CarryItemBehaviour(Robot r, WorldObject item, Vector offset) {
    super(r);
    this.item = item;
    this.offset = offset;
  }

  public void updateBehaviour(Robot r, float dt) {
    if (item != null) {
      Vector v = offset.getCopy();
      v.rotate(r.getVelocity().getAngle());
      v.add(r.getPosition());
      item.setPosition(v);
    }
  }

  public void drawLines(RenderContext g, Robot o) {
  }

  public void setItem(WorldObject item) {
    this.item = item;
  }
}
