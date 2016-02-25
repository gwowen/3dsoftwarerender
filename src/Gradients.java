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
    m_color = new Vec4f[3];

    m_color[0] = minYVert.GetColor();
    m_color[1] = midYVert.GetColor();
    m_color[2] = maxYVert.GetColor();

    float oneOverdX = 1.0f /
    (((midYVert.GetX() - maxYVert.GetX()) *
    (minYVert.GetY() - maxYVert.GetY())) -
    ((minYVert.GetX() - maxYVert.GetX()) *
    (midYVert.GetY() - maxYVert.GetY())));

    float oneOverdY = -oneOverdX;

    m_colorXStep =
      (((m_color[1].Sub(m_color[2])).Mul(
      (minYVert.GetY() - maxYVert.GetY()))).Sub(
      ((m_color[0].Sub(m_color[2])).Mul(
      (midYVert.GetY() - maxYVert.GetY()))))).Mul(oneOverdX);

    m_colorYStep =
      (((m_color[1].Sub(m_color[2])).Mul(
      (minYVert.GetX() - maxYVert.GetX()))).Sub(
      ((m_color[0].Sub(m_color[2])).Mul(
      (midYVert.GetX() - maxYVert.GetX()))))).Mul(oneOverdY);
  }
}
