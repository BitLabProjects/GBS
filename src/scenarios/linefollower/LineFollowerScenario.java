package scenarios.linefollower;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import main.GbsApplication;
import main.MainMenuScenario;
import main.WorldScenario;

public class LineFollowerScenario extends WorldScenario {
	LineFollowerWorld mWorld;
	
  @Override
  public void create(int width, int height) {
    super.create(width, height);
    mWorld = new LineFollowerWorld(width, height);
    setWorld(mWorld);
  }
  
  @Override
  public void createButtons(Table table) {
	    createButton(table, "Random", new MainMenuScenario.IButtonAction() {
	        public void doAction() {
	          mWorld.reCreateWorld();
	        }
	      }).left().top();
  }
  
  
}
