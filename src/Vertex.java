public class Vertex
{
  private Vec4f m_pos;

  public float GetX() { return m_pos.GetX(); }
  public float GetY() { return m_pos.GetY(); }



  public Vertex(float x, float y, float z)
  {
    m_pos = new Vec4f(x, y, z, 1);
  }

  public Vertex(Vec4f pos)
  {
    m_pos = pos;
  }

  public Vertex Transform(Mat4f transform)
  {
    return new Vertex(transform.Transform(m_pos));
  }

  public Vertex PerspectiveDivide()
  {
    return new Vertex(new Vec4f(m_pos.GetX()/m_pos.GetW(), m_pos.GetY()/m_pos.GetW(),
						m_pos.GetZ()/m_pos.GetW(), m_pos.GetW()));
  }

  // 2D cross product of the line from x1 to x2
  // and the line from x1 to x3 to determine the
  // sign of the triangle, and hence whether left
  // or right handed
  public float TriangleAreaTimesTwo(Vertex b, Vertex c)
  {
    float x1 = b.GetX() - m_pos.GetX();
    float y1 = b.GetY() - m_pos.GetY();

    float x2 = c.GetX() - m_pos.GetX();
    float y2 = c.GetY() - m_pos.GetY();

    return(x1 * y2 - x2 * y1);
  }
}
