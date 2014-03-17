package engine;

//import java.awt.Graphic2D;

public interface IScheduler {
  void update(float dt);
  //TODO Find a better way of printing
  void drawInfo(RenderContext g);
}
