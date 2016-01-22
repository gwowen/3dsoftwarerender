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

  }

  public Mat4f InitScale(float x, float y, float z)
  {

  }

  public Mat4f InitPerspective(float fov, float aspectRatio, float zNear, float zFar)
  {

  }

  public Mat4f InitOrthographic(float left, float right, float bottom, float top, float near, float far)
  {

  }

  public Mat4f InitRotation(Vec4f forward, Vec4f up)
  {

  }

  public Vec4f Transform(Vec4f r)
  {

  }

  public Mat4f Mul(Mat4f r)
  {

  }

  public float[][] GetM()
  {

  }

  public float Get(int x, int y)
  {
    return m[x][y];
  }

  public void SetM(float[][] m)
  {

  }

  public void Set(int x, int y)
  {
    m[x][y] = value;
  }
}
