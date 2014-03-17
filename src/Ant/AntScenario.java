package Ant;

import main.WorldScenario;

public class AntScenario extends WorldScenario {
  @Override
  public void create(int width, int height) {
    super.create(width, height);
    setWorld(new AntWorld(width, height));
  }
}
