public class Vec4f
{
	private final float x;
	private final float y;
	private final float z;
	private final float w;

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
		return x * r.GetX() + y * r.GetY() + z * r.GetZ() + w * r.GetW();
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
		float length = Length();

		return new Vec4f(x / length, y / length, z / length, w / length);
	}

	public Vec4f Rotate(Vec4f axis, float angle)
	{
		float sinAngle = (float)Math.sin(-angle);
		float cosAngle = (float)Math.cos(-angle);

		return this.Cross(axis.Mul(sinAngle)).Add(           //Rotation on local X
				(this.Mul(cosAngle)).Add(                     //Rotation on local Z
						axis.Mul(this.Dot(axis.Mul(1 - cosAngle))))); //Rotation on local Y
	}

	public Vec4f Rotate(Quaternion rotation)
	{
		Quaternion conjugate = rotation.Conjugate();

		Quaternion w = rotation.Mul(this).Mul(conjugate);

		return new Vec4f(w.GetX(), w.GetY(), w.GetZ(), 1.0f);
	}


	public Vec4f Lerp(Vec4f dest, float lerpFactor)
	{
		return dest.Sub(this).Mul(lerpFactor).Add(this);
	}

	public Vec4f Add(Vec4f r)
	{
		return new Vec4f(x + r.GetX(), y + r.GetY(), z + r.GetZ(), w + r.GetW());
	}

	public Vec4f Add(float r)
	{
		return new Vec4f(x + r, y + r, z + r, w + r);
	}

	public Vec4f Sub(Vec4f r)
	{
		return new Vec4f(x - r.GetX(), y - r.GetY(), z - r.GetZ(), w - r.GetW());
	}

	public Vec4f Sub(float r)
	{
		return new Vec4f(x - r, y - r, z - r, w - r);
	}

	public Vec4f Mul(Vec4f r)
	{
		return new Vec4f(x * r.GetX(), y * r.GetY(), z * r.GetZ(), w * r.GetW());
	}

	public Vec4f Mul(float r)
	{
		return new Vec4f(x * r, y * r, z * r, w * r);
	}

	public Vec4f Div(Vec4f r)
	{
		return new Vec4f(x / r.GetX(), y / r.GetY(), z / r.GetZ(), w / r.GetW());
	}

	public Vec4f Div(float r)
	{
		return new Vec4f(x / r, y / r, z / r, w / r);
	}

	public Vec4f Abs()
	{
		return new Vec4f(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
	}

	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
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

	public boolean equals(Vec4f r)
	{
		return x == r.GetX() && y == r.GetY() && z == r.GetZ() && w == r.GetW();
	}
}
