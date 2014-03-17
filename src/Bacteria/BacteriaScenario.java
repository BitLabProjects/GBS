package Bacteria;

import main.WorldScenario;

public class BacteriaScenario extends WorldScenario {
  @Override
  public void create(int width, int height) {
    super.create(width, height);
    setWorld(new BacteriaWorld(width, height));
  }
}
