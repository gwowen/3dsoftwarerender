import java.awt.event.KeyEvent;

public class Camera
{
  private Transform m_transform;
  private Mat4f m_projection;

  private Transform GetTransform()
  {
      return m_transform;
  }

  public Camera(Mat4f projection)
  {
    this.m_projection = projection;
    this.m_transform = new Transform();
  }

  public Matrix4f GetViewProjection()
  {
    Mat4f cameraRotation = GetTransform().GetTransformedRot().Conjugate().ToRotationMatrix();
    Vec4f cameraPos = GetTransform().GetTransformedPos().Mul(-1);

    Mat4f cameraTranslation = new Mat4f().InitTranslation(cameraPos.GetX(),
                                                          cameraPos.GetY(),
                                                          cameraPos.GetZ());

    return m_projection.Mul(cameraRotation.Mul(cameraTranslation));
  }

  public void Update(Input input, float delta)
  {
    final float sensitivityX = 2.66f * delta;
    final float sensitivityY = 2.0f * delta;
    final float movAmt = 5.0f * delta;

    if(input.GetKey(KeyEvent.VK_W))
			Move(GetTransform().GetRot().GetForward(), movAmt);
		if(input.GetKey(KeyEvent.VK_S))
			Move(GetTransform().GetRot().GetForward(), -movAmt);
		if(input.GetKey(KeyEvent.VK_A))
			Move(GetTransform().GetRot().GetLeft(), movAmt);
		if(input.GetKey(KeyEvent.VK_D))
			Move(GetTransform().GetRot().GetRight(), movAmt);

		if(input.GetKey(KeyEvent.VK_RIGHT))
			Rotate(Y_AXIS, sensitivityX);
		if(input.GetKey(KeyEvent.VK_LEFT))
			Rotate(Y_AXIS, -sensitivityX);
		if(input.GetKey(KeyEvent.VK_DOWN))
			Rotate(GetTransform().GetRot().GetRight(), sensitivityY);
		if(input.GetKey(KeyEvent.VK_UP))
			Rotate(GetTransform().GetRot().GetRight(), -sensitivityY);
  }

  private void Move(Vec4f dir, float amt)
  {
    m_transform = GetTransform().SetPos(GetTransform().GetPos().Add(dir.Mul(amt)));
  }

  private void Rotate(Vec4f axis, float angle)
  {
    m_transform = GetTransform().Rotate(new Quaternion(axis, angle));
  }
}
