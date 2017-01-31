import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Class for the RoadMap
 * @author Gary Chiu
 * @course CS2210
 * @assignment Assignment 5
 * @date December 6, 2016
 */
public class RoadMap {

	// declare instance variables
	private int initMoney, WIDTH = 0, LENGTH = 0, TOLL, GAIN;
	private Graph roadGraph;
	private int START, END;
	private Stack<Node> pathStack = new Stack<Node>();
	private String filename;

	/**
	 * Constructor for the RoadMap. Processes the input textfile and creates roadmap
	 * @param String inputfile 
	 * @throws MapException if input file does not exist
	 */
	public RoadMap(String string) throws MapException {

		// declare variables
		filename = string;
		String[] mapArray;

		try {

			// read text file, wrap in BufferedReader
			FileReader fileReader = new FileReader(filename);
			BufferedReader br = new BufferedReader(fileReader);

			String line = null;
			line = br.readLine();
			line = br.readLine();

			// read configuration data
			int i = 1;
			while (i < 8) {
				switch (i) {
				case 1:
					START = Integer.parseInt(line);
					break;
				case 2:
					END = Integer.parseInt(line);
					break;
				case 3:
					initMoney = Integer.parseInt(line);
					break;
				case 4:
					WIDTH = Integer.parseInt(line);
					break;
				case 5:
					LENGTH = Integer.parseInt(line);
					break;
				case 6:
					TOLL = Integer.parseInt(line);
					break;
				case 7:
					GAIN = Integer.parseInt(line);
					break;

				}
				line = br.readLine();
				i++;
			}

			// use an array to store map information, initialize with blank strings
			mapArray = new String[WIDTH * 2];
			for (int k = 0; k < WIDTH * 2; k++) {
				mapArray[k] = "";
			}

			// store the text file's data into the array
			i = 0;
			while (line != null) {
				mapArray[i] = line;
				i++;
				line = br.readLine();
			}
			br.close();

			// read the roadmap configuration data, process
			roadGraph = new Graph(WIDTH * LENGTH);
			int hor = 0, vert = 1, cn = 0, type = 0;

			while (mapArray[hor] != "") {
				// i is used to traverse string horizontally, processing columns of the text file
				i = 0;
				while (i < mapArray[hor].length()) {
					// process horizontal edges
					try {
						if (mapArray[hor].charAt(i + 1) != 'X') {
							// get the edge's type
							if (mapArray[hor].charAt(i + 1) == 'F') {
								type = 0;
							} else {
								if (mapArray[hor].charAt(i + 1) == 'C') {
									type = -1;
								} else {
									if (mapArray[hor].charAt(i + 1) == 'T') {
										type = 1;
									}
								}
							}
							// create the edge in the graph
							roadGraph.insertEdge(roadGraph.getNode(cn), roadGraph.getNode(cn + 1), type);
						}
					} catch (StringIndexOutOfBoundsException e) {
						//
					} catch (GraphException e) {
						//
					}

					// process vertical edges
					if (mapArray[vert] != "") {
						try {
							if (mapArray[vert].charAt(i) != 'X') {
								// get the edge's type
								if (mapArray[vert].charAt(i) == 'F') {
									type = 0;
								} else {
									if (mapArray[vert].charAt(i) == 'C') {
										type = -1;
									} else {
										if (mapArray[vert].charAt(i) == 'T') {
											type = 1;
										}
									}
								}
								roadGraph.insertEdge(roadGraph.getNode(cn), roadGraph.getNode(cn + WIDTH), type);
							}
						} catch (StringIndexOutOfBoundsException e) {
							//
						} catch (GraphException e) {
							//
						}
					}
					cn++; // increases to next node#
					i += 2; // also skips to next appropriate column
				}
				// skip to next row in the map data
				hor += 2;
				vert += 2;
			}

		} catch (Exception e) {
			throw new MapException(e);
		}

	}

	/**
	 * Getter for the graph representation of the map
	 * @return Graph of the map
	 */
	public Graph getGraph() {
		return roadGraph;
	}

	/**
	 * Getter for the destination node's name
	 * @return int name of the destination node
	 */
	public int getDestinationNode() {
		return END;
	}

	/**
	 * Getter for the starting node's name
	 * @return int name of the starting node
	 */
	public int getStartingNode() {
		return START;
	}

	/**
	 * Getter for the initial budget 
	 * @return int initMoney
	 */
	public int getInitialMoney() {
		return this.initMoney;
	}

	/**
	 * Method to find the path from a starting node to the destination. 
	 * @param start: starting node's name
	 * @param destination: destination node's name
	 * @param initialMoney: initial budget amount
	 * @return Iterator containing the nodes that lead to the solution, if a solution is found. Returns null otherwise. 
	 */
	public Iterator findPath(int start, int destination, int initialMoney) {

		// find the corresponding Nodes in the graph
		Node u = getNode(start);
		Node e = getNode(destination);

		// call helper method to store the solution Nodes in the stack
		getPath(u, e, initialMoney);

		// return an Iterator of the stack if the stack is not empty (solution exists)
		if (pathStack.iterator().hasNext()) {
			return pathStack.iterator();
		} else {
			return null;
		}
	}

	/**
	 * Helper method used in the constructor to create the edge in the graph
	 * @param int name of the target node
	 * @return Node if the target node is in the graph, null otherwise. 
	 */
	private Node getNode(int name) {
		Graph graph = getGraph();
		Node ret;
		try {
			ret = graph.getNode(name);
		} catch (GraphException e) {
			return null;
		}
		return ret;
	}

	/**
	 * Helper method used by the getPath() method to get the edges incident to a node
	 * @param Node u 
	 * @return Iterator containing the edges incident to the provided Node
	 * @throws GraphException if node is not in the graph
	 */
	private Iterator<Edge> getEdgesIncident(Node u) throws GraphException {
		Graph graph = getGraph();
		Iterator<Edge> ret;
		try {
			ret = graph.incidentEdges(u);
		} catch (GraphException e) {
			return null;
		}
		return ret;
	}

	/**
	 * Helper method used by the findPath() method to recursively search for a solution to the map. 
	 * Method stores the solution nodes in a stack (global variable) if a path exists.
	 * @param Node u: starting node
	 * @param Node e: destination node
	 * @param budget amount
	 * @return True if a path could be found. False otherwise. 
	 */
	private boolean getPath(Node u, Node e, int budget) {

		// mark the current node as visited, push node to the stack
		u.setMark(true);
		pathStack.push(u);

		// base case: if current node is the destination, return true
		if (u.getName() == e.getName()) {
			return true;
		} else {
			try {
				// get the edges incident to the current node
				Iterator<Edge> itr;
				itr = getEdgesIncident(u);

				// process the edges to find a solution
				while (itr.hasNext()) {

					Edge next = itr.next();
					int edgeType = next.getType();

					// determine which end-point the next node is located
					Node v;
					if (u.getName() == next.secondEndpoint().getName()) {
						v = next.firstEndpoint();
					} else {
						v = next.secondEndpoint();
					}

					// check if the next node has been visited or not
					if (v.getMark() == false) {
						
						// visit the node if budget allows, charge budget accordingly
						switch (edgeType){
							case 0:
								// recursively go to next node
								if (getPath(v, e, budget) == true) {
									return true;
								}
								break;
							
							case -1:
								// recursively go to next node, having gained money from compensation road
								if (getPath(v, e, budget-GAIN) == true) {
									return true;
								}
							
							case 1: 
								if (budget < TOLL){
									// cannot afford to go to this node
									break;
								} else {
									// travel to next node, pay to use toll road
									if (getPath(v, e, budget-TOLL) == true) {
										return true;
									}
								}
						}
					}

				}
				// unmark before backtracking, pop node from solution stack
				u.setMark(false);
				Node r = pathStack.pop();
				return false;

			} catch (GraphException ex) {
				System.out.println("Error: unable to process the information.");
			} 

			return false;
		}
	}

}
