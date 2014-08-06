/**
 * @file Graph.java
 *
 * @author Lewis/Chase
 *
 *  An adjacency matrix implementation of a Graph
 */
package jss2;
import jss2.*;
import jss2.exceptions.*;
import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {
	protected final int DEFAULT_CAPACITY = 100;
	protected int numVertices;
	protected boolean[][] adjMatrix;
	protected T[] vertices;

	/**
	 * Constructor. Creates empty graph, initializes adjacency matrix
	 * and vertex array to default capacity
	 */
	public Graph(){
		numVertices = 0;
		this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
		this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);

	}

	public Graph(int vertices){
		numVertices = 0;
		this.adjMatrix = new boolean[vertices][vertices];
		this.vertices = (T[])(new Object[vertices]);
	}

	/**
	 * Adds an edge between vertices in the graph
	 *
	 * @param T
	 * vertex1 - the first vertex
	 *
	 * @param T
	 * vertex2 - the adjacent vertex to vertex1
	 */
	public void addEdge(T vertex1, T vertex2){
		addEdge(getIndex(vertex1), getIndex(vertex2));
	}

	/**
	 * Adds an edge based on the vertices's indices and updates
	 * the adjacency matrix accordingly
	 *
	 * @param int
	 * index1 - index of vertex1
	 *
	 * @param int
	 * index2 - index of vertex2
	 */
	public void addEdge(int index1, int index2){
		if(indexIsValid(index1) && indexIsValid(index2)){
			adjMatrix[index1][index2] = true;
			adjMatrix[index2][index1] = true;
		}
	}

	/**
	 * Removes an edge between vertices on the graph
	 *
	 * @param T
	 * vertex1 - the first vertex
	 *
	 * @param T
	 * vertex2 - the adjacent vertex to vertex1
	 */
	public void removeEdge(T vertex1, T vertex2) throws EmptyCollectionException{
		if(isEmpty())
			throw new EmptyCollectionException("Graph");

		removeEdge(getIndex(vertex1), getIndex(vertex2));
	}

	/**
	 * Removes an edge based on the vertice's inces and updates the
	 * adjacency matrix accordingly
	 *
	 * @param int
	 * index1 - index of vertex1
	 *
	 * @param int
	 * index2 - index of vertex2
	 */
	public void removeEdge(int index1, int index2){
		if(indexIsValid(index1) && indexIsValid(index2)){
			adjMatrix[index1][index2] = false;
			adjMatrix[index2][index1] = false;
		}
	}

/*	public boolean edgeExists(T vertex1, T vertex2){
		boolean edgeExists = false;
		if(indexIsValid(getIndex(vertex1)) || indexIsValid(getIndex(vertex2))){
			System.out.println("Edge error");
		}
		else{
			int index1 = getIndex(vertex1);
			int index2 = getIndex(vertex2);

			if(adjMatrix[index1][index2] && adjMatrix[index2][index2])
				edgeExists = true;
		}

		return edgeExists;
	}*/

	/**
	 * Adds a vertex to the graph
	 *
	 * @param T
	 * vertex - the vertex to be added
	 */
	public void addVertex(T vertex){
		if (numVertices == vertices.length)
			expandCapacity();

		vertices[numVertices] = vertex;
		for(int i=0; i<=numVertices; i++){
			adjMatrix[numVertices][i] = false;
			adjMatrix[i][numVertices] = false;
		}
		numVertices++;
	}

	/**
	 * Removes a vertex from the graph
	 *
	 * @param T
	 * vertex - the vertex to be removed
	 */
	public void removeVertex(T vertex){
		if(isEmpty())
			throw new EmptyCollectionException("Graph");

		for(int scan=0; scan<numVertices; scan++){

			if(vertex.equals(vertices[scan])){
				if(indexIsValid(scan)){
					numVertices--;

					//adjust appropriate vertices over
					for(int i=scan; i<numVertices; i++)
						vertices[i] = vertices[i+1];

					//adjust adjacency matrix values appropriately
					for(int i=scan; i<numVertices; i++)
						for(int j=0; j<=numVertices; j++){
							adjMatrix[i][j] = adjMatrix[i+1][j];
							adjMatrix[j][i] = adjMatrix[j][i+1];
						}
				}

				return;
			}
		}

	}

	/**
	 * Returns an iterator that performs a breadth-first search traversal starting
	 * at the given vertex
	 *
	 * @param T
	 * startVertex - the vertex to begin the search from
	 *
	 * @return Iterator<T>
	 * an iterator that performs a breadth-first search
	 */
	public Iterator<T> iteratorBFS(T startVertex){
		return iteratorBFS(getIndex(startVertex));
	}

	/**
	 * Returns an iterator that performs a breadth-first search traversal
	 * starting at the given index
	 *
	 * @param int
	 * startIndex - the index to begin the search from
	 *
	 * @return Iterator<T>
	 * an iterator that performs a breadth-first traversal
	 */
	private Iterator<T> iteratorBFS(int startIndex){
		Integer x;
		LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
		ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
		boolean visited[] = new boolean[numVertices];

		if(!indexIsValid(startIndex))
			return resultList.iterator();

		boolean[] visisted = new boolean[numVertices];
		for(int i=0; i < numVertices; i++)
			visited[i] = false;

		traversalQueue.enqueue(new Integer(startIndex));
		visited[startIndex] = true;

		while(!traversalQueue.isEmpty()){
			x = traversalQueue.dequeue();
			resultList.addToRear(vertices[x.intValue()]);

			for(int i=0; i < numVertices; i++){
				if(adjMatrix[x.intValue()][i] && !visited[i]){
					traversalQueue.enqueue(new Integer(i));
					visited[i] = true;
				}
			}
		}
		return resultList.iterator();
	}

	/**
	 * Indicates whether or not the graph has vertices
	 *
	 * @return boolean
	 * true if there are no vertices, false otherwise
	 */
	public boolean isEmpty(){
		return (numVertices==0);
	}

	/**
	 * Returns the vertices adjacent to a given vertex
	 *
	 * @param T
	 * vertex - the given vertex
	 *
	 * @return T[]
	 * An array of the adjacent vertex values
	 */
	public ArrayList<T> vertexNeighbours(T vertex){
		ArrayList<T> neighbours = new ArrayList<T>();
		int index = getIndex(vertex);

		for(int i=0; i<numVertices; i++){
			if(adjMatrix[index][i]){
				neighbours.add(vertices[i]);
			}
		}

		return neighbours;
	}

	/**
	 * Determines whether each and every vertex has an edge connected to
	 * one or more vertices in the entire graph
	 *
	 * @return boolean
	 * true if connected, false otherwise
	 */
	public boolean isConnected(){
		if(isEmpty())
			return false;

		Iterator<T> it = iteratorBFS(0);
		int count = 0;

		while(it.hasNext()){
			it.next();
			count++;
		}

		return (count == numVertices);
	}

	/**
	 * Determines the size of the graph
	 *
	 * @return int
	 * The number of vertices in the graph
	 */
	public int size(){
		return numVertices;
	}

	/**
	 * Gets the object at the given index of the graph
	 *
	 * @param int
	 * index - the given index
	 *
	 * @return T
	 * The object at the position in the graph indicated by the index
	 */
	public T at(int index){
		return vertices[index];
	}

	/**
	 * expands the capacity of the graph. Adjustements must be made to the
	 * adjacency matrix as well
	 */
	protected void expandCapacity(){
		T[] largerVertices = (T[])(new Object[vertices.length*2]);
		boolean[][] largerAdjMatrix = new boolean[vertices.length*2][vertices.length*2];

		for(int i=0; i<numVertices; i++){
			for(int j=0; j<numVertices; j++){
				largerAdjMatrix[i][j] = adjMatrix[i][j];
			}
			largerVertices[i] = vertices[i];
		}

		vertices = largerVertices;
		adjMatrix = largerAdjMatrix;
	}

	/**
	 * Gets the index position of the given vertex
	 *
	 * @param T
	 * vertex - the given object to be examined
	 *
	 * @return int
	 * the scanned index if found, -1 if not found
	 */
	public int getIndex(T vertex){
		for(int scan=0; scan < numVertices; scan++)
			if(vertices[scan].equals(vertex))
				return scan;
		return -1;
	}

	/**
	 * Checks whether or not vertex already exists in graph
	 *
	 * @param T
	 * vertex - the given vertex to be searched
	 *
	 * @return boolean
	 * true if it exists in graph, false otherwise
	 */
	public boolean contains(T vertex){
		int index = getIndex(vertex);
		if(index == -1)
			return false;
		else
			return true;
	}

	/**
	 * Returns truth value on whether or not the index is within the graph bounds
	 *
	 * @param int
	 * index - index to examin
	 *
	 * @return boolean
	 * true if index is within bounds, false otherwise
	 */
	public boolean indexIsValid(int index){
		return ((index < numVertices) && (index >= 0));
	}

	/**
	 * toString method
	 *
	 * @return String
	 * A formatted String representation of a Graph
	 */
	public String toString(){
		String result = "Adjacency Matrix:\n---------------\n";

	    if (numVertices == 0)
         return "Graph is empty";

        result += "index\n\t";

      for (int i = 0; i < numVertices; i++)
      {
         result += "" + i;
         if (i < 10)
            result += " ";
      }
      result += "\n\n";

      for (int i = 0; i < numVertices; i++)
      {
         result += "" + i + "\t";

         for (int j = 0; j < numVertices; j++)
         {
            if (adjMatrix[i][j])
               result += "1 ";
            else
               result += "0 ";
         }
         result += "\n";
      }

      result += "\n\nVertex Values";
      result += "\n-------------\n";
      result += "index\tvalue\tadjacent values\n\n";

      for (int i = 0; i < numVertices; i++)
      {
         result += "" + i + "\t";
         result += vertices[i].toString() + "\t\t";
         ArrayList<T> neighbours = vertexNeighbours(vertices[i]);
         for(int j = 0; j < neighbours.size(); j++){
         	if(neighbours.get(j)!=null){
         		result+=neighbours.get(j).toString() + " ";
         	}
         }
         result+="\n";
      }
      result += "\n";
      return result;
	}
}
