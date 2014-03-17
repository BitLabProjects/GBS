package Tadpoles;

import main.WorldScenario;

public class TadpolesScenario extends WorldScenario {
  @Override
  public void create(int width, int height) {
    super.create(width, height);
    setWorld(new TadpolesWorld(width, height));
  }
}
