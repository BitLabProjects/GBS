package main;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.*;
import main.GbsApplication;

public class MainActivity extends AndroidApplication {

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
    cfg.useGL20 = true;
    cfg.useAccelerometer = false;
    cfg.useCompass = false;

    initialize(new GbsApplication(), cfg);
  }
}
