public class Gradients
{
  private Vec4f[] m_color;
  private Vec4f m_colorXStep;
  private Vec4f m_colorYStep;

  public Vec4f GetColor(int loc) { return m_color[loc]; }
  public Vec4f GetColorXStep() { return m_colorXStep; }
  public Vec4f GetColorYStep() { return m_colorYStep; }

  public Gradients(Vertex minYVert, Vertex midYVert, Vertex maxYVert)
  {

  }
}
