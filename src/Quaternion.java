public class Quaternion
{
  private float m_x;
  private float m_y;
  private float m_z;
  private float m_w;

  public Quaternion(float x, float y, float z, float w)
  {
    this.m_x = x;
    this.m_y = y;
    this.m_z = z;
    this.m_w = w;
  }

  public Quaternion(Vec4f axis, float angle)
  {
    float sinHalfAngle = (float)Math.sin(angle/2);
    float cosHalfAngle = (float)Math.cos(angle/2);

    this.m_x = axis.GetX() * sinHalfAngle;
    this.m_y = axis.GetY() * sinHalfAngle;
    this.m_z = axis.GetZ() * sinHalfAngle;
    this.m_w = cosHalfAngle;
  }

  public float Length()
  {
    return (float)Math.sqrt(m_x * m_x + m_y * m_y + m_z * m_z + m_w * m_w);
  }

  public Quaternion Normalized()
  {
    float length = Length();

    return new Quaternion(m_x / length, m_y / length, m_z / length, m_w / length );
  }

  public Quaternion Conjugate()
  {
    return new Quaternion(-m_x, -m_y, -m_z, -m_w);
  }

  public Quaternion Mul(float r)
  {
    return new Quaternion(m_x * r, m_y * r, m_z * r, m_w * r);
  }

  public Quaternion Mul(Quaternion r)
  {
    float w_ = m_w * r.GetW() - m_x * r.GetX() - m_y * r.GetY() - m_x * r.GetZ();
    float x_ = m_x * r.GetW() + m_w * r.GetX() + m_y * r.GetZ() - m_z * r.GetY();
    float y_ = m_y * r.GetW() + m_w * r.GetY() + m_z * r.GetX() - m_x * r.GetZ();
    float z_ = m_z * r.GetW() + m_w * r.GetZ() + m_x * r.GetY() - m_y * r.GetX();

    return new Quaternion(x_, y_, z_, w_);
  }

  public Quaternion Mul(Vec4f r)
  {
    float w_ = -m_x * r.GetX() - m_y * r.GetY() - m_z * r.GetZ();
    float x_ =  m_w * r.GetX() + m_y * r.GetZ() - m_z * r.GetY();
    float y_ =  m_w * r.GetY() + m_z * r.GetX() - m_x * r.GetZ();
    float z_ =  m_w * r.GetZ() + m_x * r.GetY() - m_y * r.GetX();

    return new Quaternion(x_, y_, z_, w_);
  }

  public Quaternion Sub(Quaternion r)
  {
    return new Quaternion(m_x - r.GetX(), m_y - r.GetY(), m_z - r.GetZ(),
                          m_w - r.GetW());

  }

  public Quaternion Add(Quaternion r)
  {
    return new Quaternion(m_x + r.GetX(), m_y + r.GetY(), m_z + r.GetZ(),
                          m_w + r.GetW());
  }

  public Mat4f ToRotationMatrix()
  {

  }

  public float Dot(Quaternion r)
  {
    return m_x * r.GetX() + m_y * r.GetY() + m_z * r.GetZ() + m_w * r.GetW();

  }

  public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest)
  {

  }

  public Quaternion SLerp(Quaternion dest, float lerpFactor, boolean shortest)
  {

  }

  public Quaternion(Mat4f rot)
  {

  }

  public Vec4f GetForward()
  {
    return new Vec4f(0, 0, 1, 1).Rotate(this);
  }

  public Vec4f GetBack()
  {
    return new Vec4f(0, 0, -1, 1).Rotate(this);
  }

  public Vec4f GetUp()
  {
    return new Vec4f(0, 1, 0, 1).Rotate(this);
  }

  public Vec4f GetDown()
  {
    return new Vec4f(0, -1, 0, 1).Rotate(this);
  }

  public Vec4f GetRight()
  {
    return new Vec4f(1, 0, 0, 1).Rotate(this);
  }

  public Vec4f GetLeft()
  {
    return new Vec4f(-1, 0, 0, 1).Rotate(this);
  }

  public float GetX()
  {
    return m_x;
  }

  public float GetY()
  {
    return m_y;
  }

  public float GetZ()
  {
    return m_z;
  }


  public float GetW()
  {
    return m_w;
  }

  public boolean equals(Quaternion r)
  {
    return m_x == r.GetX() && m_y == r.GetY() && m_z == r.GetZ() && m_w == r.GetW();
  }
}
