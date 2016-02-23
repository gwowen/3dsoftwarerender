public class RenderContext extends Bitmap
{
  private final int m_scanBuffer[];

  public RenderContext(int width, int height)
  {
    super(width, height);
    //every y-coord needs two values, hence height * 2
    // m_scanBuffer = new int[height * 2];
  }

  public void DrawScanBuffer(int yCoord, int xMin, int xMax)
  {
    m_scanBuffer[yCoord * 2] = xMin;
    m_scanBuffer[yCoord * 2 + 1] = xMax;
  }

  public void FillShape(int yMin, int yMax)
  {
    for(int j = yMin; j < yMax; j++)
    {
      int xMin = m_scanBuffer[j * 2];
      int xMax = m_scanBuffer[j * 2 + 1];

      for(int i = xMin; i < xMax; i++)
      {
        DrawPixel(i, j, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF);
      }
    }
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

    float area = minYVert.TriangleAreaTimesTwo(maxYVert, midYVert);
    //handedness depending on the value of the cross product
    // > 0, right-handed so it's 1 and on the max side of the scanbuffer
    // < 0, left-handed so on the min side of the scanbuffer
    int handedness = area >= 0 ? 1 : 0;

    ScanConvertTriangle(minYVert, midYVert, maxYVert, handedness);
    FillShape((int)minYVert.GetY(), (int)maxYVert.GetY());
  }


  public void ScanConvertTriangle(Vertex minYVert, Vertex midYVert,
                                  Vertex maxYVert, int handedness)
  {
    // minimum side of triangle, by default handedness is zero
    // however a value of 1 will invert it
    ScanConvertLine(minYVert, maxYVert, 0 + handedness);
    //middle vertex of triangle
    ScanConvertLine(minYVert, midYVert, 1 - handedness);
    //final vertex of triangle
    ScanConvertLine(midYVert, maxYVert, 1 - handedness);

  }

  //Implementation of Bresenham's line algorithm
  private void ScanConvertLine(Vertex minYVert, Vertex maxYVert, int whichSide)
  {
    int yStart = (int)Math.ceil(minYVert.GetY());
    int yEnd  = (int)Math.ceil(maxYVert.GetY());
    int xStart = (int)Math.ceil(minYVert.GetX());
    int xEnd = (int)Math.ceil(maxYVert.GetX());

    float yDist = maxYVert.GetY() - minYVert.GetY();
    float xDist = maxYVert.GetX() - minYVert.GetX();

    if(yDist <= 0)
    {
      return;
    }

    // how large a step we take along the x-axis for
    // each y-coordinate
    float xStep = (float)xDist / (float)yDist;
    float yPrestep = yStart - minYVert.GetY();
    float currentX = minYVert.GetX() + yPrestep * xStep;

    for(int j = yStart; j < yEnd; j++)
    {
      // if whichSide is zero, we write to the minimum side, if
      // whichSide is 1, we write to the maximum side
      m_scanBuffer[j * 2 + whichSide] = (int)Math.ceil(currentX);

      // progress along the x-axis
      currentX += xStep;
    }
  }
}
