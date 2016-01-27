public class Main
{
  public static void main(String[] args)
  {
    Display display = new Display(800, 600, "Awesome 3D Software Rendering!");
    RenderContext target = display.GetFrameBuffer();
    Stars3D stars = new Stars3D(3, 64.0f, 4.0f);

    Vertex minYVert = new Vertex(-1, -1, 0);
    Vertex midYVert = new Vertex(0, 1, 0);
    Vertex maxYVert = new Vertex(1, -1, 0);

    Mat4f projection = new Mat4f().InitPerspective((float)Math.toRadians(70.0f),
    (float)target.GetWidth() /(float)target.GetHeight(), 0.1f, 1000.0f);

    float rotCounter = 0.0f;
    long previousTime = System.nanoTime();

    while(true)
    {
      long currentTime = System.nanoTime();
      // there are a billion seconds in a nanosecond... as we're using
      // ns here, we have to do this...
      float delta = (float)((currentTime - previousTime)/1000000000.0);
      previousTime = currentTime;

      //stars.UpdateAndRender(target, delta);
      rotCounter += delta;
      Mat4f translation = new Mat4f().InitTranslation(0.0f, 0.0f, 3.0f);
      Mat4f rotation = new Mat4f().InitRotation(0.0f, rotCounter, 0.0f);
      Mat4f transform = projection.Mul(translation.Mul(rotation));

      target.Clear((byte)0x00);
      target.FillTriangle(maxYVert.Transform(transform),
              midYVert.Transform(transform), minYVert.Transform(transform));
    
      display.SwapBuffers();
    }
  }
}
