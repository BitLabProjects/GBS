package scenarios.flocking;

import engine.BehaviorsScheduler;
import engine.Robot;
import engine.Behaviour;
import engine.WorldObject.*;

import java.util.ArrayList;

import library.behaviours.AttractionBehaviour;
import library.behaviours.DirectionBehaviour;
import library.behaviours.RandomWalkBehaviour;
import library.behaviours.RepulsionBehaviour;

public class FlockingScheduler extends BehaviorsScheduler {

  private RandomWalkBehaviour randomWalk;
  private AttractionBehaviour attraction;
  private RepulsionBehaviour repulsion;
  private RepulsionBehaviour repulsionOtherColor;
  private DirectionBehaviour direction;

  public FlockingScheduler(Robot robot) {
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

    repulsionOtherColor = new RepulsionBehaviour(robot, 50, VisiblesTypeFilter.Robots, VisiblesColorFilter.DontMatchColor, null);
    repulsionOtherColor.setStrength(0.1f);
    repulsionOtherColor.setIsActive(true);
  }

  @Override
  public void updateScheduler(float dt) {
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
    if (repulsionOtherColor.getIsActive()) {
      result.add(repulsionOtherColor);
    }
    if (direction.getIsActive()) {
      result.add(direction);
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
}
