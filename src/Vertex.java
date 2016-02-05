public class Vertex
{
  private float m_x;
  private float m_y;

  public float GetX() { return m_x; }
  public float GetY() { return m_y; }

  public void SetX(float x) { m_x = x; }
  public void SetY(float y) { m_y = y; }

  public Vertex(float x, float y)
  {
    m_x = x;
    m_y = y;
  }

  // 2D cross product of the line from x1 to x2 
  // and the line from x1 to x3 to determine the
  // sign of the triangle, and hence whether left
  // or right handed
  public float TriangleAreaTimesTwo(Vertex b, Vertex c)
  {
    float x1 = b.GetX() - m_x;
    float y1 = b.GetY() - m_y;

    float x2 = c.GetX() - m_x;
    float y2 = c.GetY() - m_y;

    return(x1 * y2 - x2 * y1);
  }
}
