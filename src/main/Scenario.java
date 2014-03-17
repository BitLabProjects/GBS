package main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.esotericsoftware.tablelayout.Cell;

public class Scenario {
  
  private Stage mStage;
  
  public Scenario() {
    mStage = new Stage();
  }
  
  public Stage getStage() {
    return mStage;
  }
  
  public void create(int width, int height) {
    
  }
  
  //public void resize(int width, int height) {
  //  mStage.setViewport(width, height, true);
  //}
  
  public void show() {
    Gdx.input.setInputProcessor(mStage);
  }
  
  public void dispose() {
    mStage.dispose();
    mStage = null;
  }
  
  public void update(float dt) {
    mStage.act(dt);
  }
  
  public void render(Camera camera, float dt) {
    mStage.draw();
    Table.drawDebug(mStage);
  }
  
public static Table addTableToStage(Stage stage) {
    Table table = new Table();
    table.debug();
    table.setFillParent(true);
    stage.addActor(table);
    return table;
  }
  
  public static Cell createButton(Table table, String text, final MainMenuScenario.IButtonAction action) {
    final TextButton button;
    button = new TextButton(text, GbsSkin.getSkin());
    Cell c = table.add(button).fill().padTop(10);
    table.row();

    button.addListener(new ChangeListener() {
      public void changed(ChangeListener.ChangeEvent event, Actor actor) {
        button.setChecked(false);
        action.doAction();
      }
    });
    
    return c;
  }
}
