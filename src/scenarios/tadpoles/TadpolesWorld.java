package scenarios.tadpoles;

import engine.Core;
import engine.CoreLoader;
import engine.Robot;
import engine.Vector;
import engine.World;
import utils.Color;

import java.util.Random;

import Robotpainters.TadpolePainter;

public class TadpolesWorld extends World {

  private static final TadpolePainter tp = new TadpolePainter();
  private static final Random randGen = new Random();

  public TadpolesWorld(int width, int height) {
    super(width, height);

    addBorderBoxes();

    for (int i = 0; i < 10; i++) {
      Robot r = createRobotAtRandomPosition(Color.RED);
      Core c = CoreLoader.Load(getClass().getResourceAsStream("WandererCore.xml"));
      c.setRobot(r);
      r.setScheduler(c);
    }

    for (int i = 0; i < 1; i++) {
      Robot r = createRobotAtRandomPosition(Color.BLUE);
      Core c = CoreLoader.Load(getClass().getResourceAsStream("SoldierCore.xml"));
      c.setRobot(r);
      r.setScheduler(c);
    }
  }

  private Robot createRobotAtRandomPosition(Color color) {
    Robot r = new Robot(this, new Vector(randGen.nextFloat() * (getWidth() - 50) + 25,
            randGen.nextFloat() * (getHeight() - 50) + 25), tp);
    r.col = color;
    addRobot(r);
    return r;
  }

//  private Core createWanderingCore(Robot robot) {
//    Core core = new Core("Wanderer", robot);
//
//    State s = new State("Wander");
//    core.addState(s);
//    Trigger t = new TimeoutTrigger(5);
//    t.addAction(new WanderAction());
//    s.addTrigger(t);
//
//    return core;
//  }
//
//  private Core createSimpleSoldierCore(Robot robot) {
//    Core core = new Core("SimpleSoldier", robot);
//
//    Trigger t;
//    State s;
//
//    //Idle
//    s = new State("Idle");
//    core.addState(s);
//    
//    t = new SightTrigger(DistanceComparison.Within, 100);
//    t.addAction(new ChangeStateToAction("Follow"));
//    s.addTrigger(t);
//
//    //Follow
//    s = new State("Follow");
//    core.addState(s);
//    
//    t = new SightTrigger(DistanceComparison.Within, 30);
//    t.addAction(new ChangeStateToAction("Shoot"));
//    s.addTrigger(t);
//
//    t = new SightTrigger(DistanceComparison.FurtherThan, 150);
//    t.addAction(new ChangeStateToAction("Idle"));
//    s.addTrigger(t);
//
//    t = new AlwaysTrigger();
//    t.addAction(new WalkTowardsAction());
//    s.addTrigger(t);
//
//    //Shoot
//    s = new State("Shoot");
//    core.addState(s);
//    
//    t = new AlwaysTrigger();
//    t.addAction(new ShootAtAction());
//    s.addTrigger(t);
//
//    t = new SightTrigger(DistanceComparison.FurtherThan, 60);
//    t.addAction(new ChangeStateToAction("Follow"));
//    s.addTrigger(t);
//
//    return core;
//  }
}
