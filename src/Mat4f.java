public class Mat4f
{
  private float[][] m;

  public Mat4f()
  {
    m = new float[4][4]
  }

  public Mat4f InitIdentity()
  {
    m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
    m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
    m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

    return this;
  }

  public Mat4f InitScreenSpaceTransform(float halfWidth, float halfHeight)
  {
    m[0][0] = halfWidth; m[0][1] = 0; m[0][2] = 0; m[0][3] = halfWidth;
    m[1][0] = 0; m[1][1] =-halfHeight; m[1][2] = 0; m[0][2] = halfHeight;
    m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

    return this;
  }

  public Mat4f InitTranslation(float x, float y, float z)
  {
    m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
    m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[0][2] = y;
    m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

    return this;
  }

  public Mat4f InitRotation(float x, float y, float z, float angle)
  {
    float sin = (float)Math.sin(angle);
    float cos = (float)Math.cos(angle);

    m[0][0] = cos + x*x*(1-cos); m[0][1] = x*y*(1-cos)-z*sin; m[0][2] = x*z*(1-cos) + y*sin; m[0][3] = 0;
    m[1][0] = y*x*(1-cos) + z*sin; m[1][1] = cos + y*y*(1-cos); m[1][2] = y*z*(1-cos) - x*sin; m[1][3] = 0;
    m[2][0] = z*x*(1-cos) - y*sin; m[2][1] = z*y*(1-cos) + x*sin; m[2][2] = cos + z*z*(1-cos); m[2][3] = 0;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

    return this;
  }

  public Mat4f InitRotation(float x, float y, float z)
  {
    Mat4f rx = new Mat4f();
    Mat4f ry = new Mat4f();
    Mat4f rz = new Mat4f();

    rz.m[0][0] = (float)Math.coz(z); rz.m[0][1] = -(float)Math.sin(z); rz.m[0][2] = 0; rz.m[0][3] = 0;
    rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z); rz.m[1][2] = 0; rz.m[1][3] = 0;
    rz.m[2][0] = 0; rz.m[2][1] = 0; rz.m[2][2] = 1; rz.m[2][3] = 0;
    rz.m[3][0] = 0; rz.m[3][1] = 0; rz.m[3][2] = 0; rz.m[3][3] = 1;

    rx.m[0][0] = 1; rx.m[0][1] = 0; rx.m[0][2] = 0; rx.m[0][3] = 0;
    rx.m[1][0] = 0; rx.m[1][1] = (float)Math.cos(x); rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
    rx.m[2][0] = 0; rx.m[2][1] = (float)Math.sin(x); rx.m[2][2] = (float)Math.cos(x); rx.m[2][3] = 0;
    rx.m[3][0] = 0; rx.m[3][1] = 0; rx.m[3][2] = 0; rx.m[3][3] = 1;

    ry.m[0][0] = (float)Math.cos(y); ry.m[0][1] = 0; ry.m[0][2] = -(float)Math.sin(x); ry.m[0][3] = 0;
    ry.m[1][0] = 0; ry.m[1][1] = 1; ry.m[1][2] = 0; ry.m[1][3] = 0;
    ry.m[2][0] = (float)Math.sin(y); ry.m[2][1] = 0; ry.m[2][2] = (float)Math.cos(y); ry.m[2][3] = 0;
    ry.m[3][0] = 0; ry.m[3][1] = 0; ry.m[3][2] = 0; ry.m[3][3] = 1;

    m = rz.Mul(ry.Mul(rx)).GetM();
    return this;

  }

  public Mat4f InitScale(float x, float y, float z)
  {
    m[0][0] = x; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
    m[1][0] = 0; m[1][1] = y; m[1][2] = 0; m[1][3] = 0;
    m[2][0] = 0; m[2][1] = 0; m[2][2] = z; m[2][3] = 0;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

    return this;
  }

  public Mat4f InitPerspective(float fov, float aspectRatio, float zNear, float zFar)
  {
    float tanHalfFOV = (float)Math.tan(fov / 2);
    float zRange = zNear - zFar;

    m[0][0] = 1.0f / (tanHalfFOV * aspectRatio); m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
    m[1][0] = 0; m[1][1] = 1.0f / tanHalfFOV; m[1][2] = 0; m[1][3] = 0;
    m[2][0] = 0; m[2][1] = 0; m[2][2] = (-zNear -zFar) / zRange; m[2][3] = 0;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 1; m[3][3] = 0;

    return this;
  }

  public Mat4f InitOrthographic(float left, float right, float bottom, float top, float near, float far)
  {
    float width = right - left;
    float height = top - bottom;
    float depth = far - near;

    m[0][0] = 2 / width; m[0][1] = 0; m[0][2] = 0; m[0][3] = -(right + left) / width;
    m[1][0] = 0; m[1][1] = 2 / height; m[1][2] = 0; m[1][3] = -(top + bottom) / height;
    m[2][0] = 0; m[2][1] = 0; m[2][2] = - 2 / depth; m[2][3] = -(far + near) / depth;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

    return this;
  }

  public Mat4f InitRotation(Vec4f forward, Vec4f up)
  {
    Vec4f f  = forward.Normalized();

    Vec4f r = up.Normalized();

    r = r.Cross(f);

    Vec4f u = f.Cross(r);

    return InitRotation(f, u, r);
  }

  public Mat4f InitRotation(Vec4f forward, Vec4f up, Vec4f right)
  {
    Vec4f f = forward;
    Vec4f r = right;
    Vec4f u = up;

    m[0][0] = r.GetX(); m[0][1] = r.GetY(); m[0][2] = r.GetZ(); m[0][3] = 0;
    m[1][0] = u.GetX(); m[1][1] = u.GetY(); m[1][2] = u.GetZ(); m[1][3] = 0;
    m[2][0] = f.GetX(); m[2][1] = f.GetY(); m[2][2] = f.GetZ(); m[2][3] = 0;
    m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

    return this;
  }

  public Vec4f Transform(Vec4f r)
  {
    return new Vec4f(m[0][0] * r.GetX() + m[0][1] * r.GetY() + m[0][2] * r.GetZ() + m[0][3] * r.GetW(),
                     m[1][0] * r.GetX() + m[1][1] * r.GetY() + m[1][2] * r.GetZ() + m[1][3] * r.GetW(),
                     m[2][0] * r.GetX() + m[2][1] * r.GetY() + m[2][2] * r.GetZ() + m[2][3] * r.GetW(),
                     m[3][0] * r.GetX() + m[3][1] * r.GetY() + m[3][2] * r.GetZ() + m[3][3] * r.GetW());

  }

  public Mat4f Mul(Mat4f r)
  {
    Mat4f res = new Mat4f();

    for(int i = 0; i < 4; i++)
    {
      for(int j = 0; j < 4; j ++)
      {
        res.Set(i, j, m[i][0] * r.Get(0, j) +
        m[i][1] * r.Get(1, j) +
        m[i][2] * r.Get(2, j) +
        m[i][3] * r.Get(3, j));
      }
    }

    return res;
  }

  public float[][] GetM()
  {
    float[][] = new float[4][4];

    for(int i = 0; i < 4; i++)
      for(int j = 0; j < 4; j++)
        res[i][j] = m[i][j];

    return res;
  }

  public float Get(int x, int y)
  {
    return m[x][y];
  }

  public void SetM(float[][] m)
  {
    this.m = m;
  }

  public void Set(int x, int y)
  {
    m[x][y] = value;
  }
}