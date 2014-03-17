package awt;

import engine.Engine;

public class BoardEngine extends Engine {
  
  private Board mBoard;
  
  public BoardEngine(Board board) {
    mBoard = board;
  }

  @Override
  protected void unload() {
    mBoard.setWorld(null);
  }

  @Override
  protected boolean update(float dt) {
    mBoard.getWorld().update(dt);
    return !mBoard.getWorld().getIsSimulationEnded();
  }

  @Override
  protected void paint() {
    mBoard.repaint();
  }
  
}
