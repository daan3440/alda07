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
	private int vertex1;
	private int vertex2;
	private MyNode<T> goal = null;
	private MyNode<T> startBack = null;
	private MyNode<T> goalStart = null;
	private int cost;
	
	public MyEdge(MyNode<T> n1, MyNode<T> n2, int cost){	
		this.start = n1;
		this.startBack = n2;
//		String s = n1.data.toString();
//		 for(char c : s.toCharArray()) {
//
//		 }
		if(n1.data.toString().length() > 1) {
			char c = n1.data.toString().charAt(0);
			char c1 = n1.data.toString().charAt(1);
			this.vertex1 = c + c1 - '0';
		}else {
			char c = n1.data.toString().charAt(0);
			this.vertex1 = c - '0';
		}
		if(n2.data.toString().length() > 1) {
			char c2 = n2.data.toString().charAt(0);
			char c3 = n2.data.toString().charAt(1);
			this.vertex2 = c2 + c3 - '0';
		}else {
			char c2 = n2.data.toString().charAt(0);
			
			this.vertex2 = c2 - '0';
		}
		this. goal = n2;
		this. goalStart = n1;
		if(cost <= 0)
			this.cost=0;
		else
			this.cost = cost;
	}
	
	public HashMap<Integer, MyEdge<T>> startGoal = new HashMap<>();
	
	public MyNode<T> getStartNode(){
		return start;
	}
	public int getVertex1(){
		return vertex1;
	}
	public int getVertex2(){
		return vertex2;
	}
	
	public int getCost(){
		return cost;
	}
	
	
	public MyNode<T> getGoalNode(){
		return goal;
	}
	@Override
	public int hashCode() {
		return (start.toString()+goal.toString()).hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof MyEdge<?>) {
			MyEdge<T> inner = (MyEdge<T>) other;
			if ((start.equals(inner.start) && goal.equals(inner.goal)) || (startBack.equals(inner.startBack) && goalStart.equals(inner.goalStart))) {
				return true;
		}
		}
		return false;
	}
//	@Override
//	public int compareTo(Object o) {
//		MyEdge<T> other = null;
//		if(o instanceof MyEdge<?>) {
//			other = (MyEdge<T>) o;
//		}
//		if (other == null)
//			return 1;
//		if (this.cost < other.cost)
//			return -1;
//		if (this.cost < other.cost)
//			return 1;
//		return 0;
		
	@Override
	public int compareTo(Object o) {				//Compare based on edge weight (for sorting)
		MyEdge<T> other = null;
		if(o instanceof MyEdge<?>) {
			other = (MyEdge<T>) o;
		}
		return this.getCost() - other.getCost();
	
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
		
		return edges.size();
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
			if (!edges.contains(addEdge)) {
				edges.add(addEdge);
				}else {
					edges.remove(addEdge);	
					edges.add(addEdge);
				}
//			System.out.println(getNumberOfNodes() + " Nodes");
//			System.out.println(getNumberOfEdges() + " Edges");
			// förbättring på koden?
			return true;
		}
	}

	@Override
	public boolean isConnected(T node1, T node2) {
MyNode<T> n1 = null;
		
		MyNode<T> n2 = null;
		for(MyNode<T> check :nodes) {
			if(check.data.equals(node1)) {
				n1 = check;
			} 
			if(check.data.equals(node2)) {
				n2 = check;
			}
		}
		for (MyNode<T> cmpNode : nodes) {
			if (cmpNode.equals(n1)) {
				//				return cmpNode.neighbourMap.containsKey(n2);
				return cmpNode.neighbourMap.containsKey(n2);
			}
		}
		return false;
	}
	/**
	 * Returnerar kostnaden för att ta sig mellan två noder.
	 * 
	 * @param node1
	 *            den ena noden.
	 * @param node2
	 *            den andra noden.
	 * @return kostnaden för att ta sig mellan noderna eller -1 om noderna inte
	 *         är kopplade.
	 */
	@Override
	public int getCost(T node1, T node2) {
		int cost = 0;
		//		System.out.println(node1 + " " + node2);
		MyNode<T> n1 = null;
		
		MyNode<T> n2 = null;
		for(MyNode<T> check :nodes) {
			if(check.data.equals(node1)) {
				n1 = check;
			} 
			if(check.data.equals(node2)) {
				n2 = check;
			}
		}
		
		if ((n1 == null || n2 == null) || !(isConnected(n1.data, n2.data))) {
//					System.out.println(node1 + " " + node2);
			cost = -1;
		} else {
			cost = n1.neighbourMap.get(n2);
//					System.out.println("cost: " + cost);
			
		}
		return cost;
	}


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

//	
	// DisjointSet class
	//
	// CONSTRUCTION: with int representing initial number of sets
	//
	// ******************PUBLIC OPERATIONS*********************
	// void union(root1, root2) --> Merge two sets
	// int find(x)              --> Return set containing x
	// ******************ERRORS********************************
	// No error checking is performed
	// http://users.cis.fiu.edu/~weiss/dsaajava3/code/DisjSets.java

	/**
	 * Disjoint set class, using union by rank and path compression
	 * Elements in the set are numbered starting at 0
	 * @author Mark Allen Weiss
	 */
	class DisjointSet{
		private int[] set;		//the disjoint set as an array

		public int[] getSet(){		//mostly debugging method to print array
			return set;
		}

		/**
		 * Construct the disjoint sets object.
		 * @param numElements the initial number of disjoint sets.
		 */
		public DisjointSet(int numElements) {		//constructor creates singleton sets
			set = new int [numElements];
			for(int i = 0; i < set.length; i++){		//initialize to -1 so the trees have nothing in them
				set[i] = -1;
			}
		}

		/**
		 * Union two disjoint sets using the height heuristic.
		 * For simplicity, we assume root1 and root2 are distinct
		 * and represent set names.
		 * @param root1 the root of set 1.
		 * @param root2 the root of set 2.
		 */
		public void union(int root1, int root2) {
			if(set[root2] < set[root1]){		// root2 is deeper
				set[root1] = root2;		// Make root2 new root
			}
			else {
				if(set[root1] == set[root2]){
					set[root1]--;			// Update height if same
				}
				set[root2] = root1;		// Make root1 new root
			}
		}

		/**
		 * Perform a find with path compression.
		 * Error checks omitted again for simplicity.
		 * @param x the element being searched for.
		 * @return the set containing x.
		 */
		public int find(int x) {
			System.out.println(x + " print x");
			if(set[x] < 0){		//If tree is a root, return its index
				return x;
			}
			int next = x;		
			while(set[next] > 0){		//Loop until we find a root
				next=set[next];
			}
			return next;
		}
		
	}
	PriorityQueue<MyNode<T>> nodesT = new PriorityQueue<MyNode<T>>();
//	PriorityQueue<MyEdge<T>> edges = new PriorityQueue<MyEdge<T>>();
	List<MyEdge<T>> edges = new ArrayList<MyEdge<T>>();
	
	@Override
	public UndirectedGraph<T> minimumSpanningTree() {

		MyUndirectedGraph<T> minSpan = new MyUndirectedGraph<T>();
		Set<MyNode<T>> mstSet = new HashSet<MyNode<T>>(); //nånting 
		List<MyEdge<T>> mstEdges = new ArrayList<MyEdge<T>>(); //nånting 
		Set<MyNode<T>> tmp = new HashSet<MyNode<T>>(); //nånting

		int check2 = edges.size();
		int check = nodes.size();
		if (check > 0){
			System.out.println("nodes: " + check + " edges: " + check2);
			
		}
		DisjointSet nodeSet = new DisjointSet(Integer.MAX_VALUE/getNumberOfNodes());
		
		Collections.sort(edges);
		
		for (int i =0 ; i < edges.size() && mstEdges.size()<(getNumberOfNodes()-1); i++) {
			MyEdge<T> currentE = edges.get(i);
			System.out.println(currentE.getCost() + " edges Cost");
			int root1 = nodeSet.find(currentE.getVertex1());
			int root2 = nodeSet.find(currentE.getVertex2());
//			MyNode<T> root1 = currentE.getStartNode();
//			MyNode<T> root2 = currentE.getGoalNode();
			tmp.clear();
//			if(root2 != null)
//				System.out.println("root1 NotNULL");
//		}
			
			if(root1 != root2){			//if roots are in different sets the DO NOT create a cycle
				mstEdges.add(currentE);		//add the edge to the graph
				nodeSet.union(root1, root2);	//union the sets
//				unionMessage=",\tUnion("+root1+", "+root2+") done\n";		//change what's printed if a union IS performed
			}
//			if(!(root1.equals(root2)) && !(mstSet.contains(root1) && mstSet.contains(root2)) ) {
//				tmp = mstSet;
//				mstEdges.add(currentE);
//				if(!mstSet.contains(root1)) {
//					mstSet.add(root1);
//					System.out.println("root1 in mstSet");
//				}
//				if(!mstSet.contains(root2)) {
//					mstSet.add(root2);
//				System.out.println("root2 in mstSet");
//				}
//			}
			
		}
		System.out.println(mstEdges.size() + " mstEdges");
		int tmpcost = 0;
		for (MyEdge<T> edge : mstEdges) {
			MyNode<T> n1 = edge.getStartNode();
			MyNode<T> n2 = edge.getGoalNode();
//			int cost = edge.cost;
			if(!minSpan.nodes.contains(n1))
				minSpan.add(n1.data);
			if(!minSpan.nodes.contains(n2))
				minSpan.add(n2.data);
			if(!minSpan.edges.contains(edge)) {
				minSpan.connect(n1.data, n2.data, edge.getCost());
//				tmpcost += cost;
//			System.out.println(tmpcost + " tmpCost");
			}
		}
	
		return minSpan;
	}

}
