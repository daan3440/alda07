package alda.graph;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class MyTest {
	public static void main(String[] args) {
		new MyTest().testBreadthFirstSearchFromAToJ();
		new MyTest().own();
	}

	private void own() {
		createExampleGraph();

	}

	private static final String[] STANDARD_NODES = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

	private MyUndirectedGraph<String> graph = new MyUndirectedGraph<>();

	private void add(String... nodes) {
		for (String node : nodes) {
			 graph.add(node);
		}
	}

	private void connect(String node1, String node2, int cost) {
		graph.connect(node1, node2, cost);
		graph.getCost(node1, node2);
		 graph.getCost(node2, node1);
	}

	private void addExampleNodes() {
		add(STANDARD_NODES);
	}

	private void createExampleGraph() {
		addExampleNodes();

		connect("A", "A", 1);
		connect("A", "G", 3);
		connect("G", "B", 28);
		connect("B", "F", 5);
		connect("F", "F", 3);
		connect("F", "H", 1);
		connect("H", "D", 1);
		connect("H", "I", 3);
		connect("D", "I", 1);
		connect("B", "D", 2);
		connect("B", "C", 3);
		connect("C", "D", 5);
		connect("E", "C", 2);
		connect("E", "D", 2);
		connect("J", "D", 5);
	}

	private void testBreadthFirstSearch(String start, String end, int expectedathLength) {
		createExampleGraph();
		List<String> path = graph.breadthFirstSearch(start, end);
System.out.println(path);
		//testPath(start, end, path);
	}

	@Test
	public void testBreadthFirstSearchFromAToJ() {
//		MyNode<String> m=new MyNode<String>("B");
//		System.out.println(graph.hs.contains(m));
//		System.out.println(graph.isConnected("B","G"));
//		System.out.println(graph.isConnected("G","B"));
		testBreadthFirstSearch("A", "A", 5);

	}

	private void testDepthFirstSearch(String start, String end, int minimumPathLength) {
		createExampleGraph();
		List<String> path = graph.depthFirstSearch(start, end);

		System.out.println(path.size());
		//System.out.println(path);
	//	System.out.println(minimumPathLength);
		//assertTrue(path.size() <= graph.getNumberOfNodes());

		//testPath(start, end, path);
	}

	public void testDepthFirstSearchFromAToJ() {
		testDepthFirstSearch("A", "A", 5);
	}

//	public void testDepthFirstSearchFromJToA() {
//		testDepthFirstSearch("J", "A", 5);
//	}
//
//	public void testDepthFirstSearchFromFToE() {
//		testDepthFirstSearch("F", "E", 3);
//	}

	public void testDepthFirstSearchToSameNode() {
		for (String node : STANDARD_NODES) {
			graph = new MyUndirectedGraph<>();
			testDepthFirstSearch(node, node, 1);
		}
	}

}