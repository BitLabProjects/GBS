package scenarios.capturetheflag;

import main.WorldScenario;

public class CTFScenario extends WorldScenario {
  @Override
  public void create(int width, int height) {
    super.create(width, height);
    setWorld(new CTFWorld(width, height));
  }
}
