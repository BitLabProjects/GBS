package engine;

import utils.Color;

public class MovableObject extends WorldObject {

  private Vector velocity = new Vector();
  private Vector appliedForce = new Vector();

  public MovableObject(World e, Vector p) {
    this(e, p, Color.BLACK);
  }

  public MovableObject(World e, Vector p, Color c) {
    super(e, p, c);
    velocity.set(0.0f, 0.0f);
    appliedForce.set(0.0f, 0.0f);
  }

  public void addForce(Vector f) {
    appliedForce.add(f);
  }

  public void setVelocity(Vector v) {
    velocity = v.getCopy();
  }

  public Vector getVelocity() {
    return velocity.getCopy();
  }
  
  public float getMaxForce() {
    return 100.0f;
  }

  public float getMaxVelocity() {
    return 500.0f; //Was 10
  }

  @Override
  public void update(float dt) {
    super.update(dt);

    float length = appliedForce.getLength();                   //Modulo del vettore forza

    if (length > getMaxForce()) //Se il modulo è maggiore di quello massimo
    {
      appliedForce.multiply(getMaxForce() / length);         //Riducila a quello massimo
    }
    appliedForce.multiply(dt);                                 //Adatta la forza per evitare scatti

    velocity.add(appliedForce);                                     //Applica la forza

    length = velocity.getLength();                           //Modulo della velocità

    if (length > getMaxVelocity()) //Se il modulo è maggiore di quello massimo
    {
      velocity.multiply(getMaxVelocity() / length);        //Riducila a quello massimo
    }
    appliedForce.set(0.0f, 0.0f);                              //Azzera la forza applicata

    Vector velToAdd = velocity.getCopy();
    velToAdd.multiply(dt);                              //Adatta la velocità per evitare scatti

    position.add(velToAdd);                                  //Aggirona la posizione

    overEdgeCheck();
  }

  private void overEdgeCheck() {
    if (position.x >= getWorld().getWidth() - 3) {
      velocity.x = -Math.abs(velocity.x) * 0.75f;
      position.x = getWorld().getWidth() - 3;
    }

    if (position.x <= 3) {
      velocity.x = Math.abs(velocity.x) * 0.75f;
      position.x = 3;
    }

    if (position.y >= getWorld().getHeight() - 3) {
      velocity.y = -Math.abs(velocity.y) * 0.75f;
      position.y = getWorld().getHeight() - 3;
    }

    if (position.y <= 3) {
      velocity.y = Math.abs(velocity.y) * 0.75f;
      position.y = 3;
    }
  }
}
