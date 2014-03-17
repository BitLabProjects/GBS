package Behaviours;

import engine.Behaviour;
import engine.RenderContext;
import engine.Robot;
import engine.Vector;
import java.awt.*;
import java.util.Random;

public class DropPheromoneBehaviour extends Behaviour {

  private float prevUpdateTime;
  private float delayUpdateInterval;
  private Vector offset;
  private static final Random rand = new Random();

  public DropPheromoneBehaviour(Robot r, Vector offset) {

    super(r);
    this.offset = offset;
    prevUpdateTime = 0.0f;
    delayUpdateInterval = rand.nextInt(10);
  }

  public void updateBehaviour(Robot r, float dt) {

    prevUpdateTime += dt;

    if (prevUpdateTime > delayUpdateInterval) {

      delayUpdateInterval = 10.0f + rand.nextInt(10);
      prevUpdateTime = 0.0f;

      Vector v = offset.getCopy();
      v.rotate(r.getVelocity().getAngle());
      v.add(r.getPosition());

      r.dropPheromone(v);
    }
  }

  public void drawLines(RenderContext g, Robot o) {
  }
}
