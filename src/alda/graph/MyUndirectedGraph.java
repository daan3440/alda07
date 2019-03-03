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
	private MyNode<T> start = null;
	private MyNode<T> goal = null;
	int cost = 0;
	
	public MyEdge(MyNode<T> n1, MyNode<T> n2, int cost){	
		this.start = n1;
		this. goal = n2;
		this.cost = cost;
	}
	
	public HashMap<Integer, MyEdge<T>> startGoal = new HashMap<>();
	
	public MyNode<T> getStartNode(){
		return start;
	}
	public MyNode<T> getGoalNode(){
		return goal;
	}
	
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

	private int nrNodes;
	private int nrEdges;
	List<MyNode<T>> nodes = new ArrayList<>(); //bättre samling?
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
			if (cmpN.data.equals(node1)) {
				n1 = cmpN;
				// cmpN.neighbourMap.put(n2, cost);
				break;
			}
		}
		for (MyNode<T> cmpN : nodes) {

			if (cmpN.data.equals(node2)) {
				// cmpN.neighbourMap.put(n2, cost);
				n2 = cmpN;
				break;
			}
		}

		if (!nodes.contains(n1) || !nodes.contains(n2)|| cost <= 0) {
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
			nrEdges++;
//			System.out.println(getNumberOfNodes() + " Nodes");
//			System.out.println(getNumberOfEdges() + " Edges");
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
					try {
						cost = cmpNode.neighbourMap.get(n2);
					} catch (NullPointerException e) {
						
					}
				

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
	/**
	 * Disjoint set class, using union by rank and path compression
	 * Elements in the set are numbered starting at 0
	 * @author Mark Allen Weiss
	 */
//	class DisjointSet{
//		private MyNode<T>[] set;		//the disjoint set as an array
//
//		public MyNode<T>[] getSet(){		//mostly debugging method to print array
//			return set;
//		}
//
//		/**
//		 * Construct the disjoint sets object.
//		 * @param numElements the initial number of disjoint sets.
//		 */
//		public DisjointSet(int numElements) {		//constructor creates singleton sets
//			set = (MyNode<T>[]) new Object[numElements];
////			for(int i = 0; i < set.length; i++){		//initialize to -1 so the trees have nothing in them
////				set[i] = -1;
////			}
//			int i = 0;
//			for(MyNode<T> node : nodes){		//initialize to -1 so the trees have nothing in them
//				set[i] = node;
//				i++;
//			}
//		}
//
//		/**
//		 * Union two disjoint sets using the height heuristic.
//		 * For simplicity, we assume root1 and root2 are distinct
//		 * and represent set names.
//		 * @param root1 the root of set 1.
//		 * @param root2 the root of set 2.
//		 */
//		public void union(MyNode<T> root1, MyNode<T >root2) {
//			
//			if(set[root2].compareTo(roo1) < set[root1]){		// root2 is deeper
//				set[root1] = root2;		// Make root2 new root
//			}
//			else {
//				if(set[root1] == set[root2]){
//					set[root1]--;			// Update height if same
//				}
//				set[root2] = root1;		// Make root1 new root
//			}
//		}
//
//		/**
//		 * Perform a find with path compression.
//		 * Error checks omitted again for simplicity.
//		 * @param x the element being searched for.
//		 * @return the set containing x.
//		 */
//		public int find(int x) {
//			if(set[x] < 0){		//If tree is a root, return its index
//				return x;
//			}
//			int next = x;		
//			while(set[next] > 0){		//Loop until we find a root
//				next=set[next];
//			}
//			return next;
//		}
//		
//	}
	PriorityQueue<MyNode<T>> nodesT = new PriorityQueue<MyNode<T>>();
	PriorityQueue<MyEdge<T>> edges = new PriorityQueue<MyEdge<T>>();
	
	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		MyUndirectedGraph<T> minSpan = new MyUndirectedGraph<T>(); 
//		MyNode<T> start = nodesT.poll();
//		MyEdge<T> startEdge = edges.poll();
		List<MyNode<T>> mstSet = new ArrayList<MyNode<T>>(); //nånting 
		List<MyEdge<T>> mstEdges = new ArrayList<MyEdge<T>>(); //nånting 
		List<MyNode<T>> tmp = new ArrayList<MyNode<T>>(); //nånting
		//Möjlighet att börja med både Edge o Node.
		//Vi ska testa med Edge först!
//		DisjointSet nodeSet = new DisjointSet(getNumberOfNodes()+1);
//		Edges 
//			samling med noder
//			minEdges av alla  i samlingen.
//				samling med noder
//		System.out.println("PRe For");
		int check2 = edges.size();
		int check = nodesT.size();
		if (check > 0){
			System.out.println("nodesT: " + check + " edges: " + check2);
			
		}
		for (int i =0 ; i < edges.size() && mstEdges.size()<(getNumberOfNodes()-1); i++) {
//		for (int i = 0; i<10;i++){
//			System.out.println(" for loops");
			MyEdge<T> currentE = edges.poll();
			MyNode<T> root1 = currentE.getStartNode();
			MyNode<T> root2 = currentE.getGoalNode();
			tmp.clear();
//			if(root2 != null)
//				System.out.println("root1 NotNULL");
//		}
			
			if(!root1.equals(root2) && !(mstSet.contains(root1) && mstSet.contains(root2)) ) {
				tmp = mstSet;
				mstEdges.add(currentE);
				mstSet.add(root1);
				mstSet.add(root2);
//				if(mstSet.contains(root1)) {
//					System.out.println("root1 in mstSet");
//				}
//				if(!mstSet.contains(root2)) {
//				System.out.println("root2 in mstSet");
//
//				}
			}
			
		}
		System.out.println(mstEdges.size() + " mstEdges");
		for (MyEdge<T> edge : mstEdges) {
			MyNode<T> n1 = edge.getStartNode();
			MyNode<T> n2 = edge.getGoalNode();
			int cost = edge.cost;
			if(!minSpan.nodesT.contains(n1))
				minSpan.add(n1.data);
//			System.out.println(minSpan.getNumberOfNodes() + " nodes minSpan");
			if(!minSpan.nodesT.contains(n2))
				minSpan.add(n2.data);
//			System.out.println(minSpan.getNumberOfNodes() + " nodes minSpan 2");
			if(!minSpan.edges.contains(edge))
				minSpan.connect(n1.data, n2.data, cost);
//			System.out.println(minSpan.getNumberOfEdges() + " edges minSpan");
		}
	
		return minSpan;
	}

}
