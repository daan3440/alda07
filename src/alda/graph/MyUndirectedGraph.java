package alda.graph;
import java.util.ArrayList;
import java.util.Collection;
/**
 * ALDA07 - Grafer
 * Daniel Andersson - daan3440
 * @author Daniel Andersson - daan3440  
 **/
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

class MyNode<T>{
	boolean visited = false;

	public MyNode(T data) {
		this.data = data;
	}

	public T data;
	public HashMap<MyNode<T>, Integer> neighbourMap = new HashMap<>();
	//	public LinkedList<MyNode<T>> neighbourList = new LinkedList<>();

	//	public ArrayList<T> getNeighbours() {
	//		System.out.println("Neighbours!");
	//		ArrayList<T> sendList = (ArrayList<T>) neighbourMap.keySet().stream().collect(Collectors.toList());
	//		//			List<T> result = neighbourMap.keySet().stream().collect(Collectors.toList());
	//
	//		//	        sendList.addAll(result);
	//		if(sendList == null)
	//			System.out.println("NULL");
	//		return sendList;
	//	}

	public int hashCode() {
		return data.toString().hashCode();
	}

	public boolean equals(Object other) {
		if (other instanceof MyNode<?>) {
			MyNode<T> inner = (MyNode<T>) other;
			if (data.equals(inner.data))
				return true;
		}
		return false;
	}


}

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {
	//	private Map<T, Integer> nodes = new HashMap<T, Integer>();

	int nrNodes;
	int nrEdges;
	List<MyNode<T>> nodes = new ArrayList<>(); //bättre samling?

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
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		if(cost<0 && n1.equals(n2)) {
			n1.neighbourMap.put(n2, cost);
		}
		//		System.out.println(n1.data + " " + n2.data + "  n1 o n2");

		if (cost <= 0 || !nodes.contains(n1) || !nodes.contains(n2)) {
			return false;
		} else {
			for (MyNode<T> cmpNode : nodes) {
				if (cmpNode.equals(n1)) {
					//					System.out.println(" n1 found i connect");
					cmpNode.neighbourMap.put(n2, cost);
					//					System.out.println(cmpNode.neighbourMap.size() + " nMap Size");
					break;
				}
			}
			for (MyNode<T> cmpNode : nodes) {
				if (cmpNode.equals(n2)) {
					//					System.out.println(" n2 found i connect");
					cmpNode.neighbourMap.put(n1, cost);
					//					System.out.println(cmpNode.neighbourMap.size() + " nMap Size 2");
					break;
				}
			}
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

	//Depth first
	private List<T> depthList = new LinkedList<>();
	private List<T> tempList = new ArrayList<>();

	//	private List<T> visited = new LinkedList<T>();
	private Stack<T> stack = new Stack<T>();
	@Override
	public List<T> depthFirstSearch(T start, T end) {
		dfs(start, end);
		return depthList;
	}
	//	@Override
	//	public List<T> depthFirstSearch(T start, T end) {
	//		MyNode<T> s = new MyNode<T>(start);
	//		MyNode<T> endNode = new MyNode<T>(end);
	//		return dfsHasPath(start, end, visited);
	////		return visited;
	//	}
	//	
	//	private List<T> dfsHasPath(T start, T end, List<T> visit) {
	////		if(visit.contains(start)) {
	////			return false;
	////		}
	//		stack.push(start); //J, D
	////		System.out.println(stack.peek() + " stack peek");
	//		visit.add(start); //J, D
	////		System.out.println(visit.size()+ " visit Size");
	//		if(start.equals(end)) {
	//			System.out.println("Start = end");
	//			return stack;
	//		}
	//		MyNode<T> sNode = new MyNode<T>(start);
	//
	////		Set<MyNode<T>> pl = new HashSet<MyNode<T>>();
	////		pl = sNode.neighbourMap.keySet();
	////		if(pl == null) {
	////			
	////			System.out.println(" MULL");
	////		}
	////		for (MyNode<T> print : pl) {
	////			System.out.println(print + " PRINTLIST NB");
	////		}
	////		if(!visit.containsAll(sNode.neighbourMap.keySet())) {
	////			System.out.println(" J TOM");
	////			stack.pop();
	////			if(!stack.isEmpty())
	////				start= stack.peek();
	////			
	////		}else
	////		
	//		if (!visit.containsAll(sNode.neighbourMap.keySet())){
	//			System.out.println(" J barn utan visit");
	//			for (MyNode<T> cmpNode : sNode.neighbourMap.keySet()) {
	//				if(!visit.contains(cmpNode))
	//					dfsHasPath(cmpNode.data, end, visit);
	//			}
	//		}
	//		return stack;//dfs(start, end);;
	//	}

	private void dfs(T in, T exit) {
		MyNode<T> current= new MyNode<T>(in);
		MyNode<T> end = new MyNode<T>(exit);
		MyNode<T> tmp = null;

		for (MyNode<T> tmpNode : nodes) {
			if (tmpNode.data.equals(current.data)) {
				tmp = tmpNode;
				//				System.out.println(tmp + "");
				break;
			}
		}
		if (tmp == null) {
			return;
		}
		Set<MyNode<T>> set = tmp.neighbourMap.keySet();

		tempList.add(current.data);
		if (current.data.equals(end.data)) {
			if (depthList.isEmpty()) {
				depthList.addAll(tempList);
			} else {
				if (tempList.size() < depthList.size()) {
					depthList.clear();
					depthList.addAll(tempList);
				}
			}
			tempList.remove(tempList.size() - 1);
			return;
		}
		// if the end-node is not found in this round,go to next round
		for (MyNode<T> nextNode : set) {
			// TODO push the subNode
			if (!nextNode.visited) {
				nextNode.visited= true;
				dfs(nextNode.data, end.data);
				nextNode.visited = false;
			}
		}
		// TODO pop the parentNode
		tempList.remove(tempList.size() - 1);
	}
	LinkedList<MyNode<T>> queue = new LinkedList<MyNode<T>>(); 
	Set<MyNode<T>> visited = new HashSet<MyNode<T>>(); 
	List<T> path = new ArrayList<T>(); 

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		MyNode<T> s = new MyNode<T>(start);
		queue.addLast(s);
		path = bfs(start, end);
		//			LinkedList<T> bfsList = null;
		//			for(MyNode<T> adding : visited) {
		//				if (adding.data != null) {
		//					bfsList.add(adding.data);
		//				}
		//			}

		return path;
	}

	private List<T> bfs(T start, T end ) {
		MyNode<T> s = new MyNode<T>(start);
		MyNode<T> last = new MyNode<T>(end);
//		System.out.println(queue.size());
//		queue.pollFirst();
//		System.out.println(s.neighbourMap.keySet());

		queue.removeFirst();
		visited.add(s);
		System.out.println(path.size() + " Pree");
		path.add(s.data);
		System.out.println(path.size() + " Post");
		if(s.neighbourMap.keySet().isEmpty()) {
			System.out.println(path.size() + " add to Queue");
			queue.addAll(s.neighbourMap.keySet());
			System.out.println(path.size() + " Post add to Queue");
			}
		else if(!s.equals(last)) {
			path.remove(path.size()-1);
		}
		System.out.println(queue.size() + " Efter");
		if(!queue.isEmpty()) {
			System.out.println(queue.size() + " Rekursiv");
			bfs(queue.peekFirst().data, last.data);
		}
		
		return path;	



		//		MyNode<T> current = new MyNode<T>(start);
		//		MyNode<T> last = new MyNode<T>(end);
		//		//		”Set Node 1 as the start Node
		//		queue.add(current); 
		////		System.out.println(current+" current"); 
		//		while (queue.size() != 0 && !current.equals(last)) 
		//		{ 
		//
		//		//		Add this Node to the Queue
		//		//		Add this Node to the visited set
		//		current.visited =true;
		//		
		//		visited.add(current);
		//		if (visited != null)
		//			System.out.println(visited.size() + " visited"); 
		//			
		//		//		If this node is our goal node then return true, else add Node 2 and Node 3 to our Queue
		//
		//		//		Check Node 2 and if it isn’t add both Node 4 and Node 5 to our Queue. 
		//		//		Take the next node from our Queue which should be Node 3 and check that.
		//		
		//		//		If Node 3 isn’t our goal node add Node 6 and Node 7 to our Queue.
		//		//		Repeat until goal Node is found.”
		//
		//		// Mark the current node as visited and enqueue it 
		//
		//			// Dequeue a vertex from queue and print it 
		//			current = queue.poll(); 
		//			System.out.println(current+" current"); 
		//
		//			// Get all adjacent vertices of the dequeued vertex s 
		//			// If a adjacent has not been visited, then mark it 
		//			// visited and enqueue it 
		//			ArrayList<T> list = current.getNeighbours();
		//			for (T t : list) {
		//				System.out.println(t+ " toString");
		//			}
		//			Iterator<T> i = current.getNeighbours().listIterator(); 
		//			System.out.println(i+" i"); 
		//			if(i.hasNext()) {
		//				System.out.println(" hasNext"); 
		//				
		//			}
		//			while (i.hasNext()) 
		//			{ 
		//				System.out.println(" WHUKE"); 
		//				T n = i.next();
		//				MyNode<T> tmp = new MyNode<T>(n);
		//				if (!tmp.visited) 
		//				{ 
		//					tmp.visited = true; 
		//					queue.add(tmp); 
		//				} 
		//			} 
		//		} 

	}
	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
