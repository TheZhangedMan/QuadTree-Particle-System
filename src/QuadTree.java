import java.awt.Rectangle;
import java.awt.Graphics;

/**
 * [QuadTree].java
 * A tree with nodes with four children
 * @author Ethan Zhang
 * 2020/11/6
 */
class QuadTree {
  private QuadTreeNode root;
  
  /**
   * QuadTree constructor
   */
  QuadTree() {
    root = new QuadTreeNode(new Rectangle(0, 0, 1024, 1024));
  }
  
  /**
   * orderDraw
   * Calls the recursive method that draws each node's boundary
   * @param g, the Graphics class required to draw the boundaries
   */
  public void orderDraw(Graphics g) {
    orderDraw(root, g);
  }
  
  /**
   * orderDraw
   * The recursive helper method that draws each node's boundary in order
   * @param currentNode, the current node being drawn
   * @param g, the Graphics class required to draw the boundaries
   */
  private void orderDraw(QuadTreeNode currentNode, Graphics g) {
    if (currentNode != null) {
      if (currentNode.getChildren() != null) {
        orderDraw(currentNode.getChildren()[0], g);
        orderDraw(currentNode.getChildren()[1], g);
      }
      currentNode.draw(g);
      if (currentNode.getChildren() != null) {
        orderDraw(currentNode.getChildren()[2], g);
        orderDraw(currentNode.getChildren()[3], g);
      }
    }
  }
  
  /**
   * insert
   * Inserts a BouncingBall into the QuadTree
   * @param ball, the BouncingBall to be added
   */
  public void insert(BouncingBall ball) {
    root.insert(ball);
  }
  
  /**
   * update
   * Updates all properties of balls in the QuadTree
   */
  public void update() {
    root.update();
  }
  
  /**
   * checkCollision
   * Initiates the algorithm to check all collisions of balls
   */
  public void checkCollision() {
    root.checkCollision();
  }
  
  /**
   * getMasterList
   * Returns the comprehensive list of balls
   * @return SingleLinkedList<BouncingBall>, the comprehensive list of existing balls
   */
  public SingleLinkedList<BouncingBall> getMasterList() {
    return root.getLocalBalls();
  }
}