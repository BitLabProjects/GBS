package engine;

import java.io.InputStream;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.*;

public class CoreLoader {

  private CoreLoader() {
  }

  public static Core Load(InputStream source) {
    try {
      Serializer serializer = new Persister();
      return serializer.read(Core.class, source);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
