package alda.graph;
/**
 * ALDA07 - Grafer
 * Daniel Andersson - daan3440
 * @author Daniel Andersson - daan3440  
 **/
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Collections;
import java.util.Set;

class MyNode<T> implements Comparable{
	boolean known = false;

	public MyNode(T data){
		this.data = data;
	}
	public T data;
	public HashMap<MyNode<T>, Integer> neighbourMap = new HashMap<>();
	public HashMap<Integer, HashMap<MyNode<T>, Integer>> costMap = new HashMap<>();
	public LinkedList<T> visitedNodes = new LinkedList<T>();
	
	@Override
	public int hashCode() {
		return data.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyNode<?>) {
			MyNode<T> inner = (MyNode<T>) other;
			if (data.equals(inner.data))
				return true;
		}
		return false;
	}
	@Override
	public int compareTo(Object o) {
		MyNode<T> other = null;
		if(o instanceof MyNode<?>) {
			other = (MyNode<T>) o;
		}
//		System.out.println(Collections.min(this.neighbourMap.values()));
		if (other == null)
			return 1;
		if ((Collections.min(this.neighbourMap.values()) < Collections.min(other.neighbourMap.values())))
			return -1;
		if ((Collections.min(this.neighbourMap.values()) > Collections.min(other.neighbourMap.values())))
			return 1;
		return 0;
	}


}
class MyEdge<T> implements Comparable{
	MyNode<T> start = null;
	MyNode<T> goal = null;
	int cost = 0;
	
	public MyEdge(MyNode<T> n1, MyNode<T> n2, int cost){	
		this.start = n1;
		this. goal = n2;
		this.cost = cost;
	}
	
	public HashMap<Integer, MyEdge<T>> startGoal = new HashMap<>();
	
	public int compareTo(Object o) {
		MyEdge<T> other = null;
		if(o instanceof MyEdge<?>) {
			other = (MyEdge<T>) o;
		}
		if (other == null)
			return 1;
		if (this.cost < other.cost)
			return -1;
		if (this.cost < other.cost)
			return 1;
		return 0;
		
	}
	
	
}

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	int nrNodes;
	int nrEdges;
	List<MyNode<T>> nodes = new ArrayList<>(); //bättre samling?
	PriorityQueue<MyNode<T>> nodesT = new PriorityQueue<MyNode<T>>();
	PriorityQueue<MyEdge<T>> edges = new PriorityQueue<MyEdge<T>>();
//	HashSet<MyNode<T>> nodesT = new HashSet<>(); //for spanning

	@Override
	public int getNumberOfNodes() {
		return nrNodes;
	}

	@Override
	public int getNumberOfEdges() {
		return nrEdges;
	}

	@Override
	public boolean add(T newNode) {
		MyNode<T> addedNode = new MyNode<T>(newNode);
		if (nodes.contains(addedNode)) {
			return false;
		} else {
			nodes.add(addedNode);
			++nrNodes;
			return true;
		}
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {

		MyNode<T> n1 = null;
		MyNode<T> n2 = null;

		//		nodes.forEach(cmpN -> {
		//			if (cmpN.data.equals(node2)) { 
		//				n2 = cmpN;
		//				}
		//						});
		//		nodes.forEach(cmpN -> {
		//			if (cmpN.data.equals(node1)) { 
		//				n1 = cmpN;
		//				}
		//		});




		for (MyNode<T> cmpN : nodes) {

			if (cmpN.data.equals(node2)) {
				// cmpN.neighbourMap.put(n2, cost);
				n2 = cmpN;
				break;
			}
		}
		for (MyNode<T> cmpN : nodes) {
			if (cmpN.data.equals(node1)) {
				n1 = cmpN;
				// cmpN.neighbourMap.put(n2, cost);
				break;
			}
		}

		if (cost <= 0 || !nodes.contains(n1) || !nodes.contains(n2)) {
			return false;
		} else {
			n1.neighbourMap.put(n2, cost);
//			n1.costMap.put(cost, n1.neighbourMap);
//			System.out.println(nodesT.size() + " preAdd NodesT");
			nodesT.add(n1);
			n2.neighbourMap.put(n1, cost);
//			n2.costMap.put(cost, n2.neighbourMap);
			nodesT.add(n2);
//			System.out.println(nodesT.size() + " postAdd NodesT");
			MyEdge<T> addEdge = new MyEdge<T>(n1, n2, cost);
			edges.add(addEdge);
			++nrEdges;
			// förbättring på koden?
			return true;
		}
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		for (MyNode<T> cmpNode : nodes) {
			if (cmpNode.equals(n1)) {

				//				return cmpNode.neighbourMap.containsKey(n2);
				return cmpNode.neighbourMap.containsKey(n2);
			}
		}
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		int cost = 0;
		//		System.out.println(node1 + " " + node2);
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		if (!nodes.contains(n1) || !nodes.contains(n2)) {
			cost = -1;
		} else {
			for (MyNode<T> cmpNode : nodes) {
				if (cmpNode.equals(n1) && n2 != null) {
					cost = cmpNode.neighbourMap.get(n2);
				}
			}
		}
		return cost;
	}

//	public MyNode<T> getMinCost() {
//		MyNode<T> min = null;
//		int cost = 0;
//		//		System.out.println(node1 + " " + node2);
//		MyNode<T> n1 = new MyNode<T>(node1);
//		MyNode<T> n2 = new MyNode<T>(node2);
//		if (!nodes.contains(n1) || !nodes.contains(n2)) {
//			cost = -1;
//		} else {
//			for (MyNode<T> cmpNode : nodes) {
//				if (cmpNode.equals(n1) && n2 != null) {
//					cost = cmpNode.neighbourMap.get(n2);
//				}
//			}
//		}
//		return min;
//	}

	//Depth first
	private List<T> depthList = new LinkedList<>();
	private List<T> stack = new ArrayList<>();

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		MyNode<T> current= new MyNode<T>(start);
		MyNode<T> goal = new MyNode<T>(end);
		MyNode<T> tmp = null;

		for (MyNode<T> cmpN : nodes) {
			if (cmpN.data.equals(current.data)) {
				tmp = cmpN;
				break;
			}
		}		
		if (tmp == null) {
			return stack;
		}
		Set<MyNode<T>> set = tmp.neighbourMap.keySet();
		stack.add(current.data);
		if (current.data.equals(goal.data) && (depthList.isEmpty() || stack.size() < depthList.size())) {
			depthList.clear();
			depthList.addAll(stack);
			stack.remove(stack.size() - 1);
			return depthList;
		}
		// if the end-node is not found in this round,go to next round
		for (MyNode<T> nextNode : set) {
			// TODO push the subNode
			if (!nextNode.known) {
				nextNode.known= true;
				depthFirstSearch(nextNode.data, goal.data);
				nextNode.known = false;
			}
		}
		// TODO pop the parentNode
		stack.remove(stack.size() - 1);
		return depthList;
	}
	LinkedList<MyNode<T>> queue = new LinkedList<MyNode<T>>(); 
	Set<MyNode<T>> visited = new HashSet<MyNode<T>>(); 
	List<T> path = new ArrayList<T>(); 
	
	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		Queue<MyNode<T>> queue = new LinkedList<>();
		MyNode<T> current = null;
		for (MyNode<T> cmpN : nodes) {
			if (cmpN.data.equals(start)) {
				current = cmpN;
				break;
			}
		}
		queue.add(current);
		current.known = true;

		while (!queue.isEmpty() && !current.data.equals(end)) {
			MyNode<T> n = queue.poll();		
			n.visitedNodes.add(n.data);

			for (MyNode<T> cmpN : n.neighbourMap.keySet()) {
				if (!cmpN.known) {
					cmpN.visitedNodes.addAll(n.visitedNodes);
					queue.add(cmpN);
					cmpN.known = true;
				}
			}
			current=queue.peek();			
		}
		if (current.data.equals(end)) {
			current.visitedNodes.add(current.data);// add self
			return current.visitedNodes;
		}
		return null;
	}

//	HashMap<MyNode<T>, >
	
	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		MyUndirectedGraph<T> minSpan = new MyUndirectedGraph<T>(); 
		MyNode<T> start = nodesT.poll();
		MyEdge<T> startEdge = edges.poll();
		List<MyNode<T>> mst = new LinkedList<MyNode<T>>(); //nånting 
		List<MyEdge<T>> mstEdges = new LinkedList<MyEdge<T>>(); //nånting 
		//Möjlighet att börja med både Edge o Node.
		//Vi ska testa med Edge först!
		
//		Edges 
//			samling med noder
//			minEdges av alla  i samlingen.
//				samling med noder
		mst.add(start);
		/**
		 * Start at any node in the graph
		Mark the starting node as reached
		*/
		//Lägg tilll samling
		
		/**
		Mark all the other nodes in the graph as unreached
		**/         
		//Vi behåller dem i nodes lr nodesT
		/**
		 * Right now, the Minimum cost Spanning Tree (MST) consists of the starting node
			
		We expand the MST with the procedure given below....
			
		Find an edge e with minimum cost in the graph that connects:
**/
		// start.neighbourMap(foundNode)
		 //node = Collections.min(start.neighbourMap.values()
		 //	!mstList.contains()
		 
	/**	A reached node x to an unreached node y         
		Add the edge e found in the previous step to the Minimum cost Spanning Tree
		Mark the unreached node y as reached

		Repeat the steps 2 and 3 until all nodes in the graph have become reached
		**/
		//nodesT.size == mst.size()
		
		// addAll i samlingen mst to Grafen minSpan
		//mst bör vara ne samling som är enkel att iterera över.
		
		/**
		 * 
		 */
		return minSpan;
	}

}
