package alda.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.HashMap;


public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	
	HashMap<T,Node<T>> graph = new HashMap<> ();
	Node<T> first ;
	Node<T> second;
	
	

	public class Node<T>{
		public T node;
		HashMap<T,Integer> adjacencyList = new HashMap<>();
		
		
		public Node(T n){
			this.node = n; 	
			adjacencyList = new HashMap<>();
		}
	}
		
	@Override
	public int getNumberOfNodes() {
		int node= 0;
		if(graph.get(node)!= null) {
			node++;	
		}return node;
	}

	@Override
	public int getNumberOfEdges() {
		return 0 ;
	}

	@Override
	public boolean add(T newNode) {
		if(graph.get(newNode) == null){
			Node<T> newN = new Node<T>(newNode);
			graph.put(newN.node,newN);
			return true;
		}
		return false;
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if (graph.get(node1) != null && graph.get(node2) != null && cost > 0) {
			if (isConnected(node1, node2)) {
				System.out.println(node1 + "node 1 is ");
				first.adjacencyList.put(node1, cost);
				second.adjacencyList.put(node2, cost);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		if(graph.get(node1)!=null && graph.get(node2) != null ) {
			first = graph.get(node1);
			second =graph.get(node2);
			return true;
		}else if(graph.get(node1)==null && graph.get(node2) == null){
		    return false;
		}else if(graph.get(node1) != null && graph.get(node2) == null) {
			return false;
		}else {
			return false;
		}
	}

	@Override
	public int getCost(T node1, T node2) {
		if(graph.containsKey(node1) && graph.containsKey(node2)) {
			int cost = first.adjacencyList.get(node2);
			return cost;
		}
		return -1;
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		//behöve veta om vertext har redan markera eller inte. 
		// if not connect. fail. 
		//hitta unmark node.
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		Queue<String> queue = new LinkedList<String>();
		
		return null;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		// TODO Auto-generated method stub
		// om inte connect fungera inte. 
		return null;
	}

}

