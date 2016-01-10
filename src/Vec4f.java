public class Vec4f
{
  // Yet ANOTHER vector class!
  public final float x;
  public final float y;
  public final float z;
  public final float w;

  public Vec4f(float x, float y, float z, float w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public float Length()
  {
    return (float)Math.sqrt(x * x + y * y + z * z + w * w);
  }

  public float Max()
  {
      return Math.max(Math.max(x, y), Math.max(z, w));
  }

  public float Dot(Vec4f r)
  {
    return x * r.GetX() + y * r.GetY() + r.GetZ() + r.GetW();
  }

  public Vec4f Cross(Vec4f r)
  {
    float x_ = y * r.GetZ() - z * r.GetY();
    float y_ = z * r.GetX() - x * r.GetZ();
    float z_ = x * r.GetY() - y * r.GetX();

    return new Vec4f(x_, y_, z_, 0);
  }

  public Vec4f Normalized()
  {
    float length = Length()

    return new Vec4f(x / length, y / length, z / length, w / length);
  }

  public Vec4f Rotate(Vec4f axis, float angle)
  {

  }

  public Vec4f Lerp(Vec4f dest, float lerpFactor)
  {

  }

  public Vec4f Add(Vec4f r)
  {

  }

  public Vec4f Add(float r)
  {

  }

  public Vec4f Sub(Vec4f r)
  {

  }

  public Vec4f Sub(float r)
  {

  }

  public Vec4f Mul(Vec4f r)
  {

  }

  public Vec4f Mul(float r)
  {

  }

  public Vec4f Div(Vec4f r)
  {

  }

  public Vec4f Div(float r)
  {

  }

  public Vec4f Abs()
  {

  }

  public String toString()
  {
    return "(" + x + "," + y + "," + z + "," + w + ")";
  }

  public float GetX()
  {
    return x;
  }

  public float GetY()
  {
    return y;
  }

  public float GetZ()
  {
    return z;
  }

  public float GetW()
  {
    return w;
  }

  public boolean equals(Vector4f r)
  {
    return x == r.GetX() && y == r.GetY() && z == r.GetZ() && w == r.GetW();
  }

}
