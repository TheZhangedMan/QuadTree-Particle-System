/**
 * [Node].java
 * A node for a single linked list
 * @author Ethan Zhang
 * 2020/11/6
 */
class Node<T> {
  private T data;
  private Node<T> next;
  
  /**
   * Node constructor
   * @param data, the data carried by the node
   */
  public Node(T data) {
    this.data = data;
    this.next = next;
  }
  
  /**
   * Node constructor
   * @param data, the data carried by the node
   * @param next, the next node
   */
  public Node(T data, Node<T> next) {
    this.data = data;
    this.next = next;
  }
  
  /**
   * getNext
   * Returns the next node
   * @return Node<T>, the next node
   */
  public Node<T> getNext() {
    return this.next;
  }
  
  /**
   * setNext
   * Sets the next node to the given node
   * @param next, the new next node
   */
  public void setNext(Node<T> next) {
    this.next = next;
  }
  
  /**
   * getData
   * Returns the data held by the node
   * @return T, the data in the node
   */
  public T getData() {
    return this.data;
  }
  
  /**
   * setData
   * Sets the data to the given data
   * @param data, the new data
   */
  public void setData(T data) {
    this.data = data;
  }
}