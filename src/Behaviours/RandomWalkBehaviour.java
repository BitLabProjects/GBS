package Behaviours;

import engine.Behaviour;
import engine.RenderContext;
import engine.Robot;
import engine.Vector;
import utils.Color;
import java.util.*;

public class RandomWalkBehaviour extends Behaviour {

  private float prevUpdateTime;
  private float delayUpdateInterval;
  private Vector forceToAdd;
  private float strength;
  static private Random randGen = new Random();

  public RandomWalkBehaviour(Robot r) {
    super(r);
    forceToAdd = new Vector();
    delayUpdateInterval = 0.0f;
    prevUpdateTime = 0.0f;
    strength = 1.0f;
  }

  public void updateBehaviour(Robot r, float dt) {

    prevUpdateTime += dt;

    if (prevUpdateTime > delayUpdateInterval) {

      forceToAdd.setRandom(r.getMaxForce() / 100);

      r.addForce(forceToAdd);

      prevUpdateTime = 0.0f;

      delayUpdateInterval = (1000 + Math.abs(randGen.nextLong() % 3000)) / 100.0f;
    }

    forceToAdd.multiply(strength);

    r.addForce(forceToAdd);
  }

  public void drawLines(RenderContext g, Robot r) {

    g.setColor(Color.BLUE);
    //g.drawString(Long.toString(delayUpdateInterval), r.pos.x, r.pos.y);     //DEBUG
    //g.drawLine((int) r.pos.x, (int) r.pos.y, (int) (r.pos.x + forceToAdd.x), (int) (r.pos.y + forceToAdd.y));
  }

  public void setStrength(float value) {
    strength = value;
  }
}
