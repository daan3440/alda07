package alda.graph;

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
	HashSet<MyNode<T>> hs = new HashSet<>();

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
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		if (cost <= 0 || !hs.contains(n1) || !hs.contains(n2)) {

			return false;
		} else {

			// förbättring på koden?
			for (MyNode<T> mn : hs) {
				if (mn.equals(n1)) {
					mn.list.put(n2, cost);
					break;
				}
			}
			for (MyNode<T> mn : hs) {
				if (mn.equals(n2)) {
					mn.list.put(n1, cost);
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
		for (MyNode<T> mn : hs) {
			if (mn.equals(n1)) {
				return mn.list.containsKey(n2);
			}
		}
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		MyNode<T> n1 = new MyNode<T>(node1);
		MyNode<T> n2 = new MyNode<T>(node2);
		if (!hs.contains(n1) || !hs.contains(n2)) {
			return -1;
		} else {
			for (MyNode<T> mn : hs) {
				if (mn.equals(n1)) {
					return mn.list.get(n2);
				}
			}
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
			// TODO push the subNode
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

		return myBFS(start, end);
	}

	public List<T> myBFS(T start, T end) {
		HashMap<MyNode<T>, Boolean> checkVisit = new HashMap<>();
		for (MyNode<T> mn : hs) {
			checkVisit.put(mn, false);
		}
		Queue<MyNode<T>> queue = new LinkedList<>();
		MyNode<T> s = new MyNode<T>(start);
		for (MyNode<T> mn : hs) {
			if (mn.data.equals(s.data)) {
				queue.add(mn);
				checkVisit.put(mn, true);
				break;
			}
		}

		if (start.equals(end)) {
			List<T> breathList = new ArrayList<>();

			breathList.add(start);
			return breathList;
		}
		while (!queue.isEmpty()) {
			checkVisit.put(queue.peek(), true);
			MyNode<T> n = queue.peek();
			queue.poll();
			if (n.data.equals(end)) {
				n.visitedNodes.add(n.data);// 加他自己
				for (MyNode<T> mn3 : hs) {
					if (mn3.equals(n)) {
						mn3.visitedNodes.add(n.data);// 加他自己
						return mn3.visitedNodes;
					}
				}
			}
			MyNode<T> temp = null;
			for (MyNode<T> mn : hs) {
				if (mn.data.equals(n.data)) {
					temp = mn;
					mn.visitedNodes.add(mn.data);
					break;
				}
			}
			for (MyNode<T> mn2 : temp.list.keySet()) {
				if (!checkVisit.get(mn2)) {				
					for (MyNode<T> mn3 : hs) {
						if (mn3.equals(mn2)) {
							mn3.visitedNodes.addAll(temp.visitedNodes);
							break;
						}
					}
					queue.add(mn2);
					checkVisit.put(mn2, true);
				}
			}
		}
		return null;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
