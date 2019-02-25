package alda.graph;

import java.util.List;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	@Override
	public int getNumberOfNodes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfEdges() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean add(T newNode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCost(T node1, T node2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		// TODO Auto-generated method stub
		return null;
	}

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
