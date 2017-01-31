import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class for the undirected Graph, implemented using an adjacency matrix
 * @author Gary Chiu
 * @course CS2210
 * @assignment Assignment 5
 * @date December 6, 2016
 */
public class Graph {

	// declare instance variables
	private Node[] nodeList;
	private Edge[][] adjMatrix;
	private int length;
	
	/**
	 * Constructor for the Graph, creates the node list and adjacency matrix
	 * @param int n number of nodes in the graph
	 */
	public Graph(int n) {
		// create adjacency matrix of size n*n, nodelist of size n
		adjMatrix = new Edge[n][n];
		nodeList = new Node[n];
		length = n;
		
		// initialize n nodes into the nodeList
		for (int i=0; i<n; i++){
			nodeList[i] = new Node(i); 
		}
		
		// initialize the adjacency matrix with null objects
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				adjMatrix[i][j] = null;
			}
		}
	}
	
	/**
	 * Getter for the Node of a specified name
	 * @param int name of the target Node
	 * @return Node object with the target name
	 * @throws GraphException if no node with the name exists
	 */
	public Node getNode(int name) throws GraphException {
		int index = indexVertex(name);
		if (index == length){
			throw new GraphException(new Exception());
		} else {
			return nodeList[index];
		}
	}

	/**
	 * Method to add an Edge connecting Node u and Node v, with type edgeType
	 * @param Node u
	 * @param Node v
	 * @param int edgeType
	 * @throws GraphException if either u or v does not exist, or there already exists an Edge connecting u and v
	 */
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
		int row = u.getName();
		int col = v.getName();
		
		// check if nodes exist
		if (indexVertex(u.getName()) == length || indexVertex(v.getName()) == length){
			throw new GraphException(new Exception());
		} else { 
			// insert into matrix: inserted to symmetric position as well since graph is undirected
			adjMatrix[row][col] = new Edge(u, v, edgeType);
			adjMatrix[col][row] = new Edge(u, v, edgeType); 
		}
	}

	/**
	 * Method to get an Iterator containing the Edges incident to the Node u.
	 * Stores the Node's row in the adjacency matrix in an ArrayList, returns an Iterator of the ArrayList
	 * @param Node u
	 * @return Iterator containing the Edges incident to the Node u. Null if there are no incident Edges
	 * @throws GraphException 
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		
		// create ArrayList and variables
		ArrayList<Edge> arrList = new ArrayList<Edge>();
		int counter = 0;
		int row = u.getName();
		
		try {
			// copy row of adjacency matrix to the ArrayList 
			for (int i=0; i<length; i++){
				if (adjMatrix[row][i] != null){
					arrList.add(adjMatrix[row][i]);
					counter++;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e){
			throw new GraphException(e);
		}
		
		// check if there were any incident edges, return 
		if (counter == 0){
			return null;
		} else {
			return arrList.iterator();
		}

	}
	
	/**
	 * Getter for the Edge connecting Node u and Node v
	 * @param Node u
	 * @param Node v
	 * @return Edge connecting Node u and Node v
	 * @throws GraphException if no Edge exists between u and v
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		// check if nodes exist
		if (indexVertex(u.getName()) == length || indexVertex(v.getName()) == length){
			throw new GraphException(new Exception());
		} else {
			// check if edge exists
			if (adjMatrix[u.getName()][v.getName()] == null){
				throw new GraphException(new Exception());
			} else {
				return adjMatrix[u.getName()][v.getName()];
			}
		}
	}

	/**
	 * Method to determine if Node u and Node v are adjacent
	 * @param Node u
	 * @param Node v
	 * @return True if u and v are adjacent, False otherwise.
	 * @throws GraphException
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		try {
			if (adjMatrix[u.getName()][v.getName()] == null){
				return false;
			} else {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e){
			throw new GraphException(new Exception());
		}
	}
	
	/**
	 * Helper method used by the getNode() method to get the index of the Node in the nodeList
	 * @param int name of Node
	 * @return int index of the Node in the nodeList
	 */
	private int indexVertex(int name){
		int i=0;
		while (i < nodeList.length && nodeList[i].getName() != name){
			i++;
		}
		return i;
	}

}
