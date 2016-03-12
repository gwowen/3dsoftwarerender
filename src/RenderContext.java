public class RenderContext extends Bitmap
{

  public RenderContext(int width, int height)
  {
    super(width, height);

  }

  public void FillTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture)
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
      minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0,
      texture);
  }


  public void ScanTriangle(Vertex minYVert, Vertex midYVert,
      Vertex maxYVert, boolean handedness, Bitmap texture)
  {
    Gradients gradients = new Gradients(minYVert, midYVert, maxYVert);
    Edge topToBottom = new Edge(gradients, minYVert, maxYVert, 0);
    Edge topToMiddle = new Edge(gradients, minYVert, midYVert, 0);
    Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);

    ScanEdges(gradients, topToBottom, topToMiddle, handedness, texture);
    ScanEdges(gradients, topToBottom, middleToBottom, handedness, texture);
  }

  private void ScanEdges(Gradients gradients, Edge a, Edge b, boolean handedness,
                         Bitmap texture)
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
      DrawScanLine(gradients, left, right, j, texture);
      left.Step();
      right.Step();
    }
  }

  private void DrawScanLine(Gradients gradients, Edge left, Edge right, int j,
                            Bitmap texture)
  {
    int xMin = (int)Math.ceil(left.GetX());
    int xMax = (int)Math.ceil(right.GetX());
    float xPrestep = xMin - left.GetX();

    float xDist = right.GetX() - left.GetX();
    float texCoordXXStep = (right.GetTexCoordX() - left.GetTexCoordX())/xDist;
    float texCoordYXStep = (right.GetTexCoordY() - left.GetTexCoordY())/xDist;
    float oneOverZXStep = (right.GetOneOverZ() - left.GetOneOverZ())/xDist;



    float texCoordX = left.GetTexCoordX() + texCoordXXStep() * xPrestep;
    float texCoordY = left.GetTexCoordY() + texCoordYXStep() * xPrestep;
    float oneOverZ = left.GetOneOverZ() + oneOverZXStep * xPrestep;

    for(int i = xMin; i < xMax; i++)
    {
      float z = 1.0f/oneOverZ;
      int srcX = (int)(texCoordX * (texture.GetWidth() - 1) + 0.5f);
      int srcY = (int)(texCoordY * (texture.GetHeight() - 1) + 0.5f);

      CopyPixel(i, j, srcX, srcY, texture);
      oneOverZ += oneOverZXStep;
      texCoordX += texCoordXXStep();
      texCoordY += texCoordYXStep();
    }
  }
}
