public class Main
{
  public static void main(String[] args)
  {
    Display display = new Display(800, 600, "Awesome 3D Software Rendering!");
    RenderContext target = display.GetFrameBuffer();
    //Stars3D stars = new Stars3D(3, 64.0f, 4.0f);

    Bitmap texture = new Bitmap(32, 32);

    for(int j = 0; j < texture.GetHeight(); j++)
    {
      for(int i = 0; i < texture.GetWidth(); i++)
      {
        texture.DrawPixel(i, j,
        (byte)(Math.random() * 255.0 + 0.5),
        (byte)(Math.random() * 255.0 + 0.5),
        (byte)(Math.random() * 255.0 + 0.5),
        (byte)(Math.random() * 255.0 + 0.5));
      }
    }

    //Vertex minYVert = new Vertex(100, 100);
    //Vertex midYVert = new Vertex(0, 200);
    //Vertex maxYVert = new Vertex(80, 300);

    Vertex minYVert = new Vertex(new Vec4f(-1, -1, 0, 1),
                                 new Vec4f(0.0f, 0.0f, 0.0f, 0.0f));
    Vertex midYVert = new Vertex(new Vec4f(0, 1, 0, 1),
                                 new Vec4f(0.5f, 1.0f, 0.0f, 0.0f));
    Vertex maxYVert = new Vertex(new Vec4f(1, -1, 0, 1),
                                 new Vec4f(1.0f, 0.0f, 0.0f, 0.0f));

    Mat4f projection = new Mat4f().InitPerspective((float)Math.toRadians(70.0f),
      (float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f);


    float rotCounter = 0.0f;
    long previousTime = System.nanoTime();

    while(true)
    {
      long currentTime = System.nanoTime();
      // there are a billion seconds in a nanosecond... as we're using
      // ns here, we have to do this...
      float delta = (float)((currentTime - previousTime)/1000000000.0);
      previousTime = currentTime;

      rotCounter += delta;
      Mat4f translation = new Mat4f().InitTranslation(0.0f, 0.0f, 3.0f);
      Mat4f rotation = new Mat4f().InitRotation(0.0f, rotCounter, 0.0f);
      Mat4f transform = projection.Mul(translation.Mul(rotation));
      //stars.UpdateAndRender(target, delta);

      //target.Clear((byte)0x00);

//      for(int j = 100; j < 200; j++)
//      {
//        target.DrawScanBuffer(j, 300 - j, 300 + j);
//      }
      //target.FillTriangle(minYVert, midYVert, maxYVert);
      //target.ScanConvertTriangle(minYVert, midYVert, maxYVert, 0);
      //target.FillShape(100, 300);

      //stars.UpdateAndRender(target, delta);

      target.Clear((byte)0x00);
      target.FillTriangle(maxYVert.Transform(transform),
              midYVert.Transform(transform), minYVert.Transform(transform),
              texture);
      display.SwapBuffers();
    }
  }
}
