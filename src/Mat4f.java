public class Mat4f
{
  private float[][] m;

  public Mat4f()
  {

  }

  public Mat4f InitIdentity()
  {

  }

  public Mat4f InitScreenSpaceTransform(float halfWidth, float halfHeight)
  {

  }

  public Mat4f InitTranslation(float x, float y, float z)
  {

  }

  public Mat4f InitRotation(float x, float y, float z, float angle)
  {

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
