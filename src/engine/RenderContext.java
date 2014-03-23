package engine;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Matrix4;

import java.util.Stack;

public class RenderContext {
  
  Stack<Matrix4> mMatrixStack;
  Matrix4 mMatrix;
  ImmediateModeRenderer20 mImmediateRender;
  Color mColor;
  
  public RenderContext(Matrix4 matrix) {
    //Use a copy because when translating or rotating we don't want to alter the original one
    mMatrix = matrix.cpy();
    mMatrixStack = new Stack<Matrix4>();
    mImmediateRender = new ImmediateModeRenderer20(false, true, 0);
  }
  
  public void push() {
    mMatrixStack.push(mMatrix.cpy());
  }
  
  public void pop() {
    mMatrix = mMatrixStack.pop();
  }
  
  public void translate(float x, float y) {
    mMatrix.translate(x, y, 0.0f);
  }
  
  public void rotate(float angle) {
    mMatrix.rotate(0.0f, 0.0f, 1.0f, (float)Math.toDegrees(angle));
  }
  
  private void beginRender(int mode) {
    mImmediateRender.begin(mMatrix, mode);
  }
  
  private void endRender() {
    mImmediateRender.end();
  }

  public void setColor(Color color) {
    mColor = color;
  }

  public Color getColor() {
    return mColor;
  }
  
  private void setCurrentColor() {
    mImmediateRender.color(mColor.getRed()/255.0f, mColor.getGreen()/255.0f, mColor.getBlue()/255.0f, mColor.getAlpha()/255.0f);
  }

  public void drawLine(int x1, int y1, int x2, int y2) {
    beginRender(GL20.GL_LINES);
    setCurrentColor();
    mImmediateRender.vertex(x1, y1, 0.0f);
    setCurrentColor();
    mImmediateRender.vertex(x2, y2, 0.0f);
    endRender();
  }
  
  public void drawLine(float x1, float y1, float x2, float y2) {
	    beginRender(GL20.GL_LINES);
	    setCurrentColor();
	    mImmediateRender.vertex(x1, y1, 0.0f);
	    setCurrentColor();
	    mImmediateRender.vertex(x2, y2, 0.0f);
	    endRender();
	  }

  public void drawRect(int x1, int y1, int w, int h) {
    int x2 = x1 + w;
    int y2 = y1 + h;
    
    beginRender(GL20.GL_LINES);
    setCurrentColor();
    mImmediateRender.vertex(x1, y1, 0.0f);
    setCurrentColor();
    mImmediateRender.vertex(x2, y1, 0.0f);
    
    setCurrentColor();
    mImmediateRender.vertex(x2, y1, 0.0f);
    setCurrentColor();
    mImmediateRender.vertex(x2, y2, 0.0f);
    
    setCurrentColor();
    mImmediateRender.vertex(x2, y2, 0.0f);
    setCurrentColor();
    mImmediateRender.vertex(x1, y2, 0.0f);
    
    setCurrentColor();
    mImmediateRender.vertex(x1, y2, 0.0f);
    setCurrentColor();
    mImmediateRender.vertex(x1, y1, 0.0f);
    endRender();
  }
  
  public void fillRect(int x1, int y1, int w, int h) {
    drawRect(x1, y1, w, h);
  }

  public void drawOval(int x, int y, int diameterX, int diameterY) {
    float xS = diameterX / 2.0f;
    float yS = diameterY / 2.0f;
    float xC = x + xS;
    float yC = y + yS;
    final float steps = 10;
    
    beginRender(GL20.GL_LINES);
    float xCurr = 1.0f;
    float yCurr = 0.0f;
    for(int i=0; i<steps; i++) {
      float xNext = (float)Math.cos((i+1) / steps * 2 * Math.PI);
      float yNext = (float)Math.sin((i+1) / steps * 2 * Math.PI);
      setCurrentColor();
      mImmediateRender.vertex(xCurr * xS + xC, yCurr * yS + yC, 0.0f);
      setCurrentColor();
      mImmediateRender.vertex(xNext * xS + xC, yNext * yS + yC, 0.0f);
      xCurr = xNext;
      yCurr = yNext;
    }
    endRender();
  }
    
  public void fillOval(int x1, int y1, int x2, int y2) {
    drawOval(x1, y1, x2, y2);
  }
}
