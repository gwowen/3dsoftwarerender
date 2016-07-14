import java.io.IOException;


public class Main
{
  public static void main(String[] args) throws IOException
  {
    Display display = new Display(800, 600, "Awesome 3D Software Rendering!");
    RenderContext target = display.GetFrameBuffer();
    //Stars3D stars = new Stars3D(3, 64.0f, 4.0f);

    Bitmap texture = new Bitmap("./res/bricks.jpg");
    Bitmap texture2 = new Bitmap("./res/bricks2.jpg");
    Mesh monkeyMesh = new Mesh("./res/monkey0.obj");
    Transform monkeyTransform = new Transform(new Vec4f(0, 0.0f, 3.0f));

    Mesh terrainMesh = new Mesh("./res/terrain2.obj");
    Transform terrainTransform = new Transform(new Vec4f(0, -1.0f, 0.0f));

    Camera camera = new Camera(new Mat4f().InitPerspective((float)Math.toRadians(70.0f),
      (float)target.GetWidth() / (float)target.GetHeight(), 0.1f, 1000.0f));

    float rotCounter = 0.0f;
    long previousTime = System.nanoTime();

    while(true)
    {
      long currentTime = System.nanoTime();
      // there are a billion seconds in a nanosecond... as we're using
      // ns here, we have to do this...
      float delta = (float)((currentTime - previousTime)/1000000000.0);
      previousTime = currentTime;

      camera.Update(display.GetInput(), delta);
      Mat4f vp = camera.GetViewProjection();

      target.Clear((byte)0x00);
      target.ClearDepthBuffer();
      monkeyMesh.Draw(target, vp, monkeyTransform.GetTransformation(), texture2);
      terrainMesh.Draw(target, vp, terrainTransform.GetTransformation(), texture);
      display.SwapBuffers();
    }
  }
}
