import java.util.Arrays;

public class Bitmap
{
  // Width in pixels of image
  private final int m_width;
  // Width in height of image
  private final int m_height;
  // Every pixel component of image
  private final byte m_components[];


  public Bitmap(int width, int height)
  {
    m_width = width;
    m_height = height;
    /* * 4 as we have 4 components, R, G, B and A
    * OK, so this is a bit of a weird way of storing a component,
    * but the multiple arrays thing has been a pain for me in the past, so we have
    * m_components = ARGBARGBARGBARGBARGBARGBARGBARGB... and so on
    * because, however, we know that every fourth cpt of the array is B, etc
    * we can just get to it by adding 1, 2 etc to our A cpt index, which will always
    * be a multiple of 4. Again, weird, but doing a[x][y] = blah has proven annoying in
    * the past...
    */
    m_components = new byte[width * height * 4];
  }

  // clears the bitmap to a shade of gray
  public void Clear(byte shade)
  {
    Arrays.fill(m_components, shade);
  }

  // sets the pixel at (x, y) to the colour specified as (a, b, g, r)
  public void DrawPixel(int x, int y, byte a, byte b, byte g, byte r)
  {
    // represents the index of the cpt we need to start modifiying
    int index = (x + y + m_width) * 4;
    m_components[index]     = a;
    m_components[index + 1] = r;
    m_components[index + 2] = g;
    m_components[index + 3] = b;
  }

  public void CopyToByteArray(byte[] dest)
  {
    for(int i = 0; i < m_width * m_height; i++)
    {
      // so basically, we can use the byte type in Java instead, so
      // we're going to discard the alpha component for now. So now it's
      // i * 3 (destination) = i * 4 + 1 to skip over the alpha component
      dest[i * 3] = m_components[i* 4 + 1];
      dest[i * 3 + 1] = m_components[i* 4 + 2];
      dest[i * 3 + 2] = m_components[i* 4 + 3];
    }
  }
}
