package Flocking;

import main.WorldScenario;

public class FlockingScenario extends WorldScenario {
  @Override
  public void create(int width, int height) {
    super.create(width, height);
    setWorld(new FlockingWorld(width, height));
  }
}
