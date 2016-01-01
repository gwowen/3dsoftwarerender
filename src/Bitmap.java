import java.util.Arrays;

public class Bitmap
{
  // Width in pixels of image
  private final int m_width;
  // Width in height of image
  private final int m_height;
  // Every pixel component of image
  private final char m_components[];


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
    m_components = new char[width * height * 4];
  }

  // clears the bitmap to a shade of gray
  public void Clear(char shade)
  {
    Arrays.fill(m_components, shade);
  }

  public void DrawPixel(int x, int y, char a, char r, char g, char b)
  {
    // represents the index of the cpt we need to start modifiying
    int index = (x + y + m_width) * 4;
    m_components[index]     = a;
    m_components[index + 1] = r;
    m_components[index + 2] = g;
    m_components[index + 3] = b;
  }

  public void CopyToIntArray(int[] dest)
  {
    for(int i = 0; i < m_width * m_height; i++)
    {
      /* chars = 1 byte in Java
      *  int = 4 bytes in Java
      *  So we do some bit manipulation to get them correctly aligned.
      *  8 bits in a byte, so we do... 8 for g, 16 for b and so on...
      */
      int a =  ((int)m_components[i * 4]) << 24;
      int r =  ((int)m_components[i * 4 + 1]) << 16;
      int g =  ((int)m_components[i * 4 + 2]) << 8;
      int b =  ((int)m_components[i * 4 + 3]);

      // We do a bitwise OR on our components to form a single integer
      dest[i] = a | r | g | b;
    }
  }
}
