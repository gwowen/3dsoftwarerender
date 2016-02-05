public class RenderContext extends Bitmap
{
  private final int m_scanBuffer[];

  public RenderContext(int width, int height)
  {
    super(width, height);
    //every y-coord needs two values, hence height * 2
    m_scanBuffer = new int[height * 2];
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
    Vertex minYVert  = v1;
    Vertex midYVert  = v2;
    Vertex maxYVert  = v3;

    ScanConvertTriangle(minYVert, midYVert, maxYVert, 0);
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
    int yStart = (int)minYVert.GetY();
    int yEnd  = (int)maxYVert.GetY();
    int xStart = (int)minYVert.GetX();
    int xEnd = (int)maxYVert.GetX();

    int yDist = yEnd - yStart;
    int xDist = xEnd - xStart;

    if(yDist <= 0)
    {
      return;
    }

    // how large a step we take along the x-axis for
    // each y-coordinate
    float xStep = (float)xDist / (float)yDist;
    float currentX = (float)xStart;

    for(int j = yStart; j < yEnd; j++)
    {
      // if whichSide is zero, we write to the minimum side, if
      // whichSide is 1, we write to the maximum side
      m_scanBuffer[j * 2 + whichSide] = (int)currentX;

      // progress along the x-axis
      currentX += xStep;
    }

  }
}
