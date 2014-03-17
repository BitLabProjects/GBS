package main;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class GbsApplication implements ApplicationListener {

  private static GbsApplication mApp;
  public static GbsApplication getApp() {
    return mApp;
  }
  
  private OrthographicCamera mCamera;
  private Scenario mCurrentScenario;

  public void create() {
    mApp = this;
    
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();

    mCamera = new OrthographicCamera(w, h);
    mCamera.translate(w / 2.0f, h / 2.0f);
    mCamera.update();

    setScenario(new MainMenuScenario());
  }

  public void resize(int width, int height) {
    //mCurrentScenario.resize(width, height);
  }

  public void render() {
    float dt = Math.max(Gdx.graphics.getDeltaTime()*1, 1.0f/60.0f);
    for(int i=0; i<1; i++) {
      mCurrentScenario.update(dt);
    }
    
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    mCurrentScenario.render(mCamera, dt);
  }

  public void pause() {
  }

  public void resume() {
  }

  public void dispose() {
    mCurrentScenario.dispose();
  }
  
  public void setScenario(Scenario newScenario) {
    if (mCurrentScenario != null) {
      mCurrentScenario.dispose();
    }
      
    int w = Gdx.graphics.getWidth();
    int h = Gdx.graphics.getHeight();
    
    mCurrentScenario = newScenario;
    mCurrentScenario.create(w, h);
    mCurrentScenario.show();
  }
}
