package Ant;

import Behaviours.PheromoneAttractionBehaviour;
import Behaviours.RepulsionBehaviour;
import Behaviours.AttractionBehaviour;
import Behaviours.RandomWalkBehaviour;
import Behaviours.CarryItemBehaviour;
import Behaviours.DropPheromoneBehaviour;
import engine.BehaviorsScheduler;
import engine.Robot;
import engine.Behaviour;
import engine.Vector;
import engine.World;
import engine.WorldObject;
import engine.WorldObject.*;
import utils.Color;
import java.util.ArrayList;

public class AntScheduler extends BehaviorsScheduler {

  private RepulsionBehaviour otherRepulsion;
  private RepulsionBehaviour boxRepulsion;
  private RandomWalkBehaviour randomWalk;
  private DropPheromoneBehaviour dropPher;
  private PheromoneAttractionBehaviour pherAtt;
  private AttractionBehaviour foodAtt;
  private CarryItemBehaviour carryItem;
  private AttractionBehaviour homeAtt;
  private WorldObject carriedItem;
  private AntSchedulerState state;

  public enum AntSchedulerState {

    Searching,
    Returning
  }

  public AntScheduler(Robot robot) {
    super(robot);

    //Generali
    boxRepulsion = new RepulsionBehaviour(robot, 35, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, World.boxColor);
    boxRepulsion.setIsActive(true);
    boxRepulsion.setStrength(0.5f);

    otherRepulsion = new RepulsionBehaviour(robot, 8, VisiblesTypeFilter.Robots, VisiblesColorFilter.MatchColor, null);
    otherRepulsion.setIsActive(true);
    otherRepulsion.setStrength(1.0f);

    pherAtt = new PheromoneAttractionBehaviour(robot, 120, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, new Color(255, 255, 0, 100));
    pherAtt.setIsActive(true);
    pherAtt.setStrength(0.01f);

    dropPher = new DropPheromoneBehaviour(robot, new Vector(-7, 0));
    dropPher.setIsActive(false);

    randomWalk = new RandomWalkBehaviour(robot);
    randomWalk.setIsActive(true);
    randomWalk.setStrength(0.5f);

    //Search
    foodAtt = new AttractionBehaviour(robot, 120, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, Color.GREEN.darker());
    foodAtt.setIsActive(true);
    foodAtt.setStrength(0.05f);

    //Return
    carryItem = new CarryItemBehaviour(robot, null, new Vector(13, 0));
    carryItem.setIsActive(false);

    homeAtt = new AttractionBehaviour(robot, 2000, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, Color.GRAY.darker());
    homeAtt.setIsActive(false);
    homeAtt.setStrength(0.05f);

    state = AntSchedulerState.Searching;
  }

  @Override
  public void updateScheduler(float dt) {
    switch (state) {
      case Searching: {
        ArrayList<WorldObject> nearItems = getRobot().getVisibleItems(40, VisiblesColorFilter.MatchColor, Color.GREEN.darker());

        if (nearItems.size() > 0) {
          carriedItem = nearItems.get(0);
          carriedItem.col = Color.BLUE;
          carryItem.setItem(carriedItem);

          foodAtt.setIsActive(false);
          carryItem.setIsActive(true);
          homeAtt.setIsActive(true);
          dropPher.setIsActive(true);

          state = AntSchedulerState.Returning;
        }
      }
      break;

      case Returning: {
        ArrayList<WorldObject> nearItems = getRobot().getVisibleItems(10, VisiblesColorFilter.MatchColor, Color.GRAY.darker());

        if (nearItems.size() > 0) {

          carriedItem.kill();
          carryItem.setIsActive(false);
          homeAtt.setIsActive(false);
          foodAtt.setIsActive(true);
          dropPher.setIsActive(false);
          state = AntSchedulerState.Searching;

        }
      }
      break;
    }
  }

  @Override
  public ArrayList<Behaviour> getActiveBehaviours() {
    ArrayList<Behaviour> result = new ArrayList<Behaviour>();

    if (boxRepulsion.getIsActive()) {
      result.add(boxRepulsion);
    }

    if (otherRepulsion.getIsActive()) {
      result.add(otherRepulsion);
    }

    if (pherAtt.getIsActive()) {
      result.add(pherAtt);
    }

    if (randomWalk.getIsActive()) {
      result.add(randomWalk);
    }

    if (foodAtt.getIsActive()) {
      result.add(foodAtt);
    }

    if (carryItem.getIsActive()) {
      result.add(carryItem);
    }

    if (homeAtt.getIsActive()) {
      result.add(homeAtt);
    }

    if (dropPher.getIsActive()) {
      result.add(dropPher);
    }

    return result;
  }

  public void setFoodAttractionStrength(float strength) {
    foodAtt.setStrength(strength);
  }

  public void setPheromoneAttractionStrength(float strength) {
    pherAtt.setStrength(strength);
  }

  public void setHomeAttractionStrength(float strength) {
    homeAtt.setStrength(strength);
  }

  public void setFoodSightDistance(float dof) {
    foodAtt.setDistanceOfSight(dof);
  }

  public void setPheromoneSightDistance(float dof) {
    pherAtt.setDistanceOfSight(dof);
  }

  public void setHomeSightDistance(float dof) {
    homeAtt.setDistanceOfSight(dof);
  }
}
