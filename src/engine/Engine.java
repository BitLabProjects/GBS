package engine;

import awt.Board;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Engine implements Runnable {

  private Thread mSimulationThread = null;
  private boolean mStopSimulation = false;
  private final int DELAY_IN_ms = 13;
  private int mStepsMultiplier = 1;
  
  protected abstract void unload();
  protected abstract boolean update(float dt);
  protected abstract void paint();
  
  public int getStepsMultiplier() {
    return mStepsMultiplier;
  }

  public void setStepsMultiplier(int value) {
    mStepsMultiplier = value;
  }
  
  public void resetSimulation() {
    endSimulation();
    mStopSimulation = false;
    mSimulationThread = new Thread(this);
    mSimulationThread.start();
  }
  
  public void endSimulation() {
    if (mSimulationThread == null)
      return;
    
    try {
      mStopSimulation = true;
      mSimulationThread.join();
      unload();
    } catch (InterruptedException ex) {
      Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public synchronized boolean updateEngine(float dt) {
    for (int i = 0; i < mStepsMultiplier; i++) {
      if (!update(dt))
        return false;
    }
    return true;
  }
  
  public void run() {
    long frameTime, beforeTime, fps;
    float lastDt = 1.0f / DELAY_IN_ms;

    beforeTime = System.currentTimeMillis();
    frameTime = beforeTime;
    fps = 0;

    while (!mStopSimulation) {
      
      if (!updateEngine(lastDt))
        mStopSimulation = true;

      paint();

      //Temporizzazione
      long timeDiff = System.currentTimeMillis() - beforeTime;
      long sleep = DELAY_IN_ms - timeDiff;

      if (sleep < 0) {
        sleep = 2;
      }
      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        System.out.println("Interrupted");
      }

      long currentMs = System.currentTimeMillis();
      lastDt = (currentMs - beforeTime) / 1000.0f;
      beforeTime = currentMs;
      
      if (beforeTime >= frameTime + 1000) {
        frameTime = beforeTime;
        //System.out.println("Fps: " + fps);
        fps = 0;
      }
      else {
        fps++;
      }
    }
  }
}
