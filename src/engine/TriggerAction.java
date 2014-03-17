package engine;

public abstract class TriggerAction {
  protected Core mCore;
  public void setCore(Core core) {
    mCore = core;
  }
  public abstract void apply(boolean firstApplication);
  public abstract void unApply();
}
