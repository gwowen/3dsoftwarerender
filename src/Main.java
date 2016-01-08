public class Main
{
  public static void main(String[] args)
  {
    Display display = new Display(800, 600, "Awesome 3D Software Rendering!");
    RenderContext target = display.GetFrameBuffer();
    Stars3D stars = new Stars3D(3, 64.0f, 4.0f);

    Vertex minYVert = new Vertex(100, 100);
    Vertex midYVert = new Vertex(150, 200);
    Vertex maxYVert = new Vertex(80, 300);

    long previousTime = System.nanoTime();

    while(true)
    {
      long currentTime = System.nanoTime();
      // there are a billion seconds in a nanosecond... as we're using
      // ns here, we have to do this...
      float delta = (float)((currentTime - previousTime)/1000000000.0);
      previousTime = currentTime;

      stars.UpdateAndRender(target, delta);
      //target.Clear((byte)0x00);

      //for(int j = 100; j < 200; j++)
      //{
      //  target.DrawScanBuffer(j, 300 - j, 300 + j);
      //}
      //target.FillTriangle(minYVert, midYVert, maxYVert);
      //target.ScanConvertTriangle(minYVert, midYVert, maxYVert, 0);
      //target.FillShape(100, 200);

      //stars.UpdateAndRender(target, delta);
      display.SwapBuffers();
    }
  }
}
