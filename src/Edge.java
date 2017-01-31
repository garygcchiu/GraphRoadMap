/**
 * Class for the Edges in the graph
 * @author Gary Chiu
 * @course CS2210
 * @assignment Assignment 5
 * @date December 6, 2016
 */
public class Edge {

	// declare instance variables
	private Node firstEP;
	private Node secondEP;
	private int type;
	private String label;
	
	/**
	 * Constructor for the Edge objects
	 * @param first end-point Node u
	 * @param second end-point Node v
	 * @param int type of the edge
	 */
	public Edge(Node u, Node v, int type){
		this.firstEP = u;
		this.secondEP = v;
		this.type = type;
		this.label = "";
	}
	
	/**
	 * Getter for the first end-point of the Edge
	 * @return Edge object's first end-point Node
	 */
	public Node firstEndpoint(){
		return this.firstEP;
	}
	
	/**
	 * Getter for the second end-point of the Edge
	 * @return Edge object's second end-point Node
	 */
	public Node secondEndpoint(){
		return this.secondEP;
	}
	
	/**
	 * Getter for the type of the Edge
	 * @return Edge object's int type
	 */
	public int getType(){
		return this.type;
	}
	
	/**
	 * Setter for the Edge's label
	 * @param String label for the Edge
	 */
	public void setLabel(String label){
		this.label = label;
	}
	
	/**
	 * Getter for the Edge's label
	 * @return Edge's String label
	 */
	public String getLabel(){
		return this.label;
	}
	
}
