package main;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import engine.RenderContext;
import engine.World;

public class WorldScenario extends Scenario {
  private World mWorld;
  
  public void setWorld(World w) {
    mWorld = w;
  }
  
  @Override
  public void create(int width, int height) {
    Table table = Scenario.addTableToStage(getStage());
    
    createButton(table, "Back", new MainMenuScenario.IButtonAction() {
          public void doAction() {
            GbsApplication.getApp().setScenario(new MainMenuScenario());
          }
        }).left().top();
    
    createButtons(table);
  }
  
  public void createButtons(Table table) {
	  
  }
  
  @Override
  public void update(float dt) {
    super.update(dt);
    mWorld.update(dt);
  }
  
  @Override
  public void render(Camera camera, float dt) {
    super.render(camera ,dt);
    RenderContext rc = new RenderContext(camera.combined);
    mWorld.paint(rc);
  }
}
