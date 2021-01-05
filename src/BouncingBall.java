import java.awt.Rectangle;

/**
 * [BouncingBall.java]
 * A bouncing ball that bounces off surfaces and other bouncing balls
 * @author Ethan Zhang
 * 2020/11/6
 */
class BouncingBall {
  private double x, y;
  private double velocityX, velocityY;
  private Rectangle hitbox;
  private BouncingBall recentCollision;
  
  /**
   * BouncingBall constructor
   * @param x, the x coordinate of the bouncing ball
   * @param y, the y coordinate of the bouncing ball
   * @param velocityX, the displacement of the x coordinate every refresh
   * @param velocityY, the displacement of the y coordinate every refresh
   */
  BouncingBall(double x, double y, double velocityX, double velocityY) {
    this.x = x;
    this.y = y;
    this.velocityX = velocityX;
    this.velocityY = velocityY;
    hitbox = new Rectangle((int)x, (int)y, 30, 30);
  }
  
  /**
   * getX
   * Returns the x coordinate
   * @return double, the x coordinate
   */
  public double getX() {
    return x;
  }
  
  /**
   * getY
   * Returns the y coordinate
   * @return double, the y coordinate
   */
  public double getY() {
    return y;
  }
  
  /**
   * getVelocityX
   * Returns the x velocity
   * @return double, the x velocity
   */
  public double getVelocityX() {
    return velocityX;
  }
  
  /**
   * getVelocityY
   * Returns the y velocity
   * @return double, the y velocity
   */
  public double getVelocityY() {
    return velocityY;
  }
  
  /**
   * getHitbox
   * Returns the hitbox of the ball
   * @return Rectangle, the hitbox of the ball
   */
  public Rectangle getHitbox() {
    return hitbox;
  }
  
  /**
   * getRecentCollision
   * Returns the most recent bouncing ball collided with
   * @return BouncingBall, the most recent collided bouncing ball
   */
  public BouncingBall getRecentCollision() {
    return recentCollision;
  }
  
  /**
   * setX
   * Sets the x coordinate to the given value
   * @param x, the new x coordinate of the ball as a double
   */
  public void setX(double x) {
    this.x = x;
  }
  
  /**
   * setY
   * Sets the y coordinate to the given value
   * @param y, the new y coordinate of the ball as a double
   */
  public void setY(double y) {
    this.y = y;
  }
  
  /**
   * setVelocityX
   * Sets the x velocity to the given value
   * @param velocityX, the new x velocity as a double
   */
  public void setVelocityX(double velocityX) {
    this.velocityX = velocityX;
  }
  
  /**
   * setVelocityY
   * Sets the y velocity to the given value
   * @param velocityY, the new y velocity as a double
   */
  public void setVelocityY(double velocityY) {
    this.velocityY = velocityY;
  }
  
  /**
   * setRecentCollision
   * Sets the most recent collided bouncing ball
   * @param recentCollision, the most recent collided bouncing ball as a BouncingBall
   */
  public void setRecentCollision(BouncingBall recentCollision) {
    this.recentCollision = recentCollision;
  }
  
  /**
   * updatePosition
   * Adds the respective velocities to the x and y coordinate of the BouncingBall and hitbox 
   */
  public void updatePosition() {
    setX(getX() + getVelocityX());
    setY(getY() + getVelocityY());
    hitbox.setLocation((int)getX(), (int)getY());
  }
  
  /**
   * collide
   * Changes velocities based on the properties of the other collided ball
   * @param sourceX, the x coordinate of the other ball
   * @param sourceY, the y coordinate of the other ball
   * @param sourceVX, the x velocity of the other ball
   * @param sourceVY, the y velocity of the other ball
   */
  public void collide(double sourceX, double sourceY, double sourceVX, double sourceVY) {
    if (sourceX < getX()) {
      setVelocityX(sourceVX);
    } else {
      setVelocityX(-sourceVX);
    }
    
    if (sourceY < getY()) {
      setVelocityY(sourceVY);
    } else {
      setVelocityY(-sourceVY);
    }
  }
  
  /**
   * checkBorderCollision
   * Checks if the ball should bounce off the border of the simulation
   */
  public void checkBorderCollision() {
    if (getX() < 0 || getX() > 1000) {
      setVelocityX(-getVelocityX());
    } else if (getY() < 0 || getY() > 1000) {
      setVelocityY(-getVelocityY());
    }
  }
}