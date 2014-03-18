package engine;

public class Color {
  public static final Color RED   = new Color(255,   0,   0, 255);
  public static final Color GREEN = new Color(  0, 255,   0, 255);
  public static final Color BLUE  = new Color(  0,   0, 255, 255);
  public static final Color BLACK = new Color(  0,   0,   0, 255);
  public static final Color GRAY  = new Color(100, 100, 100, 255);
  
  private int mRed, mGreen, mBlue, mAlpha;

  public Color(int r, int g, int b, int a) {
    mRed = r;
    mGreen = g;
    mBlue = b;
    mAlpha = a;
  }  
  
  public int getRed() {
    return mRed;
  }

  public int getGreen() {
    return mGreen;
  }

  public int getBlue() {
    return mBlue;
  }
  
  public int getAlpha() {
    return mAlpha;
  }
  
  public Color darker() {
    return new Color((int)(mRed * 0.8f), (int)(mGreen * 0.8f), (int)(mBlue * 0.8f), mAlpha);
  }
  
  @Override
  public boolean equals(Object other) {
    if (other instanceof Color) {
      Color otherC = (Color)other;
      return (mRed == otherC.mRed && mGreen == otherC.mGreen && mBlue == otherC.mBlue && mAlpha == otherC.mAlpha);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 29 * hash + this.mRed;
    hash = 29 * hash + this.mGreen;
    hash = 29 * hash + this.mBlue;
    hash = 29 * hash + this.mAlpha;
    return hash;
  }
}
