package scenarios.termites;

import main.WorldScenario;

public class TermitesScenario extends WorldScenario {
  @Override
  public void create(int width, int height) {
    super.create(width, height);
    setWorld(new TermitesWorld(width, height));
  }
}
