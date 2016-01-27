public class Vertex
{
  private Vec4f m_pos;

  public float GetX() { return m_x; }
  public float GetY() { return m_y; }


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
    return new Vertex( new Vec4f(m_pos.GetX()/m_pos.GetW(), m_pos.GetY()/m_pos.GetW(),
                m_pos.GetZ()/m_pos.GetW(), m_pos.GetW()))
  }

  public float TriangleAreaTimesTwo(Vertex b, Vertex c)
  {
    float x1 = b.GetX() - m_x;
    float y1 = b.GetY() - m_y;

    float x2 = c.GetX() - m_x;
    float y2 = c.GetY() - m_y;

    return(x1 * y2 - x2 * y1);
  }
}
