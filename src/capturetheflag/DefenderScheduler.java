package capturetheflag;

import Behaviours.RepulsionBehaviour;
import Behaviours.AttractionBehaviour;
import Behaviours.RandomWalkBehaviour;
import Behaviours.FollowObjectBehaviour;
import engine.BehaviorsScheduler;
import engine.Robot;
import engine.Behaviour;
import engine.Vector;
import engine.WorldObject.*;
import utils.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class DefenderScheduler extends BehaviorsScheduler {

  private RandomWalkBehaviour randomWalk;
  private RepulsionBehaviour repulsion;
  private AttractionBehaviour homeAtt;
  private FollowObjectBehaviour followEnemy;
  private DefenderSchedulerState state;
  private float dOfSight;
  private int eatingDuration;
  private int delay;
  private Robot enemy;
  public static int redCount;

  public enum DefenderSchedulerState {

    WanderingAroundHome,
    GotoEnemy,
    EatEnemy
  }

  public DefenderScheduler(Robot robot) {
    super(robot);

    randomWalk = new RandomWalkBehaviour(robot);
    randomWalk.setIsActive(true);

    repulsion = new RepulsionBehaviour(robot, 8, VisiblesTypeFilter.All, VisiblesColorFilter.All, null);
    repulsion.setIsActive(true);
    repulsion.setStrength(1.0f);

    homeAtt = new AttractionBehaviour(robot, 2000, VisiblesTypeFilter.Items, VisiblesColorFilter.MatchColor, Color.GRAY);
    homeAtt.setIsActive(true);
    homeAtt.setStrength(0.05f);

    followEnemy = new FollowObjectBehaviour(robot, 2000, null);
    followEnemy.setIsActive(false);
    followEnemy.setStrength(0.05f);

    dOfSight = 150;
    eatingDuration = 50;
    delay = 0;
    state = DefenderSchedulerState.WanderingAroundHome;
  }

  @Override
  public void updateScheduler(float dt) {
    switch (state) {
      case WanderingAroundHome: {
        ArrayList<Robot> nearAttackers = getRobot().getVisibleRobots(dOfSight, VisiblesColorFilter.MatchColor, Color.RED);

        if (nearAttackers.size() > 0) {
          Random r = new Random();

          float len2 = Float.MAX_VALUE;
          enemy = null;
          for (int i = 0; i < nearAttackers.size(); i++) {
            float thisLen = getRobot().getCenterDistance(nearAttackers.get(i)).getLength2();
            if (thisLen < len2) {
              len2 = thisLen;
              enemy = nearAttackers.get(i);
            }
          }

          followEnemy.setItem(enemy);
          followEnemy.setIsActive(true);
          homeAtt.setIsActive(false);

          state = DefenderSchedulerState.GotoEnemy;
        }
      }
      break;

      case GotoEnemy: {
        ArrayList<Robot> nearAttackers = getRobot().getVisibleRobots(dOfSight, VisiblesColorFilter.MatchColor, Color.RED);

        if (nearAttackers.size() > 0) {
          Random r = new Random();

          float len2 = Float.MAX_VALUE;
          enemy = null;
          for (int i = 0; i < nearAttackers.size(); i++) {
            float thisLen = getRobot().getCenterDistance(nearAttackers.get(i)).getLength2();
            if (thisLen < len2) {
              len2 = thisLen;
              enemy = nearAttackers.get(i);
            }
          }
        }

        Vector dist = enemy.getNormalReaction(getRobot(), dOfSight);

        //TODO: Attenzione a questo isonfault
        if (dist.getLength() < 0.01f || enemy.isOnFault()) {
          followEnemy.setIsActive(false);
          homeAtt.setIsActive(true);
          state = DefenderSchedulerState.WanderingAroundHome;
        } else if (dist.getLength() <= 8.0f) {
          enemy.setOnFault(true);
          state = DefenderSchedulerState.EatEnemy;
          delay = eatingDuration;
        }
      }
      break;

      case EatEnemy: {
        delay--;
        if (delay <= 0) {
          enemy.kill();
          redCount--;
          if (redCount == 0) {
            getRobot().getWorld().endSimulation();
            JOptionPane.showMessageDialog(CTFFrame.instance, "Blues win", "Simulation terminated", JOptionPane.INFORMATION_MESSAGE);
          }
          followEnemy.setIsActive(false);
          homeAtt.setIsActive(true);
          state = DefenderSchedulerState.WanderingAroundHome;
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
    if (homeAtt.getIsActive()) {
      result.add(homeAtt);
    }
    if (followEnemy.getIsActive()) {
      result.add(followEnemy);
    }

    return result;
  }

  void setEnemiesSightDistance(float value) {
    dOfSight = value;
  }
}
