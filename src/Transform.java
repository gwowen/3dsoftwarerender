public class Transform
{
  private Vec4f m_pos;
  private Quaternion m_rot;
  private Vec4f m_pos;

  public Transform()
  {
    this(new Vec4f(0, 0, 0, 0));
  }

  public Transform(Vec4f pos)
  {
    this(pos, new Quaternion(0, 0, 0, 1), new Vec4f(1, 1, 1, 1));
  }

  public Transform(Vec4f pos, Quaternion rot, Vec4f scale)
  {
    m_pos = pos;
    m_rot = rot;
    m_scale = scale;
  }

  public Transform SetPos(Vec4f pos)
  {
    return new Transform(pos, m_rot, m_scale);
  }

  public Transform Rotate(Quaternion rotation)
  {
    return new Transform(m_pos, rotation.Mul(m_rot).Normalized(), m_scale);
  }

  public Transform LookAt(Vec4f point, Vec4f up)
  {
    return Rotate(GetLookAtRotation(point, up));

  }

  public Quaternion GetLookAtRotation(Vec4f point, Vec4f up)
  {
    return new Quaternion(new Mat4f().InitRotation(point.Sub(m_pos).Normalized(),
                          up));
  }

  public Mat4f GetTransformation()
  {
    Mat4f translationMatrix = new Mat4f().InitTranslation(m_pos.GetX(),
                                                          m_pos.GetY(),
                                                          m_pos.GetZ());
    Mat4f rotationMatrix = m_rot.ToRotationMatrix();
    Mat4f scaleMatrix = new Mat4f().InitScale(m_scale.GetX(),
                                              m_scale.GetY(),
                                              m_scale.GetZ());

    return translationMatrix.Mul(rotationMatrix.Mul(scaleMatrix));
  }

  public Vec4f GetTransformedPos()
  {
    return m_pos;
  }

  public Quaternion GetTransformedRot()
  {
    return m_rot;
  }

  public Vec4f GetPos()
  {
    return m_pos;
  }

  public Quaternion GetRot()
  {
    return m_rot;
  }

  public Vec4f GetScale()
  {
    return m_scale;
  }
}
