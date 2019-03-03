package alda.graph;
//Yelin Xu yexu9615

import java.util.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class MyNode<T> {
	public MyNode(T t) {
		data = t;
	}

	public boolean known = false;
	public T data;
	public HashMap<MyNode<T>, Integer> list = new HashMap<>();
	public LinkedList<T> visitedNodes = new LinkedList<>();

//	public List<MyNode<T>> visited = new ArrayList<>();
//	public List<MyNode<T>> unvisited = new ArrayList<>();
	public int hashCode() {
		return data.toString().hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof MyNode<?>) {
			MyNode<T> m = (MyNode<T>) o;
			if (data.equals(m.data))
				return true;
		}
		return false;
	}

}

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	int numberOfNodes;
	int numberOfEdges;
	public HashSet<MyNode<T>> hs = new HashSet<>();

	@Override
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	@Override
	public int getNumberOfEdges() {
		return numberOfEdges;
	}

	@Override
	public boolean add(T newNode) {
		MyNode<T> n = new MyNode<T>(newNode);
		if (hs.contains(n)) {
			return false;
		} else {
			hs.add(n);
			++numberOfNodes;
			return true;
		}
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		MyNode<T> n1 = null;
		MyNode<T> n2 = null;

		for (MyNode<T> mn : hs) {

			if (mn.data.equals(node2)) {
				// mn.list.put(n2, cost);
				n2 = mn;
				break;
			}
		}
		for (MyNode<T> mn : hs) {
			if (mn.data.equals(node1)) {
				n1 = mn;
				// mn.list.put(n2, cost);
				break;
			}
		}

		if (cost <= 0 || !hs.contains(n1) || !hs.contains(n2)) {
			return false;
		} else {
			n1.list.put(n2, cost);
			n2.list.put(n1, cost);
			// förbättring på koden?
//			for (MyNode<T> mn : hs) {
//				if (mn.equals(n1)) {
//					mn.list.put(n2, cost);
//					break;
//				}
//			}
//			for (MyNode<T> mn : hs) {
//				if (mn.equals(n2)) {
//					mn.list.put(n1, cost);
//					break;
//				} 
//			}

			return true;
		}
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		for (MyNode<T> mn : hs) {
			if (mn.equals(n1)) {
				return mn.list.containsKey(n2);
			}
		}
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		MyNode<T> n1 = null;
		MyNode<T> n2 = null;
		for (MyNode<T> mn : hs) {
			if (mn.data.equals(node2)) {
				n2 = mn;
				break;
			}
		}
		for (MyNode<T> mn : hs) {
			if (mn.data.equals(node1)) {
				n1 = mn;
				break;
			}
		}
		if (n1 == null || n2 == null) {
			return -1;
		}
		if (isConnected(node1, node2)) {
			return n1.list.get(n2);
		} else {
			return -1;
		}

	}

	List<T> depthList = new ArrayList<>();
	List<T> tempList = new ArrayList<>();

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		depthFirstSearch2(start, end, 0);
		return depthList;
	}

	public void depthFirstSearch2(T start, T end, int meansNothingJustDifferentSignature) {
		MyNode<T> s = new MyNode<T>(start);
		MyNode<T> e = new MyNode<T>(end);
		DFS(s, e, null);
	}

	private void DFS(MyNode<T> n, MyNode<T> end, List<MyNode<T>> l) {

		MyNode<T> temp = null;
		for (MyNode<T> mn : hs) {
			if (mn.data.equals(n.data)) {
				temp = mn;
				break;
			}
		}
		if (temp == null) {
			return;
		}
		Set<MyNode<T>> set = temp.list.keySet();

		tempList.add(n.data);
		if (n.data.equals(end.data)) {

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
			if (!nextNode.known) {
				nextNode.known = true;
				DFS(nextNode, end, null);
				nextNode.known = false;
			}
		}
		// TOD O pop the parentNode
		tempList.remove(tempList.size() - 1);
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		Queue<MyNode<T>> queue = new LinkedList<>();
		// find the corresponded node from the global hashSet
		// only in the hashSet we can find the right reference
		// and get access to other information. for example, visitedNodes
		MyNode<T> current = null;
		for (MyNode<T> mn : hs) {
			if (mn.data.equals(start)) {
				current = mn;
				break;
			}
		}
		queue.add(current);
		current.known = true;

		while (!queue.isEmpty() && !current.data.equals(end)) {
			MyNode<T> n = queue.poll();
			n.visitedNodes.add(n.data);

			for (MyNode<T> mn2 : n.list.keySet()) {
				if (!mn2.known) {
					mn2.visitedNodes.addAll(n.visitedNodes);
					queue.add(mn2);
					mn2.known = true;
				}
			}
			current = queue.peek();
		}
		if (current.data.equals(end)) {
			current.visitedNodes.add(current.data);// 加他自己
			return current.visitedNodes;
		}
		return null;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		int testNumber = 0;
		MyUndirectedGraph<T> newGraph = new MyUndirectedGraph<T>();

		MyNode<T> randomStart = null;
		for (MyNode<T> mn : hs) {
			randomStart = mn;
			break;
		}
		HashSet<MyNode<T>> marked = new HashSet<>();
		marked.add(randomStart);
		for (MyNode<T> mn : this.hs) {
			newGraph.add(mn.data);
		}

		// newGraph.hs=this.hs;
		newGraph.add(randomStart.data);
		while (hs.size() > marked.size()) {
			MyNode<T> min = null;
			MyNode<T> minsFar = null;
			for (MyNode<T> parent : marked) {
				for (MyNode<T> child : parent.list.keySet()) {
					if (!marked.contains(child) && min == null) {
						min = child;
						minsFar = parent;
					} else if (!marked.contains(child) && parent.list.get(child) < minsFar.list.get(min)) {
						min = child;
						minsFar = parent;
					}
				}
			}
			marked.add(min);
			int cost = minsFar.list.get(min);
			testNumber += cost;
			;
			newGraph.connect(min.data, minsFar.data, cost);
		}

		return newGraph;

	}

}
