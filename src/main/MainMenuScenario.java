package main;

import scenarios.ant.AntScenario;
import scenarios.bacteria.BacteriaScenario;
import scenarios.capturetheflag.CTFScenario;
import scenarios.flocking.FlockingScenario;
import scenarios.tadpoles.TadpolesScenario;
import scenarios.termites.TermitesScenario;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainMenuScenario extends Scenario {

  @Override
  public void create(int width, int height) {
    createUI();
  }

  private void createUI() {
    Table table = addTableToStage(getStage());

    final Label label = new Label("Group Behavior Simulator", GbsSkin.getSkin());
    table.add(label);
    table.row();
    
    createSetScenarioButton(table, "Ant", new IScenarioFactory() {
      public Scenario create() {
        return new AntScenario();
      }
    });
    
    createSetScenarioButton(table, "Bacteria", new IScenarioFactory() {
      public Scenario create() {
        return new BacteriaScenario();
      }
    });
    
    createSetScenarioButton(table, "Capture The Flag", new IScenarioFactory() {
      public Scenario create() {
        return new CTFScenario();
      }
    });

    createSetScenarioButton(table, "Flocking", new IScenarioFactory() {
      public Scenario create() {
        return new FlockingScenario();
      }
    });
    
    createSetScenarioButton(table, "Termites", new IScenarioFactory() {
      public Scenario create() {
        return new TermitesScenario();
      }
    });
    
    createSetScenarioButton(table, "Tadpoles", new IScenarioFactory() {
      public Scenario create() {
        return new TadpolesScenario();
      }
    });
  }

  private void createSetScenarioButton(Table table, String text, final IScenarioFactory factory) {
    createButton(table, text, new IButtonAction() {
      public void doAction() {
        GbsApplication.getApp().setScenario(factory.create());
      }
    });
  }
  
  public interface IButtonAction {
    public void doAction();
  }

  private interface IScenarioFactory {
    public Scenario create();
  }
}
