package scenarios.bacteria;

import engine.BehaviorsScheduler;
import engine.Color;
import engine.Robot;
import engine.Behaviour;
import engine.World;
import engine.WorldObject;
import engine.WorldObject.*;

import java.util.ArrayList;

import library.behaviours.AttractionBehaviour;
import library.behaviours.FollowObjectBehaviour;
import library.behaviours.RandomWalkBehaviour;
import library.behaviours.RepulsionBehaviour;

public class BacteriaScheduler extends BehaviorsScheduler {

  private RandomWalkBehaviour randomWalk;
  private RepulsionBehaviour repulsion;
  private RepulsionBehaviour boxRepulsion;
  private AttractionBehaviour foodAttraction;
  private FollowObjectBehaviour bacteriaFollow;
  private float dOfSight;
  private BacteriaSchedulerState state;

  public enum BacteriaSchedulerState {

    Wandering,
    AttractedByBacteria,
    Attracted
  }

  public BacteriaScheduler(Robot robot) {
    super(robot);

    randomWalk = new RandomWalkBehaviour(robot);
    randomWalk.setIsActive(true);

    repulsion = new RepulsionBehaviour(robot, 8, VisiblesTypeFilter.Robots, VisiblesColorFilter.MatchColor, null);
    repulsion.setIsActive(true);
    repulsion.setStrength(1.0f);

    boxRepulsion = new RepulsionBehaviour(robot, 8, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, World.boxColor);
    boxRepulsion.setIsActive(true);
    boxRepulsion.setStrength(1.0f);

    foodAttraction = new AttractionBehaviour(robot, 80, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, Color.GREEN.darker());
    foodAttraction.setIsActive(false);

    bacteriaFollow = new FollowObjectBehaviour(robot, 80, null);
    bacteriaFollow.setIsActive(false);

    dOfSight = 30;

    state = BacteriaSchedulerState.Wandering;
  }

  @Override
  public void updateScheduler(float dt) {
    switch (state) {
      case Wandering: {
        ArrayList<WorldObject> nearItems = getRobot().getVisibleItems(dOfSight, VisiblesColorFilter.MatchColor, Color.GREEN.darker());

        if (nearItems.size() > 0) {
          foodAttraction.setIsActive(true);
          getRobot().col = Color.BLUE;
          state = BacteriaSchedulerState.Attracted;
        }

        ArrayList<Robot> nearExcitedRobots = getRobot().getVisibleRobots(dOfSight, VisiblesColorFilter.MatchColor, Color.BLUE);

        if (nearExcitedRobots.size() > 0) {
          bacteriaFollow.setIsActive(true);
          bacteriaFollow.setItem(nearExcitedRobots.get(0));
          getRobot().col = Color.BLUE;
          state = BacteriaSchedulerState.AttractedByBacteria;
        }
      }
      break;

      case AttractedByBacteria: {
        ArrayList<WorldObject> nearItems = getRobot().getVisibleItems(dOfSight, VisiblesColorFilter.MatchColor, Color.GREEN.darker());

        if (nearItems.size() > 0) {
          foodAttraction.setIsActive(true);
          bacteriaFollow.setIsActive(false);
          getRobot().col = Color.BLUE;
          state = BacteriaSchedulerState.Attracted;
        }
      }
      break;

      case Attracted: {
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
    if (repulsion.getIsActive()) {
      result.add(repulsion);
    }
    if (boxRepulsion.getIsActive()) {
      result.add(boxRepulsion);
    }
    if (foodAttraction.getIsActive()) {
      result.add(foodAttraction);
    }
    if (bacteriaFollow.getIsActive()) {
      result.add(bacteriaFollow);
    }

    return result;
  }

  public void setFoodAttractionStrength(float strength) {
    foodAttraction.setStrength(strength);
    bacteriaFollow.setStrength(strength);
  }

  public void setFoodSightDistance(float dof) {
    foodAttraction.setDistanceOfSight(dof);
    bacteriaFollow.setDistanceOfSight(dof);
    dOfSight = Math.max(1, dof - 10);
  }
}
