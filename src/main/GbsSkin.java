
package main;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GbsSkin {
  
  private static Skin mSkin;
  
  private GbsSkin() {
  }
  
  public static Skin getSkin() {
    if (mSkin == null) {
      mSkin = create();
    }
    return mSkin;
  }
  
  private static Skin create() {
    Skin skin = new Skin();

    Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
    pixmap.setColor(Color.WHITE);
    pixmap.fill();
    skin.add("white", new Texture(pixmap));

    skin.add("default", new BitmapFont());

    TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
    textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
    textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
    textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
    textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
    textButtonStyle.font = skin.getFont("default");
    skin.add("default", textButtonStyle);
    
    Label.LabelStyle labelStyle = new Label.LabelStyle();
    labelStyle.background = null;
    labelStyle.font = skin.getFont("default");
    labelStyle.fontColor = Color.WHITE;
    skin.add("default", labelStyle);
    
    return skin;
  }
}
