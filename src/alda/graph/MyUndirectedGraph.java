package alda.graph;
/**
 * ALDA07 - Grafer
 * Daniel Andersson - daan3440
 * @author Daniel Andersson - daan3440  
 **/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class MyNode<T>{
	boolean known = false;

	public MyNode(T data) {
		this.data = data;
	}
	public T data;
	public HashMap<MyNode<T>, Integer> neighbourMap = new HashMap<>();
	public LinkedList<T> visitedNodes = new LinkedList<T>();

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
			n2.neighbourMap.put(n1, cost);
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

	//Depth first
	private List<T> depthList = new LinkedList<>();
	private List<T> stack = new ArrayList<>();

	//	private List<T> visited = new LinkedList<T>();
	//	private Stack<T> stack = new Stack<T>();
	//	@Override
	//	public List<T> depthFirstSearch(T start, T end) {
	//		dfs(start, end);
	//		return depthList;
	//	}	
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

	//	private void dfs(T in, T exit) {
	//		MyNode<T> current= new MyNode<T>(in);
	//		MyNode<T> end = new MyNode<T>(exit);
	//		MyNode<T> tmp = null;
	//
	//		for (MyNode<T> tmpNode : nodes) {
	//			if (tmpNode.data.equals(current.data)) {
	//				tmp = tmpNode;
	//				//				System.out.println(tmp + "");
	//				break;
	//			}
	//		}
	//		if (tmp == null) {
	//			return;
	//		}
	//		Set<MyNode<T>> set = tmp.neighbourMap.keySet();
	//
	//		tempList.add(current.data);
	//		if (current.data.equals(end.data)) {
	//			if (depthList.isEmpty()) {
	//				depthList.addAll(tempList);
	//			} else {
	//				if (tempList.size() < depthList.size()) {
	//					depthList.clear();
	//					depthList.addAll(tempList);
	//				}
	//			}
	//			tempList.remove(tempList.size() - 1);
	//			return;
	//		}
	//		// if the end-node is not found in this round,go to next round
	//		for (MyNode<T> nextNode : set) {
	//			// TODO push the subNode
	//			if (!nextNode.known) {
	//				nextNode.known= true;
	//				dfs(nextNode.data, end.data);
	//				nextNode.known = false;
	//			}
	//		}
	//		// TODO pop the parentNode
	//		tempList.remove(tempList.size() - 1);
	//	}
	LinkedList<MyNode<T>> queue = new LinkedList<MyNode<T>>(); 

	//	LinkedList<T> queue = new LinkedList<T>(); 
	Set<MyNode<T>> visited = new HashSet<MyNode<T>>(); 
	List<T> path = new ArrayList<T>(); 

	//	@Override
	//	public List<T>  breadthFirstSearch( T start, T end){
	////	Map<String,List<String>> adjacentWords
	//		Map<MyNode<T>,MyNode<T>> parentNode= new HashMap<MyNode<T>,MyNode<T>>( );
	//		LinkedList<String> q = new LinkedList<String>( );
	//		
	//		MyNode<T> s = new MyNode<T>(start);
	//		MyNode<T> last = new MyNode<T>(end);
	//		
	//		queue.addLast( start);
	//		while( !queue.isEmpty( ) )
	//		{
	//			MyNode<T> current = new MyNode<T>(queue.removeFirst());
	//			Set<MyNode<T>> adj = s.neighbourMap.keySet();
	//			if( adj != null ) {
	//				for( MyNode<T> adjNode : adj ) {
	//					if( parentNode.get( adjNode ) == null )
	//					{
	//						parentNode.put( adjNode, current );
	//						queue.addLast( adjNode.data );
	//					}
	//				}
	//			}
	//		}
	//		parentNode.put( s, null );
	//		return getChainFromPreviousMap( parentNode, s, last);
	//		}
	//// After the shortest path calculation has run, computes the List that
	//// contains the sequence of word changes to get from first to second.
	//// Returns null if there is no path.
	//		private List<T> getChainFromPreviousMap( Map<MyNode<T>, MyNode<T>> parentNode, MyNode<T> s, MyNode<T> last ){
	//			LinkedList<T> result = null;
	//		    if( parentNode.get( last ) != null )
	//		    {
	//		        result = new LinkedList<T>( );
	//		        for( MyNode<T> data = last; data != null; data = parentNode.get( data ) )
	//		            result.addFirst( data.data );
	//		}
	//		    return result;
	//		
	//}
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
	//	@Override
	//	public List<T> breadthFirstSearch(T start, T end) {
	//		MyNode<T> s = new MyNode<T>(start);
	//		//		queue.clear();
	//		queue.addLast(s);
	//		if( !bfs(start, end)) {
	//			path.clear();
	//		}
	//		//			LinkedList<T> bfsList = null;
	//		//			for(MyNode<T> adding : visited) {
	//		//				if (adding.data != null) {
	//		//					bfsList.add(adding.data);
	//		//				}
	//		//			}
	//		System.out.println(path.size() + " Path!");
	//		return path;
	//	}

	private boolean bfs(T start, T end ) { //J
		//queue J
		MyNode<T> s = null;// new MyNode<T>(start); //J
		MyNode<T> last = new MyNode<T>(end); //A

		for (MyNode<T> mn : nodes) {
			if (mn.data.equals(start)) {
				s = mn;
				break;
			}
		}
		//		System.out.println(queue.size());
		//		queue.pollFirst();
		//		System.out.println(s.neighbourMap.keySet());

		path.add(s.data); //J
		while(!queue.isEmpty()) {
			MyNode<T> cNode = queue.removeFirst();

			ArrayList<MyNode<T>>  list = new ArrayList<MyNode<T>> (cNode.neighbourMap.keySet());//.stream().collect(Collectors.toList()));

			System.out.println(list.size() + " BIG array");
			if (cNode.equals(last)) {
				return true;
			}
			if(!visited.contains(cNode)) {
				visited.add(s);
			}

			for (int i = 0; i < list.size(); i++ ) {
				queue.addLast(list.get(i));
				System.out.println(queue.size() + " Queue Size");
				//			if ((boolean) queue.peekFirst().data)
			}
			if(!queue.isEmpty()) {
				bfs(queue.peekFirst().data, end);
			}
		}
		return false;
	}
	//		private boolean bfs(T start, T end ) { //J
	//		System.out.println(path.size() + " Pree");
	//		System.out.println(path.size() + " Post");
	//		if(s.neighbourMap.keySet().isEmpty()) {
	//			System.out.println(path.size() + " add to Queue");
	//			queue.addAll(s.neighbourMap.keySet());
	//			System.out.println(path.size() + " Post add to Queue");
	//		}
	//		else if(!s.equals(last)) {
	//			path.remove(path.size()-1);
	//		}
	//		System.out.println(queue.size() + " Efter");
	//		if(!queue.isEmpty()) {
	//			System.out.println(queue.size() + " Rekursiv");
	//			bfs(queue.peekFirst().data, last.data);
	//		}
	//
	//		return path;	


	//
	//		//		MyNode<T> current = new MyNode<T>(start);
	//		//		MyNode<T> last = new MyNode<T>(end);
	//		//		//		”Set Node 1 as the start Node
	//		//		queue.add(current); 
	//		////		System.out.println(current+" current"); 
	//		//		while (queue.size() != 0 && !current.equals(last)) 
	//		//		{ 
	//		//
	//		//		//		Add this Node to the Queue
	//		//		//		Add this Node to the visited set
	//		//		current.visited =true;
	//		//		
	//		//		visited.add(current);
	//		//		if (visited != null)
	//		//			System.out.println(visited.size() + " visited"); 
	//		//			
	//		//		//		If this node is our goal node then return true, else add Node 2 and Node 3 to our Queue
	//		//
	//		//		//		Check Node 2 and if it isn’t add both Node 4 and Node 5 to our Queue. 
	//		//		//		Take the next node from our Queue which should be Node 3 and check that.
	//		//		
	//		//		//		If Node 3 isn’t our goal node add Node 6 and Node 7 to our Queue.
	//		//		//		Repeat until goal Node is found.”
	//		//
	//		//		// Mark the current node as visited and enqueue it 
	//		//
	//		//			// Dequeue a vertex from queue and print it 
	//		//			current = queue.poll(); 
	//		//			System.out.println(current+" current"); 
	//		//
	//		//			// Get all adjacent vertices of the dequeued vertex s 
	//		//			// If a adjacent has not been visited, then mark it 
	//		//			// visited and enqueue it 
	//		//			ArrayList<T> list = current.getNeighbours();
	//		//			for (T t : list) {
	//		//				System.out.println(t+ " toString");
	//		//			}
	//		//			Iterator<T> i = current.getNeighbours().listIterator(); 
	//		//			System.out.println(i+" i"); 
	//		//			if(i.hasNext()) {
	//		//				System.out.println(" hasNext"); 
	//		//				
	//		//			}
	//		//			while (i.hasNext()) 
	//		//			{ 
	//		//				System.out.println(" WHUKE"); 
	//		//				T n = i.next();
	//		//				MyNode<T> tmp = new MyNode<T>(n);
	//		//				if (!tmp.visited) 
	//		//				{ 
	//		//					tmp.visited = true; 
	//		//					queue.add(tmp); 
	//		//				} 
	//		//			} 
	//		//		} 
	//
	//	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
