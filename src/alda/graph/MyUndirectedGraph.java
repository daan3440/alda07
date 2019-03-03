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
import java.util.Queue;
import java.util.Set;

@SuppressWarnings("rawtypes")
class MyNode<T> implements Comparable{
	boolean known = false;

	public MyNode(T data){
		this.data = data;
	}
	public T data;
	public HashMap<MyNode<T>, Integer> neighbourMap = new HashMap<>();
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
		return Collections.min(this.neighbourMap.values()) - Collections.min(other.neighbourMap.values());
	}
}
class MyEdge<T> implements Comparable{
	private MyNode<T> start = null;
	private int vertex1;
	private int vertex2;
	private MyNode<T> goal = null;
	private int cost;

	public MyEdge(MyNode<T> n1, MyNode<T> n2, int cost){	
		this.start = n1;

		if(n1.data.toString().length() > 1) {
			//for-loop?
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
		if(cost <= 0)
			this.cost=0;
		else
			this.cost = cost;
	}

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
			if (start.equals(inner.start) && goal.equals(inner.goal)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public int compareTo(Object o) {
		MyEdge<T> other = null;
		if(o instanceof MyEdge<?>) {
			other = (MyEdge<T>) o;
		}
		return this.getCost() - other.getCost();

	}


}

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	private int nrNodes;
	private List<T> depthList = new LinkedList<>();
	private List<T> stack = new ArrayList<>(); 
	List<MyNode<T>> nodes = new ArrayList<>(); 
	List<MyEdge<T>> edges = new ArrayList<MyEdge<T>>();

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

		for (MyNode<T> cmpN : nodes) {
			if (cmpN.data.equals(node1)) {
				n1 = cmpN;
				break;
			}
		}
		for (MyNode<T> cmpN : nodes) {

			if (cmpN.data.equals(node2)) {
				n2 = cmpN;
				break;
			}
		}

		if (!nodes.contains(n1) || !nodes.contains(n2)|| cost <= 0) {
			return false;
		} else {
			n1.neighbourMap.put(n2, cost);
			n2.neighbourMap.put(n1, cost);
			MyEdge<T> addEdge = new MyEdge<T>(n1, n2, cost);
			if (!edges.contains(addEdge)) {
				edges.add(addEdge);
			}else {
				edges.remove(addEdge);	
				edges.add(addEdge);
			}
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
				return cmpNode.neighbourMap.containsKey(n2);
			}
		}
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		int cost = 0;
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
			cost = -1;
		} else {
			cost = n1.neighbourMap.get(n2);

		}
		return cost;
	}

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
		for (MyNode<T> nextNode : set) {
			if (!nextNode.known) {
				nextNode.known= true;
				depthFirstSearch(nextNode.data, goal.data);
				nextNode.known = false;
			}
		}
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

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {

		MyUndirectedGraph<T> minSpan = new MyUndirectedGraph<T>();
		List<MyEdge<T>> mstEdges = new ArrayList<MyEdge<T>>(); 
		
		DisjointSet nodeSet = new DisjointSet(Integer.MAX_VALUE/getNumberOfNodes());

		Collections.sort(edges);

		for (int i =0 ; i < edges.size() && mstEdges.size()<(getNumberOfNodes()-1); i++) {
			MyEdge<T> currentE = edges.get(i);
			int root1 = nodeSet.find(currentE.getVertex1());
			int root2 = nodeSet.find(currentE.getVertex2());
			if(root1 != root2){			
				mstEdges.add(currentE);	
				nodeSet.union(root1, root2);
			}
		}
		for (MyEdge<T> edge : mstEdges) {
			MyNode<T> n1 = edge.getStartNode();
			MyNode<T> n2 = edge.getGoalNode();
			if(!minSpan.nodes.contains(n1))
				minSpan.add(n1.data);
			if(!minSpan.nodes.contains(n2))
				minSpan.add(n2.data);
			if(!minSpan.edges.contains(edge)) {
				minSpan.connect(n1.data, n2.data, edge.getCost());
			}
		}

		return minSpan;
	}
//Data Structures and Algorithm Analysis in Java 3rd Edition - Weiss, kapitel 8.
	class DisjointSet{
		private int[] set;
		public DisjointSet(int numElements) {
			set = new int [numElements];
			for(int i = 0; i < set.length; i++){
				set[i] = -1;
			}
		}

		public void union(int root1, int root2) {
			if(set[root2] < set[root1]){	
				set[root1] = root2;		
			}
			else {
				if(set[root1] == set[root2]){
					set[root1]--;	
				}
				set[root2] = root1;	
			}
		}

		public int find(int x) {
			if(set[x] < 0){	
				return x;
			}
			int next = x;		
			while(set[next] > 0){
				next=set[next];
			}
			return next;
		}

	}

}
