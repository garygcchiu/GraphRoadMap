/**
 * Class for the Nodes in the graph
 * @author Gary Chiu
 * @course CS2210
 * @assignment Assignment 5
 * @date December 6, 2016
 */
public class Node {

	// declare instance variables
	private int name;
	private boolean mark;
	
	/**
	 * Constructor for the Node objects. By default, Nodes are marked as false
	 * @param int name of the Node
	 */
	public Node(int name) {
		this.name = name;
		this.mark = false;
	}

	/**
	 * Setter for the Node's mark
	 * @param boolean mark
	 */
	public void setMark(boolean mark){
		this.mark = mark;
	}
	
	/**
	 * Getter for the Node's mark
	 * @return boolean mark of the Node
	 */
	public boolean getMark(){
		return this.mark;
	}
	
	/**
	 * Getter for the name of the Node
	 * @return int name of Node
	 */
	public int getName() {
		return this.name;
	}

}
