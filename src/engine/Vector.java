package engine;

public class Vector {

  public float x, y;

  public Vector() {
    x = y = 0.0f;
  }

  public Vector(float vx, float vy) {
    x = vx;
    y = vy;
  }

  public boolean equals(Vector v) {
    return (v.x == x && v.y == y);
  }

  public Vector getCopy() {
    return new Vector(x, y);
  }

  public void set(float vx, float vy) {
    x = vx;
    y = vy;
  }

  public void add(Vector v) {
    x += v.x;
    y += v.y;
  }

  public void sub(Vector v) {
    x -= v.x;
    y -= v.y;
  }

  public void multiply(float n) {
    x *= n;
    y *= n;
  }

  public Vector getOpposite() {
    return new Vector(-x, -y);
  }

  public float getLength() {
    return (float) Math.sqrt(x * x + y * y);
  }

  public float getLength2() {
    return x * x + y * y;
  }

  public void reduceLength(float r) {

    float len = getLength();
    if (r > len - 0.01f) {
      len = 10.0f;
    } else {
      len = 1 - r / len;
    }

    x *= len;
    y *= len;
  }

  public void normalize() {
    setLength(1.0f);
  }
  
  public void setLength(float newLength) {
    float length = (float) Math.sqrt(x * x + y * y) / newLength;
    if (length > 0.0001f) {
      x /= length;
      y /= length;
    }
  }

  public void setRandom(float length) {
    float randAngle = (float) (Math.random() * Math.PI * 2);         //Varia l'angolo in modo random di 180°
    x = (float) Math.cos(randAngle) * length;
    y = (float) Math.sin(randAngle) * length;
  }

  public void rotate(float angle) {
    float tmp_x = x;
    float cos = (float) Math.cos(angle);
    float sin = (float) Math.sin(angle);
    x = tmp_x * cos - y * sin;
    y = tmp_x * sin + y * cos;
  }

  public float getAngle() {
    return (float) Math.atan2(y, x);
  }

  public void clamp(float length) {
    float len = getLength();

    if (len > length) {
      multiply(length / len);
    }
  }
}
