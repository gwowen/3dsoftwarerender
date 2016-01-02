public class Main {
  public static void main(String[] args)
  {
    Display display = new Display(800, 600, "Software Rendering");
    Bitmap target = display.GetFrameBuffer();
    Stars3D stars = new Stars3D(4096, 64.0f, 20.0f);

    long previousTime = System.nanoTime();
    
    while(true)
    {
      long currentTime = System.nanoTime();
      // there are a billion seconds in a nanosecond... as we're using
      // ns here, we have to do this...
      float delta = (float)((currentTime - previousTime)/1000000000.0);
      previousTime = currentTime;

      stars.UpdateAndRender(target, delta);
      display.SwapBuffers();
    }
  }
}
