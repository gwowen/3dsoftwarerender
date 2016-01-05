import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferByte;
import javax.swing.JFrame;

/**
* Represents a window that can be drawn in using a software renderer
*/

public class Display extends Canvas
{
  // The window being used for display
  private final JFrame m_frame;
  // The rendercontext representing the final image to display
  private final RenderContext m_frameBuffer;
  // Used to display the framebuffer in the window
  private final BufferedImage m_displayImage;
  // The pixels of the display image as an array of byte components
  private final byte[] m_displayComponents;
  // The buffers in the Canvas
  private final BufferStrategy m_bufferStategy;
  // A graphics object that draws into the Canvas's buffers
  private final Graphics m_graphics;

  public RenderContext GetFrameBuffer() { return m_frameBuffer; }

  /**
  * Create and initialize the Display
  *
  * @param width  How wide the display is in pixels
  * @param height How high the display is in pixels
  * @param title  The text displayed in the window's title bar
  */

  public Display(int width, int height, String title)
  {
    // Guard against unintentional resizing by setting preferred minimum,
    // maximum and preferred size
    Dimension size = new Dimension(width, height);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);

    // create images used for display
    m_frameBuffer = new RenderContext(width, height);
    m_displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    //use this to get direct access to the pixels of the image
    m_displayComponents = ((DataBufferByte)m_displayImage.getRaster().getDataBuffer()).getData();

    m_frameBuffer.Clear((byte)0x80);
    m_frameBuffer.DrawPixel(100, 100, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF);

    // create a JFrame specifically to show the display
    m_frame = new JFrame();
    m_frame.add(this);
    m_frame.pack();
    m_frame.setResizable(false);
    m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    m_frame.setLocationRelativeTo(null);
    m_frame.setTitle(title);
    m_frame.setVisible(true);

    // allocate 1 display buffer, and use the buffer strategy to get access
    // and a graphics object to draw into it
    createBufferStrategy(1);
    m_bufferStategy = getBufferStrategy();
    m_graphics = m_bufferStategy.getDrawGraphics();
  }

  /**
  * Displays drawn image in the window
  */
  public void SwapBuffers()
  {
    // copy the framebuffer Bitmap's components to display components
    // then draw
    m_frameBuffer.CopyToByteArray(m_displayComponents);
    m_graphics.drawImage(m_displayImage, 0, 0, m_frameBuffer.GetWidth(),
      m_frameBuffer.GetHeight(), null);
    m_bufferStategy.show();
  }
}
