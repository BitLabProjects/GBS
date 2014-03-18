package scenarios.capturetheflag;

import engine.BehaviorsScheduler;
import engine.Color;
import engine.Robot;
import engine.Behaviour;
import engine.Vector;
import engine.WorldObject;
import engine.WorldObject.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import library.behaviours.AttractionBehaviour;
import library.behaviours.DirectionBehaviour;
import library.behaviours.RandomWalkBehaviour;
import library.behaviours.RepulsionBehaviour;

public class AttackerScheduler extends BehaviorsScheduler {

  private RandomWalkBehaviour randomWalk;
  private AttractionBehaviour attraction;
  private RepulsionBehaviour repulsion;
  private DirectionBehaviour direction;
  private AttractionBehaviour homeAtt;
  private AttackerSchedulerState state;
  private float dOfSight;
  private WorldObject home;

  public enum AttackerSchedulerState {

    Wandering,
    GotoHome
  }

  public AttackerScheduler(Robot robot) {
    super(robot);

    randomWalk = new RandomWalkBehaviour(robot);
    randomWalk.setIsActive(true);

    attraction = new AttractionBehaviour(robot, 50, VisiblesTypeFilter.Robots, VisiblesColorFilter.MatchColor, null);
    attraction.setIsActive(true);
    attraction.setStrength(0.01f);

    direction = new DirectionBehaviour(robot, 50);
    direction.setIsActive(true);
    direction.setStrength(1 / 8.0f);

    repulsion = new RepulsionBehaviour(robot, 8, VisiblesTypeFilter.All, VisiblesColorFilter.All, null);
    repulsion.setIsActive(true);
    repulsion.setStrength(1.0f);

    homeAtt = new AttractionBehaviour(robot, 2000, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, Color.GRAY);
    homeAtt.setIsActive(false);
    homeAtt.setStrength(0.05f);

    dOfSight = 150;
    state = AttackerSchedulerState.Wandering;
  }

  @Override
  public void updateScheduler(float dt) {
    switch (state) {
      case Wandering: {
        ArrayList<WorldObject> nearItems = getRobot().getVisibleItems(dOfSight, VisiblesColorFilter.MatchColor, Color.GRAY);

        if (nearItems.size() > 0) {
          homeAtt.setIsActive(true);
          home = nearItems.get(0);
          //getRobot().col = Color.blue;
          state = AttackerSchedulerState.GotoHome;
        }
      }
      break;

      case GotoHome: {
        Vector dist = home.getCenterDistance(getRobot());
        if (dist.getLength() <= 15) {
          getRobot().getWorld().endSimulation();
          JOptionPane.showMessageDialog(CTFFrame.instance, "Reds win!", "Simulation terminated", JOptionPane.INFORMATION_MESSAGE);
        }
      }
      break;
    }
  }

  @Override
  public ArrayList<Behaviour> getActiveBehaviours() {
    ArrayList<Behaviour> result = new ArrayList<Behaviour>();

    if (randomWalk.getIsActive()) {
      result.add(randomWalk);
    }
    if (attraction.getIsActive()) {
      result.add(attraction);
    }
    if (repulsion.getIsActive()) {
      result.add(repulsion);
    }
    if (direction.getIsActive()) {
      result.add(direction);
    }
    if (homeAtt.getIsActive()) {
      result.add(homeAtt);
    }

    return result;
  }

  public void setCohesion(float cohesion) {
    attraction.setStrength(cohesion);
  }

  public void setSightDistance(float cohesion) {
    attraction.setDistanceOfSight(cohesion);
    direction.setDistanceOfSight(cohesion);
  }

  void setGoalSightDistance(float value) {
    dOfSight = value;
  }
}
