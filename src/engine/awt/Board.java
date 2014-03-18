package engine.awt;

import engine.Engine;
import engine.World;
import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

  private BoardEngine mEngine;
  private World mWorld;
  public static final int boardWidth = 700;
  public static final int boardHeight = 700;

  public Board() {
    setBackground(Color.WHITE);
    setDoubleBuffered(true);

    Dimension dim = new Dimension(boardWidth, boardHeight);
    super.setPreferredSize(dim);
    super.setMinimumSize(dim);
    super.setMaximumSize(dim);
  }

  public World getWorld() {
    return mWorld;
  }
  
  public void setWorld(World world) {
    mWorld = world;
  }
  
  public Engine getEngine() {
    return mEngine;
  }

  @Override
  public synchronized void paint(Graphics g) {
    super.paint(g);

    //RenderContext g2d = (RenderContext) g;
    //mWorld.paint(g2d);

    //Attendi la terminazione del disegno
    Toolkit.getDefaultToolkit().sync();
    g.dispose();
  }

  @Override
  public void addNotify() {
    super.addNotify();
    if (mEngine == null) {
      mEngine = new BoardEngine(this);
      mEngine.resetSimulation();
    }
  }
}
