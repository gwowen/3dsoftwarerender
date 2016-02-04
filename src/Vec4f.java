public class Vec4f
{
  // x, y, z and w coordinates of the vector
  public final float x;
  public final float y;
  public final float z;
  public final float w;

  // constructor
  public Vec4f(float x, float y, float z, float w)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  // returns the length of the vector
  public float Length()
  {
    return (float)Math.sqrt(x * x + y * y + z * z + w * w);
  }

  // returns the greater of the pair of x and y, and z and w
  // values
  public float Max()
  {
      return Math.max(Math.max(x, y), Math.max(z, w));
  }

  // Dot product
  public float Dot(Vec4f r)
  {
    return x * r.GetX() + y * r.GetY() +  z * r.GetZ() + w * r.GetW();
  }

  // Cross product
  public Vec4f Cross(Vec4f r)
  {
    float x_ = y * r.GetZ() - z * r.GetY();
    float y_ = z * r.GetX() - x * r.GetZ();
    float z_ = x * r.GetY() - y * r.GetX();

    return new Vec4f(x_, y_, z_, 0);
  }

  // Returns the normalized vector
  public Vec4f Normalized()
  {
    float length = Length();

    return new Vec4f(x / length, y / length, z / length, w / length);
  }

  // Rotation
  public Vec4f Rotate(Vec4f axis, float angle)
  {
    float sinAngle = (float)Math.sin(-angle);
    float cosAngle = (float)Math.cos(-angle);

    return this.Cross(axis.Mul(sinAngle)).Add( //rotation on local X
    (this.Mul(cosAngle)).Add( // rotation on local Z
    axis.Mul(this.Dot(axis.Mul(1 - cosAngle))))); // rotation on local Y
  }

  // linear interpolation
  public Vec4f Lerp(Vec4f dest, float lerpFactor)
  {
    return dest.Sub(this).Mul(lerpFactor).Add(this);
  }

  // Addition of another vector
  public Vec4f Add(Vec4f r)
  {
    return new Vec4f(x + r.GetX(), y + r.GetY(), z + r.GetZ(), w + r.GetW());
  }

  // Addition of another scalar
  public Vec4f Add(float r)
  {
    return new Vec4f(x + r, y + r, z + r, w + r);
  }

  // Subtraction of a vector
  public Vec4f Sub(Vec4f r)
  {
    return new Vec4f(x - r.GetX(), y - r.GetY(), z - r.GetZ(), w - r.GetW());
  }

  //Subtraction of a scalar
  public Vec4f Sub(float r)
  {
    return new Vec4f(x - r, y - r, z - r, w - r);
  }

  // Multiplication by vector
  public Vec4f Mul(Vec4f r)
  {
    return new Vec4f(x * r.GetX(), y * r.GetY(), z * r.GetZ(), w * r.GetW());
  }

  // Multiplication by scalar
  public Vec4f Mul(float r)
  {
    return new Vec4f(x * r, y * r, z * r, w * r);
  }

  // Division by vector
  public Vec4f Div(Vec4f r)
  {
    return new Vec4f(x / r.GetX(), y / r.GetY(), z / r.GetZ(), w / r.GetW());
  }

  // Division by scalar
  public Vec4f Div(float r)
  {
    return new Vec4f(x / r, y / r, z / r, w / r);
  }

  // Returns an absolute version of the vector
  public Vec4f Abs()
  {
    return new Vec4f(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
  }

  // Prints the vector in string form
  public String toString()
  {
    return "(" + x + "," + y + "," + z + "," + w + ")";
  }

  //Getters for x, y, z and w
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

  // We can't overload exactly,
  // but we can cheat. Sort-of overloaded
  // equality operator for comparing vectors
  public boolean equals(Vec4f r)
  {
    return x == r.GetX() && y == r.GetY() && z == r.GetZ() && w == r.GetW();
  }

}
