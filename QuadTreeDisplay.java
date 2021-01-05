//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//These are probably worth importing
import java.awt.Point;
import java.awt.Rectangle;

/**
 * [QuadTreeDisplay].java
 * This template can be used as reference or a starting point for your QuadTree Assignment
 * @author Mr. Mangat, Ethan Zhang
 * 2020/11/6
 */
class QuadTreeDisplay extends JFrame { 
  private static GameAreaPanel gamePanel;  
  private QuadTree segments = new QuadTree();
  
  /**
   * main
   * The main method that runs the program
   * @param args, a String array of arguments
   */
  public static void main(String[] args) { 
    new QuadTreeDisplay ();
  }
  
  /**
   * QuadTreeDisplay constructor
   */
  QuadTreeDisplay () { 
    super("QuadTree Fun!");      
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1024, 1024);
    this.setUndecorated(false);
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());    
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    this.requestFocusInWindow();    
    this.setVisible(true);
    
    //***** Initialize the Tree here *****
    //new QuadTree etc..
    
    //Start the game loop in a separate thread (yikes)
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
    
  } //End of Constructor
  
  /**
   * animate
   * Refreshes the simulation frame
   */
  public void animate() { 
    while(true){
      try{ Thread.sleep(20);} catch (Exception exc){exc.printStackTrace();}  //delay
      this.repaint();
    }    
  }
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
    public void paintComponent(Graphics g) {   
      super.paintComponent(g); //required
      setDoubleBuffered(true); 
      g.setColor(Color.BLUE); //There are many graphics commands that Java can use
      
      segments.orderDraw(g);
      segments.update();
      
      for (int i = 0; i < segments.getMasterList().size(); i++) {
        g.fillOval((int)segments.getMasterList().get(i).getX(), (int)segments.getMasterList().get(i).getY(), 30, 30);
      }
      
      segments.checkCollision();
    }
  }
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
      
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {  
        segments.insert(new BouncingBall(Math.floor(Math.random() * 991), Math.floor(Math.random() * 991),
                                             Math.floor(Math.random() * 21) - 10, 
                                             Math.floor(Math.random() * 21) - 10));
      } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { 
        System.exit(0);
      }
    }              
  }  
}