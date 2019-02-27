package alda.graph;

import java.util.*;

class MyNode<T> {
	public MyNode(T t) {
		data = t;
	}

	public boolean known = false;
	public T data;
	public HashMap<MyNode<T>, Integer> list = new HashMap<>();

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

//	if(node1.equals("G")&&node2.equals("A")) {
//		System.out.println("王八蛋");
//
//		System.out.println(mn.equals(n1));
//		System.out.println(mn.list.containsKey(n2));
//	}
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
		// TODO pop the parentNode
		tempList.remove(tempList.size() - 1);
	}

	int step;

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
