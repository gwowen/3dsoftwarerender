import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferByte;
import javax.swing.JFrame;


public class Display extends Canvas
{
  private final JFrame m_frame;
  private final Bitmap m_frameBuffer;
  private final BufferedImage m_displayImage;
  private final byte[] m_displayComponents;
  private final BufferStrategy m_bufferStategy;
  private final Graphics m_graphics;


  public Display(int width, int height, String title)
  {
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);

    m_frameBuffer = new Bitmap(width, height);
    m_displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    //use this to get direct access to the pixels of the image
    m_displayComponents = ((DataBufferByte)m_displayImage.getRaster().getDataBuffer()).getData();

    m_frame = new JFrame();
    m_frame.add(this);
    m_frame.pack();
    m_frame.setResizable(false);
    m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    m_frame.setLocationRelativeTo(null);
    m_frame.setTitle(title);
    m_frame.setVisible(true);

    //allocate the buffer
    createBufferStrategy(1);
    m_bufferStategy = getBufferStrategy();
    m_graphics = m_bufferStategy.getDrawGraphics();
  }

  public void SwapBuffers()
  {
    m_frameBuffer.CopyToByteArray(m_displayComponents);
    m_graphics.drawImage(m_displayImage, 0, 0, m_frameBuffer.GetWidth(),
      m_frameBuffer.GetHeight(), null);
    m_bufferStategy.show();
  }
}
