public class RenderContext extends Bitmap
{


  public RenderContext(int width, int height)
  {
    super(width, height);

  }

  public void FillTriangle(Vertex v1, Vertex v2, Vertex v3)
  {
    Mat4f screenSpaceTransform =
      new Mat4f().InitScreenSpaceTransform(GetWidth()/2, GetHeight()/2);
    Vertex minYVert  = v1.Transform(screenSpaceTransform).PerspectiveDivide();
    Vertex midYVert  = v2.Transform(screenSpaceTransform).PerspectiveDivide();
    Vertex maxYVert  = v3.Transform(screenSpaceTransform).PerspectiveDivide();

    //sort the vertices... bit involved
    // swaps max and mid if mid is greater than max
    // (i.e v2 > v3)
    if(maxYVert.GetY() < midYVert.GetY())
    {
      Vertex temp = maxYVert;
      maxYVert = midYVert;
      midYVert = temp;
    }

    // if the mid is greater than the min, then
    // swap them into the minimum vertex
    if(midYVert.GetY() < minYVert.GetY())
    {
      Vertex temp = midYVert;
      midYVert = minYVert;
      minYVert = temp;
    }

    // if after all this swapping, the mid is still greater
    // than the max, we swap them again so we should end up
    // with v1, v2, v3 in the right order
    if(maxYVert.GetY() < midYVert.GetY())
    {
      Vertex temp = maxYVert;
      maxYVert = midYVert;
      midYVert = temp;
    }


    ScanTriangle(minYVert, midYVert, maxYVert,
                        minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0);
  }


  public void ScanTriangle(Vertex minYVert, Vertex midYVert,
                                  Vertex maxYVert, boolean handedness)
  {
    Gradients gradients = new Gradients(minYVert, midYVert, maxYVert);
    Edge topToBottom = new Edge(minYVert, maxYVert);
    Edge topToMiddle = new Edge(minYVert, midYVert);
    Edge middleToBottom = new Edge(midYVert, maxYVert);

    ScanEdges(gradients, topToBottom, topToMiddle, handedness);
    ScanEdges(gradients, topToBottom, middleToBottom, handedness);
  }

  private void ScanEdges(Gradients gradients, Edge a, Edge b, boolean handedness)
  {
    Edge left = a;
    Edge right = b;

    // this time, swap based on the handedness of the triangle
    if(handedness)
    {
      Edge temp = left;
      left = right;
      right = temp;
    }

    int yStart = b.GetYStart();
    int yEnd = b.GetYEnd();

    for(int j = yStart; j < yEnd; j++)
    {
      DrawScanLine(gradients, left, right, j);
      left.Step();
      right.Step();
    }
  }

  private void DrawScanLine(Gradients gradients, Edge left, Edge right, int j)
  {
    int xMin = (int)Math.ceil(left.GetX());
    int xMax = (int)Math.ceil(right.GetX());
    float xPrestep = xMin - left.GetX();

    Vec4f color = left.GetColor().Add(gradients.GetColorXStep().Mul(xPrestep));

    for(int i = xMin; i < xMax; i++)
    {
      byte r = (byte)(color.GetX() * 255.0f + 0.5f);
      byte g = (byte)(color.GetY() * 255.0f + 0.5f);
      byte b = (byte)(color.GetZ() * 255.0f + 0.5f);

      DrawPixel(i, j, (byte)0xFF, b, g, r);
      color = color.Add(gradients.GetColorXStep());
    }
  }
}
