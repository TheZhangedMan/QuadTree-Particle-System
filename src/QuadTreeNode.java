import java.awt.Rectangle;
import java.awt.Graphics;

/**
 * [QuadTreeNode].java
 * A node for a quad tree
 * @author Ethan Zhang
 * 2020/11/6
 */
class QuadTreeNode {
  private static final int THRESHOLD = 6;
  private Rectangle boundary;
  private SingleLinkedList<BouncingBall> localBalls;
  private QuadTreeNode[] children;
  
  /**
   * QuadTreeNode constructor
   * @param boundary, the Rectangle class for the boundary of the node
   */
  QuadTreeNode(Rectangle boundary) {
    this.boundary = boundary;
    localBalls = new SingleLinkedList<BouncingBall>();
  }
  
  /**
   * subdivide
   * Creates four children nodes for the respective node
   */
  public void subdivide() {
    children = new QuadTreeNode[4];
    double halfWidth = boundary.getWidth() / 2;
    double halfHeight = boundary.getHeight() / 2;
    children[0] = new QuadTreeNode(new Rectangle((int)boundary.getX(), (int)boundary.getY(), (int)halfWidth, 
                                                 (int)halfHeight));
    children[1] = new QuadTreeNode(new Rectangle((int)(boundary.getX() + halfWidth), (int)boundary.getY(), 
                                                 (int)halfWidth, (int)halfHeight));
    children[2] = new QuadTreeNode(new Rectangle((int)boundary.getX(), (int)(boundary.getY() + halfHeight), 
                                                 (int)halfWidth, (int)halfHeight));
    children[3] = new QuadTreeNode(new Rectangle((int)(boundary.getX() + halfWidth), 
                                                 (int)(boundary.getY() + halfHeight), (int)halfWidth, 
                                                 (int)halfHeight));
  }
  
  /**
   * collapse
   * Removes the children of a node
   */
  public void collapse() {
    children = null;
  }
  
  /**
   * getChildren
   * Returns the children of the node
   * @return QuadTreeNode[], an array of children
   */
  public QuadTreeNode[] getChildren() {
    return children;
  }
  
  /**
   * getLocalBalls
   * Returns the local balls within a boundary
   * @return SingledLinkedList<BouncingBall>, the list of local balls
   */
  public SingleLinkedList<BouncingBall> getLocalBalls() {
    return localBalls;
  }
  
  /**
   * inBounds
   * Checks if a rectangle is in the node's boundary
   * @param r, the Rectangle class to be checked
   * @return boolean, true if the rectangle is contained
   */
  public boolean inBounds(Rectangle r) {
    return boundary.intersects(r);
  }
  
  /**
   * insert
   * Inserts a ball into the root list
   * @param ball, the ball to be added
   */
  public void insert(BouncingBall ball) {
    localBalls.add(ball);
    
    if (localBalls.size() >= THRESHOLD && children == null) {
      subdivide();
    }
  }
  
  /**
   * update
   * Initiates the updating sequence for all balls and nodes, first updating ball positions and checking border 
   * collisions
   */
  public void update() {
    for (int i = 0; i < localBalls.size(); i++) {
      localBalls.get(i).updatePosition();
      localBalls.get(i).checkBorderCollision();
    }
    updateBallSegments();
  }
  
  /**
   * updateBallSegments
   * Makes sure that all balls are in the correct boundary and subdivides and collapses accordingly
   */
  public void updateBallSegments() {
    for (int i = 0; i < localBalls.size(); i++) {
      if (!inBounds(localBalls.get(i).getHitbox())) {
        remove(localBalls.get(i));
      }
    }
    if (children != null) {
      children[0].getLocalBalls().clear();
      children[1].getLocalBalls().clear();
      children[2].getLocalBalls().clear();
      children[3].getLocalBalls().clear();
      for (int i = 0; i < localBalls.size(); i++) {
        if (children[0].inBounds(localBalls.get(i).getHitbox())) {
          children[0].insert(localBalls.get(i));
        }
        if (children[1].inBounds(localBalls.get(i).getHitbox())) {
          children[1].insert(localBalls.get(i));
        }
        if (children[2].inBounds(localBalls.get(i).getHitbox())) {
          children[2].insert(localBalls.get(i));
        }
        if (children[3].inBounds(localBalls.get(i).getHitbox())) {
          children[3].insert(localBalls.get(i));
        }
      }
      children[0].updateBallSegments();
      children[1].updateBallSegments();
      children[2].updateBallSegments();
      children[3].updateBallSegments();
      if (localBalls.size() < THRESHOLD) {
        collapse();
      }
    }
    if (localBalls.size() >= THRESHOLD && children == null) {
      subdivide();
    }
  }
  
  /**
   * remove
   * Removes a bouncing ball from the root list
   * @param ball, the ball to be removed
   */
  public void remove(BouncingBall ball) {
    localBalls.remove(ball);
    if (children != null) {
      children[0].remove(ball);
      children[1].remove(ball);
      children[2].remove(ball);
      children[3].remove(ball);
    }
  }
  
  /**
   * checkCollision
   * Checks collisions of balls in a node
   */
  public void checkCollision() {
    if (children == null) {
      for (int i = 0; i < localBalls.size(); i++) {
        for (int j = 0; j < localBalls.size(); j++) {
          if (localBalls.get(i).getHitbox().intersects(localBalls.get(j).getHitbox()) && 
              !localBalls.get(i).equals(localBalls.get(j)) &&
              (localBalls.get(i).getRecentCollision() == null || 
               !localBalls.get(i).getRecentCollision().equals(localBalls.get(j)))) {
            double tempX = localBalls.get(i).getX();
            double tempY = localBalls.get(i).getY();
            double tempVX = localBalls.get(i).getVelocityX();
            double tempVY = localBalls.get(i).getVelocityY();
            localBalls.get(i).collide(localBalls.get(j).getX(), localBalls.get(j).getY(), 
                                      localBalls.get(j).getVelocityX(), localBalls.get(j).getVelocityY());
            localBalls.get(i).setRecentCollision(localBalls.get(j));
            localBalls.get(j).collide(tempX, tempY, tempVX, tempVY);
            localBalls.get(j).setRecentCollision(localBalls.get(i));
          }
        }
      }
      
      for (int i = 0; i < localBalls.size(); i++) {
        if (localBalls.get(i).getRecentCollision() != null && 
            !localBalls.get(i).getHitbox().intersects(localBalls.get(i).getRecentCollision().getHitbox())) {
          localBalls.get(i).setRecentCollision(null);
        }
      }
    } else {
      children[0].checkCollision();
      children[1].checkCollision();
      children[2].checkCollision();
      children[3].checkCollision();
    }
  }
  
  /**
   * isLeaf
   * Identifies if a node has no children or not
   * @return boolean, true if there are no children
   */
  public boolean isLeaf() { 
    if (children == null) { 
      return true;
    }
    return false;
  }
  
  /**
   * draw
   * Draws the boundary of the node
   * @param g, the Graphics class required to draw the boundary
   */
  public void draw(Graphics g) {
    g.drawRect((int)boundary.getX(), (int)boundary.getY(), (int)boundary.getWidth(), (int)boundary.getHeight());
  }
}