import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Input implements KeyListener, FocusListener,
  MouseListener, MouseMotionListener
  {

    private boolean[] keys = new boolean[65536];
    private boolean[] mouseButtons = new boolean[4];

    public void focusLost(FocusEvent e)
    {
      for(int i = 0; i < keys.length; i++)
        keys[i] = false;
    }

    public void keyPressed(KeyEvent e)
    {
      int code = e.getKeyCode();
      if(code > 0 && code < keys.length)
        keys[code] = true;
    }

    public void keyReleased(KeyEvent e)
    {
      int code = e.getKeyCode();
      if(code > 0 && code < keys.length)
        keys[code] = false;
    }
  }
