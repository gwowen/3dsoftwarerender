import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class RenderContext extends Bitmap
{
  private float[] m_zBuffer;

  public RenderContext(int width, int height)
  {
    super(width, height);
    m_zBuffer = new float[width * height];
  }

  public void ClearDepthBuffer()
  {
    for(int i = 0; i < m_zBuffer.length; i++)
    {
      m_zBuffer[i] = Float.MAX_VALUE;
    }
  }

  public void DrawTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture)
  {
    boolean v1Inside = v1.IsInsideViewFrustrum();
    boolean v2Inside = v2.IsInsideViewFrustrum();
    boolean v3Inside = v3.IsInsideViewFrustrum();

    // if inside, draw...
    if(v1Inside && v2Inside && v3Inside)
    {
      FillTriangle(v1, v2, v3, texture);
      return;
    }

    // if not, don't!
    if(!v1Inside && !v2Inside && !v3Inside)
    {
      return;
    }

    List<Vertex> vertices = new ArrayList<>();
    List<Vertex> auxillaryList = new ArrayList<>();

    vertices.add(v1);
    vertices.add(v2);
    vertices.add(v3);

    if(ClipPolygonAxis(vertices, auxillaryList, 0) &&
       ClipPolygonAxis(vertices, auxillaryList, 1) &&
       ClipPolygonAxis(vertices, auxillaryList, 2))
    {
      Vertex initialVertex = vertices.get(0);

      for(int i = 1; i < vertices.size() - 1; i++)
      {
        FillTriangle(initialVertex, vertices.get(i), vertices.get(i + 1), texture);
      }
    }
  }

  private boolean ClipPolygonAxis(List<Vertex> vertices,
          List<Vertex> auxillaryList, int componentIndex)
  {
      ClipPolygonComponent(vertices, componentIndex, 1.0f, auxillaryList);
      vertices.clear();

      if(auxillaryList.isEmpty())
      {
        return false;
      }

      ClipPolygonComponent(auxillaryList, componentIndex, -1.0f, vertices);
      auxillaryList.clear();

      return !vertices.isEmpty();
  }

  private void ClipPolygonComponent(List<Vertex> vertices, int componentIndex,
          float componentFactor, List<Vertex> result)
  {
      // last vertex looked at in process of clipping
      Vertex previousVertex = vertices.get(vertices.size() - 1);
      float previousComponent = previousVertex.Get(componentIndex) * componentFactor;
      boolean previousInside = previousComponent <= previousVertex.GetPosition().GetW();

      Iterator<Vertex> it = vertices.iterator();
      while(it.hasNext())
      {
        Vertex currentVertex = it.next();
        float currentComponent = currentVertex.Get(componentIndex) * componentFactor;
        boolean currentInside = currentComponent <= currentVertex.GetPosition().GetW();

        if(currentInside ^ previousInside)
        {
            float lerpAmt = (previousVertex.GetPosition().GetW() - previousComponent) /
                ((previousVertex.GetPosition().GetW() - previousComponent) -
                (currentVertex.GetPosition().GetW() - currentComponent));

            result.add(previousVertex.Lerp(currentVertex, lerpAmt));
        }

        if(currentInside)
        {
          result.add(currentVertex);
        }

        previousVertex = currentVertex;
        previousComponent = currentComponent;
        previousInside = currentInside;
      }
  }



  private void FillTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture)
  {
    Mat4f screenSpaceTransform =
      new Mat4f().InitScreenSpaceTransform(GetWidth()/2, GetHeight()/2);
    Vertex minYVert  = v1.Transform(screenSpaceTransform).PerspectiveDivide();
    Vertex midYVert  = v2.Transform(screenSpaceTransform).PerspectiveDivide();
    Vertex maxYVert  = v3.Transform(screenSpaceTransform).PerspectiveDivide();

    if(minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0)
    {
      return;
    }
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
    Edge topToBottom    = new Edge(gradients, minYVert, maxYVert, 0);
    Edge topToMiddle    = new Edge(gradients, minYVert, midYVert, 0);
    Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);

    ScanEdges(topToBottom, topToMiddle, handedness, texture);
    ScanEdges(topToBottom, middleToBottom, handedness, texture);
  }

  private void ScanEdges(Edge a, Edge b, boolean handedness, Bitmap texture)
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
      DrawScanLine(left, right, j, texture);
      left.Step();
      right.Step();
    }
  }

  private void DrawScanLine(Edge left, Edge right, int j, Bitmap texture)
  {
    int xMin = (int)Math.ceil(left.GetX());
    int xMax = (int)Math.ceil(right.GetX());
    float xPrestep = xMin - left.GetX();

    float xDist = right.GetX() - left.GetX();
    float texCoordXXStep = (right.GetTexCoordX() - left.GetTexCoordX())/xDist;
    float texCoordYXStep = (right.GetTexCoordY() - left.GetTexCoordY())/xDist;
    float oneOverZXStep = (right.GetOneOverZ() - left.GetOneOverZ())/xDist;
    float depthXStep = (right.GetDepth() - left.GetDepth())/xDist;

    float texCoordX = left.GetTexCoordX() + texCoordXXStep * xPrestep;
    float texCoordY = left.GetTexCoordY() + texCoordYXStep * xPrestep;
    float oneOverZ = left.GetOneOverZ() + oneOverZXStep * xPrestep;
    float depth = left.GetDepth() + depthXStep * xPrestep;

    for(int i = xMin; i < xMax; i++)
    {
      int index = i + j * GetWidth();

      // we only draw the pixel if its value is less than that in the
      // z-buffer
      if(depth < m_zBuffer[index])
      {
        // reassign the value of the pixel in the z-buffer at that point
        // to be the depth
        m_zBuffer[index] = depth;
        float z = 1.0f/oneOverZ;
        int srcX = (int)((texCoordX * z) * (float)(texture.GetWidth() - 1) + 0.5f);
        int srcY = (int)((texCoordY * z) * (float)(texture.GetHeight() - 1) + 0.5f);

        CopyPixel(i, j, srcX, srcY, texture);
      }
      oneOverZ += oneOverZXStep;
      texCoordX += texCoordXXStep;
      texCoordY += texCoordYXStep;
      depth += depthXStep;
    }
  }
}
