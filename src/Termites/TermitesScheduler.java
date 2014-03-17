package Termites;

import Behaviours.*;
import engine.*;
import engine.WorldObject.*;
import utils.Color;
import java.util.ArrayList;

public class TermitesScheduler extends BehaviorsScheduler {

  private RandomWalkBehaviour randomWalk;
  private RepulsionBehaviour repulsion;
  private RepulsionBehaviour boxRepulsion;
  private CarryItemBehaviour carry;
  private AttractionBehaviour attraction;
  private WorldObject carriedItem;
  private int delayTimer;
  private TermitesSchedulerState state;

  public enum TermitesSchedulerState {

    Wandering,
    Transporting,
    WanderingWait
  }

  public TermitesScheduler(Robot robot) {
    super(robot);

    randomWalk = new RandomWalkBehaviour(robot);
    randomWalk.setIsActive(true);

    repulsion = new RepulsionBehaviour(robot, 8, VisiblesTypeFilter.Robots, VisiblesColorFilter.MatchColor, null);
    repulsion.setIsActive(true);
    repulsion.setStrength(1.0f);

    boxRepulsion = new RepulsionBehaviour(robot, 8, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, World.boxColor);
    boxRepulsion.setIsActive(true);
    boxRepulsion.setStrength(1.0f);

    attraction = new AttractionBehaviour(robot, 40, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, Color.GREEN.darker());
    attraction.setIsActive(true);
    attraction.setStrength(0.01f);

    carry = new CarryItemBehaviour(robot, null, new Vector(13, 0));

    state = TermitesSchedulerState.Wandering;
  }

  @Override
  public void updateScheduler(float dt) {
    switch (state) {
      case Wandering: {
        ArrayList<WorldObject> nearItems = getRobot().getVisibleItems(10, VisiblesColorFilter.MatchColor, Color.GREEN.darker());

        if (nearItems.size() > 0) {
          carriedItem = nearItems.get(0);
          carriedItem.col = Color.BLUE;
          carry.setItem(carriedItem);
          carry.setIsActive(true);
          state = TermitesSchedulerState.Transporting;
          delayTimer = 100;
        }
      }
      break;

      case Transporting: {
        ArrayList<WorldObject> nearItems = getRobot().getVisibleItems(10, VisiblesColorFilter.MatchColor, Color.GREEN.darker());

        delayTimer--;
        if (delayTimer > 0) {
          break;
        }

        if (nearItems.size() > 0) {
          carriedItem.col = Color.GREEN.darker();
          carry.setIsActive(false);
          state = TermitesSchedulerState.WanderingWait;
          delayTimer = 300;
        }
      }
      break;

      case WanderingWait: {
        delayTimer--;
        if (delayTimer <= 0) {
          state = TermitesSchedulerState.Wandering;
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
    if (repulsion.getIsActive()) {
      result.add(repulsion);
    }
    if (boxRepulsion.getIsActive()) {
      result.add(boxRepulsion);
    }
    if (carry.getIsActive()) {
      result.add(carry);
    }
    if (attraction.getIsActive()) {
      result.add(attraction);
    }

    return result;
  }

  public void setFoodAttractionStrength(float strength) {
    attraction.setStrength(strength);
  }

  public void setFoodSightDistance(float dof) {
    attraction.setDistanceOfSight(dof);
  }
}
